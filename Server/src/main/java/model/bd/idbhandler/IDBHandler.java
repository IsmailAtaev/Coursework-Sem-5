package model.bd.idbhandler;

import model.configs.constBD.Const;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IDBHandler {

    Const tempConst = new Const();

    default Connection getDbConnection() throws SQLException, ClassNotFoundException {
        String connectionString = "jdbc:mysql://" + tempConst.getDbHost() + ":" + tempConst.getDbPort() + "/" + tempConst.getDbName();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection dbConnection = DriverManager.getConnection(connectionString, tempConst.getDbUser(), tempConst.getDbPass());
        return dbConnection;
    }

    boolean addObj(Object obj);

    ArrayList<Object> getList();

    boolean deleteObj(Object obj);

    boolean editObj(Object obj);
}
