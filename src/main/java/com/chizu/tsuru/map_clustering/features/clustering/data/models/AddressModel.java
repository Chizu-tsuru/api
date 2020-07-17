package com.chizu.tsuru.map_clustering.features.clustering.data.models;
import com.chizu.tsuru.map_clustering.core.services.JsonParsingService;
import com.chizu.tsuru.map_clustering.features.clustering.domain.entities.Address;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class AddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;

    private String area;

    private String administrativeAreaOne;

    private String administrativeAreaTwo;

    private String country;

    private String city;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    private ClusterModel cluster;

    public AddressModel() {
    }

    public AddressModel(String area, String administrativeAreaOne, String administrativeAreaTwo, String country,
                        String city, ClusterModel clusterModel) {
        this.area = area;
        this.administrativeAreaOne = administrativeAreaOne;
        this.administrativeAreaTwo = administrativeAreaTwo;
        this.country = country;
        this.city = city;
        this.cluster = clusterModel;
    }

    public AddressModel(String area, String administrativeAreaOne, String administrativeAreaTwo, String country,
                        String city) {
        this.area = area;
        this.administrativeAreaOne = administrativeAreaOne;
        this.administrativeAreaTwo = administrativeAreaTwo;
        this.country = country;
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public ClusterModel getCluster() {
        return cluster;
    }

    public void setCluster(ClusterModel cluster) {
        this.cluster = cluster;
    }

    public Address toAddress() {
        return new Address(area, administrativeAreaOne, administrativeAreaTwo, country, city);
    }

    public static AddressModel fromAddress(Address address, ClusterModel clusterModel) {
        if (address == null) return null;
        return new AddressModel(
                address.getArea(),
                address.getAdministrativeAreaOne(),
                address.getAdministrativeAreaTwo(),
                address.getCountry(),
                address.getCity(),
                clusterModel);
    }

    public static AddressModel FromJson(JSONObject jsonObject) {
        return new AddressModel(
                JsonParsingService.parseJson("postal_code", jsonObject),
                JsonParsingService.parseJson("administrative_area_level_1", jsonObject),
                JsonParsingService.parseJson("administrative_area_level_2", jsonObject),
                JsonParsingService.parseJson("country", jsonObject),
                JsonParsingService.parseJson("locality", jsonObject)
        );
    }
}
