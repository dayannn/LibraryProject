package libSource.Database;
import  libSource.Attributes.*;
import  libSource.*;
import java.util.List;
import javax.swing.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by JacKeTUs on 07.04.2017.
 */
public class QueryManager {

    private static final String MAINTABLE = "web_resources";

    public QueryManager() {}
    /*
        I. SELECT +
            1. Выборка всех электронных ресурсов
            2. Выборка только тем / типа / типа доступа
            3. Поиск по каким-то полям

        II. INSERT
            1. Добавление электронного ресурса
            2. Добавление темы / типа / типа доступа

        III. UPDATE
            1. Изменение электронного ресурса
            2. Изменение темы / типа / типа доступа
     */

    public String selectAll() {
       /*
        return
                "SELECT resource_name           AS \"Имя ресурса\"," +
                "       resource_description    AS \"Описание\"," +
                "       resource_link           AS \"Ссылка\"," +
                "       type_value              AS \"Тип ресурса\"," +
                "       theme_value             AS \"Тема\"," +
                "       access_type_value       AS \"Тип доступа\" " +
                "FROM   web_resources, type, theme, access_type " +

                "WHERE  resource_type           = type.key          AND" +
                "       resource_theme          = theme.key         AND" +
                "       resource_access_type    = access_type.key ";
                */

        return  "SELECT web_resources.resource_name, " +
                "       web_resources.resource_description, " +
                "       web_resources.resource_link, " +
                "       type.type_value,              " +
                "       theme.theme_value,             " +
                "       access_type.access_type_value       " +
                " FROM web_resources " +
                " INNER JOIN type " +
                " ON web_resources.resource_type = type.key " +
                " INNER JOIN theme " +
                " ON web_resources.resource_theme = theme.key " +
                " INNER JOIN access_type " +
                " ON web_resources.resource_access_type = access_type.key ";
    }

    // SELECT - по кастомным атрибутам
    public String extendedSelectFromMainTable(AttributeList lst) {
        String query;

        query = "SELECT ";
        for (int i = 0; i < lst.size(); i++) {
            if (lst.get(i).getMidT().isEmpty()) {
                query = query + " " + lst.get(i).getAttributeTableName() + "." + lst.get(i).getAttributeName();
            } else {
                query = query + " group_concat(DISTINCT " +
                        lst.get(i).getAttributeTableName() + "." + lst.get(i).getAttributeName() + ") AS " +
                        lst.get(i).getAttributeName();
            }

            if (i != lst.size() - 1) query = query + ", ";
        }
        query = query + " FROM " + MAINTABLE + " ";

        for (int i = 0; i < lst.size(); i++) {
            if (lst.get(i).getAttributeTableName() == MAINTABLE)
                continue;

            if (lst.get(i).getMidT().isEmpty()) {
                query = query + " INNER JOIN " + lst.get(i).getAttributeTableName();
                query = query + " ON " + MAINTABLE + ".resource_" + lst.get(i).getAttributeTableName() + " = " +
                        lst.get(i).getAttributeTableName() + ".key ";
            } else {
                query = query + " INNER JOIN " + lst.get(i).getMidT();
                query = query + " ON " + lst.get(i).getMidT() + ".resource_id = " + MAINTABLE +
                        ".resource_id ";

                query = query + " INNER JOIN " + lst.get(i).getAttributeTableName();
                query = query + " ON " + lst.get(i).getAttributeTableName() + ".key = " +
                        lst.get(i).getMidT() + "." + lst.get(i).getAttributeTableName() + "_id ";
            }
        }
        return query;
    }

    public String extendedSelect(AttributeList lst) {
        String query;
        query = "SELECT ";
        for (int i = 0; i < lst.size(); i++) {
            query = query + " " + lst.get(i).getAttributeTableName() + "." + lst.get(i).getAttributeName();
            if (i != lst.size()-1) query = query + ", ";
        }
        query = query + " FROM " + MAINTABLE + " ";
        return query;
    }

    public String orderBy(BaseAttribute sortAttr) {
        return " ORDER BY " + sortAttr.getAttributeName() + " desc ";
    }

    public String selectThemes() { return " SELECT theme_value AS \"Тема\" FROM theme "; }

    public String selectTypes() { return " SELECT type_value AS \"Тип\" FROM types "; }

    public String selectAccessTypes() {
        return " SELECT access_type_value AS \"Тип доступа\" FROM access_types ";
    }

    // Простой поиск - по имени и описанию
    public String simpleSearchResource(AttributeList lstOut, String searchQuery) {
        return extendedSelectFromMainTable(lstOut) + " AND " +
                "       ( resource_description LIKE  '%"+searchQuery+"%' OR" +
                "        resource_name LIKE         '%"+searchQuery+"%' )";
    }

    // Расширенный поиск - по кастомным атрибутам
    public String extendedSearch(AttributeList lst) {
        String query;
        query = " WHERE " + " ( ";
        for (int i = 0; i < lst.size(); i++) {
            query = query + " "+lst.get(i).getAttributeTableName()+"."+lst.get(i).getAttributeName()+" LIKE '%"+lst.get(i).getAttributeValue()+"%' ";
            if (i != lst.size()-1) query = query + "AND ";
        }
        query = query + ")";
        return query;
    }

    public String getCardByID(int ID) {
        Source src = new Source();
        String query = this.extendedSelectFromMainTable(src.getList());
        query = query + " WHERE " + MAINTABLE + ".resource_id = " + String.valueOf(ID) + " ";

        return query;
    }

    public String getArchiveBySourceID(int ID) {
        String query =  "SELECT key, " +
                        "archive_date, " +
                        "resource_name, " +
                        "resource_provider, subscription_model_value, subscription_price, resource_contract_duration, " +
                        "resource_chg_description FROM archive WHERE resource_id = " + String.valueOf(ID) + "; ";
        return query;
    }

    public String groupBy() {
        return " GROUP BY " + MAINTABLE + ".resource_id ";
    }

    public String deleteRow(int ID) {
        String query = "";
        query = query + " DELETE FROM archive WHERE resource_id = " + String.valueOf(ID) + "; ";
        query = query + " DELETE FROM resource_theme WHERE resource_id = " + String.valueOf(ID) + "; ";
        query = query + " DELETE FROM resource_operator WHERE resource_id = " + String.valueOf(ID) + "; ";
        query = query + " DELETE FROM resource_language WHERE resource_id = " + String.valueOf(ID) + "; ";
        query = query + " DELETE FROM " + MAINTABLE + " WHERE resource_id = " + String.valueOf(ID) + "; ";
        return query;
    }

    public String addSourceInMainTable(AttributeList attributeList) {
        String query;
        query = " INSERT INTO " + MAINTABLE + " (";
        for (int i = 0; i < attributeList.size(); i++) {

            if (attributeList.get(i).getMidT().isEmpty()) {

                if (attributeList.get(i).getAttributeTableName() == MAINTABLE)
                    query = query + attributeList.get(i).getAttributeName();
                else
                    query = query + "resource_" + attributeList.get(i).getAttributeTableName();

                if (i != attributeList.size() - 1) query = query + ", ";
            }
        }
        query = query + ") VALUES (";
        for (Integer i = 0; i < attributeList.size(); i++) {
            if (attributeList.get(i).getMidT().isEmpty()) {
                if (attributeList.get(i).getAttributeValue().isEmpty())
                    query = query + "\"1\"";
                else
                    query = query + "\"" + attributeList.get(i).getAttributeValue() + "\"";
                if (i != attributeList.size() - 1) query = query + ", ";
            }
        }
        query = query + "); ";
        return query;
    }


    public String getIDForSource(AttributeList attributeList) {
        String query;
        query = "SELECT resource_id FROM " + MAINTABLE + " ";
        query = query + "WHERE ";
        for (Integer i = 0; i < attributeList.size(); i++) {
            if (attributeList.get(i).getMidT().isEmpty()) {
                if (attributeList.get(i).getAttributeTableName() == MAINTABLE)
                    query = query + attributeList.get(i).getAttributeName();
                else
                    query = query + "resource_" + attributeList.get(i).getAttributeTableName();
                query = query + " = ";
                if (attributeList.get(i).getAttributeValue().isEmpty())
                    query = query + "\"1\"";
                else
                    query = query + "\"" + attributeList.get(i).getAttributeValue() + "\"";
                if (i != attributeList.size() - 1) query = query + " AND ";
            }
        }
        return query;
    }


    public String addSourceInOtherTable(Integer ID, AttributeList attributeList) {
        String query = " ";

        // Вставка в таблицы "многие ко многим"
        for(Integer i = 0; i < attributeList.size(); i++) {
            if (!attributeList.get(i).getMidT().isEmpty()) {

                DefaultListModel<String> values;
                values = attributeList.get(i).getValues();
                for (Integer j = 0; j < values.size(); j++) {
                    String value = values.get(j);

                    query = query + " INSERT INTO " + attributeList.get(i).getMidT() +
                            " (resource_id, " + attributeList.get(i).getAttributeTableName() + "_id) VALUES (";
                    query = query + String.valueOf(ID) + ", " + value + "); ";
                }

            }
        }

        return query;
    }

    public String getDictionaryForTable(String table) {
        return "SELECT key, " + table + "_value FROM " + table + " ORDER BY " + table + "_value ";
    }

    public String updateMainTable(Integer ID, AttributeList attributeList) {
        String query = "";
        query = query + "UPDATE " + MAINTABLE;
        query = query + " SET ";

        for (Integer i = 0; i < attributeList.size(); i++) {
            if (attributeList.get(i).getMidT().isEmpty()) {
                if (attributeList.get(i).getAttributeTableName() == MAINTABLE) {
                    query = query + attributeList.get(i).getAttributeName();
                } else {
                    query = query + "resource_" + attributeList.get(i).getAttributeTableName();
                }
                query = query + " = ";
                if (attributeList.get(i).getAttributeValue().isEmpty())
                    query = query + "\"1\"";
                else
                    query = query + "\"" + attributeList.get(i).getAttributeValue() + "\"";

                if (i != attributeList.size() - 1) query = query + ", ";
            }
        }

        query = query + " WHERE resource_id = " + String.valueOf(ID) + " ";
        return query;
    }

    public String deleteFromOtherTables(Integer ID) {
        String query = "";
        query = query + " DELETE FROM resource_theme WHERE resource_id = " + String.valueOf(ID) + "; ";
        query = query + " DELETE FROM resource_operator WHERE resource_id = " + String.valueOf(ID) + "; ";
        query = query + " DELETE FROM resource_language WHERE resource_id = " + String.valueOf(ID) + "; ";
        return query;
    }

    public String updateOtherTables(Integer ID, AttributeList attributeList) {
        String query = "";
        query = query + addSourceInOtherTable(ID, attributeList);

        return query;
    }

    public String extendedSelectIndexesFromMainTable(AttributeList lst) {
        String query;

        query = "SELECT ";
        for (Integer i = 0; i < lst.size(); i++) {
            if (lst.get(i).getMidT().isEmpty()) {
                if (lst.get(i).getAttributeTableName().equals(MAINTABLE)) {
                    query = query + " " + lst.get(i).getAttributeTableName() + "." + lst.get(i).getAttributeName() +
                            " AS " + lst.get(i).getAttributeName();
                } else {
                    query = query + " resource_" + lst.get(i).getAttributeTableName() +
                            " AS " + lst.get(i).getAttributeName();
                }
            } else {
                query = query + " group_concat(DISTINCT resource_" +
                        lst.get(i).getAttributeTableName() + "." + lst.get(i).getAttributeTableName() + "_id) AS " +
                        lst.get(i).getAttributeName();
            }

            if (i != lst.size() - 1) query = query + ", ";
        }
        query = query + " FROM " + MAINTABLE + " ";

        for (Integer i = 0; i < lst.size(); i++) {
            if (lst.get(i).getAttributeTableName().equals(MAINTABLE))
                continue;

            if (!lst.get(i).getMidT().isEmpty()) {
                query = query + " INNER JOIN " + lst.get(i).getMidT();
                query = query + " ON " + lst.get(i).getMidT() + ".resource_id = " + MAINTABLE +
                        ".resource_id ";
            }
        }
        return query;
    }

    public String getCardIndexesByID(int ID) {
        Source src = new Source();
        String query = this.extendedSelectIndexesFromMainTable(src.getList());
        query = query + " WHERE " + MAINTABLE + ".resource_id = " + String.valueOf(ID) + " ";

        return query;
    }

    public String getDictionariesInfo() {
        return " SELECT * FROM dictionary_info ORDER BY dict_description";
    }

    public String addDictValueInDictionary(String dict_name, String value) {
        return " INSERT INTO " + dict_name + "(" + dict_name + "_value) VALUES (\"" + value + "\")";
    }

    public String findDictValueUsages(String dict_name, int id) {
        String query = "";
        if ((dict_name.equals("language")) || (dict_name.equals("theme")) || (dict_name.equals("operator")))
            query =  " SELECT * FROM resource_" + dict_name + " WHERE " + dict_name + "_id = " + String.valueOf(id);
        else
            query = " SELECT resource_" + dict_name + " FROM " + MAINTABLE + " WHERE resource_" + dict_name + " = " + String.valueOf(id);

        return query;
    }


    public String delDictValueFromDictionary(String dict_name, int id) {
        return " DELETE FROM " + dict_name + " WHERE key = " + String.valueOf(id);// " INSERT INTO " + dict_name + "(" + dict_name + "_value) VALUES (\"" + value + "\")";
    }


    public String addArchiveValue(String description, int id, AttributeList lst) {
        String query = " INSERT INTO archive(resource_id, resource_chg_description, archive_date, ";

        for (int i = 0; i < lst.size(); i++) {
            query = query + lst.get(i).getAttributeName();
            if (i != lst.size() - 1) query = query + ", ";
        }
        query = query + ") VALUES (" + id + ", \"" + description + "\", (SELECT date('now')), ";
        for (int i = 0; i < lst.size(); i++) {
            query = query + "\"" + lst.get(i).getAttributeValue() + "\"";
            if (i != lst.size() - 1) query = query + ", ";
        }
        query = query + ") ";

        return query;
    }

    public String editValueInDictionary(String dict_name, String value, int id) {
        return " UPDATE " + dict_name + " SET " +dict_name + "_value = \"" + value + "\" WHERE key = " + String.valueOf(id);
    }

    public String getRoleForPair(String login, String pass) {
        return " SELECT role FROM authentication WHERE login = \"" + login + "\" AND password = \"" + pass + "\" ";
    }
}
