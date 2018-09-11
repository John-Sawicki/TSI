package com.example.android.tsi.utilities;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import java.util.List;

//https://stackoverflow.com/questions/2227292/how-to-get-latitude-and-longitude-of-the-mobile-device-in-android
//https://developers.google.com/maps/documentation/geolocation/intro
public class LocationClass {
    public static String getLocation(Context context){
        String provider;
        Geocoder geocoder;
        double lat=0.0, longitude = 0.0;
        //https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&key=API_Key
        String urlBase = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+"  "+","+""+"&key="+ApiKey.Apikey;
        List<Address> user = null;
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = lm.getBestProvider(criteria, false);
        try{
            Location location =lm.getLastKnownLocation(provider);
            geocoder = new Geocoder(context);
            lat = user.get(0).getLatitude();
            longitude = user.get(0).getLongitude();
        }catch (SecurityException e){
            return "Location not Recorded";
        }
        return "Earth";
    }
}
