package biz.zphu.activity_first;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    EditText textBox;
    Button passButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Set up Shared Preference
        SharedPreferences preferences = getSharedPreferences(getString(R.string.save_message) , Context.MODE_PRIVATE);
        textBox = (EditText) findViewById(R.id.textBox2) ;

        String message = "";
        String saveText = preferences.getString(getString(R.string.text) , message);
        textBox.setText(saveText);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }


    public void sendMessage(View view){
        textBox = (EditText)findViewById(R.id.textBox2);
               passButton = (Button)findViewById(R.id.passButton);
        String str = textBox.getText().toString();
        // check if string is valid

        // if string is valid, save in shared prefs & navigate to other activity
        if (noname(str)) {
        Context context = getApplication();
        SharedPreferences preferences = getSharedPreferences(getString(R.string.save_message), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(getString(R.string.text) , str);
        editor.apply();

                    Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                       intent.putExtra("message", str);

                      startActivity(intent);

    }else{

                Toast.makeText(getApplicationContext(), "Please, Enter a valid text", Toast.LENGTH_SHORT).show();

        }
    }

    public boolean noname (String str) {
        return !str.isEmpty();
    }

    public void movieinfo(View view) {
        Intent intent = new Intent(this, Movieitem.class);
        startActivity(intent);

    }


    public void button2(View v){

        Toast.makeText(getApplicationContext(),"Hello", Toast.LENGTH_SHORT).show();
    }

    public void fun(View v){

        Toast.makeText(getApplicationContext(),"Fun", Toast.LENGTH_SHORT).show();
    }

    public void more(View v){

        Toast.makeText(getApplicationContext(),"More", Toast.LENGTH_SHORT).show();
    }




    //-----------------------------------------------------------------
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if(id==R.id.about_us){
            Intent intent = new Intent(this,Aboutapp.class);
            startActivity(intent);

        }else if(id==R.id.movies){
            Intent intent = new Intent(this, Movieitem.class);
            startActivity(intent);
        }
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}