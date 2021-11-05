package controller;

import java.io.IOException;

public interface IController {



    void saveDate(String msg);

    void editDate(String msg);

    void deleteDate(String msg) throws IOException;

    void getDate(String msg) throws IOException;

    void start();


}
