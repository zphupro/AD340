package biz.zphu.activity_first;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class Mapfuntionpopupwindow implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public Mapfuntionpopupwindow(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;

    }

    @Override
    public View getInfoWindow(Marker marker) {

        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.activity_map_popup, null);

        TextView camName = view.findViewById(R.id.webcamName);
        ImageView camPic = view.findViewById(R.id.webcamImage);

        camName.setText(marker.getTitle());


        Camhelper camData = (Camhelper) marker.getTag();
        String imageURL = camData.getImage();
        String camDataLabel = camData.getLabel();

        String camDescription = camData.getLabel();
        String camOwnership = camData.getType();


        Picasso.with(view.getContext()).load(imageURL).error(R.mipmap.ic_launcher_round).resize(480, 405).into(camPic, new MarkerCallback(marker));

        return view;
    }

    // Picasso interface called Callback
    static class MarkerCallback implements Callback {
        Marker marker = null;

        MarkerCallback(Marker marker) {
            this.marker = marker;
        }

        @Override
        public void onError() {
        }

        @Override
        public void onSuccess() {
            if (marker == null) {
                return;
            }

            if (!marker.isInfoWindowShown()) {
                return;
            }



            marker.hideInfoWindow();
            marker.showInfoWindow();
        }
    }


}
