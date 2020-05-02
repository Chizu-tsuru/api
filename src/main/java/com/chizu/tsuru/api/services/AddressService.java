package com.chizu.tsuru.api.services;

import com.chizu.tsuru.api.Entities.Address;
import com.chizu.tsuru.api.Entities.Cluster;
import com.chizu.tsuru.api.exceptions.NotFoundException;
import com.chizu.tsuru.api.repositories.AddressRepository;
import com.chizu.tsuru.api.repositories.ClusterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService {

    private AddressRepository addressRepository;
    private ClusterRepository clusterRepository;
    private GeocodingService geocodingService;

    @Autowired
    public AddressService(AddressRepository addressRepository,
                          ClusterRepository clusterRepository,
                          GeocodingService geocodingService) {
        this.addressRepository = addressRepository;
        this.clusterRepository = clusterRepository;
        this.geocodingService = geocodingService;
    }

    @Transactional
    public Integer createAddress(int cluster_id) {

        Cluster c =  clusterRepository.findById(cluster_id).orElseThrow(()-> new NotFoundException("Cluster not found"));

        if( ! doesTheAddressAlreadyExist(c)){
            String response = geocodingService.getDataFromCoordonate(c.getLatitude(),c.getLongitude());
            Address address = geocodingService.convertResponseStringToAddressObject(response, c);

            Address created = addressRepository.save(address);
            return created.getAddressId();
        }
        return 0;
    }

    private boolean doesTheAddressAlreadyExist(Cluster cluster){
        return  addressRepository.findOneByCluster(cluster)  == null ? false:true;
    }
}