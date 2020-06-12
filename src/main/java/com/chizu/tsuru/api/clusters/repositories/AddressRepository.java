package com.chizu.tsuru.api.clusters.repositories;

import com.chizu.tsuru.api.clusters.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
