package com.chizu.tsuru.api.repositories;

import com.chizu.tsuru.api.Entities.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClusterRepository extends JpaRepository<Cluster, Integer> {
}
