package com.john.android.tsi.utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LocationAsyncTask extends AsyncTask<String, Void, String>{
    String locationAddress = "";
    private OnAddressComplete mAddressComplete;
    public LocationAsyncTask(OnAddressComplete context){
        this.mAddressComplete = context;
    }
    public interface OnAddressComplete{
        void onAddressComplete(String address);
    }
        @Override
        protected String doInBackground(String... urlString) {
            Log.d("TASK_LIST_ACT", "LocationAsyncTask "+urlString[0]);
            String rawJson = "", jsonAddress = "Unable to determine location.";
            URL url;
            HttpURLConnection httpURLConnection = null;
            try {
                url = new URL(urlString[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    rawJson += current;
                    data = reader.read();
                }
                //Log.d(ACTIVITY, "raw json " + rawJson);
                //Log.d("TASK_LIST_ACT", "LocationAsyncTask raw JSON "+rawJson);
                jsonAddress = LocationClass.parseStaticJsonAddress(rawJson);
                return jsonAddress;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Unable to determine location.";
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("TASK_LIST_ACT", "LocationAsyncTask onPost "+s);
            mAddressComplete.onAddressComplete(s);
            locationAddress = s;
        }

}
