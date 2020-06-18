package com.chizu.tsuru.api.core.mysqlDatasources.repositories;

import com.chizu.tsuru.api.core.mysqlDatasources.models.MysqlWorkspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MysqlWorkspaceRepository extends JpaRepository<MysqlWorkspace, Integer> {
}
