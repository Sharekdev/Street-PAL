package com.sharekeg.streetpal.safeplace;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sharekeg.streetpal.Androidversionapi.ApiInterface;
import com.sharekeg.streetpal.Home.HomeActivity;
import com.sharekeg.streetpal.R;
import com.sharekeg.streetpal.safeplace.nearbyplaceutil.Example;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SafePlaceActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_CHECK_SETTINGS = 10;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 2;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private ApiInterface myAPI;
    private Retrofit retrofit;
    private RecyclerView recylcerView;
    private Button btnCallVolunteer, btnMarkSafe, btnNavigate;
    private Chronometer mChronometer;
    private static MediaRecorder mediaRecorder;
    private static MediaPlayer mediaPlayer;
    private Call<List<Guide>> myCall;
    private static String audioFilePath;
    private static ImageView ivStop, ivPause, ivPlay;
    private boolean isRecording = false;
    private long timeDifference;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private int PROXIMITY_RADIUS = 500;
    private String Type;
    double closest_distance=0;
    private LatLng nearest_place,latLng;
    private MarkerOptions markerOptions;
    private String placeName;
    private String vicinity;

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_palce);

        checkLocationPermission();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        SharedPreferences navTagPref=getSharedPreferences("Tag",MODE_PRIVATE);
        Type=navTagPref.getString("NavigationTag","");



        mChronometer = (Chronometer) findViewById(R.id.chrono);
        //recordButton = (Button) findViewById(R.id.recordButton);
        ivStop = (ImageView) findViewById(R.id.imStop);
        ivPlay = (ImageView) findViewById(R.id.imPlay);
        ivPlay.setVisibility(View.INVISIBLE);
        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivPlay.setVisibility(View.INVISIBLE);
                ivPause.setVisibility(View.VISIBLE);
                mChronometer.start();
                mChronometer.setBase(timeDifference + SystemClock.elapsedRealtime());


            }
        });


        ivPause = (ImageView) findViewById(R.id.imPause);
        ivPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivPause.setVisibility(View.INVISIBLE);
                ivPlay.setVisibility(View.VISIBLE);
                timeDifference = 0;
                mChronometer.stop();
                timeDifference = mChronometer.getBase() - SystemClock.elapsedRealtime();


            }
        });

        ivStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
                Toast.makeText(SafePlaceActivity.this, R.string.txtRecordingstop, Toast.LENGTH_SHORT).show();

            }
        });


        audioFilePath =
                Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/Recordevidence.3gp";

        recylcerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recylcerView.setLayoutManager(new LinearLayoutManager(this));

        btnMarkSafe = (Button) findViewById(R.id.btnMarkSafe);
        btnMarkSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();
                Intent i = new Intent(SafePlaceActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
        btnCallVolunteer = (Button) findViewById(R.id.btnCallVolunteer);
        btnCallVolunteer.setEnabled(false);


//        btnNavigate = (Button) findViewById(R.id.btnNavigate);
//        btnNavigate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (markerClicked == false) {
//                    Toast.makeText(getApplicationContext(), "Please Click The Place You Want To Navigate On The Map", Toast.LENGTH_LONG).show();
//                } else if (markerClicked == true) {
////                    clearPath();
////                    polylineOptions=new PolylineOptions()
////                            .add(latLng)
////                            .add(MarkerPosition)
////                            .color( Color.parseColor( "#CC0000FF" ))
////                            .width( 5 )
////                            .visible( true );
////
////                    path =mGoogleMap.addPolyline(new PolylineOptions());
////
////
////                    mGoogleMap.addPolyline(polylineOptions);
//                    sendRequest();
//
//                }
//
//            }
//        });


//        retrofit = new Retrofit.Builder()
//                .baseUrl("https://jsonplaceholder.typicode.com")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//
//        myAPI = retrofit.create(ApiInterface.class);
//        myCall = myAPI.getGuide();
//        myCall.enqueue(new Callback<List<Guide>>() {
//            @Override
//            public void onResponse(Call<List<Guide>> call, Response<List<Guide>> response) {
//                progressDialoge.dismiss();
//                List<Guide> myResponse = response.body();
//                GuideAdapter adapter = new GuideAdapter(SafePlaceActivity.this, myResponse);
//
//                recylcerView.setAdapter(adapter);
//
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Guide>> call, Throwable t) {
//                progressDialoge.dismiss();
//
//                Toast.makeText(SafePlaceActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
//
//            }
//        });


    }

    protected void startRecording() {
        if (!hasMicrophone()) {
            ivStop.setEnabled(false);
            //recordButton.setEnabled(false);
        } else {
            ivStop.setEnabled(false);
        }

        //recordButton.setOnClickListener(new View.OnClickListener() {
        //  @Override
        //public void onClick(View v) {


        isRecording = true;
        ivStop.setEnabled(true);
        //recordButton.setEnabled(false);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(audioFilePath);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaRecorder.start();

    }

    protected void stopRecording() {
        ivStop.setEnabled(false);
        mChronometer.setBase(SystemClock.elapsedRealtime() - SystemClock.elapsedRealtime());
        mChronometer.stop();

        if (isRecording) {
            //recordButton.setEnabled(false);
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
        } else {
            mediaPlayer.release();
            mediaPlayer = null;
            //recordButton.setEnabled(true);
        }

    }


    protected boolean hasMicrophone() {
        PackageManager pmanager = this.getPackageManager();
        return pmanager.hasSystemFeature(
                PackageManager.FEATURE_MICROPHONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startRecording();
        mGoogleApiClient.connect();


    }

    @Override
    protected void onPause() {
        super.onPause();
        mChronometer.stop();
        timeDifference = mChronometer.getBase() - SystemClock.elapsedRealtime();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (checkLocationPermission()) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                //Request location updates:
                mGoogleApiClient.connect();
            }
        }
        mChronometer.setBase(timeDifference + SystemClock.elapsedRealtime());
        mChronometer.start();
    }


    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {


        checkLocationPermission();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.
                PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission
                .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CHECK_SETTINGS);
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            updateUserLocation();
        }
        build_retrofit_and_get_response(Type, mLastLocation.getLatitude(), mLastLocation.getLongitude());

        createLocationRequest();
    }

    Marker userMarker;

    private void updateUserLocation() {
        LatLng userLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        if (userMarker == null) {
            userMarker = mMap.addMarker(new MarkerOptions()
                    .position(userLatLng).title(getApplicationContext().getResources().getString(R.string.mapyourLocation)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15));
        } else {
            userMarker.setPosition(userLatLng);
        }
        build_retrofit_and_get_response(Type, mLastLocation.getLatitude(), mLastLocation.getLongitude());

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    protected void createLocationRequest() {
        final LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        final PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can
                        // initialize location requests here.

                        checkLocationPermission();
                        if (ActivityCompat.checkSelfPermission(SafePlaceActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(SafePlaceActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, SafePlaceActivity.this);

                        build_retrofit_and_get_response(Type, mLastLocation.getLatitude(), mLastLocation.getLongitude());

//                        Toast.makeText(SafePlaceActivity.this, R.string.txtSuccess, Toast.LENGTH_SHORT).show();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            Toast.makeText(SafePlaceActivity.this, R.string.resolutionRequired, Toast.LENGTH_SHORT).show();
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    SafePlaceActivity.this,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Toast.makeText(SafePlaceActivity.this, R.string.txtSettingsChange, Toast.LENGTH_SHORT).show();
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        //  Toast.makeText(this, location.getLatitude() + " , " + location.getLongitude(), Toast.LENGTH_SHORT).show();
        mLastLocation = location;
        updateUserLocation();
    }

    public void onClick(View view) {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (Exception e) {
            // TODO: Handle the error.
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                showSearchedPlaceOnMap(place.getLatLng());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
            } else if (resultCode == RESULT_CANCELED) {
            }
        }
    }

    Marker placeMarker;

    private void showSearchedPlaceOnMap(LatLng latLng) {
        if (placeMarker == null) {
            placeMarker = mMap.addMarker(new MarkerOptions().position(latLng).
                    icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location)));
        } else {
            placeMarker.setPosition(latLng);
        }

        if (mLastLocation != null) {
            LatLng userLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            GoogleMapsUtilities.getAndDrawPath(this, mMap, userLatLng, latLng, false);
        }


    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(SafePlaceActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        mGoogleApiClient.connect();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }



    @Override
    public void onBackPressed() {
        // do nothing.
    }


    private void build_retrofit_and_get_response(String schoolText, final double lat, final double lon) {

        Log.i("latlng", lat + " , " + lon);

        String url = "https://maps.googleapis.com/maps/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface service = retrofit.create(ApiInterface.class);

        Call<Example> call = service.getNearbyPlaces(schoolText, lat + "," + lon, PROXIMITY_RADIUS);
        Log.i("latlng", lat + " , " + lon);
        Log.i("Type ", schoolText);


        call.enqueue(new Callback<Example>() {

            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {


                try {


                    // mMap.clear();
                    // This loop will go through all the results and add marker on each location.
                    for (int i = 0; i < response.body().getResults().size(); i++) {


                        Log.d(" lat and long", response.body().getResults().get(i).getGeometry().getLocation().getLat() + " , " + response.body().getResults().get(i).getGeometry().getLocation().getLng());
                        Double lat = response.body().getResults().get(i).getGeometry().getLocation().getLat();
                        Double lng = response.body().getResults().get(i).getGeometry().getLocation().getLng();
                        placeName = response.body().getResults().get(i).getName();
                        vicinity = response.body().getResults().get(i).getVicinity();
                        markerOptions = new MarkerOptions();
                        latLng = new LatLng(lat, lng);

                        LatLng userLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                        double distance= CalculationByDistance(userLatLng,latLng);

                        if(i==0){
                            closest_distance=distance;
                            nearest_place=latLng;
                        }
                        else if(distance<=closest_distance){
                            closest_distance=distance;
                            nearest_place=latLng;
                        }

//                        // Position of Marker on Map
//                        markerOptions.position(latLng);
//                        // Adding Title to the Marker
//                        markerOptions.title(placeName + " : " + vicinity);
//                        // Adding Marker to the Camera.
//                        Marker m = mMap.addMarker(markerOptions);
//                        // Adding colour to the marker
//                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                        // move map camera
//                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));


//                LatLng latLng = new LatLng(lat, lon);
//                // Position of Marker on Map
//                // Adding Title to the Marker
//                if (userMarker == null) {
//
//
//                    userMarker = mMap.addMarker(new MarkerOptions()
//                            .position(latLng).title("test"));
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//                } else {
//                    userMarker.setPosition(latLng);
//                    Log.i("latLng", latLng.toString());
//
//                }
                    }

                    markerOptions.position(nearest_place);
                    markerOptions.title(placeName + " : " + vicinity);
                    mMap.addMarker(markerOptions);
//                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    Log.i("nearestPlace",nearest_place.toString());
                    showSearchedPlaceOnMap(nearest_place);


                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<Example> call, Throwable t) {

                Toast.makeText(SafePlaceActivity.this, "Check internet connection", Toast.LENGTH_SHORT).show();

            }


        });

    }
    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }




}




