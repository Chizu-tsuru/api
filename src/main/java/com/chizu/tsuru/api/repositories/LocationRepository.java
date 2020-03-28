package com.chizu.tsuru.api.repositories;

import com.chizu.tsuru.api.Entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
}
