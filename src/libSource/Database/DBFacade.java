package libSource.Database;
import  libSource.Attributes.*;
import  libSource.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by JacKeTUs on 08.04.2017.
 */
public class DBFacade {
    private DBManager dbManager;
    private QueryManager queryManager;

    public DBFacade() {
        dbManager = new DBManager();
        queryManager = new QueryManager();
    }

    // Подключение к базе
    public Connection connectDB() throws SQLException {
        return dbManager.ConnectSQLiteDB();
    }

    // Получить все ресурсы
    public ResultSet getAllResources(BaseAttribute sortAttr) throws SQLException {
        return dbManager.ExecQuery(queryManager.selectAll() + queryManager.orderBy(sortAttr));
    }

    // Получить некоторые ресурсы
    public ResultSet getSomeResources(AttributeList lst) throws SQLException {
        return dbManager.ExecQuery(queryManager.extendedSelectFromMainTable(lst) + queryManager.groupBy());
    }

    // Получить все темы
    public ResultSet getThemes() throws SQLException {
        return dbManager.ExecQuery(queryManager.selectThemes());
    }

    // Получить все типы
    public ResultSet getTypes() throws SQLException {
        return dbManager.ExecQuery(queryManager.selectTypes());
    }

    // Простой поиск
    public ResultSet simpleSearch(AttributeList lstOut, String searchQuery) throws SQLException {
        return dbManager.ExecQuery(queryManager.simpleSearchResource(lstOut, searchQuery)  + queryManager.groupBy());
    }

    public ResultSet extendedSearch(AttributeList lstOut, AttributeList lst) throws SQLException {
        return dbManager.ExecQuery(queryManager.extendedSelectFromMainTable(lstOut) + queryManager.extendedSearch(lst) + queryManager.groupBy());
    }

    public ResultSet getCard(int id) throws  SQLException {
        return dbManager.ExecQuery(queryManager.getCardByID(id) + queryManager.groupBy());
    }


    public void addSource(Source src) throws  SQLException {
        AttributeList list;
        list = src.getList();

        // Вставка в MainTable
        dbManager.ExecQueryWOResultSet(queryManager.addSourceInMainTable(list));
        // получаем ID новоприбывшего
        ResultSet rs = dbManager.ExecQuery(queryManager.getIDForSource(list));
        Integer id = Integer.parseInt(rs.getString("resource_id"));

        // Вставка в остальные таблицы
        dbManager.ExecQueryWOResultSet(queryManager.addSourceInOtherTable(id, list));
    }

    public ResultSet getArchiveForSourceID(int id) throws SQLException {
        return dbManager.ExecQuery(queryManager.getArchiveBySourceID(id));
    }

    public void addToArchiveSourceByID(String description, int id, AttributeList values) throws SQLException {
        dbManager.ExecQueryWOResultSet(queryManager.addArchiveValue(description, id, values));
    }


    public void deleteSource(int id) throws SQLException {
        dbManager.ExecQueryWOResultSet(queryManager.deleteRow(id));

    }

    public void restoreSource(int id) throws SQLException {
        dbManager.ExecQueryWOResultSet(queryManager.restoreRow(id));

    }

    public ResultSet getDictionary(String tableName) throws SQLException {
        return dbManager.ExecQuery(queryManager.getDictionaryForTable(tableName));
    }

    public void delDictValue(String dict_name, int id) throws SQLException {

        if (!dbManager.ExecQuery(queryManager.findDictValueUsages(dict_name, id)).next()) {
            dbManager.ExecQueryWOResultSet(queryManager.delDictValueFromDictionary(dict_name, id));
        }

    }

    public void editDictValue(String dict_name, String value, int id) throws SQLException {
        dbManager.ExecQueryWOResultSet(queryManager.editValueInDictionary(dict_name, value, id));
    }

    public void chgSource(int id, Source src) throws SQLException {
        AttributeList list;
        list = src.getList();

        // удаляем другие таблицы
        dbManager.ExecQueryWOResultSet(queryManager.deleteFromOtherTables(id));
        // изменяем main
        dbManager.ExecQueryWOResultSet(queryManager.updateMainTable(id, list));
        // Вставка в остальные таблицы
        dbManager.ExecQueryWOResultSet(queryManager.addSourceInOtherTable(id, list));
    }

    public ResultSet getCardIndexes(int ID) throws SQLException {
        return dbManager.ExecQuery(queryManager.getCardIndexesByID(ID) + queryManager.groupBy());
    }

    public ResultSet getDictionariesInfo() throws SQLException {
        return dbManager.ExecQuery(queryManager.getDictionariesInfo());
    }

    public void addDictValue(String table_name, String value) throws SQLException {
        dbManager.ExecQueryWOResultSet(queryManager.addDictValueInDictionary(table_name, value));
    }

    public ResultSet getRoleByLogin(String login, String pass) throws SQLException {
        return dbManager.ExecQuery(queryManager.getRoleForPair(login, pass));
    }

    public ResultSet getStats(int ID) throws SQLException{
        return dbManager.ExecQuery(queryManager.getStats(ID));
    }

    public ResultSet getResourceIDByDateFromStat(int year, int month) throws SQLException{
        return dbManager.ExecQuery(queryManager.getStatsIDByDate(year, month));
    }

    public ResultSet getResourceNameByID(int ID) throws SQLException {
        return dbManager.ExecQuery(queryManager.getResourceNameByID(ID));
    }

    public ResultSet checkIfStatsExist (Integer month, Integer year, Integer ID) throws SQLException{
        return dbManager.ExecQuery(queryManager.checkIfStatsExist(month, year, ID));
    }

    public void addStats (Integer month, Integer year, Integer views_num, Integer ID) throws  SQLException{
        dbManager.ExecQueryWOResultSet(queryManager.addStats(month, year, views_num, ID));
    }

    public void updateStats (Integer month, Integer year, Integer views_num, Integer ID) throws  SQLException{
        dbManager.ExecQueryWOResultSet(queryManager.updateStats(month, year, views_num, ID));
    }
}
