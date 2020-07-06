package com.chizu.tsuru.api.features.workspace.domain.entities;

public class Address {
    private Integer addressId;
    private String area;
    private String administrative_area_1;
    private String administrative_area_2;
    private String country;
    private String city;

    public Address(String area, String administrative_area_1, String administrative_area_2, String country, String city) {
        this.area = area;
        this.administrative_area_1 = administrative_area_1;
        this.administrative_area_2 = administrative_area_2;
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

    public String getAdministrative_area_1() {
        return administrative_area_1;
    }

    public void setAdministrative_area_1(String administrative_area_1) {
        this.administrative_area_1 = administrative_area_1;
    }

    public String getAdministrative_area_2() {
        return administrative_area_2;
    }

    public void setAdministrative_area_2(String administrative_area_2) {
        this.administrative_area_2 = administrative_area_2;
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
