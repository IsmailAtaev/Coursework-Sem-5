package controller.mythread;

import com.example.model.client.Client;
import com.example.model.connect.Connect;
import com.example.model.myexception.MyException;
import controller.IController;

public class MyThread extends Thread{

    private Connect connect;
    private Object locker;

    public MyThread(Connect connect,Object locker){
        this.connect = connect;
        this.locker = locker;
    }



    @Override
    public void run() {
        try{
            String msgButton = connect.readLine();
            String login = null;
            String pass = null;
            if(msgButton.equals("singIn")){



            } else if (msgButton.equals("singUp")) {
                Client client = (Client) connect.readObj();


            }




        }catch (Exception e){
            new MyException(e);
        }



    }
}
