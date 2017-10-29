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
    public void simpleSearchResource() throws Exception {
        // Пустой SEARCHQUERY

        //AttributeList не содержит аттрибутов
        assertEquals(Q_TEST.simpleSearchResource(Attribute_test, ""),"SELECT  FROM web_resources  AND        ( resource_description LIKE  '%%' OR        resource_name LIKE         '%%' )");

        //AttributeList содержит 1 аттрибутов, не сод.среднюю таблицу
        Attribute_test.add(AAType_TEST);
        assertEquals(Q_TEST.simpleSearchResource(Attribute_test, ""),"SELECT  access_type.access_type_value FROM web_resources  INNER JOIN access_type ON web_resources.resource_access_type = access_type.key  AND        ( resource_description LIKE  '%%' OR        resource_name LIKE         '%%' )");

        //AttributeList содержит 2 аттрибута не содерж. maintable
        Attribute_test.add(AAMode_TEST);
        assertEquals(Q_TEST.simpleSearchResource(Attribute_test, ""),"SELECT  access_type.access_type_value,  access_mode.access_mode_value FROM web_resources  INNER JOIN access_type ON web_resources.resource_access_type = access_type.key  INNER JOIN access_mode ON web_resources.resource_access_mode = access_mode.key  AND        ( resource_description LIKE  '%%' OR        resource_name LIKE         '%%' )");

        Attribute_test.remove(1);
        Attribute_test.remove(0);

        // содержит среднюю таблицу
        Attribute_test.add(AALang_TEST);
        assertEquals(Q_TEST.simpleSearchResource(Attribute_test, ""),"SELECT  group_concat(DISTINCT language.language_value) AS language_value FROM web_resources  INNER JOIN resource_language ON resource_language.resource_id = web_resources.resource_id  INNER JOIN language ON language.key = resource_language.language_id  AND        ( resource_description LIKE  '%%' OR        resource_name LIKE         '%%' )");
        Attribute_test.remove(0);

        // содержит maintable (1 атрибут)
        Attribute_test.add(AAName_TEST);
        assertEquals(Q_TEST.simpleSearchResource(Attribute_test, ""), "SELECT  web_resources.resource_name FROM web_resources  AND        ( resource_description LIKE  '%%' OR        resource_name LIKE         '%%' )");

        // содержит maintable (2 атрибута)
        Attribute_test.add(AALink_TEST);
        assertEquals(Q_TEST.simpleSearchResource(Attribute_test, ""), "SELECT  web_resources.resource_name,  web_resources.resource_link FROM web_resources  AND        ( resource_description LIKE  '%%' OR        resource_name LIKE         '%%' )");
    }

    @org.junit.Test
    public void addSourceInMainTable() throws Exception {
        //AttributeList не содержит аттрибутов
        assertEquals(Q_TEST.addSourceInMainTable(Attribute_test),"INSERT INTO web_resources () VALUES (); ");

        //AttributeList содержит 1 аттрибутов, не сод.среднюю таблицу
        Attribute_test.add(AAType_TEST);
        assertEquals(Q_TEST.addSourceInMainTable(Attribute_test),"INSERT INTO web_resources (resource_access_type) VALUES (\"Свободный\"); ");

        //AttributeList содержит 2 аттрибута не содерж. maintable
        Attribute_test.add(AAMode_TEST);
        assertEquals(Q_TEST.addSourceInMainTable(Attribute_test),"INSERT INTO web_resources (resource_access_type, resource_access_mode) VALUES (\"Свободный\", \"Внутренний(в определенном месте))\"); ");

        Attribute_test.remove(1);
        Attribute_test.remove(0);

        // содержит maintable (1 атрибут)
        Attribute_test.add(AAName_TEST);
        assertEquals(Q_TEST.addSourceInMainTable(Attribute_test), "INSERT INTO web_resources (resource_name) VALUES (\"Имя\"); ");

        // содержит maintable (2 атрибута)
        Attribute_test.add(AALink_TEST);
        assertEquals(Q_TEST.addSourceInMainTable(Attribute_test), "INSERT INTO web_resources (resource_name, resource_link) VALUES (\"Имя\", \"Ссылка\"); ");
    }

    @org.junit.Test
    public void getDictionaryForTable() throws Exception {
        assertEquals(Q_TEST.getDictionaryForTable(""), "SELECT key, _value FROM  ORDER BY _value ");

        assertEquals(Q_TEST.getDictionaryForTable("SomeDict"), "SELECT key, SomeDict_value FROM SomeDict ORDER BY SomeDict_value ");
    }

    @org.junit.Test
    public void deleteFromOtherTables() throws Exception {
        assertEquals(Q_TEST.deleteFromOtherTables(-1), "");

        assertEquals(Q_TEST.deleteFromOtherTables(0), " DELETE FROM resource_theme WHERE resource_id = 0;  DELETE FROM resource_operator WHERE resource_id = 0;  DELETE FROM resource_language WHERE resource_id = 0; ");

        assertEquals(Q_TEST.deleteFromOtherTables(1), " DELETE FROM resource_theme WHERE resource_id = 1;  DELETE FROM resource_operator WHERE resource_id = 1;  DELETE FROM resource_language WHERE resource_id = 1; ");
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

    @org.junit.Test
    public void getIDForSource() throws Exception {
        // System.out.println("HERE:");

        assertEquals(Q_TEST.getIDForSource(Attribute_test),"SELECT resource_id FROM web_resources WHERE ");

        Attribute_test.add(AALink_TEST);
        assertEquals(Q_TEST.getIDForSource(Attribute_test),"SELECT resource_id FROM web_resources WHERE resource_link = \"Ссылка\"");

        Attribute_test.add(AAName_TEST);
        assertEquals(Q_TEST.getIDForSource(Attribute_test),"SELECT resource_id FROM web_resources WHERE resource_link = \"Ссылка\" AND resource_name = \"Имя\"");

        Attribute_test.add(AALang_TEST);
        assertEquals(Q_TEST.getIDForSource(Attribute_test),"SELECT resource_id FROM web_resources WHERE resource_link = \"Ссылка\" AND resource_name = \"Имя\" AND ");

        Attribute_test.add(AAMode_TEST);
        assertEquals(Q_TEST.getIDForSource(Attribute_test),"SELECT resource_id FROM web_resources WHERE resource_link = \"Ссылка\" AND resource_name = \"Имя\" AND resource_access_mode = \"Внутренний(в определенном месте))\"");

        Attribute_test.add(AAType_TEST);
        assertEquals(Q_TEST.getIDForSource(Attribute_test),"SELECT resource_id FROM web_resources WHERE resource_link = \"Ссылка\" AND resource_name = \"Имя\" AND resource_access_mode = \"Внутренний(в определенном месте))\" AND resource_access_type = \"Свободный\"");

    }
    @org.junit.Test
    public void getRoleForPair() throws Exception {
        assertEquals(Q_TEST.getRoleForPair("", ""), "");
        assertEquals(Q_TEST.getRoleForPair("login", ""), "");
        assertEquals(Q_TEST.getRoleForPair("", "pass"), "");
        assertEquals(Q_TEST.getRoleForPair("login", "pass"), " SELECT role FROM authentication WHERE login = \"login\" AND password = \"pass\" ");
    }

    @org.junit.Test
    public void editValueInDictionary() throws Exception {
        assertEquals(Q_TEST.editValueInDictionary("", "", -1), "");
        assertEquals(Q_TEST.editValueInDictionary("dict", "", -1), "");
        assertEquals(Q_TEST.editValueInDictionary("", "value", -1), "");
        assertEquals(Q_TEST.editValueInDictionary("", "", 0), "");
        assertEquals(Q_TEST.editValueInDictionary("", "", 1), "");
        assertEquals(Q_TEST.editValueInDictionary("dict", "", 1), "");
        assertEquals(Q_TEST.editValueInDictionary("", "value", 1), "");
        assertEquals(Q_TEST.editValueInDictionary("dict", "value", -1), "");
        assertEquals(Q_TEST.editValueInDictionary("dict", "value", 0), " UPDATE dict SET dict_value = \"value\" WHERE key = 0");
        assertEquals(Q_TEST.editValueInDictionary("dict", "value", 1), " UPDATE dict SET dict_value = \"value\" WHERE key = 1");
    }

    @org.junit.Test
    public void delDictValueFromDictionary() throws Exception {
        assertEquals(Q_TEST.delDictValueFromDictionary("dict", -1), "");
        assertEquals(Q_TEST.delDictValueFromDictionary("", -1), "");
        assertEquals(Q_TEST.delDictValueFromDictionary("", 1), "");
        assertEquals(Q_TEST.delDictValueFromDictionary("dict", 1), " DELETE FROM dict WHERE key = 1");
    }

    @org.junit.Test
    public void findDictValueUsages() throws Exception {
        assertEquals(Q_TEST.findDictValueUsages("dict", -1), "");
        assertEquals(Q_TEST.findDictValueUsages("", -1), "");
        assertEquals(Q_TEST.findDictValueUsages("", 1), "");

        assertEquals(Q_TEST.findDictValueUsages("not_language", 1), " SELECT resource_not_language FROM web_resources WHERE resource_not_language = 1");
        assertEquals(Q_TEST.findDictValueUsages("language", 1), " SELECT * FROM resource_language WHERE language_id = 1");
        assertEquals(Q_TEST.findDictValueUsages("not_theme", 1), " SELECT resource_not_theme FROM web_resources WHERE resource_not_theme = 1");
        assertEquals(Q_TEST.findDictValueUsages("theme", 1), " SELECT * FROM resource_theme WHERE theme_id = 1");
        assertEquals(Q_TEST.findDictValueUsages("not_operator", 1), " SELECT resource_not_operator FROM web_resources WHERE resource_not_operator = 1");
        assertEquals(Q_TEST.findDictValueUsages("operator", 1), " SELECT * FROM resource_operator WHERE operator_id = 1");

    }

    @org.junit.Test
    public void addDictValueInDictionary() throws Exception {
        assertEquals(Q_TEST.addDictValueInDictionary("dict", ""), "");
        assertEquals(Q_TEST.addDictValueInDictionary("", "value"), "");
        assertEquals(Q_TEST.addDictValueInDictionary("dict", "value"), " INSERT INTO dict(dict_value) VALUES (\"value\")");

    }



}