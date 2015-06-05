package icws.itinerary;

import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentActivity;
import android.widget.*;
import android.util.Log;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.app.Activity;
import android.view.View;
import android.location.Location;


public class CityMap extends Activity {

    private GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_map);
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to
        // go to the settings
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        createMapView();
        addMarker();
//        double lat = 37.403146;
//        double lng= -122.075382;
//        LatLng latlng = new LatLng(lat, lng);
//        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        map = fm.getMap();
       // map=((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        //map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,14));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_city_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Route Button
    public void goGoogleMaps(View v){
        String destination = "5000 Forbes Avenue, Pittsburgh, PA 15213";
       googleMap.setMyLocationEnabled(true);
        Location userLocation = googleMap.getMyLocation();
        if (userLocation==null)
        Toast.makeText(getApplicationContext(),
                "error",Toast.LENGTH_SHORT).show();
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http:/a/maps.google.com/maps?"
                + "saddr="+ 37.422006+","+ -122.084095 + "&daddr="+destination));

        intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");

        startActivity(intent);
    }

    private void createMapView(){
        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try {
            if(null == googleMap){
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                        R.id.map)).getMap();

                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(null == googleMap) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map",Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }

    /**
     * Adds a marker to the map
     */
    private void addMarker(){

        /** Make sure that the map has been initialised **/
        if(null != googleMap){
            LatLng latlng = new LatLng(37.403146, -122.075382);
            googleMap.addMarker(new MarkerOptions()
                            .position(latlng)
                            .title("777 W Middlefield Rd, #71")
                            .draggable(true)
            );
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,14));
        }
    }
}
