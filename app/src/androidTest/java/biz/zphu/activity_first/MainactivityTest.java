package biz.zphu.activity_first;
import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.content.Context;
import android.preference.PreferenceManager;
import android.content.Intent;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import android.view.View;
import android.widget.EditText;
import android.content.SharedPreferences;

public class MainactivityTest {

    private MainActivity Maact;
    private EditText textBox2;
    private Intent intent;
    private SharedPreferences.Editor preferencesEditor;

    private SharedPreferences.Editor preedit;
    private EditText editTextBoxEntry;
    Instrumentation.ActivityMonitor Monitor = getInstrumentation().addMonitor(Main2Activity.class.getName(), null, false);

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class, true,
            false);
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
    // this will test when user enter..
    public void testViewswilldisplaywhenenter(){
        View buttonView = Maact.findViewById(R.id.passButton9);
        View editTextView = Maact.findViewById(R.id.textBox2);
        assertNotNull(editTextView); assertNotNull(buttonView);
    }
    @Test
    //this will test when click button go
    public void TestopenOfSendButtonClick() {

        onView(withId(R.id.passButton9)).perform(click());

        Activity secondActivity = getInstrumentation().waitForMonitorWithTimeout(Monitor, 5000);

        assertNotNull(secondActivity);

    }
    @Test
    public void testValueUserInput() {
        editTextBoxEntry.clearComposingText();
        String testUsername = "test"; preedit.putString("username", testUsername); preedit.commit();
    }

    @Test
    // this will test when user doent enter anything
    public void testEditTextForEmptyInput() {

        assertFalse(" ".isEmpty());
        onView(withId(R.id.textBox2)).perform(clearText()); onView(withId(R.id.passButton9)).perform(click());
    }

    // Tests for data storage & retrieval
    @Test
    // this will test for storage and retrieval when
    public void populateUsernameFromSharedPrefsTest() {
        String testUsername = "test";
        // Set SharedPreferences data
        preedit.putString("username", testUsername);
        preedit.commit();
        // Launch activity
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.textBox2))
                .check(matches(isDisplayed()));

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

