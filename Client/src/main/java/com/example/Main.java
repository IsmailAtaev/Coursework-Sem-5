package com.example;


import com.example.model.client.Client;
import com.example.model.connect.Connect;
import com.example.model.order.Order;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Order order = new Order("q11", "w22");
        Connect connect = new Connect("127.0.0.1", 8189);
        connect.writeObj(order);
        Client client = new Client("qwerty", 2536);
        connect.writeObj(client);
    }
}





