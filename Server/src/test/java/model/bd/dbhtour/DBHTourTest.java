package model.bd.dbhtour;

import com.example.model.tour.Tour;
import model.bd.idbhandler.IDBHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class DBHTourTest {

    @Test
    void addObj() {
        Tour tour = new Tour("qq","ww", 5.3F,"4","T3443","12-06-2021","rr","gg");
        IDBHandler handler = new DBHTour();
        boolean actually = handler.addObj(tour);
        Assertions.assertEquals(true, actually);

    }

    @Test
    void getList() {
        IDBHandler handler = new DBHTour();
        ArrayList<Tour> list = (ArrayList<Tour>) handler.getList().clone();
        System.out.println(" i am tour ");
        for (Tour t : list) {
            System.out.println(t.toString());
        }
    }

    @Test
    void deleteObjTest() {
        IDBHandler handler = new DBHTour();
        Tour t = new Tour();
        t.setId(1);
        boolean actually = handler.deleteObj(t);
        Assertions.assertEquals(true, actually);
    }
}