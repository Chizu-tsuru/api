package com.chizu.tsuru.api.repositories;

import com.chizu.tsuru.api.Entities.Address;
import com.chizu.tsuru.api.Entities.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    Address findOneByCluster(@Param("cluster") Cluster cluster);
}
