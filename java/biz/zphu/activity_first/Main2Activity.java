package biz.zphu.activity_first;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        text = (TextView)findViewById(R.id.text);

        Intent intent = getIntent();
        String str = intent.getStringExtra("message");
        text.setText(str);
    }
}