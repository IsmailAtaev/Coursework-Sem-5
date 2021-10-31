package com.example.model.land;

import com.example.model.iinformation.IInformation;
import com.example.model.tour.Tour;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Atayev Ismayyl
 * */

public class Land extends Tour implements Serializable, IInformation {

    private String carType;


    public Land() {
    }

    public Land(String countryName, String cityName, float price, String duration, String tourCode,
                String tourDate, String tourName, String tourType, String carType) {
        super(countryName, cityName, price, duration, tourCode, tourDate, tourName, tourType);
        this.carType = carType;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Land land = (Land) o;
        return Objects.equals(carType, land.carType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), carType);
    }

    @Override
    public String toString() {
        return "Land{" +
                "carType='" + carType + '\'' +
                '}';
    }
}

