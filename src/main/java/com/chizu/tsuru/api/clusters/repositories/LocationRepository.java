package com.chizu.tsuru.api.clusters.repositories;

import com.chizu.tsuru.api.clusters.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
}
