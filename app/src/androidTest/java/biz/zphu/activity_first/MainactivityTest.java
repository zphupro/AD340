package biz.zphu.activity_first;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;


import org.junit.Rule;





import android.widget.EditText;


public class MainactivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class, true,
            false); // Activity is not launched immediately

    private MainActivity mActivity = null;
    private String mField = " ";
    private Intent intent;
    private String xInput = "T";
   // private SharedPreferences.Editor preferencesEditor;
    private EditText editTextBoxEntry;





        }

    }
}