package model.bd.dbhtour;

import com.example.model.tour.Tour;
import model.bd.dbhclient.DBHClient;
import model.bd.idbhandler.IDBHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    }

    @Test
    void deleteObj() {
    }

    @Test
    void editObj() {
    }
}