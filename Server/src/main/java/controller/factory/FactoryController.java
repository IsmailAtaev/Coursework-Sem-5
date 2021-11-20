package controller.factory;

import controller.AdminController;
import controller.ClientController;

/**
 * @author Ataeyv I.M.
 * Factory pattern
 * */

public class FactoryController {
    public static IController getType(String type) {
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
