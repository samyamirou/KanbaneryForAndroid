package com.kanbandroid.manager;

import android.util.Base64;
import android.util.Log;
import com.google.common.io.CharStreams;
import com.octo.android.rest.client.request.ContentRequest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ProxySimpleTextRequest extends ContentRequest< String > {

    private String username;
    private String password;
    private String url;

    public ProxySimpleTextRequest(String username, String password, String url) {
        super( String.class );
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public String loadDataFromNetwork() throws Exception {
        try {
            Log.d(getClass().getName(), "Call web service " + url);
            URL urlObject = new URL(url);
            URLConnection urlConnection = urlObject.openConnection();
            String auth = new String(Base64.encode((username + ":" + password).getBytes(), Base64.URL_SAFE));
            auth = "Basic " + auth;
            urlConnection.setRequestProperty("Proxy-Connection","Keep-Alive");
            urlConnection.setRequestProperty("Proxy-Authorization",auth);
            return CharStreams.toString(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
        } catch ( MalformedURLException e ) {
            Log.e( getClass().getName(), "Unable to create image URL" );
            return null;
        } catch ( IOException e ) {
            Log.e( getClass().getName(), "Unable to download image" );
            return null;
        }
    }

    public String getUsername() {
        return username;
    }

    // can't use activity here or any non serializable field
    // will be invoked in remote service
    protected final String getUrl() {
        return this.url;
    }
}
