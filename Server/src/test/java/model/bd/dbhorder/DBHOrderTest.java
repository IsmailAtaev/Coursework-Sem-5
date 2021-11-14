package model.bd.dbhorder;

import com.example.model.order.Order;
import model.bd.idbhandler.IDBHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class DBHOrderTest {

    @Test
    void addObjTest() {
        IDBHandler idbHandler = new DBHOrder();
        Order order = new Order();
        order.setTourCode("T91");
        order.setClientCode("C55");
        boolean actually = idbHandler.addObj(order);
        Assertions.assertEquals(true, actually);
    }

    @Test
    void getList() {
        IDBHandler idbHandler = new DBHOrder();
        ArrayList<Order> orders = (ArrayList<Order>) idbHandler.getList().clone();
        for (Order o : orders) {
            System.out.println(o.toString());
        }
    }

    @Test
    void deleteObj() {
    }

    @Test
    void editObj() {
    }
}