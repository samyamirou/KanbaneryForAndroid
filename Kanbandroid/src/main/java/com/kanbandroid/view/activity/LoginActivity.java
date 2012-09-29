package com.kanbandroid.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.common.base.Preconditions;
import com.kanbandroid.R;
import com.kanbandroid.model.User;
import com.kanbandroid.rest.request.UserRequest;
import com.kanbandroid.util.Preferences;
import com.kanbandroid.util.RequestKey;
import de.akquinet.android.androlog.Log;

import java.util.HashSet;
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

        getSupportActionBar().hide();

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
        //getContentManager().removeAllDataFromCache();
    }

    private void navigateToProjectsScreen() {
        Intent intent = new Intent(this, ProjectListActivity.class);
        startActivity(intent);
        finish();
    }

    private void login(String username, String password) {
        //On empêche d'abord toute requête d'être lancée
        btnLogin.setEnabled(false);
        progressLayout.setVisibility(View.VISIBLE);

        Preconditions.checkArgument(username != null, "Username shouldn't be null");
        Preconditions.checkArgument(password != null, "Password shouldn't be null");

        requestForData(new UserRequest(username, password), RequestKey.USER);
    }

    @Override
    protected void handleRequestSuccess(RequestKey requestKey, Object requestedData) {
        super.handleRequestSuccess(requestKey, requestedData);
        user = (User) requestedData;
        Log.i(LoginActivity.this, "Login successful ! User : " + user.getEmail());
        Preferences.putSharedPreference(user.getApiKey(), LoginActivity.this, Preferences.PREF_KEY, Preferences.API_KEY);
        navigateToProjectsScreen();
    }

    @Override
    protected void handleEndRequest(RequestKey requestKey) {
        btnLogin.setEnabled(true);
        progressLayout.setVisibility(View.INVISIBLE);
    }

    public void onClick(View view) {
        if(!clickedEditTexts.contains(view)) {
            ((EditText) view).setText("");
            clickedEditTexts.add(view);
        }
    }
}
