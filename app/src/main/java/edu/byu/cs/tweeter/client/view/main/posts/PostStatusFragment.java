package edu.byu.cs.tweeter.client.view.main.posts;

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

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.time.LocalDateTime;

import androidx.fragment.app.DialogFragment;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.client.presenter.PostStatusPresenter;
import edu.byu.cs.tweeter.client.view.asyncTasks.PostStatusTask;
import edu.byu.cs.tweeter.client.view.main.MainActivity;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.response.PostStatusResponse;

public class PostStatusFragment extends DialogFragment implements PostStatusTask.Observer,
        PostStatusPresenter.View {

    private static final String LOG_TAG = "PostStatusFragment";

    private PostStatusPresenter presenter;
    private EditText editText;
    private Button postButton;

    public PostStatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_status, container, false);

        presenter = new PostStatusPresenter(this);

        Iconify.with(new FontAwesomeModule());
        Button exitButton = view.findViewById(R.id.exitButton);
        exitButton.setBackground(new IconDrawable(this.getContext(), FontAwesomeIcons.fa_close)
                .colorRes(R.color.colorAccent));
        exitButton.setOnClickListener(v -> dismiss());

        editText = view.findViewById(R.id.editText);
        editText.addTextChangedListener(textChangeWatcher);

        postButton = view.findViewById(R.id.postButton);
        postButton.setOnClickListener(v -> {
            String text = editText.getText().toString();
            LocalDateTime timeStamp = LocalDateTime.now();
            postStatus(text, timeStamp);
        });
        return view;
    }

    private void postStatus(String text, LocalDateTime timeStamp) {
        PostStatusRequest request = new PostStatusRequest(text, ((MainActivity)getActivity()).getCurrentUser(), timeStamp);
        PostStatusTask task = new PostStatusTask(presenter, this);
        task.execute(request);
    }

    // TODO: This doesn't seem to be working
    private void togglePostButton(){
        String postText = editText.getText().toString();
        postButton.setEnabled(!postText.equals(""));
    }

    private final TextWatcher textChangeWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            togglePostButton();
        }
    };

    @Override
    public void postStatusSuccessful(PostStatusResponse response) {
        dismiss();
    }

    @Override
    public void postStatusUnsuccessful(PostStatusResponse response) {
        Toast.makeText(getActivity(), "Failed to post status. " + response.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(getActivity(), "Failed to post status because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();

    }
}
