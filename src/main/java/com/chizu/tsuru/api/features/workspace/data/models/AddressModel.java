package com.chizu.tsuru.api.features.workspace.data.models;
import com.chizu.tsuru.api.features.workspace.domain.entities.Address;
import com.chizu.tsuru.api.features.workspace.domain.entities.Location;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "address")
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;

    private String area;

    private String administrative_area_1;

    private String administrative_area_2;

    private String country;

    private String city;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cluster_id")
    private ClusterModel cluster;

    public AddressModel() {
    }

    public AddressModel(String area, String administrative_area_1, String administrative_area_2, String country,
                        String city, ClusterModel clusterModel) {
        this.area = area;
        this.administrative_area_1 = administrative_area_1;
        this.administrative_area_2 = administrative_area_2;
        this.country = country;
        this.city = city;
        this.cluster = clusterModel;
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

    public ClusterModel getCluster() {
        return cluster;
    }

    public void setCluster(ClusterModel cluster) {
        this.cluster = cluster;
    }

    public Address toLocation() {
        return new Address(area, administrative_area_1, administrative_area_2, country, city);
    }

    public static AddressModel fromAddress(Address address, ClusterModel clusterModel) {
        return new AddressModel(address.getArea(), address.getAdministrative_area_1(),
                address.getAdministrative_area_2(), address.getCountry(), address.getCity(), clusterModel);
    }
}
