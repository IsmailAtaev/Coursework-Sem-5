package model.bd.dbhtour;

import com.example.model.client.Client;
import com.example.model.myexception.MyException;
import com.example.model.tour.Tour;
import model.bd.idbhandler.IDBHandler;
import model.configs.clientBD.ConstClient;
import model.configs.tourBD.ConstTour;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBHTour implements IDBHandler {

    @Override
    public boolean addObj(Object obj) {
        try {
            Tour tour = (Tour) obj;
            String insert = "INSERT INTO " + ConstTour.TOUR_TABLE + "("
                    + ConstTour.TOUR_COUNTRY_NAME + ","
                    + ConstTour.TOUR_CITY_NAME + ","
                    + ConstTour.TOUR_PRICE + ","
                    + ConstTour.TOUR_DURATION + ","
                    + ConstTour.TOUR_CODE + ","
                    + ConstTour.TOUR_DATE + ","
                    + ConstTour.TOUR_NAME + ","
                    + ConstTour.TOUR_TYPE + ")" + "VALUES(?,?,?,?,?,?,?,?)";


            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, tour.getCountryName());
            prSt.setString(2, tour.getCityName());
            prSt.setFloat(3,tour.getPrice());
            prSt.setString(4, tour.getDuration());
            prSt.setString(5,tour.getTourCode());
            prSt.setString(6,tour.getTourDate());
            prSt.setString(7,tour.getTourName());
            prSt.setString(8,tour.getTourType());

            prSt.executeUpdate();

        } catch (SQLException e) {
            new MyException(e);
            return false;
        } catch (ClassNotFoundException e) {
            new MyException(e);
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<Object> getList() {
        ArrayList<Object> arrayList = new ArrayList<>();
        try {
            String select = "SELECT * FROM " + ConstTour.TOUR_TABLE;
            Statement statement = getDbConnection().createStatement();
            ResultSet resSet = statement.executeQuery(select);
            while (resSet.next()) {
                Tour t = new Tour();
                t.setId(resSet.getInt(1));
                t.setCountryName(resSet.getString(2));
                t.setCityName(resSet.getString(3));
                t.setPrice(resSet.getFloat(4));
                t.setDuration(resSet.getString(5));
                t.setTourCode(resSet.getString(6));
                t.setTourDate(resSet.getString(7));
                t.setTourName(resSet.getString(8));
                t.setTourType(resSet.getString(9));
                arrayList.add(t);
               /* Client client = new Client();
                client.setId(resSet.getInt(1));
                client.setFIO(resSet.getString(2));
                client.setClientCode(resSet.getString(3));
                client.setPassportId(resSet.getString(4));
                client.setMail(resSet.getString(5));
                client.setMobileNumber(resSet.getString(6));
                client.setLogin(resSet.getString(7));
                client.setPassword(resSet.getString(8));
                client.setFlag(resSet.getInt(9));*/

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
        return false;
    }

    @Override
    public boolean editObj(Object obj) {
        return false;
    }
}
