package com.chizu.tsuru.map_clustering.stats.repositories;

import com.chizu.tsuru.map_clustering.stats.entities.Stat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatRepository extends PagingAndSortingRepository<Stat, Integer> {
    Page<Stat> findAll(Pageable pageable);
}
