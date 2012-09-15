package com.kanbandroid.activity;

import android.app.Activity;
import com.octo.android.rest.client.ContentManager;
import com.octo.android.rest.client.SpringAndroidContentService;

public class ContentActivity extends Activity {
    private ContentManager contentManager = new ContentManager( SpringAndroidContentService.class );

    @Override
    protected void onStart() {
        contentManager.start( this );
        super.onStart();
    }

    @Override
    protected void onStop() {
        contentManager.shouldStop();
        super.onStop();
    }

    public ContentManager getContentManager() {
        return contentManager;
    }
}
