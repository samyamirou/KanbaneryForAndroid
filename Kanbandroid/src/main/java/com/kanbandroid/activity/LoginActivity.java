package com.kanbandroid.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.common.base.Preconditions;
import com.kanbandroid.R;
import com.kanbandroid.manager.ProxySimpleTextRequest;
import com.kanbandroid.service.RestResource;
import com.octo.android.rest.client.ContentActivity;
import com.octo.android.rest.client.ContentManager;
import com.octo.android.rest.client.exception.ContentManagerException;
import com.octo.android.rest.client.request.ContentRequest;
import com.octo.android.rest.client.request.RequestListener;
import de.akquinet.android.androlog.Log;

public class LoginActivity extends ContentActivity implements RequestListener<String> {
    private EditText etUsername;
    private EditText etPassword;
    private TextView tvLoginError;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.tvLoginError = (TextView) findViewById(R.id.tv_login_error);

        this.etUsername = (EditText) findViewById(R.id.et_login_username);
        this.etPassword = (EditText) findViewById(R.id.et_login_password);

        Button btnLogin = (Button) findViewById(R.id.bt_login_connexion);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                login(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });
    }

    private void navigateToProjectsScreen() {
    }

    private void login(String username, String password) {
        ContentManager manager = getContentManager();
        Preconditions.checkArgument(username != null, "Username shouldn't be null");
        Preconditions.checkArgument(password != null, "Password shouldn't be null");
        ContentRequest<String> request = new ProxySimpleTextRequest(username, password, RestResource.LOGIN.getUrl());
        /*try {
            request.loadDataFromNetwork();
        } catch (Exception e) {
            Log.e(this, "Error during login", e);
        }*/
        manager.execute(request, this);
    }

    public void onRequestSuccess(String s) {
        navigateToProjectsScreen();
    }

    public void onRequestFailure(ContentManagerException contentManagerException) {
        showErrorMessage(contentManagerException.getMessage());
    }

    private void showErrorMessage(String message) {
        this.tvLoginError.setText(message);
        this.tvLoginError.setVisibility(View.VISIBLE);
    }
}
