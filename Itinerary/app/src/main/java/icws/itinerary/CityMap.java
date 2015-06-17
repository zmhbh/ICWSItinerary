package icws.itinerary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.*;
import android.util.Log;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.app.Activity;
import android.view.View;
import android.location.Location;
import utility.AccountUtils;
import android.accounts.Account;

import android.accounts.AccountManager;

import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class CityMap extends Activity {
    static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
    static final int REQUEST_CODE_PICK_ACCOUNT = 1002;

    //progress dialog
    private ProgressDialog progressDialog;

    private LocationManager locationManager;
    private GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_map);


        new Load().execute();
    }

    @Override
    protected void onResume(){
        super.onResume();

        createMapView();
        addMarker();
        //&&checkUserAccount()
//        if(checkPlayServices()){
//            createMapView();
//            addMarker();
//        }
    }
    private boolean checkPlayServices() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
                showErrorDialog(status);
            } else {
                Toast.makeText(this, "This device is not supported.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    void showErrorDialog(int code) {
        GooglePlayServicesUtil.getErrorDialog(code, this, REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
    }

    private boolean checkUserAccount() {
        String accountName = AccountUtils.getAccountName(this);
        if (accountName == null) {
            // Then the user was not found in the SharedPreferences. Either the
            // application deliberately removed the account, or the application's
            // data has been forcefully erased.
            showAccountPicker();
            return false;
        }

        Account account = AccountUtils.getGoogleAccountByName(this, accountName);
        if (account == null) {
            // Then the account has since been removed.
            AccountUtils.removeAccount(this);
            showAccountPicker();
            return false;
        }

        return true;
    }

    private void showAccountPicker() {
        Intent pickAccountIntent = AccountPicker.newChooseAccountIntent(null, null,
                new String[] { GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE }, true, null, null, null, null);
        startActivityForResult(pickAccountIntent, REQUEST_CODE_PICK_ACCOUNT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_RECOVER_PLAY_SERVICES:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Google Play Services must be installed and up-to-date.",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            case REQUEST_CODE_PICK_ACCOUNT:
                if (resultCode == RESULT_OK) {
                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    AccountUtils.setAccountName(this, accountName);
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "This application requires a Google account.",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    // Route Button
    public void goGoogleMaps(View v){
        String destination = "Millennium Broadway Hotel 145 West 44th Street, New York, NY 10036";
       googleMap.setMyLocationEnabled(true);
        //Location userLocation = googleMap.getMyLocation();
        Criteria criteria =new Criteria();
        String provider=locationManager.getBestProvider(criteria,false);
        Location location = locationManager.getLastKnownLocation(provider);
        if (location==null)
        Toast.makeText(getApplicationContext(),
                "error",Toast.LENGTH_SHORT).show();
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http:/a/maps.google.com/maps?"
                + "saddr="+ location.getLatitude()+","+ location.getLongitude() + "&daddr="+destination));

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
            LatLng latlng = new LatLng(40.757116, -73.984865);
            googleMap.addMarker(new MarkerOptions()
                            .position(latlng)
                            .title("Millennium Broadway Hotel")
                            .snippet("145 West 44th Street, New York")
                            .draggable(true)
            );
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,14));
        }
    }


    class Load extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(CityMap.this);
            progressDialog.setMessage("loading Map");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            // test initialization
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // check if enabled and if not send user to the GSP settings
            // Better solution would be to display a dialog and suggesting to
            // go to the settings
            if (!enabled) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
            checkPlayServices();

            return null;

        }
        @Override
        protected void onPostExecute(String file_url){

            progressDialog.dismiss();

        }
    }
}
