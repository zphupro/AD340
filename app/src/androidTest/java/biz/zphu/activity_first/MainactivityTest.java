package biz.zphu.activity_first;


import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.content.Context;
import android.preference.PreferenceManager;
import android.content.Intent;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.*;

import android.view.View;
import android.widget.EditText;
import android.content.SharedPreferences;





import android.widget.EditText;

import static android.support.test.InstrumentationRegistry.getInstrumentation;


public class MainactivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class, true,
            false); // Activity is not launched immediately

    private MainActivity Maact;
    private EditText editText1;
    private Intent intent;
    private SharedPreferences.Editor preedit;
    private EditText editTextBoxEntry;

    Instrumentation.ActivityMonitor aMonitor = getInstrumentation().addMonitor(Main2Activity.class.getName(), null, false);

    public MainactivityTest() {
        Maact = null;
    }


    @Before
    public void setUp() throws Exception {
        Maact = mActivityTestRule.getActivity();

        intent = new Intent();
        Context context = getInstrumentation().getTargetContext();

        // create a SharedPreferences editor
        preedit = PreferenceManager.getDefaultSharedPreferences(context).edit();

        // create an Edit Text box for use by main activity
        editTextBoxEntry = (EditText) Maact.findViewById(biz.zphu.activity_first.R.id.textBox2);

    }
    @Test
    public void testViewswilldisplaywhenenter(){
        View buttonView = Maact.findViewById(R.id.passButton9);
        View editTextView = Maact.findViewById(R.id.textBox2);
        assertNotNull(editTextView); assertNotNull(buttonView);
    }



    @Test
    public void onCreate() {
    }


    @Test
    public void onCreateOptionsMenu() {
    }


    @After
    public void tearDown() throws Exception {
        Maact = null;
    }




}

