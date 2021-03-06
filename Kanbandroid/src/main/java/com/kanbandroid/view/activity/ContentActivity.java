package com.kanbandroid.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.kanbandroid.R;
import com.kanbandroid.model.User;
import com.kanbandroid.model.Workspaces;
import com.kanbandroid.rest.request.UserRequest;
import com.kanbandroid.rest.request.WorkspacesRequest;
import com.kanbandroid.service.KanbandroidContentService;
import com.kanbandroid.util.Preferences;
import com.kanbandroid.util.RequestKey;
import com.octo.android.robospice.ContentManager;
import com.octo.android.robospice.exception.ContentManagerException;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.ContentRequest;
import com.octo.android.robospice.request.RequestListener;
import de.akquinet.android.androlog.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public abstract class ContentActivity extends SherlockFragmentActivity {
    private ContentManager contentManager = new ContentManager(KanbandroidContentService.class);
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

    private void clearApiKeyPreference() {
        SharedPreferences pref = getSharedPreferences(Preferences.PREF_KEY, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    protected void onStart() {
        Log.i(this, "Starting activity " + getLocalClassName() + " ...");
        contentManager.start(this);

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

    protected void requestForUser() {
        requestForData(new UserRequest(apiKey), RequestKey.USER);
    }

    protected void requestForWorkspaces() {
        requestForData(new WorkspacesRequest(apiKey), RequestKey.WORKSPACES);
    }

    public <T> void requestForData(ContentRequest<T> contentRequest, final RequestKey requestKey, Object... requestParams) {
        getSherlock().setProgressBarIndeterminateVisibility(true);
        ContentManager manager = getContentManager();

        RequestListener<T> requestListener = new RequestListener<T>() {
            public void onRequestSuccess(T requestedData) {
                handleRequestSuccess(requestKey, requestedData);
            }

            public void onRequestFailure(ContentManagerException contentManagerException) {
                handleRequestError(requestKey, contentManagerException);
            }
        };

        String cacheKey;
        if(requestParams.length > 0) {
            cacheKey = String.format(requestKey.getCacheKey(), requestParams);
        } else {
            cacheKey = requestKey.getCacheKey();
        }

        manager.execute(contentRequest, cacheKey, DurationInMillis.ONE_HOUR, requestListener);
    }

    protected void goBackToLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    protected void handleEndRequest(RequestKey requestKey) {
        getSherlock().setProgressBarIndeterminateVisibility(false);
    }

    protected void handleRequestSuccess(RequestKey requestKey, Object requestedData) {
        handleEndRequest(requestKey);
        switch (requestKey) {
            case USER:
                this.user = (User) requestedData;
                break;
            case WORKSPACES:
                this.workspaces = (Workspaces) requestedData;
        }
    }

    protected void handleRequestError(RequestKey requestKey, ContentManagerException contentManagerException) {
        Throwable cause = contentManagerException.getCause();
        String errorMessage = "Unexpected error";
        if (cause != null) {
            try {
                throw cause;
            } catch (HttpClientErrorException e) {
                HttpStatus statusCode = e.getStatusCode();
                if (statusCode == HttpStatus.UNAUTHORIZED) {
                    errorMessage = getString(R.string.error_wrong_credentials);
                }
            } catch (Throwable e) {
                errorMessage += " " + e.getMessage();
            }
        }
        Log.e(this, errorMessage, contentManagerException);
        showErrorMessage(errorMessage);
        handleEndRequest(requestKey);
    }

    private void showErrorMessage(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getSupportMenuInflater().inflate(R.menu.overflow_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_item_logout) {
            logout();
        }

        return false;
    }

    private void logout() {
        clearApiKeyPreference();
        contentManager.removeAllDataFromCache();
        goBackToLoginScreen();
    }
}
