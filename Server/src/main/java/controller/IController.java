package controller;

import java.io.IOException;

public interface IController {



    void saveDate();

    void editDate();

    void deleteDate();

    void getDate(String msg) throws IOException;

    void start();


}
