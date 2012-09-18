package com.kanbandroid.view.activity;

import android.content.Intent;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.kanbandroid.KanbandroidApplication;
import com.kanbandroid.model.User;
import com.kanbandroid.model.Workspaces;
import com.octo.android.rest.client.ContentManager;
import com.octo.android.rest.client.SpringAndroidContentService;
import com.octo.android.rest.client.exception.CacheLoadingException;
import com.octo.android.rest.client.persistence.CacheManager;
import com.octo.android.rest.client.persistence.DurationInMillis;
import de.akquinet.android.androlog.Log;

public abstract class ContentActivity extends SherlockFragmentActivity {
    private ContentManager contentManager = new ContentManager( SpringAndroidContentService.class );
    protected User user;
    protected Workspaces workspaces;

    protected CacheManager getCacheManager() {
        return ((KanbandroidApplication)getApplication()).getCacheManager();
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

    protected void loadUserFromCache() {
        String errorMsg = "Error while loading user data from cache";

        try {
            this.user = getCacheManager().loadDataFromCache(User.class, "user", DurationInMillis.ONE_HOUR);
        } catch (CacheLoadingException e) {
            Log.e(this, errorMsg, e);
        }

        if(this.user == null) {
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
            goBackToLoginScreen();
        }
    }

    protected void loadWorkspacesFromCache() {
        String errorMsg = "Error while loading workspaces data from cache";

        try {
            this.workspaces = getCacheManager().loadDataFromCache(Workspaces.class, "workspaces", DurationInMillis.ONE_HOUR);
        } catch (CacheLoadingException e) {
            Log.e(this, errorMsg, e);
        }

        if(this.workspaces == null) {
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
            goBackToLoginScreen();
        }
    }

    protected void goBackToLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
