package biz.zphu.activity_first;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import biz.zphu.activity_first.MainActivity;


public class activitylocaltest {
    //private
    private MainActivity mPhu = new MainActivity();

    @Test
    public void entry_isValid() {
        Boolean result = mPhu.noname((" Hello"));
        assertTrue(result);
    }
    @Test
    public void entry_false() {
        Boolean result = mPhu.noname((""));
        assertFalse(result);


    }
}

