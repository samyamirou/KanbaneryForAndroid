package com.kanbandroid.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.common.base.Preconditions;
import com.kanbandroid.R;
import com.kanbandroid.model.User;
import com.kanbandroid.model.Workspace;
import com.kanbandroid.model.Workspaces;
import com.kanbandroid.rest.request.UserRequest;
import com.kanbandroid.rest.request.WorkspacesRequest;
import com.octo.android.rest.client.ContentManager;
import com.octo.android.rest.client.exception.ContentManagerException;
import com.octo.android.rest.client.persistence.DurationInMillis;
import com.octo.android.rest.client.request.ContentRequest;
import com.octo.android.rest.client.request.RequestListener;
import de.akquinet.android.androlog.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoginActivity extends ContentActivity implements View.OnClickListener {
    private EditText etUsername;
    private EditText etPassword;
    private Set<View> clickedEditTexts = new HashSet<View>();
    private Button btnLogin;
    private View progressLayout;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        this.etUsername = (EditText) findViewById(R.id.et_login_username);
        this.etUsername.setOnClickListener(this);
        this.etPassword = (EditText) findViewById(R.id.et_login_password);
        this.etPassword.setOnClickListener(this);

        btnLogin = (Button) findViewById(R.id.bt_login_connexion);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                login(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });

        this.progressLayout = findViewById(R.id.ly_progress);

        // Pour tests
        getCacheManager().removeAllDataFromCache();
    }

    private void navigateToProjectsScreen() {
        Log.i(this, "Login successful !");
    }

    private void login(String username, String password) {
        //On empêche d'abord toute requête d'être lancée
        btnLogin.setEnabled(false);
        progressLayout.setVisibility(View.VISIBLE);

        ContentManager manager = getContentManager();
        Preconditions.checkArgument(username != null, "Username shouldn't be null");
        Preconditions.checkArgument(password != null, "Password shouldn't be null");
        ContentRequest<User> contentRequest = new UserRequest(username, password);

        manager.execute(contentRequest, "user", DurationInMillis.ONE_HOUR, new RequestListener<User>() {
            public void onRequestSuccess(User user) {
                Log.i(LoginActivity.this, "Login successful ! User : " + user.getEmail());
                requestForWorkspaces();
            }

            public void onRequestFailure(ContentManagerException contentManagerException) {
                handleRequestError(contentManagerException);
            }
        });
    }

    private void showErrorMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void onClick(View view) {
        if(!clickedEditTexts.contains(view)) {
            ((EditText) view).setText("");
            clickedEditTexts.add(view);
        }
    }

    private void requestForWorkspaces() {
        ContentManager manager = getContentManager();
        ContentRequest<Workspaces> contentRequest = new WorkspacesRequest(user.getApiKey());

        manager.execute(contentRequest, "workspaces", DurationInMillis.ONE_HOUR, new RequestListener<Workspaces> () {

            public void onRequestSuccess(Workspaces workspaces) {
                progressLayout.setVisibility(View.INVISIBLE);
                Log.i(LoginActivity.this, "Found workspaces for this user : " + user.getEmail());
                navigateToProjectsScreen();
            }

            public void onRequestFailure(ContentManagerException contentManagerException) {
                handleRequestError(contentManagerException);
            }
        });
    }

    private void handleRequestError(ContentManagerException contentManagerException) {
        Throwable cause = contentManagerException.getCause();
        String errorMessage = "Unexpected error";
        if(cause != null) {
            try {
                throw cause;
            } catch (HttpClientErrorException e) {
                HttpStatus statusCode = e.getStatusCode();
                if(statusCode == HttpStatus.UNAUTHORIZED) {
                    errorMessage = getString(R.string.error_wrong_credentials);
                }
            } catch (Throwable e) {
                errorMessage += " " + e.getMessage();
            }
        }
        showErrorMessage(errorMessage);
        btnLogin.setEnabled(true);
        progressLayout.setVisibility(View.INVISIBLE);
    }
}
