package biz.zphu.activity_first;


import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import static android.Manifest.*;
import static android.widget.Toast.*;
import static com.google.android.gms.maps.GoogleMap.*;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker;

class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, OnMarkerClickListener, OnInfoWindowClickListener {

    private static final ThreadLocal<Integer> LOCATION_PERMISSION_REQUEST_CODE;

    static {
        LOCATION_PERMISSION_REQUEST_CODE = new ThreadLocal<Integer>() {
            @Override
            protected Integer initialValue() {
                return 1;
            }
        };
    }


    List<Camhelper> webcamArrList;
    RequestQueue requestQueue;
    ArrayList<Marker> markers;
    Location lastLocation;
    Marker currLocMarker;
    Geocoder geocoder;
    Marker currMarker;

    //boolean not_first_time_showing_info_window;
    final ThreadLocal<List<Address>> geocoded = new ThreadLocal<List<Address>>();
    private Hashtable<String, String> allMarkers = new Hashtable<>();



    public MapsActivity() {
        onMyLocationClickListener = new OnMyLocationClickListener() {
            @Override
            public void onMyLocationClick(@NonNull Location location) {

                mMap.setMinZoomPreference(6);

                CircleOptions circleOptions = new CircleOptions();
                circleOptions.center(new LatLng(location.getLatitude(),
                        location.getLongitude())).radius(220);

                circleOptions.fillColor(Color.RED).strokeWidth(6);

                final Circle circle = mMap.addCircle(circleOptions);
            }
        };
    }
    GoogleMap mMap;
    GoogleMap mGoogleMap;
    LocationManager locationManager;
    LocationListener locationListener;
    RecyclerView camView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        final boolean b = getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }



    /**

     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onInfoWindowClick(Marker marker) {
        makeText(this, "Info window clicked",
                LENGTH_SHORT).show();
        marker.getTag();
        marker.getTitle();
        marker.getId();

    }



    @Override
    public boolean onMarkerClick(Marker marker) {
        switch (Log.d("MARKERCLICK", "value: ")) {
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        webcamArrList = new ArrayList<>();

        webcamArrList = parseJson();


        // Add a marker in BC and move the camera
        LatLng bc;
        bc = new LatLng(53.7267, -127.6476);
        final Marker bc1 = mMap.addMarker(new MarkerOptions().position(bc).title("BC"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bc));

        locationPermitted();
        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        mMap.setOnMyLocationClickListener(onMyLocationClickListener);

        mMap.setMinZoomPreference(6);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnInfoWindowClickListener(this);


        mMap.setOnMarkerClickListener(this);

        // Set a listener for info window events.


    }

    public List<Camhelper> parseJson() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Getting Ready..");
        progressDialog.show();

        String url = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array;
                    array = response.getJSONArray("Features");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject camObj;
                        camObj = array.getJSONObject(i);
                        String camLabel;
                        camLabel = "";
                        String camImage;
                        camImage = "";
                        String camOwnership;
                        camOwnership = "";
                        String camAdd;
                        camAdd = "";


                        Camhelper camera = new Camhelper(camLabel, camImage, camOwnership);
                        JSONArray camLocation = camObj.getJSONArray("PointCoordinate");

                        camera.setLatitude(camLocation.getDouble(0));
                        camera.setLongitude(camLocation.getDouble(1));


                        JSONArray camArray;
                        camArray = camObj.getJSONArray("Cameras");
                        int j;
                        for (j = 0; j < camArray.length(); j++) {
                            JSONObject cameras = camArray.getJSONObject(j);
                            camera.setLiveCamID(cameras.getString("Id"));
                            camera.setLabel(cameras.getString("Description"));
                            camera.setType(cameras.getString("Type"));
                            camera.setImageUrl(cameras.getString("ImageUrl"));
                        }

                        MarkerOptions markerOptions;
                        markerOptions = new MarkerOptions();
                        LatLng newCamLocation = new LatLng(camera.getLatitude(),
                                camera.getLongitude());
                        final MarkerOptions position;
                        position = markerOptions.position(newCamLocation);
                        final MarkerOptions title;
                        title = markerOptions.title(camera.getLabel());
                        final MarkerOptions snippet;
                        snippet = markerOptions.snippet(camera.getLabel());
                        markerOptions.icon(camera.getType().equals(("wsdot")) ?
                                defaultMarker(BitmapDescriptorFactory.HUE_CYAN) :
                                defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

                        mMap.setInfoWindowAdapter(new Mapfuntionpopupwindow(MapsActivity.this));
                        Marker m;
                        m = mMap.addMarker((markerOptions));
                        m.setTag(camera);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(newCamLocation));

                        boolean add;
                        if (webcamArrList.add(camera)) add = true;
                        else add = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                switch (Log.e("Volley", error.toString())) {
                }
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        final Request<JSONObject> add;
        add = requestQueue.add(jsonObjectRequest);

        return webcamArrList;
    }

    private OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    mMap.setMinZoomPreference(6);
                    return false;
                }
            };

    private OnMyLocationClickListener onMyLocationClickListener;


    private void locationPermitted() {
        if (ContextCompat.checkSelfPermission(this,
                permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{permission.ACCESS_FINE_LOCATION,
                            permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE.get());
        }
    }


    /**
     * sharelocation
     */
    private void showDefaultLocation() {
        Toast.makeText(this, new StringBuilder().append("Local from ").append("Hello Seattle").toString(),
                LENGTH_SHORT).show();AtomicReference<LatLng> northSeattleCollege = new AtomicReference<>(new LatLng(47.608013, -122.335167));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(northSeattleCollege.get()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (!(requestCode == LOCATION_PERMISSION_REQUEST_CODE.get())) {
            return;
        }
        if ((grantResults.length > 0))
            if ((grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                locationPermitted();
            }
        showDefaultLocation();
        return;


    }
}






