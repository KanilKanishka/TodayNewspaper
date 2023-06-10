package com.example.todaynewspaper;

import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.todaynewspaper.databinding.ActivityLocationBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Location extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityLocationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        new FetchJSONTask().execute("http://10.0.2.2/Subs_list.json");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        new FetchJSONTask().execute("http://10.0.2.2/Subs_list.json");

//        try {
//            InputStream inputStream = getAssets().open("your_json_file.json");
//            int size = inputStream.available();
//            byte[] buffer = new byte[size];
//            inputStream.read(buffer);
//            inputStream.close();
//            String jsonString = new String(buffer, "UTF-8");
//
//            // Parse and process the JSON data here
//            // You can use libraries like Gson or JSONObject to parse the JSON
//
//        } catch (
//                IOException e) {
//            e.printStackTrace();
//        }
    }
    private class FetchJSONTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String url =urls[0];
            String result="";

            try {
                URL urlObject = new URL(url);
                HttpURLConnection connection =(HttpURLConnection) urlObject.openConnection();
                connection.connect();
                InputStream inputStream=connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder= new StringBuilder();
                String line;
                while ((line=reader.readLine()) !=null){
                    stringBuilder.append(line);
                }
                result=stringBuilder.toString();
                reader.close();
                inputStream.close();
                connection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
        @SuppressLint("SetTextI18n")

        @Override
        protected void onPostExecute(String result) {

            try {

                JSONArray jsonArray = new JSONArray(result);

                for (int i =0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        name = jsonObject.getString("Fullname");
//                         city = jsonObject.getString("city");
                    double latti = jsonObject.getDouble("latitude");
                    double longtt = jsonObject.getDouble("longitude");
                    //Toast.makeText(MapActivity.this, "Name: " + name + ", City: " + city, Toast.LENGTH_SHORT).show();
                    //LatLng location = new LatLng(latti, longtt);

                    LatLng location = new LatLng(latti, longtt);
//                     .title(name).snippet(city);

                            mMap.addMarker(new MarkerOptions().position(location));
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
