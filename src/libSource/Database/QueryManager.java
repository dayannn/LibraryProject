package libSource.Database;
import  libSource.Attributes.*;
import  libSource.*;

import javax.swing.*;
import java.util.Objects;

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

        StringBuilder queryBuilder = new StringBuilder("SELECT");
        for (int i = 0; i < lst.size(); i++) {
            if (lst.get(i).getMidT().isEmpty()) {
                queryBuilder.append(" ").append(lst.get(i).getAttributeTableName()).append(".").append(lst.get(i).getAttributeName());
            } else {
                queryBuilder.append(" group_concat(DISTINCT ").append(lst.get(i).getAttributeTableName()).append(".").append(lst.get(i).getAttributeName()).append(")AS ").append(lst.get(i).getAttributeName());
            }

            if (i != lst.size() - 1) queryBuilder.append(", ");
        }
        queryBuilder.append(" FROM " + MAINTABLE + " ");
        for (int i = 0; i < lst.size(); i++) {
            if (Objects.equals(lst.get(i).getAttributeTableName(), MAINTABLE))
                continue;

            if (lst.get(i).getMidT().isEmpty()) {
                queryBuilder.append(" INNER JOIN ").append(lst.get(i).getAttributeTableName());
                queryBuilder.append(" ON " + MAINTABLE + ".resource_").append(lst.get(i).getAttributeTableName()).append(" = ").append(lst.get(i).getAttributeTableName()).append(".key ");
            } else {
                queryBuilder.append(" INNER JOIN ").append(lst.get(i).getMidT());
                queryBuilder.append(" ON ").append(lst.get(i).getMidT()).append(".resource_id = ").append(MAINTABLE).append(".resource_id ");

                queryBuilder.append(" INNER JOIN ").append(lst.get(i).getAttributeTableName());
                queryBuilder.append(" ON ").append(lst.get(i).getAttributeTableName()).append(".key = ").append(lst.get(i).getMidT()).append(".").append(lst.get(i).getAttributeTableName()).append("_id ");
            }
        }
        for (int i = 0; i < lst.size(); i++) {
            if (Objects.equals(lst.get(i).getAttributeName(), "resource_deleted")) {
                if (!lst.get(i).getAttributeValue().isEmpty()) {
                    queryBuilder.append(" WHERE web_resources.resource_deleted = ");
                    queryBuilder.append(lst.get(i).getAttributeValue());
                }
                break;
            }
        }

        query = queryBuilder.toString();
        System.out.println(query);
        return query;

    }

    public String extendedSelect(AttributeList lst) {
        String query;
        StringBuilder queryBuilder = new StringBuilder("SELECT ");
        for (int i = 0; i < lst.size(); i++) {
            queryBuilder.append(" ").append(lst.get(i).getAttributeTableName()).append(".").append(lst.get(i).getAttributeName());
            if (i != lst.size()-1) queryBuilder.append(", ");
        }
        queryBuilder.append(" FROM " + MAINTABLE + " ");
        query = queryBuilder.toString();
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
                "         resource_link LIKE  '%"+searchQuery+"%' OR" +
                "        resource_name LIKE         '%"+searchQuery+"%' )";
    }

    // Расширенный поиск - по кастомным атрибутам
    public String extendedSearch(AttributeList lst) {
        String query;
        StringBuilder queryBuilder = new StringBuilder(" WHERE " + " ( ");
        for (int i = 0; i < lst.size(); i++) {
            queryBuilder.append(" ").append(lst.get(i).getAttributeTableName()).append(".").append(lst.get(i).getAttributeName()).append(" LIKE '%").append(lst.get(i).getAttributeValue()).append("%' ");
            if (i != lst.size()-1) queryBuilder.append("AND ");
        }
        queryBuilder.append(")");
        query = queryBuilder.toString();
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
        //query = query + " DELETE FROM archive WHERE resource_id = " + String.valueOf(ID) + "; ";
        //query = query + " DELETE FROM resource_theme WHERE resource_id = " + String.valueOf(ID) + "; ";
        //query = query + " DELETE FROM resource_operator WHERE resource_id = " + String.valueOf(ID) + "; ";
        //query = query + " DELETE FROM resource_language WHERE resource_id = " + String.valueOf(ID) + "; ";
        //query = query + " DELETE FROM " + MAINTABLE + " WHERE resource_id = " + String.valueOf(ID) + "; ";
        query = query + "UPDATE web_resources SET resource_deleted = 1 WHERE resource_id = " + String.valueOf(ID);
        return query;
    }

    public String restoreRow(int ID) {
        String query = "";
        query = query + "UPDATE web_resources SET resource_deleted = 0 WHERE resource_id = " + String.valueOf(ID);
        return query;
    }


    public String addSourceInMainTable(AttributeList attributeList) {
        String query;
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO " + MAINTABLE + " (");
        for (int i = 0; i < attributeList.size(); i++) {

            if (attributeList.get(i).getMidT().isEmpty()) {

                if (Objects.equals(attributeList.get(i).getAttributeTableName(), MAINTABLE))
                    queryBuilder.append(attributeList.get(i).getAttributeName());
                else
                    queryBuilder.append("resource_").append(attributeList.get(i).getAttributeTableName());

                if (i != attributeList.size() - 1) queryBuilder.append(", ");
            }
        }
        queryBuilder.append(") VALUES (");
        for (Integer i = 0; i < attributeList.size(); i++) {
            if (attributeList.get(i).getMidT().isEmpty()) {
                if (attributeList.get(i).getAttributeValue().isEmpty())
                    queryBuilder.append("\"1\"");
                else
                    queryBuilder.append("\"").append(attributeList.get(i).getAttributeValue()).append("\"");
                if (i != attributeList.size() - 1) queryBuilder.append(", ");
            }
        }
        queryBuilder.append("); ");
        query = queryBuilder.toString();
        return query;
    }


    public String getIDForSource(AttributeList attributeList) {
        String query;
        query = "SELECT resource_id FROM " + MAINTABLE + " " + "WHERE ";
        StringBuilder queryBuilder = new StringBuilder(query);
        for (Integer i = 0; i < attributeList.size(); i++) {
            if (attributeList.get(i).getMidT().isEmpty()) {
                if (Objects.equals(attributeList.get(i).getAttributeTableName(), MAINTABLE))
                    queryBuilder.append(attributeList.get(i).getAttributeName());
                else
                    queryBuilder.append("resource_").append(attributeList.get(i).getAttributeTableName());
                queryBuilder.append(" = ");
                if (attributeList.get(i).getAttributeValue().isEmpty())
                    queryBuilder.append("\"1\"");
                else
                    queryBuilder.append("\"").append(attributeList.get(i).getAttributeValue()).append("\"");
                if (i != attributeList.size() - 1) queryBuilder.append(" AND ");
            }
        }
        query = queryBuilder.toString();
        return query;
    }


    public String addSourceInOtherTable(Integer ID, AttributeList attributeList) {
        StringBuilder query = new StringBuilder(" ");

        // Вставка в таблицы "многие ко многим"
        for(Integer i = 0; i < attributeList.size(); i++) {
            if (!attributeList.get(i).getMidT().isEmpty()) {

                DefaultListModel<String> values;
                values = attributeList.get(i).getValues();
                for (Integer j = 0; j < values.size(); j++) {
                    String value = values.get(j);

                    query.append(" INSERT INTO ").append(attributeList.get(i).getMidT()).append(" (resource_id, ").append(attributeList.get(i).getAttributeTableName()).append("_id) VALUES (");
                    query.append(String.valueOf(ID)).append(", ").append(value).append("); ");
                }

            }
        }

        return query.toString();
    }

    public String getDictionaryForTable(String table) {
        return "SELECT key, " + table + "_value FROM " + table + " ORDER BY " + table + "_value ";
    }

    public String updateMainTable(Integer ID, AttributeList attributeList) {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE " + MAINTABLE);
        query.append(" SET ");

        for (Integer i = 0; i < attributeList.size(); i++) {
            if (attributeList.get(i).getMidT().isEmpty()) {
                if (Objects.equals(attributeList.get(i).getAttributeTableName(), MAINTABLE)) {
                    query.append(attributeList.get(i).getAttributeName());
                } else {
                    query.append("resource_").append(attributeList.get(i).getAttributeTableName());
                }
                query.append(" = ");
                if (attributeList.get(i).getAttributeValue().isEmpty())
                    query.append("\"1\"");
                else
                    query.append("\"").append(attributeList.get(i).getAttributeValue()).append("\"");

                if (i != attributeList.size() - 1) query.append(", ");
            }
        }

        query.append(" WHERE resource_id = ").append(String.valueOf(ID)).append(" ");
        return query.toString();
    }

    public String deleteFromOtherTables(Integer ID) {
        String query = "";
        if (ID < 0) return query;
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
        StringBuilder query;

        query = new StringBuilder("SELECT ");
        for (Integer i = 0; i < lst.size(); i++) {
            if (lst.get(i).getMidT().isEmpty()) {
                if (lst.get(i).getAttributeTableName().equals(MAINTABLE)) {
                    query.append(" ").append(lst.get(i).getAttributeTableName()).append(".").append(lst.get(i).getAttributeName()).append(" AS ").append(lst.get(i).getAttributeName());
                } else {
                    query.append(" resource_").append(lst.get(i).getAttributeTableName()).append(" AS ").append(lst.get(i).getAttributeName());
                }
            } else {
                query.append(" group_concat(DISTINCT resource_").append(lst.get(i).getAttributeTableName()).append(".").append(lst.get(i).getAttributeTableName()).append("_id) AS ").append(lst.get(i).getAttributeName());
            }

            if (i != lst.size() - 1) query.append(", ");
        }
        query.append(" FROM " + MAINTABLE + " ");

        for (Integer i = 0; i < lst.size(); i++) {
            if (lst.get(i).getAttributeTableName().equals(MAINTABLE))
                continue;

            if (!lst.get(i).getMidT().isEmpty()) {
                query.append(" INNER JOIN ").append(lst.get(i).getMidT());
                query.append(" ON ").append(lst.get(i).getMidT()).append(".resource_id = ").append(MAINTABLE).append(".resource_id ");
            }
        }
        return query.toString();
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
        if (dict_name.isEmpty() || value.isEmpty()) return "";
        return " INSERT INTO " + dict_name + "(" + dict_name + "_value) VALUES (\"" + value + "\")";
    }

    public String findDictValueUsages(String dict_name, int id) {
        if (dict_name.isEmpty() || id < 0) return "";
        String query = "";
        if ((dict_name.equals("language")) || (dict_name.equals("theme")) || (dict_name.equals("operator")))
            query =  " SELECT * FROM resource_" + dict_name + " WHERE " + dict_name + "_id = " + String.valueOf(id);
        else
            query = " SELECT resource_" + dict_name + " FROM " + MAINTABLE + " WHERE resource_" + dict_name + " = " + String.valueOf(id);

        return query;
    }


    public String delDictValueFromDictionary(String dict_name, int id) {
        if (dict_name.isEmpty() || id < 0) return "";
        return " DELETE FROM " + dict_name + " WHERE key = " + String.valueOf(id);// " INSERT INTO " + dict_name + "(" + dict_name + "_value) VALUES (\"" + value + "\")";
    }


    public String addArchiveValue(String description, int id, AttributeList lst) {
        StringBuilder query = new StringBuilder(" INSERT INTO archive(resource_id, resource_chg_description, archive_date, ");

        for (int i = 0; i < lst.size(); i++) {
            query.append(lst.get(i).getAttributeName());
            if (i != lst.size() - 1) query.append(", ");
        }
        query.append(") VALUES (").append(id).append(", \"").append(description).append("\", (SELECT date('now')), ");
        for (int i = 0; i < lst.size(); i++) {
            query.append("\"").append(lst.get(i).getAttributeValue()).append("\"");
            if (i != lst.size() - 1) query.append(", ");
        }
        query.append(") ");

        return query.toString();
    }

    public String editValueInDictionary(String dict_name, String value, int id) {
        if (dict_name.isEmpty() || value.isEmpty() || id < 0) return "";
        return " UPDATE " + dict_name + " SET " +dict_name + "_value = \"" + value + "\" WHERE key = " + String.valueOf(id);
    }

    public String getRoleForPair(String login, String pass) {
        if (login.isEmpty() || pass.isEmpty()) return "";
        return " SELECT role FROM authentication WHERE login = \"" + login + "\" AND password = \"" + pass + "\" ";
    }

    public String getStats(int ID){
        return "SELECT year, month, views FROM statistics WHERE resorce_id = " + String.valueOf(ID) + " ORDER BY year, month";
    }

    public String getStatsIDByDate(int year, int month) {
        return "SELECT resorce_id, views FROM statistics WHERE year = " + String.valueOf(year) + " AND month = " + String.valueOf(month) + " ";
    }

    public String getResourceNameByID(int ID) {
        return "SELECT resource_name FROM " + MAINTABLE + " WHERE resource_id = " + String.valueOf(ID) + " ";
    }

    public String checkIfStatsExist(Integer month, Integer year, Integer ID){
        return "SELECT * FROM statistics WHERE year = " + String.valueOf(year) + " AND month = " +
                String.valueOf(month) + " AND resorce_id = " + String.valueOf(ID);
    }

    public String updateStats(Integer month, Integer year, Integer views_num, Integer ID){
        return "UPDATE statistics SET views = " + String.valueOf(views_num) +
                " WHERE year = " + String.valueOf(year) + " AND month = " +
                String.valueOf(month) + " AND resorce_id = " + String.valueOf(ID);
    }

    public String addStats(Integer month, Integer year, Integer views_num, Integer ID){
        return " INSERT INTO " + "statistics " + "(resorce_id, month, views, year) VALUES (" + String.valueOf(ID) +
                ", " +  String.valueOf(month) + ", " + String.valueOf(views_num) + ", " + String.valueOf(year) + ")";
    }
}
