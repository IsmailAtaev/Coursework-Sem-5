package com.example.model.connect;

import com.example.model.myexception.MyException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Atayev.I.M
 * */

public class Connect implements Closeable {

    private Socket socket;

    private BufferedReader bufferedReader;

    private BufferedWriter bufferedWriter;

    private ObjectOutputStream oos;

    private ObjectInputStream ois;


    public Connect(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            this.bufferedReader = createReader();
            this.bufferedWriter = createWrite();
            this.oos = createOOS();
            this.ois = createOIS();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connect(ServerSocket serverSocket) {
        try {
            this.socket = serverSocket.accept();
            this.bufferedReader = createReader();
            this.bufferedWriter = createWrite();
            this.oos = createOOS();
            this.ois = createOIS();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedReader createReader() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private BufferedWriter createWrite() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    private ObjectOutputStream createOOS() throws IOException {
        return new ObjectOutputStream(socket.getOutputStream());
    }

    private ObjectInputStream createOIS() throws IOException {
        return new ObjectInputStream(socket.getInputStream());
    }

    public void writeLine(String message) throws IOException {
        bufferedWriter.write(message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    public String readLine() throws IOException {
        return bufferedReader.readLine();
    }

    public Object readObj() throws IOException, ClassNotFoundException {
        return ois.readObject();
    }

    public void writeObj(Object obj) throws IOException {
        oos.writeObject(obj);
    }

    public ArrayList<Object> readObjList() throws IOException, ClassNotFoundException {
        return (ArrayList<Object>) ois.readObject();
    }

    public void writeObjList(ArrayList<Object> objList) throws IOException {
        oos.writeObject(objList);
        oos.flush();
    }

    public void clearConnect() throws IOException {
        oos.flush();
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    @Override
    public void close() {
        try {
            bufferedReader.close();
            bufferedWriter.close();
            oos.close();
            ois.close();
            socket.close();
        } catch (IOException e) {
            new MyException("Class Connect ",e);
        }
    }
}