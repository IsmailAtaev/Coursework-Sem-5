package model.bd.dbhtour;

import com.example.model.myexception.MyException;
import com.example.model.tour.Tour;
import model.bd.idbhandler.IDBHandler;
import model.configs.tourBD.ConstTour;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        return null;
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
