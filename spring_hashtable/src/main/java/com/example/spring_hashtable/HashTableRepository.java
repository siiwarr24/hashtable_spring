package com.example.spring_hashtable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashTableRepository extends JpaRepository<HashtableEntity,Integer>{
//  List<HashtableEntity> findByName(String name);
//
//  @Query("Select a from HashtableEntity a where a.name=:p")
//  List<HashtableEntity> findByName2(@Param("p") String name);

}
