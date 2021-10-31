package controller.main;

import com.example.model.client.Client;
import com.example.model.tour.Tour;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        client.setFIO("qwerty");
        System.out.println(client.getFIO());

        Tour tour = new Tour();
        tour.setTourName("asdf");
        System.out.print(tour.getTourName());
    }
}
