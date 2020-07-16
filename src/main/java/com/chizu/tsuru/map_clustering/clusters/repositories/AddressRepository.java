package com.chizu.tsuru.map_clustering.clusters.repositories;

import com.chizu.tsuru.map_clustering.clusters.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
