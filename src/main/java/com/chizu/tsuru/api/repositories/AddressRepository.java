package com.chizu.tsuru.api.repositories;

import com.chizu.tsuru.api.Entities.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Cluster, Integer> {
}
