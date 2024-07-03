package com.example.spring_hashtable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HashtableRowRepository extends JpaRepository<HashtableRowEntity,Integer> {
    @Override
    List<HashtableRowEntity> findAll();
    Optional<HashtableRowEntity> findByIndex(int index);

}
