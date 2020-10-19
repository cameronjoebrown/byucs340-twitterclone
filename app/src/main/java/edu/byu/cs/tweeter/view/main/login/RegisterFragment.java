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
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginRegisterResponse;
import edu.byu.cs.tweeter.presenter.RegisterPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.RegisterTask;
import edu.byu.cs.tweeter.view.main.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements RegisterPresenter.View, RegisterTask.Observer {

    private static final String LOG_TAG = "RegisterFragment";

    private EditText usernameEditText, passwordEditText, firstNameEditText,
            lastNameEditText, profilePicURLEditText;
    private Button registerButton;
    private RegisterPresenter presenter;
    private Toast registerToast;

    public RegisterFragment() {
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        presenter = new RegisterPresenter(this);

        usernameEditText = view.findViewById(R.id.usernameEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        firstNameEditText = view.findViewById(R.id.firstNameEditText);
        lastNameEditText = view.findViewById(R.id.lastNameEditText);
        profilePicURLEditText = view.findViewById(R.id.imageURLEditText);
        registerButton = view.findViewById(R.id.registerButton);

        // Set Camera Button Icon


        // Disable Register Button to Start
        toggleRegisterButton();

        // set listeners
        usernameEditText.addTextChangedListener(textChangeWatcher);
        passwordEditText.addTextChangedListener(textChangeWatcher);
        firstNameEditText.addTextChangedListener(textChangeWatcher);
        lastNameEditText.addTextChangedListener(textChangeWatcher);
        profilePicURLEditText.addTextChangedListener(textChangeWatcher);

        registerButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String firstName = firstNameEditText.getText().toString();
            String lastName = lastNameEditText.getText().toString();
            String imageURL = profilePicURLEditText.getText().toString();

            registerToast = Toast.makeText(getActivity(), "Registering", Toast.LENGTH_LONG);
            registerToast.show();

            if(validateUsername(username)) {
                RegisterRequest registerRequest = new RegisterRequest(username, password, firstName,
                        lastName, imageURL);
                RegisterTask registerTask = new RegisterTask(presenter, this);
                registerTask.execute(registerRequest);
            }
            else {
                Toast.makeText(getContext(),R.string.handleFormat, Toast.LENGTH_SHORT).show();
            }

        });

        return view;
    }
    /**
     * Enables or disables the RegisterButton if input fields are filled or not
     */
    private void toggleRegisterButton(){
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String imageURL = profilePicURLEditText.getText().toString();


        registerButton.setEnabled(!username.equals("") && !password.equals("") && !firstName.equals("") &&
                !lastName.equals("") && !imageURL.equals(""));
    }

    private boolean validateUsername(String username) {
        return username.charAt(0) == '@';
    }

    /**
     * After text fields have changed, it checks to see if Register button should be enabled.
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
            toggleRegisterButton();
        }
    };

    @Override
    public void registerSuccessful(LoginRegisterResponse response) {
        Intent mainIntent = new Intent(getActivity(), MainActivity.class);

        mainIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);

        mainIntent.putExtra(MainActivity.CURRENT_USER_KEY, response.getUser());
        mainIntent.putExtra(MainActivity.AUTH_TOKEN_KEY, response.getAuthToken());

        registerToast.cancel();
        startActivity(mainIntent);
    }

    @Override
    public void registerUnsuccessful(LoginRegisterResponse response) {
        Toast.makeText(getActivity(), "Failed to register. " + response.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(getActivity(), "Failed to register because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }

}
