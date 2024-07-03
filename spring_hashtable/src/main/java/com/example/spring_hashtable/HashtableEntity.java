package com.example.spring_hashtable;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class HashtableEntity {
    @Id @GeneratedValue
    private int id;
    private int size;
    private String name;
    @OneToMany(cascade = CascadeType.ALL)//
    private List<HashtableRowEntity> rows;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<HashtableRowEntity> getRows() {
        return rows;
    }

    public void setRows(List<HashtableRowEntity> rows) {
        this.rows = rows;
    }
}
