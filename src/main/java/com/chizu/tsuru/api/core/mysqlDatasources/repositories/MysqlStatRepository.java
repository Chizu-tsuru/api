package com.chizu.tsuru.api.core.mysqlDatasources.repositories;

import com.chizu.tsuru.api.core.mysqlDatasources.models.MysqlStat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MysqlStatRepository  extends JpaRepository<MysqlStat, Integer> {
    Page<MysqlStat> findAll(Pageable pageable);
}
