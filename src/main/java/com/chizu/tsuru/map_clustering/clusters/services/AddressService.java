package com.chizu.tsuru.map_clustering.clusters.services;

import com.chizu.tsuru.map_clustering.clusters.entities.Address;
import com.chizu.tsuru.map_clustering.clusters.entities.Cluster;
import com.chizu.tsuru.map_clustering.core.errors.NotFoundException;
import com.chizu.tsuru.map_clustering.clusters.repositories.AddressRepository;
import com.chizu.tsuru.map_clustering.workspaces.services.GeocodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final GeocodingService geocodingService;

    @Autowired
    public AddressService(AddressRepository addressRepository,
                          GeocodingService geocodingService) {
        this.addressRepository = addressRepository;
        this.geocodingService = geocodingService;
    }

    @Transactional
    public Address createAddress(Cluster c) {
        String response = geocodingService.getDataFromCoordinate(c.getLatitude(), c.getLongitude());
        Address address = geocodingService.convertResponseStringToAddressObject(response);

        return addressRepository.save(address);
    }

    @Transactional(readOnly = true)
    public Address getAddress(Integer addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException(addressId + ": this address has not been found"));
    }
}