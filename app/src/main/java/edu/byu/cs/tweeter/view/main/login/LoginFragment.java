package edu.byu.cs.tweeter.view.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.LoginTask;
import edu.byu.cs.tweeter.view.main.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements LoginPresenter.View, LoginTask.Observer {

    private static final String LOG_TAG = "LoginFragment";

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private LoginPresenter presenter;
    private Toast loginToast;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        presenter = new LoginPresenter(this);

        usernameEditText = view.findViewById(R.id.userNameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        loginButton = view.findViewById(R.id.loginButton);

        // Disable Login Button to Start
        toggleLoginButton();

        // set listeners
        usernameEditText.addTextChangedListener(textChangeWatcher);
        passwordEditText.addTextChangedListener(textChangeWatcher);
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            loginToast = Toast.makeText(getActivity(), "Logging In", Toast.LENGTH_LONG);
            loginToast.show();

            LoginRequest loginRequest = new LoginRequest(username, password);
            LoginTask loginTask = new LoginTask(presenter, this);
            loginTask.execute(loginRequest);

        });

        return view;

    }
    /**
     * Enables or disables the LoginButton if input fields are filled or not
     */
    private void toggleLoginButton(){
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        loginButton.setEnabled(!username.equals("") && !password.equals(""));
    }

    /**
     * Enables or disables the RegisterButton if input fields are filled or not
     */
    private final TextWatcher textChangeWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            toggleLoginButton();
        }
    };

    /**
     * The callback method that gets invoked for a successful login. Displays the MainActivity.
     *
     * @param response the response from the login request.
     */
    @Override
    public void loginSuccessful(LoginRegisterResponse response) {
        Intent mainIntent = new Intent(getActivity(), MainActivity.class);

        mainIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);

        mainIntent.putExtra(MainActivity.CURRENT_USER_KEY, response.getUser());
        mainIntent.putExtra(MainActivity.AUTH_TOKEN_KEY, response.getAuthToken());

        loginToast.cancel();
        startActivity(mainIntent);
    }

    /**
     * The callback method that gets invoked for an unsuccessful login. Displays a toast with a
     * message indicating why the login failed.
     *
     * @param response the response from the login request.
     */
    @Override
    public void loginUnsuccessful(LoginRegisterResponse response) {
        Toast.makeText(getActivity(), "Failed to login. " + response.getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * A callback indicating that an exception was thrown in an asynchronous method called on the
     * presenter.
     *
     * @param exception the exception.
     */
    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(getActivity(), "Failed to login because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
