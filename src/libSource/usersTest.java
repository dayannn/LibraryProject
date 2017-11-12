package libSource;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dayan on 12.11.2017.
 */
public class usersTest {
    mainwindow mw;
    CardForm cf;
    @Before
    public void setUp() throws Exception {
        mw = new mainwindow();
        cf = mw.getCardForm();

    }

    @Test
    public void changeRoleTest() throws Exception{
        mw.setUserRole(true);
        assertEquals(cf.isAdmin(), true);
        assertEquals(cf.getTabbedPane1().getTabCount(), 7);

        mw.setUserRole(false);
        assertEquals(cf.isAdmin(), false);
        assertEquals(cf.getTabbedPane1().getTabCount(), 6);
    }

}