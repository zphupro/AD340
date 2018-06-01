package biz.zphu.activity_first;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Maincam extends AppCompatActivity  {

    String url;

    {
        url = "https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2";
    }

    RecyclerView camView;
    CamAdapter mAdapter;
    ArrayList<Camhelper> maincamArrList = new ArrayList<Camhelper>();
    RequestQueue requestQueue;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_cam_main);


        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.live_cam_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Web Camera");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);






        camView = findViewById(R.id.camList);
        camView.setHasFixedSize(true);
        camView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CamAdapter(Maincam.this, maincamArrList);
        camView.setAdapter(mAdapter);


        requestQueue = Volley.newRequestQueue(this);
        jsonreader();
    }





    void jsonreader() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Features");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject feature = jsonArray.getJSONObject(i);





                                JSONArray cameras;
                                cameras = feature.getJSONArray("Cameras");
                                int j = 0;
                                while (j < cameras.length()) {
                                    JSONObject camera = cameras.getJSONObject(j);
                                    String type;
                                    type = camera.getString("Type");
                                    String imageURL;
                                    imageURL = camera.getString("ImageUrl");
                                    if (!"sdot".equals(type)) {
                                        imageURL = "http://images.wsdot.wa.gov/nw/" + imageURL;
                                    } else {
                                        imageURL = "http://www.seattle.gov/trafficcams/images/" + imageURL;
                                    }
                                    String camDescription;
                                    camDescription = camera.getString("Description");
                                    final boolean add = maincamArrList.add(new Camhelper(camDescription, imageURL, type));
                                    
                                    j++;
                                }
                            }

                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });
        requestQueue.add(request);
    }

    //Menu /Setting
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            Toast.makeText(getApplicationContext(),"Setting Clicked", Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }








}



