package biz.zphu.activity_first;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MoviesList extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);



        Toolbar toolbar = (Toolbar) findViewById(R.id.movie_list_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Movie List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





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
