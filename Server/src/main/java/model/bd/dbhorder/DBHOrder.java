package model.bd.dbhorder;

import com.example.model.myexception.MyException;
import com.example.model.order.Order;
import model.bd.idbhandler.IDBHandler;
import model.configs.orderBD.ConstOrder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Ataeyv I.M.
 * @implNote IDBHandler
 * */

public class DBHOrder implements IDBHandler {

   @Override
    public boolean addObj(Object obj) {
       try {
           Order order = (Order) obj;
           String insert = "INSERT INTO " + ConstOrder.ORDER_TABLE + "("
                   + ConstOrder.ORDER_CLIENT_CODE + ","
                   + ConstOrder.ORDER_TOUR_CODE + ")" + "VALUES(?,?)";
           PreparedStatement prSt = getDbConnection().prepareStatement(insert);
           prSt.setString(1, order.getClientCode());
           prSt.setString(2, order.getTourCode());
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
            String select = "SELECT * FROM " + ConstOrder.ORDER_TABLE;
            Statement statement = getDbConnection().createStatement();
            ResultSet resSet = statement.executeQuery(select);
            while (resSet.next()) {
                Order order = new Order();
                order.setId(resSet.getInt(1));
                order.setClientCode(resSet.getString(2));
                order.setTourCode(resSet.getString(3));
                arrayList.add(order);
            }
        } catch (ClassNotFoundException | SQLException e) {
            new MyException(e);
        }
        return arrayList;
    }

    @Override
    public boolean deleteObj(Object obj) {
        try {
            Order order = (Order) obj;
            String DELETE = "DELETE FROM " + ConstOrder.ORDER_TABLE + " WHERE " + ConstOrder.ORDER_ID + "='" + order.getId() + "'; ";
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
        return false;
    }
}
