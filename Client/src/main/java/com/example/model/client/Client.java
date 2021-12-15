package com.example.model.client;

import java.io.Serializable;
import java.util.Objects;

public class Client implements Serializable {

    private int id;

    private String FIO;

    private String clientCode;

    private String passportId;

    private String mail;

    private String mobileNumber;

    private String login;

    private String password;

    private int flag;

    public Client() {
    }

    public Client(String FIO, String clientCode, String passportId, String mail,
                  String mobileNumber, String login, String password, int flag) {
        this.FIO = FIO;
        this.clientCode = clientCode;
        this.passportId = passportId;
        this.mail = mail;
        this.mobileNumber = mobileNumber;
        this.login = login;
        this.password = password;
        this.flag = flag;
    }

    public String getFIO() {
        return FIO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return flag == client.flag
                && Objects.equals(FIO, client.FIO)
                && Objects.equals(clientCode, client.clientCode)
                && Objects.equals(passportId, client.passportId)
                && Objects.equals(mail, client.mail)
                && Objects.equals(mobileNumber, client.mobileNumber)
                && Objects.equals(login, client.login)
                && Objects.equals(password, client.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(FIO, clientCode, passportId, mail, mobileNumber, login, password, flag);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", FIO='" + FIO + '\'' +
                ", clientCode='" + clientCode + '\'' +
                ", passportId='" + passportId + '\'' +
                ", mail='" + mail + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", flag=" + flag +
                '}';
    }
}