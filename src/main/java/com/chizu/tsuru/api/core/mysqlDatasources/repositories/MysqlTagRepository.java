package com.chizu.tsuru.api.core.mysqlDatasources.repositories;

import com.chizu.tsuru.api.core.mysqlDatasources.models.MysqlTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MysqlTagRepository  extends JpaRepository<MysqlTag, Integer> {
}
