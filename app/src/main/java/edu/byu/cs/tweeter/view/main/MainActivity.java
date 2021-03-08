package edu.byu.cs.tweeter.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.NumFollowsRequest;
import edu.byu.cs.tweeter.model.service.response.NumFollowsResponse;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.presenter.FollowerPresenter;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;
import edu.byu.cs.tweeter.presenter.LogoutPresenter;
import edu.byu.cs.tweeter.presenter.Presenter;
import edu.byu.cs.tweeter.view.asyncTasks.LogoutTask;
import edu.byu.cs.tweeter.view.asyncTasks.NumFolloweesTask;
import edu.byu.cs.tweeter.view.asyncTasks.NumFollowersTask;
import edu.byu.cs.tweeter.view.main.login.LoginRegisterActivity;
import edu.byu.cs.tweeter.view.main.posts.PostStatusFragment;
import edu.byu.cs.tweeter.view.util.ImageUtils;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class MainActivity extends AppCompatActivity implements Presenter.View, LogoutTask.Observer,
        NumFolloweesTask.Observer, NumFollowersTask.Observer {

    private static final String LOG_TAG = "MainActivity";

    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private User currentUser;
    private AuthToken authToken;

    private LogoutPresenter logoutPresenter;
    private FollowerPresenter followerPresenter;
    private FollowingPresenter followingPresenter;

    TextView followeeCount;
    TextView followerCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Iconify.with(new FontAwesomeModule());

        logoutPresenter = new LogoutPresenter(this);
        followerPresenter = new FollowerPresenter(this);
        followingPresenter = new FollowingPresenter(this);

        currentUser = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        getFolloweeCount(currentUser);
        getFollowerCount(currentUser);

        if(currentUser == null) {
            throw new RuntimeException("User not passed to activity");
        }

        authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), currentUser, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);

        // Set Icon for Logout Button
        Button logoutButton = findViewById(R.id.app_bar_logout);
        logoutButton.setBackground(new IconDrawable(this, FontAwesomeIcons.fa_sign_out)
                .colorRes(R.color.white)
                .actionBarSize());

        // Set OnClickListener for Logout Button
        logoutButton.setOnClickListener(v -> logout());

        // We should use a Java 8 lambda function for the listener (and all other listeners), but
        // they would be unfamiliar to many students who use this code.
        fab.setOnClickListener(view -> {
            FragmentManager fm = getSupportFragmentManager();
            DialogFragment createPostDialog = new PostStatusFragment();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            createPostDialog.show(fragmentTransaction, "dialog");
        });


        TextView userName = findViewById(R.id.userName);
        userName.setText(currentUser.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(currentUser.getUsername());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(currentUser.getImageBytes()));

        followeeCount = findViewById(R.id.followeeCount);
        followerCount = findViewById(R.id.followerCount);
    }

    public void getFolloweeCount(User user) {
        NumFollowsRequest request = new NumFollowsRequest(user.getUsername());
        NumFolloweesTask task = new NumFolloweesTask(followingPresenter, this);
        task.execute(request);
    }

    public void getFollowerCount(User user) {
        NumFollowsRequest request = new NumFollowsRequest(user.getUsername());
        NumFollowersTask task = new NumFollowersTask(followerPresenter, this);
        task.execute(request);
    }

    public void logout() {
        LogoutRequest request = new LogoutRequest(authToken);
        LogoutTask logoutTask = new LogoutTask(logoutPresenter, this);
        logoutTask.execute(request);
    }

    @Override
    public void followerCountReturned(NumFollowsResponse response) {
        followerCount.setText(R.string.followerCount);
        followerCount.append(" " + response.getFollowCount().toString());
    }


    @Override
    public void followeesCountReturned(NumFollowsResponse response) {
        System.out.println("Yoafdosjifajdsfnkadsfasd " + response.getFollowCount());
        followeeCount.setText(R.string.followeeCount);
        followeeCount.append(" " + response.getFollowCount().toString());
    }


    @Override
    public void logoutSuccessful(Response response) {
        Intent intent = new Intent(MainActivity.this, LoginRegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void logoutUnsuccessful(Response response) {
        Toast.makeText(this, "Failed to logout. " + response.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(this, "Failed to logout because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }
}