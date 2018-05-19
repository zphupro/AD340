package biz.zphu.activity_first;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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


class Maincam extends AppCompatActivity  {

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







}



