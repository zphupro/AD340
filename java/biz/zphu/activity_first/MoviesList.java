package biz.zphu.activity_first;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MoviesList extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);


        input();
    }



    public void input(){

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data
        String title = bundle.getString("movieTitle");

        String description = bundle.getString("movieDescription");


        TextView textTitle = (TextView)findViewById(R.id.movieTitle);
        TextView textDescription = (TextView)findViewById(R.id.movieDescription);


        textTitle.setText(title);
        textDescription.setText(description);
    }
}
