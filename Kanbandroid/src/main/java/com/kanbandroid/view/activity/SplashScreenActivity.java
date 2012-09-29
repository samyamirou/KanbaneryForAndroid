package com.kanbandroid.view.activity;

import android.content.Intent;
import android.os.Bundle;
import com.kanbandroid.R;
import com.kanbandroid.util.RequestKey;
import com.octo.android.robospice.exception.ContentManagerException;

public class SplashScreenActivity extends ContentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.main);
        if(apiKey != null) {
            requestForWorkspaces();
        } else {
            navigateToLoginScreen();
        }
    }

    @Override
    protected void handleRequestSuccess(RequestKey requestKey, Object requestedData) {
        super.handleRequestSuccess(requestKey, requestedData);
        navigateToProjectsScreen();
    }

    @Override
    protected void handleRequestError(RequestKey requestKey, ContentManagerException contentManagerException) {
        super.handleRequestError(requestKey, contentManagerException);
        navigateToLoginScreen();
    }

    private void navigateToLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void navigateToProjectsScreen() {
        Intent intent = new Intent(this, ProjectListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}