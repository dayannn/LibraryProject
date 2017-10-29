package libSource.Database;

import libSource.AttributeList;
import libSource.Attributes.*;

import javax.management.Attribute;

import static org.junit.Assert.*;

public class QueryManagerTest {
    private final QueryManager Q_TEST = new QueryManager();
    private AttributeList Attribute_test = new AttributeList();
    private AttributeList Attribute_testone = new AttributeList();
    private AttributeAccessType AAType_TEST = new AttributeAccessType("Свободный");
    private AttributeAccessMode AAMode_TEST = new AttributeAccessMode("Внутренний(в определенном месте))");
    private AttributeLanguage AALang_TEST = new AttributeLanguage("Язык");
    private AttributeName AAName_TEST = new AttributeName("Имя");
    private AttributeLink AALink_TEST = new AttributeLink("Ссылка");


    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.Test
    public void extendedSelectFromMainTable() throws Exception {
        //AttributeList не содержит аттрибутов
        assertEquals(Q_TEST.extendedSelectFromMainTable(Attribute_test),"SELECT  FROM web_resources ");

        //AttributeList содержит 1 аттрибутов, не сод.среднюю таблицу
        Attribute_test.add(AAType_TEST);
        assertEquals(Q_TEST.extendedSelectFromMainTable(Attribute_test),"SELECT  access_type.access_type_value FROM web_resources  INNER JOIN access_type ON web_resources.resource_access_type = access_type.key ");

        //AttributeList содержит 2 аттрибута не содерж. maintable
        Attribute_test.add(AAMode_TEST);
        assertEquals(Q_TEST.extendedSelectFromMainTable(Attribute_test),"SELECT  access_type.access_type_value,  access_mode.access_mode_value FROM web_resources  INNER JOIN access_type ON web_resources.resource_access_type = access_type.key  INNER JOIN access_mode ON web_resources.resource_access_mode = access_mode.key ");

        Attribute_test.remove(1);
        Attribute_test.remove(0);

        // содержит среднюю таблицу
        Attribute_test.add(AALang_TEST);
        assertEquals(Q_TEST.extendedSelectFromMainTable(Attribute_test),"SELECT  group_concat(DISTINCT language.language_value) AS language_value FROM web_resources  INNER JOIN resource_language ON resource_language.resource_id = web_resources.resource_id  INNER JOIN language ON language.key = resource_language.language_id ");
        Attribute_test.remove(0);

        // содержит maintable (1 атрибут)
        Attribute_test.add(AAName_TEST);
        assertEquals(Q_TEST.extendedSelectFromMainTable(Attribute_test), "SELECT  web_resources.resource_name FROM web_resources ");

        // содержит maintable (2 атрибута)
        Attribute_test.add(AALink_TEST);
        assertEquals(Q_TEST.extendedSelectFromMainTable(Attribute_test), "SELECT  web_resources.resource_name,  web_resources.resource_link FROM web_resources ");

    }


    @org.junit.Test
    public void extendedSelect() throws Exception {
    }

    @org.junit.Test
    public void extendedSearch()throws Exception {
        //System.out.println("HERE:");

        Attribute_test.add(AALink_TEST);
        assertEquals(Q_TEST.extendedSearch(Attribute_test), " WHERE  (  web_resources.resource_link LIKE '%Ссылка%' )");

        Attribute_test.add(AAName_TEST);
        assertEquals(Q_TEST.extendedSearch(Attribute_test), " WHERE  (  web_resources.resource_link LIKE '%Ссылка%' AND  web_resources.resource_name LIKE '%Имя%' )");

        Attribute_test.add(AALang_TEST);
        assertEquals(Q_TEST.extendedSearch(Attribute_test), " WHERE  (  web_resources.resource_link LIKE '%Ссылка%' AND  web_resources.resource_name LIKE '%Имя%' AND  language.language_value LIKE '%Язык%' )");

        Attribute_test.add(AAMode_TEST);
        assertEquals(Q_TEST.extendedSearch(Attribute_test), " WHERE  (  web_resources.resource_link LIKE '%Ссылка%' AND  web_resources.resource_name LIKE '%Имя%' AND  language.language_value LIKE '%Язык%' AND  access_mode.access_mode_value LIKE '%Внутренний(в определенном месте))%' )");

        Attribute_test.add(AAType_TEST);
        assertEquals(Q_TEST.extendedSearch(Attribute_test), " WHERE  (  web_resources.resource_link LIKE '%Ссылка%' AND  web_resources.resource_name LIKE '%Имя%' AND  language.language_value LIKE '%Язык%' AND  access_mode.access_mode_value LIKE '%Внутренний(в определенном месте))%' AND  access_type.access_type_value LIKE '%Свободный%' )");


    }

}