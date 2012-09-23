package com.kanbandroid.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.kanbandroid.R;
import com.kanbandroid.model.User;
import com.kanbandroid.model.Workspaces;
import com.kanbandroid.rest.request.UserRequest;
import com.kanbandroid.rest.request.WorkspacesRequest;
import com.kanbandroid.service.KanbandroidContentService;
import com.kanbandroid.util.CacheKeys;
import com.kanbandroid.util.Preferences;
import com.octo.android.robospice.ContentManager;
import com.octo.android.robospice.exception.ContentManagerException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.ContentRequest;
import com.octo.android.robospice.request.RequestListener;
import de.akquinet.android.androlog.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public abstract class ContentActivity extends SherlockFragmentActivity {
    private ContentManager contentManager = new ContentManager( KanbandroidContentService.class );
    protected User user;
    protected Workspaces workspaces;
    protected String apiKey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiKey = loadApiKeyFromPreferences();

        getSupportActionBar().setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
    }

    private String loadApiKeyFromPreferences() {
        SharedPreferences pref = getSharedPreferences(Preferences.PREF_KEY, 0);
        return pref.getString(Preferences.API_KEY, null);
    }

    @Override
    protected void onStart() {
        Log.i(this, "Starting activity " + getLocalClassName() + " ...");
        contentManager.start( this );

        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i(this, "Stopping activity " + getLocalClassName());
        contentManager.shouldStop();
        super.onStop();
    }

    public ContentManager getContentManager() {
        return contentManager;
    }

    protected void loadUser() {
        //On empêche d'abord toute requête d'être lancée

        ContentManager manager = getContentManager();
        ContentRequest<User> contentRequest = new UserRequest(apiKey);

        manager.execute(contentRequest, CacheKeys.USER, DurationInMillis.ONE_HOUR, new RequestListener<User>() {
            public void onRequestSuccess(User requestedUser) {
                user = requestedUser;
            }

            public void onRequestFailure(ContentManagerException contentManagerException) {
                handleRequestError(contentManagerException);
            }
        });

        if(this.user == null) {
            goBackToLoginScreen();
        }
    }

    protected void loadWorkspaces() {
        ContentManager manager = getContentManager();
        ContentRequest<Workspaces> contentRequest = new WorkspacesRequest(user.getApiKey());

        manager.execute(contentRequest, "workspaces", DurationInMillis.ONE_HOUR, new RequestListener<Workspaces> () {

            public void onRequestSuccess(Workspaces workspaces) {
                handleRequestSuccess();
                ContentActivity.this.workspaces = workspaces;
            }

            public void onRequestFailure(ContentManagerException contentManagerException) {
                handleRequestError(contentManagerException);
            }
        });

        if(this.workspaces == null) {
            goBackToLoginScreen();
        }
    }

    protected void goBackToLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    protected void handleEndRequest() {

    }

    protected void handleRequestSuccess() {
        handleEndRequest();
    }

    protected void handleRequestError(ContentManagerException contentManagerException) {
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
        Log.e(this, errorMessage, contentManagerException);
        showErrorMessage(errorMessage);
    }

    private void showErrorMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
