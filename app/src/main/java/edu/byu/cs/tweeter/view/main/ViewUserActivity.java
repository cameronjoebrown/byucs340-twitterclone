package edu.byu.cs.tweeter.view.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.presenter.ViewUserPresenter;
import edu.byu.cs.tweeter.view.ViewUserSectionsPagerAdapter;
import edu.byu.cs.tweeter.view.asyncTasks.FollowTask;
import edu.byu.cs.tweeter.view.asyncTasks.UnfollowTask;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class ViewUserActivity extends AppCompatActivity implements FollowTask.Observer, UnfollowTask.Observer,
            ViewUserPresenter.View {

    private static final String LOG_TAG = "ViewUserActivity";

    public static final String VIEWED_USER_KEY = "ViewedUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    public static final String LOGGED_IN_USER_KEY = "LoggedInUser";

    private User viewedUser;
    private User loggedInUser;
    private AuthToken authToken;

    private ViewUserPresenter presenter;
    Button followButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        presenter = new ViewUserPresenter(this);

        viewedUser = (User) getIntent().getSerializableExtra(VIEWED_USER_KEY);
        loggedInUser = (User) getIntent().getSerializableExtra(LOGGED_IN_USER_KEY);
        if(viewedUser == null) {
            throw new RuntimeException("User not passed to activity");
        }

        authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);

        ViewUserSectionsPagerAdapter viewUserSectionsPagerAdapter =
                new ViewUserSectionsPagerAdapter(this, getSupportFragmentManager(), viewedUser, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(viewUserSectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        followButton = findViewById(R.id.followButton);

        Boolean areFollowing = getIntent().getExtras().getBoolean("areFollowing");

        if(areFollowing) {
            followButton.setBackgroundColor(Color.BLUE);
            followButton.setText(getResources().getString(R.string.unfollow));
        } else {
            followButton.setBackgroundColor(Color.WHITE);
            followButton.setText(getResources().getString(R.string.follow));
        }

        followButton.setOnClickListener(v -> {
            if(followButton.getText().equals("Follow")) {
                follow(viewedUser, loggedInUser);
            }
            else if(followButton.getText().equals("Unfollow")) {
                unfollow(viewedUser, loggedInUser);
            }
        });

        TextView userName = findViewById(R.id.userName);
        userName.setText(viewedUser.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(viewedUser.getUsername());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(viewedUser.getImageBytes()));

        TextView followeeCount = findViewById(R.id.followeeCount);
        followeeCount.setText("Following: " + "20");

        TextView followerCount = findViewById(R.id.followerCount);
        followerCount.setText("Followers: " + "20");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        if (menu.getItemId() == android.R.id.home) {
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(menu);
    }

    private void follow(User followee, User follower) {
        FollowUnfollowRequest request = new FollowUnfollowRequest(followee, follower);
        FollowTask followTask = new FollowTask(presenter, this);
        followTask.execute(request);
    }

    private void unfollow(User followee, User follower) {
        FollowUnfollowRequest request = new FollowUnfollowRequest(followee, follower);
        UnfollowTask unfollowTask = new UnfollowTask(presenter, this);
        unfollowTask.execute(request);
    }


    @Override
    public void followSuccessful(Response response) {
        // TODO: Change follower count
        followButton.setBackgroundColor(Color.BLUE);
        followButton.setText(getResources().getString(R.string.unfollow));
    }

    @Override
    public void unfollowSuccessful(Response response) {
        // TODO: Change follower count
        followButton.setBackgroundColor(Color.WHITE);
        followButton.setText(getResources().getString(R.string.follow));
    }

    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(this, "Exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
