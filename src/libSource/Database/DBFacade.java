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
        return dbManager.ExecQuery(queryManager.simpleSearchResource(lstOut, searchQuery));
    }

    public ResultSet extendedSearch(AttributeList lstOut, AttributeList lst) throws SQLException {
        return dbManager.ExecQuery(queryManager.extendedSelectFromMainTable(lstOut) + queryManager.extendedSearch(lst));
    }

    public ResultSet getCard(int id) throws  SQLException {
        return dbManager.ExecQuery(queryManager.getCardByID(id));
    }

    public void editSource(int id, BaseSource src) throws  SQLException {
        return;
    }

    public int addSource(BaseSource src) throws  SQLException {
        return 0;
    }

    public ResultSet getArchiveForSourceID(int id) throws  SQLException {
        return dbManager.ExecQuery(queryManager.getArchiveBySourceID(id));
    }

    public void deleteSource(int id) throws SQLException {
        dbManager.ExecQueryWOResultSet(queryManager.deleteRow(id));
    }

}
