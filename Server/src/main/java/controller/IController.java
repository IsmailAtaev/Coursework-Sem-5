package controller;

import java.io.IOException;

public interface IController {



    void saveDate(String msg);

    void editDate();

    void deleteDate();

    void getDate(String msg) throws IOException;

    void start();


}
