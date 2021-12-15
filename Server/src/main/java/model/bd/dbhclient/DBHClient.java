package model.bd.dbhclient;

import com.example.model.client.Client;
import com.example.model.myexception.MyException;
import model.bd.idbhandler.IDBHandler;
import model.configs.clientBD.ConstClient;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBHClient implements IDBHandler {

    @Override
    public boolean addObj(Object obj) {
        try {
            Client client = (Client) obj;
            String insert = "INSERT INTO " + ConstClient.CLIENT_TABLE + "("
                    + ConstClient.CLIENT_FIO + ","
                    + ConstClient.CLIENT_CODE + ","
                    + ConstClient.CLIENT_PASSPORT_ID + ","
                    + ConstClient.CLIENT_MAIL + ","
                    + ConstClient.CLIENT_MOBILE_NUMBER + ","
                    + ConstClient.CLIENT_LOGIN + ","
                    + ConstClient.CLIENT_PASSWORD + ","
                    + ConstClient.CLIENT_FLAG + ")" + "VALUES(?,?,?,?,?,?,?,?)";

            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, client.getFIO());
            prSt.setString(2, client.getClientCode());
            prSt.setString(3, client.getPassportId());
            prSt.setString(4, client.getMail());
            prSt.setString(5, client.getMobileNumber());
            prSt.setString(6, client.getLogin());
            prSt.setString(7, client.getPassword());
            prSt.setInt(8, client.getFlag());
            prSt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            new MyException(e);
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<Object> getList() {
        ArrayList<Object> arrayList = new ArrayList<>();
        try {
            String select = "SELECT * FROM " + ConstClient.CLIENT_TABLE;
            Statement statement = getDbConnection().createStatement();
            ResultSet resSet = statement.executeQuery(select);
            while (resSet.next()) {
                Client client = new Client();
                client.setId(resSet.getInt(1));
                client.setFIO(resSet.getString(2));
                client.setClientCode(resSet.getString(3));
                client.setPassportId(resSet.getString(4));
                client.setMail(resSet.getString(5));
                client.setMobileNumber(resSet.getString(6));
                client.setLogin(resSet.getString(7));
                client.setPassword(resSet.getString(8));
                client.setFlag(resSet.getInt(9));
                arrayList.add(client);
            }
        } catch (SQLException e) {
            new MyException(e);
        } catch (ClassNotFoundException e) {
            new MyException(e);
        }
        return arrayList;
    }

    @Override
    public boolean deleteObj(Object obj) {
        try {
            Client client = (Client) obj;
            String DELETE = "DELETE FROM " + ConstClient.CLIENT_TABLE + " WHERE " + ConstClient.CLIENT_ID + "='" + client.getId() + "'; ";
            PreparedStatement preparedStatementDelete = getDbConnection().prepareStatement(DELETE);
            preparedStatementDelete.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            new MyException(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean editObj(Object obj) {
        try {
            Client client = (Client) obj;
            String update = "UPDATE " + ConstClient.CLIENT_TABLE +
                    " SET " + ConstClient.CLIENT_FIO + "=?, " + ConstClient.CLIENT_CODE + "=?, " + ConstClient.CLIENT_PASSPORT_ID + "=?, " + ConstClient.CLIENT_MAIL + "=?, " + ConstClient.CLIENT_MOBILE_NUMBER + "=?, " + ConstClient.CLIENT_LOGIN + "=?, " + ConstClient.CLIENT_PASSWORD + "=?, " + ConstClient.CLIENT_FLAG + "=? " +
                    " WHERE " + ConstClient.CLIENT_ID + "=?";

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(update);

            preparedStatement.setString(1, client.getFIO());
            preparedStatement.setString(2, client.getClientCode());
            preparedStatement.setString(3, client.getPassportId());
            preparedStatement.setString(4, client.getMail());
            preparedStatement.setString(5, client.getMobileNumber());
            preparedStatement.setString(6, client.getLogin());
            preparedStatement.setString(7, client.getPassword());
            preparedStatement.setInt(8, client.getFlag());
            preparedStatement.setInt(9, client.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            new MyException(e);
            return false;
        }
        return true;
    }
}