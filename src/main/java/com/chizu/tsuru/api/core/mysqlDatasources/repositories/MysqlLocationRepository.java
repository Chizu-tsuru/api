package com.chizu.tsuru.api.core.mysqlDatasources.repositories;

import com.chizu.tsuru.api.core.mysqlDatasources.models.MysqlLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MysqlLocationRepository  extends JpaRepository<MysqlLocation, Integer> {
}
