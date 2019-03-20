package com.utsavgupta.cmclage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.util.Arrays;
import java.util.List;

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class MapsMarkerActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnPolylineClickListener {
    private static final int COLOR_BLACK_ARGB = 0xff000000;
    private static final int COLOR_WHITE_ARGB = 0xffffffff;
    private static final int COLOR_GREEN_ARGB = 0xff388E3C;
    private static final int COLOR_PURPLE_ARGB = 0xff81C784;
    private static final int COLOR_ORANGE_ARGB = 0xffF57F17;
    private static final int COLOR_BLUE_ARGB = 0xffF9A825;

    private static final int POLYLINE_STROKE_WIDTH_PX = 12;
    private static final int POLYGON_STROKE_WIDTH_PX = 8;
    private static final int PATTERN_DASH_LENGTH_PX = 20;
    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);

    // Create a stroke pattern of a gap followed by a dot.
    private static final List<PatternItem> PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT);

    // Create a stroke pattern of a gap followed by a dash.
    private static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);

    // Create a stroke pattern of a dot followed by a gap, a dash, and another gap.
    private static final List<PatternItem> PATTERN_POLYGON_BETA =
            Arrays.asList(DOT, GAP, DASH, GAP);
    private Marker destinationMarker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap map) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng sydney = new LatLng(12.9246101,79.1357963);
        Marker silver_gate=map.addMarker(new MarkerOptions().position(new LatLng(12.9242864,79.1344753)));
        Marker main_opd=map.addMarker(new MarkerOptions().position(new LatLng(12.9240055,79.1356892)))  ;
        Marker blood_collection=map.addMarker(new MarkerOptions().position(new LatLng(12.9245178,79.1354926)))  ;
        Marker isscc_building=map.addMarker(new MarkerOptions().position(new LatLng(12.9246101,79.1357963)))  ;
        Marker pmr_building=map.addMarker(new MarkerOptions().position(new LatLng(12.925232,79.1357144)))  ;
        Marker a_ward=map.addMarker(new MarkerOptions().position(new LatLng(12.9257034,79.1356096)))  ;
        Marker william_building=map.addMarker(new MarkerOptions().position(new LatLng(12.9255656,79.1350716)))  ;
        Marker haematology=map.addMarker(new MarkerOptions().position(new LatLng(12.925509,79.1356326)))  ;
        haematology.setTitle("Haematology");

       // IconFactory iconFactory = IconFactory.getInstance(mapbox.this);
        //  Drawable iconDrawable = ContextCompat.getDrawable(mapbox.this, R.drawable._ic_hospitall);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.locpiin);
        haematology.setIcon(icon);
        blood_collection.setTitle("Blood Collection Area");
        blood_collection.setIcon(icon);
        silver_gate.setTitle("Silver Gate");
        silver_gate.setIcon(icon);
        main_opd.setTitle("Main OPD");
        main_opd.setIcon(icon);
        main_opd.setSnippet("Main OPD");
        william_building.setTitle("William Building");
        william_building.setIcon(icon);
        isscc_building.setTitle("ISSCC Building");
        isscc_building.setIcon(icon);
        pmr_building.setTitle("PMR Building");
        pmr_building.setIcon(icon);
        a_ward.setTitle("A Ward");
        a_ward.setIcon(icon);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,18));
        Polyline sg_mo = map.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(12.9242864,79.1344753),
                        new LatLng(12.9240055,79.1356892)));
        // Store a data object with the polyline, used here to indicate an arbitrary type.
        sg_mo.setTag("Silver Gate to Main OPD");
        // Style the polyline.
       stylePolyline(sg_mo);
        Polyline sg_aw = map.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(12.9242864,79.1344753),
                        new LatLng(12.9257034,79.1356096)));
        // Store a data object with the polyline, used here to indicate an arbitrary type.
        sg_aw.setTag("Silver Gate to A Ward");
        // Style the polyline.
        stylePolyline(sg_mo);
        Polyline sg_wb = map.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(12.9242864,79.1344753),
                        new LatLng(12.9255656,79.1350716)));
        // Store a data object with the polyline, used here to indicate an arbitrary type.
        sg_wb.setTag("Silver Gate to William Building");
        // Style the polyline.
        stylePolyline(sg_wb);
       map.setOnPolylineClickListener(this);

        Polygon polygon1 = map.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(12.9242864,79.1344753),
                        new LatLng(12.9240055,79.1356892),
                        new LatLng(12.9245178,79.1354926),
                        new LatLng(12.9246101,79.1357963),
                        new LatLng(12.925232,79.1357144),
                        new LatLng(12.9257034,79.1356096),
                        new LatLng(12.9255656,79.1350716),
                        new LatLng(12.925509,79.1356326)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon1.setTag("alpha");
       // map.moveCamera(CameraUpdateFactory.zoomBy(50));
       // map.addPolyline(polyline1);
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                // Clearing the markers and polylines in the google map
                if(destinationMarker!=null)
                {
                    destinationMarker.remove();
                }
                destinationMarker=map.addMarker(new MarkerOptions().position(point));

               // map.addMarker(new MarkerOptions().position(point));


            }
        });
    }
    private void stylePolyline(Polyline polyline) {
        String type = "";
        // Get the data object stored with the polyline.
        if (polyline.getTag() != null) {
            type = polyline.getTag().toString();
        }

        switch (type) {
            // If no type is given, allow the API to use the default.
            case "A":
                // Use a custom bitmap as the cap at the start of the line.
                polyline.setStartCap(
                        new CustomCap(
                                BitmapDescriptorFactory.fromResource(R.drawable.ic_arrow), 10));
                break;
            case "B":
                // Use a round cap at the start of the line.
                polyline.setStartCap(new RoundCap());
                break;
        }

        polyline.setEndCap(new RoundCap());
        polyline.setWidth(POLYLINE_STROKE_WIDTH_PX);
        polyline.setColor(COLOR_ORANGE_ARGB);
        polyline.setJointType(JointType.ROUND);
    }
    @Override
    public void onPolylineClick(Polyline polyline) {

        if ((polyline.getPattern() == null) || (!polyline.getPattern().contains(DOT))) {
            polyline.setPattern(PATTERN_POLYLINE_DOTTED);
        } else {
            // The default pattern is a solid stroke.
            polyline.setPattern(null);
        }

        Toast.makeText(this,  polyline.getTag().toString(),
                Toast.LENGTH_SHORT).show();
    }


}
