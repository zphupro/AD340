package biz.zphu.activity_first;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class permissionfindmap {
    private Context context;

    public permissionfindmap(Context context) {
        this.context = context;
    }

    public List<Address> getFromLocation() throws IOException {
        return getFromLocation();
    }

    public List<Address> getFromLocation(Location location) throws IOException {
        Geocoder geocoder;
        geocoder = new Geocoder(context, Locale.getDefault());
        return geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);


    }
}