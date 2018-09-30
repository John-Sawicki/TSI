package com.example.android.tsi.utilities;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.util.List;

//https://stackoverflow.com/questions/2227292/how-to-get-latitude-and-longitude-of-the-mobile-device-in-android
//https://developers.google.com/maps/documentation/geolocation/intro
public class LocationClass {
    double[] latLong ={0,0};
    String locationString="Earth", urlBase = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+latLong[0]+","+latLong[1]+"&key="+ApiKey.GoogleApiKey;
    public  String getLocation(Context context){
        Geocoder geocoder;
        double lat=0.0, longitude = 0.0;

        //https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&key=API_Key
        try{
            LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = lm.getBestProvider(criteria, false);
            Location location =lm.getLastKnownLocation(provider);
            if(location!=null){
                latLong[0]=location.getLatitude();
                latLong[1]= location.getLongitude();
            }else{
                latLong[0]=40.714224;
                latLong[1]= -73.961452;
            }

            geocoder = new Geocoder(context);
            //List<Address> user = null;
            //latLong[0] = user.get(0).getLatitude();
            //latLong[1] = user.get(0).getLongitude();
            Log.d("location",latLong[0]+" "+latLong[1]);
            new AsysncLocationTask().execute(latLong);
            return locationString;
        }catch (SecurityException e){
            return "Location not Recorded";
        }

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
