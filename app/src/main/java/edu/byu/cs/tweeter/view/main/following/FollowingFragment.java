package edu.byu.cs.tweeter.view.main.following;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.ViewUserRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.ViewUserResponse;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;
import edu.byu.cs.tweeter.presenter.ViewUserPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetFollowingTask;
import edu.byu.cs.tweeter.view.asyncTasks.ViewUserTask;
import edu.byu.cs.tweeter.view.main.ViewUserActivity;
import edu.byu.cs.tweeter.view.util.ImageUtils;

/**
 * The fragment that displays on the 'Following' tab.
 */
public class FollowingFragment extends Fragment implements FollowingPresenter.View,
        ViewUserTask.Observer, ViewUserPresenter.View {

    private static final String LOG_TAG = "FollowingFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private User currentUser;
    private AuthToken authToken;
    private FollowingPresenter followingPresenter;
    private ViewUserPresenter viewUserPresenter;

    private FollowingRecyclerViewAdapter followingRecyclerViewAdapter;

    /**
     * Creates an instance of the fragment and places the user and auth token in an arguments
     * bundle assigned to the fragment.
     *
     * @param user the logged in user.
     * @param authToken the auth token for this user's session.
     * @return the fragment.
     */
    public static FollowingFragment newInstance(User user, AuthToken authToken) {
        FollowingFragment fragment = new FollowingFragment();

        Bundle args = new Bundle(2);
        args.putSerializable(USER_KEY, user);
        args.putSerializable(AUTH_TOKEN_KEY, authToken);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);

        currentUser = (User) getArguments().getSerializable(USER_KEY);
        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        followingPresenter = new FollowingPresenter(this);
        viewUserPresenter = new ViewUserPresenter(this);

        RecyclerView followingRecyclerView = view.findViewById(R.id.followingRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        followingRecyclerView.setLayoutManager(layoutManager);

        followingRecyclerViewAdapter = new FollowingRecyclerViewAdapter();
        followingRecyclerView.setAdapter(followingRecyclerViewAdapter);

        followingRecyclerView.addOnScrollListener(new FollowRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }

    @Override
    public void userRetrieved(ViewUserResponse viewUserResponse) {
        Intent intent = new Intent(getActivity(), ViewUserActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
        System.out.println(viewUserResponse.getUser().getUsername());
        intent.putExtra(ViewUserActivity.VIEWED_USER_KEY, viewUserResponse.getUser());
        intent.putExtra(ViewUserActivity.LOGGED_IN_USER_KEY, currentUser);
        intent.putExtra(ViewUserActivity.AUTH_TOKEN_KEY, authToken);
        startActivity(intent);
    }

    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();

    }

    /**
     * The ViewHolder for the RecyclerView that displays the Following data.
     */
    private class FollowingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private User user;
        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;

        /**
         * Creates an instance and sets an OnClickListener for the user's row.
         *
         * @param itemView the view on which the user will be displayed.
         */
        FollowingHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.userImage);
            userAlias = itemView.findViewById(R.id.userAlias);
            userName = itemView.findViewById(R.id.userName);

            itemView.setOnClickListener(this);
        }

        /**
         * Binds the user's data to the view.
         *
         * @param user the user.
         */
        void bindUser(User user) {
            this.user = user;
            userImage.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));
            userAlias.setText(user.getUsername());
            userName.setText(user.getName());
        }

        @Override
        public void onClick(View view) {
            viewUser(user.getUsername());
        }

    }

    /**
     * The adapter for the RecyclerView that displays the Following data.
     */
    private class FollowingRecyclerViewAdapter extends RecyclerView.Adapter<FollowingHolder> implements GetFollowingTask.Observer {

        private final List<User> users = new ArrayList<>();

        private edu.byu.cs.tweeter.model.domain.User lastFollowee;

        private boolean hasMorePages;
        private boolean isLoading = false;

        /**
         * Creates an instance and loads the first page of following data.
         */
        FollowingRecyclerViewAdapter() {
            loadMoreItems();
        }

        /**
         * Adds new users to the list from which the RecyclerView retrieves the users it displays
         * and notifies the RecyclerView that items have been added.
         *
         * @param newUsers the users to add.
         */
        void addItems(List<User> newUsers) {
            int startInsertPosition = users.size();
            users.addAll(newUsers);
            this.notifyItemRangeInserted(startInsertPosition, newUsers.size());
        }

        /**
         * Adds a single user to the list from which the RecyclerView retrieves the users it
         * displays and notifies the RecyclerView that an item has been added.
         *
         * @param user the user to add.
         */
        void addItem(User user) {
            users.add(user);
            this.notifyItemInserted(users.size() - 1);
        }

        /**
         * Removes a user from the list from which the RecyclerView retrieves the users it displays
         * and notifies the RecyclerView that an item has been removed.
         *
         * @param user the user to remove.
         */
        void removeItem(User user) {
            int position = users.indexOf(user);
            users.remove(position);
            this.notifyItemRemoved(position);
        }

        /**
         *  Creates a view holder for a followee to be displayed in the RecyclerView or for a message
         *  indicating that new rows are being loaded if we are waiting for rows to load.
         *
         * @param parent the parent view.
         * @param viewType the type of the view (ignored in the current implementation).
         * @return the view holder.
         */
        @NonNull
        @Override
        public FollowingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FollowingFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.user_row, parent, false);
            }

            return new FollowingHolder(view);
        }

        /**
         * Binds the followee at the specified position unless we are currently loading new data. If
         * we are loading new data, the display at that position will be the data loading footer.
         *
         * @param followingHolder the ViewHolder to which the followee should be bound.
         * @param position the position (in the list of followees) that contains the followee to be
         *                 bound.
         */
        @Override
        public void onBindViewHolder(@NonNull FollowingHolder followingHolder, int position) {
            if(!isLoading) {
                followingHolder.bindUser(users.get(position));
            }
        }

        /**
         * Returns the current number of followees available for display.
         * @return the number of followees available for display.
         */
        @Override
        public int getItemCount() {
            return users.size();
        }

        /**
         * Returns the type of the view that should be displayed for the item currently at the
         * specified position.
         *
         * @param position the position of the items whose view type is to be returned.
         * @return the view type.
         */
        @Override
        public int getItemViewType(int position) {
            return (position == users.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        /**
         * Causes the Adapter to display a loading footer and make a request to get more following
         * data.
         */
        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            GetFollowingTask getFollowingTask = new GetFollowingTask(followingPresenter, this);
            FollowingRequest request = new FollowingRequest(currentUser, PAGE_SIZE, lastFollowee);
            getFollowingTask.execute(request);
        }

        /**
         * A callback indicating more following data has been received. Loads the new followees
         * and removes the loading footer.
         *
         * @param followingResponse the asynchronous response to the request to load more items.
         */
        @Override
        public void followeesRetrieved(FollowingResponse followingResponse) {
            List<User> followees = followingResponse.getFollowees();

            lastFollowee = (followees.size() > 0) ? followees.get(followees.size() -1) : null;
            hasMorePages = followingResponse.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            followingRecyclerViewAdapter.addItems(followees);
        }

        /**
         * A callback indicating that an exception was thrown by the presenter.
         *
         * @param exception the exception.
         */
        @Override
        public void handleException(Exception exception) {
            Log.e(LOG_TAG, exception.getMessage(), exception);
            removeLoadingFooter();
            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * Adds a dummy user to the list of users so the RecyclerView will display a view (the
         * loading footer view) at the bottom of the list.
         */
        private void addLoadingFooter() {
            addItem(new User("Dummy", "User", ""));
        }

        /**
         * Removes the dummy user from the list of users so the RecyclerView will stop displaying
         * the loading footer at the bottom of the list.
         */
        private void removeLoadingFooter() {
            removeItem(users.get(users.size() - 1));
        }
    }

    /**
     * A scroll listener that detects when the user has scrolled to the bottom of the currently
     * available data.
     */
    private class FollowRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        /**
         * Creates a new instance.
         *
         * @param layoutManager the layout manager being used by the RecyclerView.
         */
        FollowRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        /**
         * Determines whether the user has scrolled to the bottom of the currently available data
         * in the RecyclerView and asks the adapter to load more data if the last load request
         * indicated that there was more data to load.
         *
         * @param recyclerView the RecyclerView.
         * @param dx the amount of horizontal scroll.
         * @param dy the amount of vertical scroll.
         */
        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!followingRecyclerViewAdapter.isLoading && followingRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    followingRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }
    private void viewUser(String alias) {
        ViewUserRequest viewUserRequest = new ViewUserRequest(alias);
        ViewUserTask viewUserTask = new ViewUserTask(viewUserPresenter, this);
        viewUserTask.execute(viewUserRequest);
    }
}