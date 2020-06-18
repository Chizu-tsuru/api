package com.chizu.tsuru.api.core.mysqlDatasources.repositories;

import com.chizu.tsuru.api.core.mysqlDatasources.models.MysqlCluster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MysqlClusterRepository  extends JpaRepository<MysqlCluster, Integer>  {
}
