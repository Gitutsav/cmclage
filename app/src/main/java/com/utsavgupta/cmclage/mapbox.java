package com.utsavgupta.cmclage;

import android.annotation.SuppressLint;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mapbox.android.core.location.LocationEngine;

import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.NavigationViewOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mapbox extends AppCompatActivity implements OnMapReadyCallback, LocationEngineListener,
        PermissionsListener ,MapboxMap.OnMapClickListener{
private MapView mapview;
private MapboxMap map;
private PermissionsManager permissionsManager;
private LocationEngine locationEngine;
private LocationLayerPlugin locationLayerPlugin;
private Location originLocation;
private Point originPosition;
private Point destinationPosition;
private Marker destinationMarker;
private Button startButton;
private NavigationMapRoute navigationMapRoute;
private static final String TAG="mapbox";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this,getString(R.string.access_token));
        setContentView(R.layout.activity_mapbox);

        mapview=(MapView) findViewById(R.id.mapview);
        mapview.setStyleUrl(Style.MAPBOX_STREETS);
        mapview.onCreate(savedInstanceState);
        mapview.getMapAsync(this);
       // originLocation=new Location(12.973545,79.1638017);
        //originLocation.setLatitude(12.973545);
        //originLocation.setLongitude(79.1638017);
        startButton=(Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"1"+originLocation.getLatitude()+"\n"
                        +originLocation.getLongitude(),Toast.LENGTH_LONG).show();
               // startActivity(new Intent(getApplicationContext(),nav_d.class));
                NavigationLauncherOptions.Builder optionsBuilder = NavigationLauncherOptions.builder()
                        .directionsProfile(DirectionsCriteria.PROFILE_CYCLING)
                        .origin(originPosition)
                        .destination(destinationPosition);

                NavigationLauncher.startNavigation(mapbox.this,optionsBuilder.build());
            }
        });

    }


    @Override
    public void onMapReady(MapboxMap mapboxMap) {
     map=mapboxMap;
        Marker silver_gate=map.addMarker(new MarkerOptions().position(new LatLng(12.9242864,79.1344753)));
        Marker main_opd=map.addMarker(new MarkerOptions().position(new LatLng(12.9240055,79.1356892)))  ;
        Marker blood_collection=map.addMarker(new MarkerOptions().position(new LatLng(12.9245178,79.1354926)))  ;
        Marker isscc_building=map.addMarker(new MarkerOptions().position(new LatLng(12.9246101,79.1357963)))  ;
        Marker pmr_building=map.addMarker(new MarkerOptions().position(new LatLng(12.925232,79.1357144)))  ;
        Marker a_ward=map.addMarker(new MarkerOptions().position(new LatLng(12.9257034,79.1356096)))  ;
        Marker william_building=map.addMarker(new MarkerOptions().position(new LatLng(12.9255656,79.1350716)))  ;
        Marker haematology=map.addMarker(new MarkerOptions().position(new LatLng(12.925509,79.1356326)))  ;
        haematology.setTitle("Haematology");
        IconFactory iconFactory = IconFactory.getInstance(mapbox.this);
      //  Drawable iconDrawable = ContextCompat.getDrawable(mapbox.this, R.drawable.hospital);
        haematology.setIcon(iconFactory.fromResource(R.drawable.hospital));
        blood_collection.setTitle("Blood Collection Area");
        blood_collection.setIcon(iconFactory.fromResource(R.drawable.hospital));
        silver_gate.setTitle("Silver Gate");
        silver_gate.setIcon(iconFactory.fromResource(R.drawable.hospital));
        main_opd.setTitle("Main OPD");
        main_opd.setIcon(iconFactory.fromResource(R.drawable.hospital));
        main_opd.setSnippet("Main OPD");
        william_building.setTitle("William Building");
        william_building.setIcon(iconFactory.fromResource(R.drawable.hospital));
        isscc_building.setTitle("ISSCC Building");
        isscc_building.setIcon(iconFactory.fromResource(R.drawable.hospital));
        pmr_building.setTitle("PMR Building");
        pmr_building.setIcon(iconFactory.fromResource(R.drawable.hospital));
        a_ward.setTitle("A Ward");
        a_ward.setIcon(iconFactory.fromResource(R.drawable.hospital));
        MarkerOptions options=new MarkerOptions();

        //map.addOnMapClickListener(this);
     enableLocation();
    }

    private void enableLocation()
    {
if(PermissionsManager.areLocationPermissionsGranted(this)){
initializeLocationEngine();
initializeLocationLayer();
}
else
{
    permissionsManager=new PermissionsManager(this);
    permissionsManager.requestLocationPermissions(this);
}
    }
    @SuppressLint("MissingPermission")
    private void initializeLocationEngine(){
        locationEngine=new LocationEngineProvider(this).obtainBestLocationEngineAvailable();
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();
         Location lastLocation=locationEngine.getLastLocation();
        if(lastLocation!=null)
        {
            originLocation=lastLocation;
            Toast.makeText(this,"1"+originLocation.getLatitude()+"\n"
                    +originLocation.getLongitude(),Toast.LENGTH_LONG).show();
            setCameraPosition(lastLocation);
        }
        else
        {
            locationEngine.addLocationEngineListener(this);
        }
    }
    @SuppressLint("MissingPermission")
    private void initializeLocationLayer(){
        locationLayerPlugin=new LocationLayerPlugin(mapview,map,locationEngine);
        locationLayerPlugin.setLocationLayerEnabled(true);
        locationLayerPlugin.setCameraMode(CameraMode.TRACKING);
        locationLayerPlugin.setRenderMode(RenderMode.NORMAL);
    }
    private void setCameraPosition(Location location){
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),
                location.getLongitude()),15.0));
    }
    @Override
    @SuppressLint("MissingPermission")
    public void onConnected() {
locationEngine.requestLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
if(location!=null)
{
   originLocation=location;
    Toast.makeText(this,"2"+originLocation.getLatitude()+"\n"
            +originLocation.getLongitude(),Toast.LENGTH_LONG).show();
   setCameraPosition(location);
}
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this,permissionsToExplain.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
if(granted)
{
    enableLocation();
}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    @SuppressLint("MissingPermission")
    protected void onStart() {
        super.onStart();
        if(locationEngine!=null)
        {
            locationEngine.requestLocationUpdates();
        }
        if(locationLayerPlugin!=null)
        {
            locationLayerPlugin.onStart();
        }
        mapview.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapview.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(locationEngine!=null)
        {
            locationEngine.removeLocationUpdates();
        }
        if(locationLayerPlugin!=null)
        {
            locationLayerPlugin.onStop();
        }
        mapview.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapview.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapview.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapview.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(locationEngine!=null)
        {
            locationEngine.deactivate();
        }
        mapview.onDestroy();
    }


    @Override
    public void onMapClick(@NonNull LatLng point) {
        if(destinationMarker!=null)
        {
            map.removeMarker(destinationMarker);
        }
    destinationMarker=map.addMarker(new MarkerOptions().position(point));
       // 1,loc_1,EMERGENCY CHEST PAIN UNIT,12.9242864,79.1344753
       // Point emerg=  Point.fromLngLat(12.9242864,79.1344753);
        Marker silver_gate=map.addMarker(new MarkerOptions().position(new LatLng(12.9242864,79.1344753)));

        Marker main_opd=map.addMarker(new MarkerOptions().position(new LatLng(12.9240055,79.1356892)))  ;

        Marker blood_collection=map.addMarker(new MarkerOptions().position(new LatLng(12.9245178,79.1354926)))  ;

        Marker isscc_building=map.addMarker(new MarkerOptions().position(new LatLng(12.9246101,79.1357963)))  ;

        Marker pmr_building=map.addMarker(new MarkerOptions().position(new LatLng(12.925232,79.1357144)))  ;

        Marker a_ward=map.addMarker(new MarkerOptions().position(new LatLng(12.9257034,79.1356096)))  ;

        Marker william_building=map.addMarker(new MarkerOptions().position(new LatLng(12.9255656,79.1350716)))  ;

        Marker haematology=map.addMarker(new MarkerOptions().position(new LatLng(12.925509,79.1356326)))  ;

    destinationPosition= Point.fromLngLat(point.getLongitude(),
            point.getLatitude());
           if(originLocation!=null){ originPosition= Point.fromLngLat(originLocation.getLongitude(),
           originLocation.getLatitude());
           }
          else{
          //    Toast.makeText(this,"3"+originLocation.getLatitude()+"\n"
        //  +originLocation.getLongitude(),Toast.LENGTH_LONG).show();
               originPosition= Point.fromLngLat(12.925232,79.1357144);
          }


    getRoute(originPosition,destinationPosition);
    startButton.setEnabled(true);

    }
    private void getRoute(Point origin,Point destination)
    {
        NavigationRoute.builder()
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        if(response.body()==null)
                        {
                            Log.e(TAG,"No routes found, " +
                                    "check right user and accesss token");
                            return;
                        }
                        else if(response.body().routes().size()==0)
                        {
                            Log.e(TAG,"No routes found");
                            return;
                        }
                        DirectionsRoute currentRoute=response.body().routes().get(0);
                        if(navigationMapRoute!=null)
                        {
                            navigationMapRoute.removeRoute();
                        }
                        else{
                            navigationMapRoute=new NavigationMapRoute(null,mapview,map);
                        }
                        navigationMapRoute.addRoute(currentRoute);

                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                           Log.e(TAG,"Error:"+t.getMessage());
                    }
                });
    }
}
