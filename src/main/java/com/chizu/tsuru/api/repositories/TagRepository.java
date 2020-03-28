package com.chizu.tsuru.api.repositories;

import com.chizu.tsuru.api.Entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
}
