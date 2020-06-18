package com.chizu.tsuru.api.core.mysqlDatasources.repositories;

import com.chizu.tsuru.api.core.mysqlDatasources.models.MysqlAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MysqlAddressRepository extends JpaRepository<MysqlAddress, Integer> {
}
