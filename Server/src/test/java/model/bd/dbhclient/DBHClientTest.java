package model.bd.dbhclient;

import com.example.model.client.Client;
import com.example.model.iinformation.IInformation;
import model.bd.idbhandler.IDBHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DBHClientTest {

    @Test
    void addObjPositiveTest() {

        Client client = new Client("Kadyrow.M","C12","AS12343",
                "kadyrowm@gmail.com","+37525123654789","k123","kadyrow123",2);
            IDBHandler handler = new DBHClient();
            boolean actually = handler.addObj(client);
        Assertions.assertEquals(true,actually);
    }

    @Test
    void getList() {
        IDBHandler handler = new DBHClient();

        ArrayList<Client> list = (ArrayList<Client>) handler.getList().clone();
        for (Client c : list){
            System.out.println(c.toString());
        }


    }
}