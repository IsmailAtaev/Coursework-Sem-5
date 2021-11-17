package controller;


/**
 * @author Ataeyv Ismayyl
 * */

public class FactoryController {
    public static synchronized IController getType(String type) {
        switch (type) {
            case "admin":
                return new AdminController();
            case "client":
                return new ClientController();
            default:
                throw new RuntimeException();
        }
    }
}
