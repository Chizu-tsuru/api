package com.chizu.tsuru.api.clusters.repositories;

import com.chizu.tsuru.api.clusters.entities.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClusterRepository extends JpaRepository<Cluster, Integer> {
    @Query(value = "SELECT * from clusters WHERE workspace_id = :workspace", nativeQuery = true)
    List<Cluster> findAllByWorkspaceId(@Param("workspace") Integer workspace);
}
