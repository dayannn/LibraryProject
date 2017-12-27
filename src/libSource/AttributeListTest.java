package libSource;

import libSource.Attributes.AttributeAccessMode;
import libSource.Attributes.AttributeAccessType;
import libSource.Attributes.AttributeLanguage;
import libSource.Attributes.BaseAttribute;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by dayan on 22.10.2017.
 */
public class AttributeListTest {

    @Test
    public void constructor() throws Exception {
        AttributeList AL_TEST_1 = new AttributeList();
        AttributeList AL_TEST_2;
        AttributeList AL_TEST_3;
        AttributeAccessType AAType_TEST = new AttributeAccessType("Свободный");
        AttributeAccessMode AAMode_TEST = new AttributeAccessMode("Внутренний(в определенном месте))");

        AL_TEST_2 = new AttributeList(AL_TEST_1);
        assertEquals(AL_TEST_2.size(), 0);

        AL_TEST_1.add(AAType_TEST);
        AL_TEST_1.add(AAMode_TEST);
        AL_TEST_3 = new AttributeList(AL_TEST_1);
        assertEquals(AL_TEST_1.get(0), AL_TEST_3.get(0));
        assertEquals(AL_TEST_1.get(1), AL_TEST_3.get(1));
        assertEquals(AL_TEST_1.size(), AL_TEST_3.size());
    }

    @Test
    public void getIterator() throws Exception {
        AttributeList AL_TEST = new AttributeList();
        AL_TEST.getIterator();

    }


    @Test
    public void size() throws Exception {
        AttributeList AL_TEST = new AttributeList();
        AttributeAccessType AAType_TEST = new AttributeAccessType("Свободный");
        AttributeAccessMode AAMode_TEST = new AttributeAccessMode("Внутренний(в определенном месте))");
        AttributeLanguage AALang_TEST = new AttributeLanguage("Язык");
        // removing from empty list
        //
        //        assertEquals(AL_TEST.size(),0);
        //
        //        AL_TEST.add(AAType_TEST);
        //        assertEquals(AL_TEST.size(), 1);
        //
        //        AL_TEST.add(AAMode_TEST);
        //        AL_TEST.add(AALang_TEST);
        //        assertEquals(AL_TEST.size(), 3);
        //
        //        AL_TEST.remove(1);
        //        assertEquals(AL_TEST.size(), 2);
        //
        //        AL_TEST.remove(0);
        //        AL_TEST.remove(0);
        //        assertEquals(AL_TEST.size(),0);
        AL_TEST.remove(0);
        AL_TEST.remove(0);
        assertEquals(AL_TEST.size(),0);
    }

    @Test
    public void add() throws Exception {
        AttributeList AL_TEST = new AttributeList();
        AttributeAccessType AAType_TEST = new AttributeAccessType("Свободный");
        AttributeLanguage AALang_TEST = new AttributeLanguage("Язык");


        AL_TEST.add(AALang_TEST);
        Iterator<BaseAttribute> it = AL_TEST.getIterator();
        assertEquals(it.next(), AALang_TEST);

        AL_TEST.add(AAType_TEST);
        it = AL_TEST.getIterator();
        it.next();
        assertEquals(it.next(), AAType_TEST);

    }

    @Test
    public void get() throws Exception {
        AttributeList AL_TEST = new AttributeList();
        AttributeAccessType AAType_TEST = new AttributeAccessType("Свободный");
        AttributeAccessMode AAMode_TEST = new AttributeAccessMode("Внутренний(в определенном месте))");
        AttributeLanguage AALang_TEST = new AttributeLanguage("Язык");

        assertEquals(AL_TEST.get(1), null);

        AL_TEST.add(AALang_TEST);
        AL_TEST.add(AAMode_TEST);
        AL_TEST.add(AAType_TEST);
        assertEquals(AL_TEST.get(0), AALang_TEST);
        assertEquals(AL_TEST.get(1), AAMode_TEST);
        assertEquals(AL_TEST.get(2), AAType_TEST);
    }

    @Test
    public void remove() throws Exception {
        AttributeList AL_TEST = new AttributeList();
        AttributeAccessType AAType_TEST = new AttributeAccessType("Свободный");
        AttributeAccessMode AAMode_TEST = new AttributeAccessMode("Внутренний(в определенном месте))");
        AttributeLanguage AALang_TEST = new AttributeLanguage("Язык");

        AL_TEST.remove(0);
        assertEquals(AL_TEST.size(), 0);

        AL_TEST.add(AAType_TEST);
        AL_TEST.add(AAMode_TEST);
        AL_TEST.add(AALang_TEST);

        AL_TEST.remove(0);
        assertEquals(AL_TEST.get(0), AAMode_TEST);
        assertEquals(AL_TEST.get(1), AALang_TEST);

        AL_TEST.remove(1);
        assertEquals(AL_TEST.get(0), AAMode_TEST);

        AL_TEST.remove(0);
        assertEquals(AL_TEST.size(), 0);

    }

    @Test
    public void set() throws Exception {
        AttributeList AL_TEST = new AttributeList();
        AttributeAccessType AAType_TEST = new AttributeAccessType("Свободный");
        AttributeAccessMode AAMode_TEST = new AttributeAccessMode("Внутренний(в определенном месте))");

        AL_TEST.set(0, AAType_TEST);
        assertEquals(AL_TEST.size(), 0);

        AL_TEST.add(AAType_TEST);
        AL_TEST.set(0, AAMode_TEST);
        assertEquals(AL_TEST.get(0), AAMode_TEST);
    }

    @Test
    public void setAttributeValue() throws Exception {
        AttributeList AL_TEST = new AttributeList();
        AttributeAccessType AAType_TEST = new AttributeAccessType("Свободный");

        AL_TEST.add(AAType_TEST);
        AL_TEST.setAttributeValue(0, "По подписке");
        assertEquals(AL_TEST.get(0).getAttributeValue(), "По подписке");
    }

    @Test
    public void setAttributeValue1() throws Exception {
        AttributeList AL_TEST = new AttributeList();
        AttributeAccessType AAType_TEST = new AttributeAccessType("Свободный");

        AL_TEST.add(AAType_TEST);
        AL_TEST.setAttributeValue(AAType_TEST.getAttributeName(), "По подписке");
        assertEquals(AL_TEST.get(0).getAttributeValue(), "По подписке");

        AL_TEST.setAttributeValue("blabla", "blabla");
        assertEquals(AL_TEST.size(), 1);
        assertEquals(AL_TEST.get(0).getAttributeName(), "access_type_value");
        assertEquals(AL_TEST.get(0).getAttributeValue(), "По подписке");
    }

}