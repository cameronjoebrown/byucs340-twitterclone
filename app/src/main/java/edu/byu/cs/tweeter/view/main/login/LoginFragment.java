package edu.byu.cs.tweeter.view.main.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
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
public class LoginFragment extends Fragment implements LoginPresenter.View {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private LoginPresenter presenter;
    private LoginObserver loginObserver;

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
        loginObserver = new LoginObserver();

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

            loginObserver.login(username, password);

        });

        return view;

    }
    /**
     * Enables or disables the LoginButton if input fields are filled or not
     */
    private void toggleLoginButton(){
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(username.equals("") || password.equals("")){
            loginButton.setEnabled(false);
        } else {
            loginButton.setEnabled(true);
        }
    }

    /**
     * Enables or disables the RegisterButton if input fields are filled or not
     */
    private TextWatcher textChangeWatcher = new TextWatcher() {
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

    private class LoginObserver implements LoginTask.Observer {
        void login(String username, String password) {
            LoginRequest loginRequest = new LoginRequest(username, password);
            LoginTask loginTask = new LoginTask(presenter, this);
            loginTask.execute(loginRequest);
        }

        @Override
        public void loginSuccessful(LoginRegisterResponse response) {
            Intent mainIntent = new Intent(LoginFragment.this.getActivity(), MainActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mainIntent.putExtra("username", response.getUser().getUsername());
            startActivity(mainIntent);
        }

        @Override
        public void loginUnsuccessful(LoginRegisterResponse response) {
            Toast toast =
                    Toast.makeText(getContext(), response.getMessage(),Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 20);
            toast.show();
        }

        @Override
        public void handleException(Exception ex) {

        }
    }
}
