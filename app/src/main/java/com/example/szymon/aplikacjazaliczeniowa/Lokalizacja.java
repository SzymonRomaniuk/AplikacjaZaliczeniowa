package com.example.szymon.aplikacjazaliczeniowa;


        import java.io.FileNotFoundException;
        import java.io.IOException;
        import java.io.PrintWriter;
        import java.util.List;
        import java.util.Locale;

        import android.Manifest;
        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.ContentResolver;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.pm.ActivityInfo;
        import android.content.pm.PackageManager;
        import android.location.Address;
        import android.location.Geocoder;
        import android.location.Location;
        import android.location.LocationListener;
        import android.location.LocationManager;
        import android.os.Bundle;
        import android.provider.Settings;
        import android.support.v4.app.ActivityCompat;

        import android.util.Log;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ProgressBar;
        import android.widget.Toast;

/**
 * Created by Krystian on 29.06.2017.
 */

public class Lokalizacja extends Activity implements OnClickListener {

    private LocationManager locationManager = null;
    private LocationListener locationListener = null;

    private Button btnGetLocation = null;
    private Button saveLocation=null;
    private EditText editLocation = null;
    private ProgressBar pb = null;

    private static final String TAG = "Debug";
    private Boolean flag = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lokalizacja);

        //if you want to lock screen for always Portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        editLocation = (EditText) findViewById(R.id.editTextLocation);

        btnGetLocation = (Button) findViewById(R.id.przycisk);
        btnGetLocation.setOnClickListener(this);
        saveLocation=(Button) findViewById(R.id.przycisk1);
        saveLocation.setOnClickListener(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);




    }


    public void onClick(View v) {
        flag = displayGpsStatus();
        if (flag) {

            Log.v(TAG, "onClick");

            editLocation.setText("Poczekaj, za chwilę wyświetli się Twoja lokalizacja ;)");

            pb.setVisibility(View.VISIBLE);
            locationListener = new MyLocationListener();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            locationManager.requestLocationUpdates(LocationManager
                    .GPS_PROVIDER, 5000, 10, locationListener);

        } else {
            alertbox("Gps Status!!", "Your GPS is: OFF");
        }
    }

    /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.GPS_PROVIDER);
        if(gpsStatus) {
            return true;
        }
        else {
            return false;
        }
    }

    /*----------Method to create an AlertBox ------------- */
    protected void alertbox(String title, String mymessage){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Device's GPS is Disable")
                .setCancelable(false).setTitle("** GPS Status **")
                .setPositiveButton("Gps On", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        // finish the current activity
                        // AlertBoxAdvance.this.finish();
                        Intent myIntent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                        startActivity(myIntent);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        // cancel dialog box
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private class MyLocationListener implements LocationListener{

        public void onLocationChanged(Location loc) {



            editLocation.setText("");
            pb.setVisibility(View.INVISIBLE);

           // Toast.makeText(getBaseContext(), "Location chaned : Lat: " + loc.getLatitude() + "Lng: "
            //        + loc.getLongitude(), Toast.LENGTH_SHORT).show();

            String longitude = "Długość Geograficzna: " + loc.getLongitude();
            Log.v(TAG, longitude);
            
            String latitude = "Szerokość Geograficzna: " + loc.getLatitude();
            Log.v(TAG, latitude);




            String s = longitude + "\n" + latitude;
            editLocation.setText(s);

        }






        public void onProviderDisabled(String provider){}

        public void onProviderEnabled(String provider){}

        public void onStatusChanged(String provider, int status, Bundle extras){}
    }
}
