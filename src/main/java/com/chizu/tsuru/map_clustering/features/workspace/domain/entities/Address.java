package com.chizu.tsuru.map_clustering.features.workspace.domain.entities;

public class Address {
    private Integer addressId;
    private String area;
    private String administrativeAreaOne;
    private String administrativeAreaTwo;
    private String country;
    private String city;

    public Address(String area, String administrativeAreaOne, String administrativeAreaTwo, String country, String city) {
        this.area = area;
        this.administrativeAreaOne = administrativeAreaOne;
        this.administrativeAreaTwo = administrativeAreaTwo;
        this.country = country;
        this.city = city;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAdministrativeAreaOne() {
        return administrativeAreaOne;
    }

    public void setAdministrativeAreaOne(String administrativeAreaOne) {
        this.administrativeAreaOne = administrativeAreaOne;
    }

    public String getAdministrativeAreaTwo() {
        return administrativeAreaTwo;
    }

    public void setAdministrativeAreaTwo(String administrativeAreaTwo) {
        this.administrativeAreaTwo = administrativeAreaTwo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
