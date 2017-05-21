package libSource.Database;
import  libSource.Attributes.*;
import  libSource.*;

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
        for (Integer i = 0; i < lst.size(); i++) {
            if (lst.get(i).getMidT().isEmpty()) {
                query = query + " " + lst.get(i).getAttributeTableName() + "." + lst.get(i).getAttributeName();
            } else {
                query = query + " group_concat(" +
                        lst.get(i).getAttributeTableName() + "." + lst.get(i).getAttributeName() + ") AS " +
                        lst.get(i).getAttributeName();
            }

            if (i != lst.size() - 1) query = query + ", ";
        }
        query = query + " FROM " + MAINTABLE + " ";

        for (Integer i = 0; i < lst.size(); i++) {
            if (lst.get(i).getAttributeTableName() == MAINTABLE)
                continue;

            if (lst.get(i).getMidT().isEmpty()) {
                query = query + " INNER JOIN " + lst.get(i).getAttributeTableName();
                query = query + " ON " + MAINTABLE + ".resource_" + lst.get(i).getAttributeTableName() + " = " +
                        lst.get(i).getAttributeTableName() + ".key ";
            } else {
                query = query + " INNER JOIN " + lst.get(i).getMidT();
                query = query + " ON " + lst.get(i).getMidT() + ".resource_id = " + MAINTABLE +
                        ".resource_" + lst.get(i).getAttributeTableName();

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
        for (Integer i = 0; i < lst.size(); i++) {
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
        for (Integer i = 0; i < lst.size(); i++) {
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
        String query =  "SELECT key, archive_date, resource_name, resource_chg_description FROM archive WHERE resource_id = \n" +
                        "(SELECT resource_archive_id FROM " + MAINTABLE + " WHERE " + MAINTABLE + ".resource_id = " + String.valueOf(ID) + ") ";
        return query;
    }

    public String groupBy() {
        return " GROUP BY " + MAINTABLE + ".resource_id ";
    }

    public String deleteRow(int ID) {
        String query;
        query = " DELETE FROM " + MAINTABLE + ", archive, resource_theme WHERE resource_id = " + String.valueOf(ID) + " ";
        return query;
    }
}
