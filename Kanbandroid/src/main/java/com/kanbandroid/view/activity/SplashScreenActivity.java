package com.kanbandroid.view.activity;

import android.content.Intent;
import android.os.Bundle;
import com.kanbandroid.R;
import com.octo.android.robospice.exception.ContentManagerException;

public class SplashScreenActivity extends ContentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.main);
        if(apiKey != null) {
            requestForUser();
            requestForWorkspaces();
        }
    }

    @Override
    protected void handleRequestSuccess() {
        super.handleRequestSuccess();
        navigateToProjectsScreen();
    }

    @Override
    protected void handleRequestError(ContentManagerException contentManagerException) {
        super.handleRequestError(contentManagerException);
        navigateToLoginScreen();
    }

    private void navigateToProjectsScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void navigateToLoginScreen() {
        Intent intent = new Intent(this, ProjectListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}