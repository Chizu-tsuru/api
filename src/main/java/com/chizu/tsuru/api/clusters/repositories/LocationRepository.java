package com.chizu.tsuru.api.clusters.repositories;

import com.chizu.tsuru.api.clusters.entities.Cluster;
import com.chizu.tsuru.api.clusters.entities.Location;
import com.chizu.tsuru.api.workspaces.entities.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    List<Location> findAllByCluster(@Param("cluster") Cluster cluster);

    @Query(value = "SELECT * from locations WHERE cluster_id IN (SELECT cluster_id FROM clusters WHERE workspace_id = :workspace)", nativeQuery=true)
    List<Location> findAllByWorkspace(@Param("workspace") Workspace workspace);

}
