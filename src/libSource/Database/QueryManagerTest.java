package libSource.Database;

import libSource.AttributeList;
import libSource.Attributes.AttributeAccessMode;
import libSource.Attributes.AttributeAccessType;

import javax.management.Attribute;

import static org.junit.Assert.*;

public class QueryManagerTest {
    private final QueryManager Q_TEST = new QueryManager();
    private AttributeList Attribute_test = new AttributeList();
    private AttributeAccessType AAType_TEST = new AttributeAccessType("Свободный");
    private AttributeAccessMode AAMode_TEST = new AttributeAccessMode("Внутренний(в определенном месте))");

    @org.junit.Before
    public void setUp() throws Exception {


    }

    @org.junit.Test
    public void extendedSelectFromMainTable() throws Exception {
        //AttributeList не содержит аттрибутов
        assertEquals(Q_TEST.extendedSelectFromMainTable(Attribute_test),"SELECT  FROM web_resources ");

        //AttributeList содержит 1 аттрибутов
        Attribute_test.add(AAType_TEST);
        assertEquals(Q_TEST.extendedSelectFromMainTable(Attribute_test),"SELECT  access_type.access_type_value FROM web_resources  INNER JOIN access_type ON web_resources.resource_access_type = access_type.key ");

        //AttributeList содержит 2 аттрибута
        Attribute_test.add(AAMode_TEST);
        assertEquals(Q_TEST.extendedSelectFromMainTable(Attribute_test),"SELECT  access_type.access_type_value,  access_mode.access_mode_value FROM web_resources  INNER JOIN access_type ON web_resources.resource_access_type = access_type.key  INNER JOIN access_mode ON web_resources.resource_access_mode = access_mode.key ");
    }

    @org.junit.Test
    public void extendedSelect() throws Exception {
    }

}