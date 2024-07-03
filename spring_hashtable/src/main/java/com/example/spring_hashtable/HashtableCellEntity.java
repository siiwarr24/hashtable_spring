package com.example.spring_hashtable;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class HashtableCellEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String data;

    @ManyToOne
    @JoinColumn(name = "row_id")
    @JsonBackReference
    private HashtableRowEntity row;

    @ManyToOne
    @JoinColumn(name = "next_id")
    private HashtableCellEntity next;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public HashtableRowEntity getRow() {
        return row;
    }

    public void setRow(HashtableRowEntity row) {
        this.row = row;
    }

    public HashtableCellEntity getNext() {
        return next;
    }

    public void setNext(HashtableCellEntity next) {
        this.next = next;
    }
}
