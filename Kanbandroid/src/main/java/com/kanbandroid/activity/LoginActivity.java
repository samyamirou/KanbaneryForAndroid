package com.kanbandroid.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.common.base.Preconditions;
import com.kanbandroid.R;
import com.kanbandroid.rest.request.UserRequest;
import com.kanbandroid.model.KanbaneryUser;
import com.octo.android.rest.client.ContentManager;
import com.octo.android.rest.client.exception.ContentManagerException;
import com.octo.android.rest.client.persistence.DurationInMillis;
import com.octo.android.rest.client.request.ContentRequest;
import com.octo.android.rest.client.request.RequestListener;
import de.akquinet.android.androlog.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashSet;
import java.util.Set;

public class LoginActivity extends ContentActivity implements RequestListener<KanbaneryUser>, View.OnClickListener {
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
        setContentView(R.layout.main);

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
        ContentRequest<KanbaneryUser> contentRequest = new UserRequest(username, password);

        manager.execute(contentRequest, "user", DurationInMillis.ONE_SECOND, this);
    }

    public void onRequestSuccess(KanbaneryUser s) {
        Log.i(this, "Login successful ! KanbaneryUser : " + s);
        progressLayout.setVisibility(View.INVISIBLE);
        navigateToProjectsScreen();
    }

    public void onRequestFailure(ContentManagerException contentManagerException) {
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
                errorMessage += e.getMessage();
            }
        }
        showErrorMessage(errorMessage);
        btnLogin.setEnabled(true);
        progressLayout.setVisibility(View.INVISIBLE);
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
}
