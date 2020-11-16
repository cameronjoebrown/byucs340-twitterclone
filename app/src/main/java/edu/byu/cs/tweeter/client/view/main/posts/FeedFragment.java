package edu.byu.cs.tweeter.client.view.main.posts;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.client.model.domain.AuthToken;
import edu.byu.cs.tweeter.client.model.domain.Status;
import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.presenter.FeedPresenter;
import edu.byu.cs.tweeter.client.view.asyncTasks.GetFeedTask;
import edu.byu.cs.tweeter.client.view.util.ImageUtils;
import edu.byu.cs.tweeter.model.service.request.FeedStoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedStoryResponse;

public class FeedFragment extends Fragment implements FeedPresenter.View {
    private static final String LOG_TAG = "FeedFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private User user;
    private AuthToken authToken;
    private FeedPresenter presenter;

    private FeedRecyclerViewAdapter feedRecyclerViewAdapter;

    /**
     * Creates an instance of the fragment and places the user and auth token in an arguments
     * bundle assigned to the fragment.
     *
     * @param user the logged in user.
     * @param authToken the auth token for this user's session.
     * @return the fragment.
     */
    public static FeedFragment newInstance(User user, AuthToken authToken) {
        FeedFragment fragment = new FeedFragment();

        Bundle args = new Bundle(2);
        args.putSerializable(USER_KEY, user);
        args.putSerializable(AUTH_TOKEN_KEY, authToken);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        user = (User) getArguments().getSerializable(USER_KEY);
        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        presenter = new FeedPresenter(this);

        RecyclerView feedRecyclerView = view.findViewById(R.id.feedRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        feedRecyclerView.setLayoutManager(layoutManager);

        feedRecyclerViewAdapter = new FeedRecyclerViewAdapter();
        feedRecyclerView.setAdapter(feedRecyclerViewAdapter);

        feedRecyclerView.addOnScrollListener(new FeedRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }

    /**
     * The ViewHolder for the RecyclerView that displays the Feed data.
     */
    private class FeedHolder extends RecyclerView.ViewHolder {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;
        private final TextView statusText;
        private final TextView timeStamp;

        /**
         * Creates an instance and sets an OnClickListener for the status's row.
         *
         * @param itemView the view on which the status will be displayed.
         */
        FeedHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.userImage);
            userAlias = itemView.findViewById(R.id.userAlias);
            userName = itemView.findViewById(R.id.userName);
            statusText = itemView.findViewById(R.id.statusText);
            timeStamp = itemView.findViewById(R.id.timeStamp);

            itemView.setOnClickListener(view -> {

            });
        }

        /**
         * Binds the status's data to the view.
         *
         * @param status the status.
         */
        void bindStatus(Status status) {
            userImage.setImageDrawable(ImageUtils.drawableFromByteArray(status.getUser().getImageBytes()));
            userAlias.setText(status.getUser().getUsername());
            userName.setText(status.getUser().getName());


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            String formatDateTime = status.getTimeStamp().format(formatter);
            timeStamp.setText(formatDateTime);

            String text = status.getStatusText();

            // Initialize a new SpannableString instance
            SpannableStringBuilder ss = new SpannableStringBuilder(text);

            // Initialize a new ClickableSpan to display red background
            Pattern p = Pattern.compile("[#@][a-zA-Z0-9]+");
            Matcher m = p.matcher(text); //get matcher, applying the pattern to caption string
            while (m.find()) { // Find each match in turn
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(@NotNull View textView) {
                        //Clicked word
                        Toast.makeText(getContext(), "Can't find this user", Toast.LENGTH_LONG).show();
                    }
                };
                ss.setSpan(clickableSpan, m.start(), m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            // Display the spannable text to TextView
            statusText.setText(ss);

            // Specify the TextView movement method
            statusText.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    /**
     * The adapter for the RecyclerView that displays the Feed data.
     */
    private class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedHolder> implements GetFeedTask.Observer {

        private final List<Status> statuses = new ArrayList<>();

        private edu.byu.cs.tweeter.client.model.domain.Status lastStatus;

        private boolean hasMorePages;
        private boolean isLoading = false;

        /**
         * Creates an instance and loads the first page of feed data.
         */
        FeedRecyclerViewAdapter() {
            loadMoreItems();
        }

        /**
         * Adds new statuses to the list from which the RecyclerView retrieves the statuses it displays
         * and notifies the RecyclerView that items have been added.
         *
         * @param newStatuses the users to add.
         */
        void addItems(List<Status> newStatuses) {
            int startInsertPosition = statuses.size();
            statuses.addAll(newStatuses);
            this.notifyItemRangeInserted(startInsertPosition, newStatuses.size());
        }

        /**
         * Adds a single status to the list from which the RecyclerView retrieves the statuses it
         * displays and notifies the RecyclerView that an item has been added.
         *
         * @param status the user to add.
         */
        void addItem(Status status) {
            statuses.add(status);
            this.notifyItemInserted(statuses.size() - 1);
        }

        /**
         * Removes a status from the list from which the RecyclerView retrieves the statuses it displays
         * and notifies the RecyclerView that an item has been removed.
         *
         * @param status the user to remove.
         */
        void removeItem(Status status) {
            int position = statuses.indexOf(status);
            statuses.remove(position);
            this.notifyItemRemoved(position);
        }

        /**
         *  Creates a view holder for a status to be displayed in the RecyclerView or for a message
         *  indicating that new rows are being loaded if we are waiting for rows to load.
         *
         * @param parent the parent view.
         * @param viewType the type of the view (ignored in the current implementation).
         * @return the view holder.
         */
        @NonNull
        @Override
        public FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FeedFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.status_row, parent, false);
            }

            return new FeedHolder(view);
        }

        /**
         * Binds the status at the specified position unless we are currently loading new data. If
         * we are loading new data, the display at that position will be the data loading footer.
         *
         * @param feedHolder the ViewHolder to which the status should be bound.
         * @param position the position (in the list of statuses) that contains the status to be
         *                 bound.
         */
        @Override
        public void onBindViewHolder(@NonNull FeedHolder feedHolder, int position) {
            if(!isLoading) {
                feedHolder.bindStatus(statuses.get(position));
            }
        }

        /**
         * Returns the current number of statuses available for display.
         * @return the number of statuses available for display.
         */
        @Override
        public int getItemCount() {
            return statuses.size();
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
            return (position == statuses.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        /**
         * Causes the Adapter to display a loading footer and make a request to get more feed
         * data.
         */
        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            GetFeedTask getFeedTask = new GetFeedTask(presenter, this);
            FeedStoryRequest request = new FeedStoryRequest(user, PAGE_SIZE, lastStatus);
            getFeedTask.execute(request);
        }

        /**
         * A callback indicating more feed data has been received. Loads the new statuses
         * and removes the loading footer.
         *
         * @param feedResponse the asynchronous response to the request to load more items.
         */
        @Override
        public void feedRetrieved(FeedStoryResponse feedResponse) {
            List<Status> statuses = feedResponse.getStatuses();

            lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() -1) : null;
            hasMorePages = feedResponse.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            feedRecyclerViewAdapter.addItems(statuses);
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
         * Adds a dummy status to the list of users so the RecyclerView will display a view (the
         * loading footer view) at the bottom of the list.
         */
        private void addLoadingFooter() {
            LocalDateTime date = LocalDateTime.now();
            addItem(new Status("Dummy Status", user, date));
        }

        /**
         * Removes the dummy status from the list of statuses so the RecyclerView will stop displaying
         * the loading footer at the bottom of the list.
         */
        private void removeLoadingFooter() {
            removeItem(statuses.get(statuses.size() - 1));
        }
    }

    /**
     * A scroll listener that detects when the user has scrolled to the bottom of the currently
     * available data.
     */
    private class FeedRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        /**
         * Creates a new instance.
         *
         * @param layoutManager the layout manager being used by the RecyclerView.
         */
        FeedRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
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

            if (!feedRecyclerViewAdapter.isLoading && feedRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    feedRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }
}
