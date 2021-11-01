package controller.main;


import com.example.model.client.Client;
import com.example.model.connect.Connect;
import com.example.model.order.Order;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static int port = 8189;

    public static void main(String[] args) {
        try {
            Connect connect = new Connect(new ServerSocket(port));
            Order client = (Order) connect.readObj();
            System.out.println(client.toString());
            Client client1 =  (Client) connect.readObj();
            System.out.println(client1.toString());


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
