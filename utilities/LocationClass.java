package com.example.android.tsi.utilities;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.tsi.TaskListActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import java.util.List;

//https://stackoverflow.com/questions/2227292/how-to-get-latitude-and-longitude-of-the-mobile-device-in-android
//https://developers.google.com/maps/documentation/geolocation/intro
public class LocationClass {
    double[] latLong ={0,0};
    String locationString="Earth", urlBase = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+latLong[0]+","+latLong[1]+"&key="+ApiKey.GoogleApiKey;
    private FusedLocationProviderClient mFusedLocationClient;
    public  String getLocation(double[] mLatLong){
        latLong = mLatLong;
            new AsysncLocationTask().execute(latLong);
            return locationString;
    }
    public class AsysncLocationTask extends AsyncTask<double[], Integer, String>{
        @Override
        protected String doInBackground(double[]... doubleLatLong) {
            String asyncLocation;
            String rawJson = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+doubleLatLong[0]+","+doubleLatLong[1]+"&key="+ApiKey.GoogleApiKey;
            Log.d("location rawJson",rawJson);
            try{
                JSONObject locationJson = new JSONObject(rawJson);
                asyncLocation = locationJson.getString("formatted_address");
                Log.d("location", asyncLocation);
                return asyncLocation;
            }catch (Exception e){
                e.printStackTrace();
                return "Location not Found";
            }
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String s) {
            Log.d("location", s);
            locationString = s;
            super.onPostExecute(s);
        }
    }
}
