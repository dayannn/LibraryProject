package libSource;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dayan on 12.11.2017.
 */


public class HelpTest {
    mainwindow mw;
    HelpForm hf;
    @Before
    public void setUp() throws Exception {
        mw = new mainwindow();
        hf = mw.getHelpForm();
    }

    @Test
    public void changeRoleTest() throws Exception{

        assertEquals(hf.isVisible(), true);

    }
}
