package ca.gbc.mobile.adrianpaiva.personalrestaurantguide;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsRoute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MapActivity extends Activity implements LocationListener,GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    LatLng myLocation;
    LatLng restaurantLocation;
    List<Polyline> polylines;
    Restaurant r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent i = getIntent();
        r = (Restaurant)i.getSerializableExtra("restaurant");

        polylines = new ArrayList<Polyline>();

        setUpMapIfNeeded();


        // enable location
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(provider);

        if(location!=null){
            onLocationChanged(location);
        }

        locationManager.requestLocationUpdates(provider, 20000, 0, this);

        myLocation = new LatLng(location.getLatitude(),location.getLongitude());

        try
        {
            restaurantLocation = getCoordinates(r.getAddress());

            mMap.addMarker(new MarkerOptions()
                    .position(restaurantLocation)
                    .title(r.getName())
                    .snippet(r.getAddress())
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Restaurant address is invalid", Toast.LENGTH_SHORT).show();
        }






        mMap.setOnMarkerClickListener(this);

        Toast.makeText(getApplicationContext(), "Select the marker for directions", Toast.LENGTH_SHORT).show();

    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {

                mMap.setMyLocationEnabled(true);

                mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(43.676655,-79.410123), 11.0f) ); // default camera location

                //addMarkers();


            }
        }
    }
    public LatLng getCoordinates(String address)
    {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        LatLng temp = new LatLng(43.651190, -79.370203);

        String address2 = address + "," + " Toronto , Canada";
        try {
            addresses = geocoder.getFromLocationName(address2 , 1);
            if(addresses.size() > 0) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                temp = new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return temp;
    }

    public String getAddressFromCoordinates(LatLng position) {

        Geocoder geocoder;
        List<Address> addresses = null;

        String address= null;
        String city = null;
        String country = null;
        String completeAddress = null;

        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(position.latitude, position.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses != null)
        {
            address = addresses.get(0).getAddressLine(0);
            city = addresses.get(0).getAddressLine(1);
            country = addresses.get(0).getAddressLine(2);
            completeAddress = address + "," + city + "," + country;

        }

        return completeAddress;

    }

    public void getDirections(String currentAddress, String destAddress)
    {
        GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBTapNnH4omO-MNgp7JVegJg89ofWy41iU");
        DirectionsApiRequest req =  DirectionsApi.getDirections(context, currentAddress, destAddress);


        try {
            DirectionsRoute routs[] = req.await();

            if (routs!=null)
            {
                if (routs.length>0)
                {
                    for(Polyline line : polylines)
                    {
                        line.remove();
                    }

                    polylines.clear();

                    DirectionsRoute r = routs[0];
                    List<com.google.maps.model.LatLng> points =
                            r.overviewPolyline.decodePath();
                    PolylineOptions pline = new PolylineOptions();
                    Log.i("Location", "size:" + points.size());
                    for (int i=0; i<points.size();i++) {
                        Log.i("Location",points.get(i).toString());
                        LatLng p = new LatLng(points.get(i).lat,points.get(i).lng);
                        pline.add(p);
                    }

                    polylines.add(mMap.addPolyline(pline));

                }
            }
            // Handle successful request.
        } catch (Exception e) {
            // Handle error
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onMarkerClick(Marker marker) {

        if (restaurantLocation.latitude == marker.getPosition().latitude && restaurantLocation.longitude == marker.getPosition().longitude)
        {
            String currentAddress = getAddressFromCoordinates(myLocation);
            String destAddress = getAddressFromCoordinates(restaurantLocation);

            getDirections(currentAddress,destAddress);

        }


        return false;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
