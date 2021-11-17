package model.bd.dbhclient;

import com.example.model.client.Client;
import model.bd.idbhandler.IDBHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class DBHClientTest {

    @Test
    void addObjPositiveTest() {

        Client client = new Client("Kadyrow.M", "C12", "AS12343",
                "kadyrowm@gmail.com", "+37525123654789", "k123", "kadyrow123", 2);
        IDBHandler handler = new DBHClient();
        boolean actually = handler.addObj(client);
        Assertions.assertEquals(true, actually);
    }

    @Test
    void getList() {
        IDBHandler handler = new DBHClient();

        ArrayList<Client> list = (ArrayList<Client>) handler.getList().clone();
        for (Client c : list) {
            System.out.println(c.toString());
        }
    }

    @Test
    void deleteObjPositiveTest() {
        IDBHandler handler = new DBHClient();
        ArrayList<Client> list = (ArrayList<Client>) handler.getList().clone();
        String pass = "qwerty123";
        boolean actually = false;

        for (Client c : list) {
            if (pass.equals(c.getPassword())) {
                actually = handler.deleteObj(c);
                break;
            }
        }
        Assertions.assertEquals(true, actually);

    }

}