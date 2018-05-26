package biz.zphu.activity_first;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.squareup.picasso.Picasso.with;
import static java.util.Objects.requireNonNull;

class CamAdapter extends RecyclerView.Adapter<CamAdapter.Webcametrafficviewholder>  {


     private Context context;
    private ArrayList<Camhelper> Webcamearrayprocess;
    private ArrayList<Camhelper> Getlistinsaprate;

    //create an adapter
    CamAdapter(Context context, ArrayList<Camhelper> trafficCamArrayList){
        this.Webcamearrayprocess = trafficCamArrayList;
        this.context = context;
        Getlistinsaprate = trafficCamArrayList;
    }
    @Override
    public int getItemCount() {
        return Webcamearrayprocess.size();
    }


    class Webcametrafficviewholder extends RecyclerView.ViewHolder {


        Webcametrafficviewholder(View itemView) {
            super(itemView);
            Webcamephoto = itemView.findViewById(R.id.image_view);
            WebcameLocation = itemView.findViewById(R.id.text_view_ownership);

            WebcamTittle = itemView.findViewById(R.id.Webtittle);
        }

        TextView WebcamTittle;
        TextView WebcameLocation;
        ImageView Webcamephoto;


    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull Webcametrafficviewholder holder, int position) {
        Camhelper trafficCam = Webcamearrayprocess.get(position);
        String imageURL = trafficCam.getImage();
        String Webcamdetails = trafficCam.getLabel();
        String Webcametype = trafficCam.getType();
        requireNonNull(holder).WebcameLocation.setText(Webcametype);

        with(context).load(imageURL).fit().centerInside().into(holder.Webcamephoto);

        requireNonNull(holder).WebcamTittle.setText(Webcamdetails);
    }

    @NonNull
    @Override
    public Webcametrafficviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.a_cam_recyclerview, parent, false);
        return new Webcametrafficviewholder(view);
    }


}

