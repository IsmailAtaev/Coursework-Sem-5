package model.bd.dbhclient;

import com.example.model.client.Client;
import com.example.model.iinformation.IInformation;
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
    public boolean addObj(IInformation obj) {
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
            // prSt.setString(2, client.getClientCode());
            // prSt.setString(3, client.getPassportId());
            // prSt.setString(4, client.getMail());
            //prSt.setString(5, client.getMobileNumber());
            // prSt.setString(6, client.getLogin());
            //prSt.setString(7, client.getPassword());
            //prSt.setInt(8, client.getFlag());
            prSt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            new MyException(e);
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<IInformation> getList() {
        ArrayList<IInformation> arrayList = new ArrayList<>();
        try {
            String select = "SELECT * FROM " + ConstClient.CLIENT_TABLE;
            Statement statement = getDbConnection().createStatement();
            ResultSet resSet = statement.executeQuery(select);
            while (resSet.next()) {
                Client client = new Client();
                client.setFIO(resSet.getString(2));
                /*client.setClientCode(resSet.getString(3));
                client.setPassportId(resSet.getString(4));
                client.setMail(resSet.getString(5));
                client.setMobileNumber(resSet.getString(6));
                client.setLogin(resSet.getString(7));
                client.setPassword(resSet.getString(8));
                client.setFlag(resSet.getInt(9));
                  */
                arrayList.add((IInformation) client);
            }
        } catch (SQLException e) {
            new MyException(e);
        } catch (ClassNotFoundException e) {
            new MyException(e);
        }
        return arrayList;
    }
}
