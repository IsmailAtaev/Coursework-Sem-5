package com.example.model.client;


import java.io.Serializable;

public class Client implements Serializable {
    private String FIO;
    private int year;

    public Client(String FIO, int year) {
        this.FIO = FIO;
        this.year = year;
    }

    public Client() {

    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    @Override
    public String toString() {
        return "Client{" +
                "FIO='" + FIO + '\'' +
                ", year=" + year +
                '}';
    }
}
