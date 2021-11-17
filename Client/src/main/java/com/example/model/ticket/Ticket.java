package com.example.model.ticket;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Atayev I.M.
 */

public class Ticket implements  Serializable {

    private int id;

    private String ticketCode;

    private String userCode;

    private String transportType;

    private String departurePoint;

    private String arrivalPoint;

    private String departureData;

    public Ticket() {
    }

    public Ticket(String ticketCode, String userCode, String transportType,
                  String departurePoint, String arrivalPoint, String departureData) {
        this.ticketCode = ticketCode;
        this.userCode = userCode;
        this.transportType = transportType;
        this.departurePoint = departurePoint;
        this.arrivalPoint = arrivalPoint;
        this.departureData = departureData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getDeparturePoint() {
        return departurePoint;
    }

    public void setDeparturePoint(String departurePoint) {
        this.departurePoint = departurePoint;
    }

    public String getArrivalPoint() {
        return arrivalPoint;
    }

    public void setArrivalPoint(String arrivalPoint) {
        this.arrivalPoint = arrivalPoint;
    }

    public String getDepartureData() {
        return departureData;
    }

    public void setDepartureData(String departureData) {
        this.departureData = departureData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(ticketCode, ticket.ticketCode)
                && Objects.equals(userCode, ticket.userCode)
                && Objects.equals(transportType, ticket.transportType)
                && Objects.equals(departurePoint, ticket.departurePoint)
                && Objects.equals(arrivalPoint, ticket.arrivalPoint)
                && Objects.equals(departureData, ticket.departureData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketCode, userCode, transportType, departurePoint, arrivalPoint, departureData);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", ticketCode='" + ticketCode + '\'' +
                ", userCode='" + userCode + '\'' +
                ", transportType='" + transportType + '\'' +
                ", departurePoint='" + departurePoint + '\'' +
                ", arrivalPoint='" + arrivalPoint + '\'' +
                ", departureData='" + departureData + '\'' +
                '}';
    }
}
