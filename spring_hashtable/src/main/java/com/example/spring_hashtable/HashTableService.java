package com.example.spring_hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HashTableService {

    private final HashtableRowRepository rowRepository;
    private final HashtableCellRepository cellRepository;
    private final int TABLE_SIZE = 5;

    @Autowired
    public HashTableService(HashtableRowRepository rowRepository, HashtableCellRepository cellRepository) {
        this.rowRepository = rowRepository;
        this.cellRepository = cellRepository;
    }

    @Transactional
    public void add(String data) {
        int hash = calculateHash(data);
        HashtableRowEntity row = getOrCreateRow(hash);

        if (containsInRow(row, data)) {
            System.out.println(data + " already exists in the table.");
            return;
        }

        HashtableCellEntity newCell = new HashtableCellEntity();
        newCell.setData(data);
        newCell.setRow(row);

        if (row.getFirstCell() == null) {
            row.setFirstCell(newCell);
        } else {
            HashtableCellEntity current = row.getFirstCell();
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newCell);
        }

        row.getCells().add(newCell);
        cellRepository.save(newCell);
        rowRepository.save(row);

        System.out.println("Added cell: " + data);
        System.out.print("Current state of row " + hash + ": ");
        printRow(row);
    }


    @Transactional
    public boolean remove(String data) {
        int hash = calculateHash(data);
        Optional<HashtableRowEntity> rowOptional = rowRepository.findByIndex(hash);
        if (rowOptional.isPresent()) {
            HashtableRowEntity row = rowOptional.get();
            HashtableCellEntity currentCell = row.getFirstCell();
            HashtableCellEntity previousCell = null;

            while (currentCell != null) {
                if (currentCell.getData().equals(data)) {
                    if (previousCell == null) {
                        row.setFirstCell(currentCell.getNext());
                    } else {
                        previousCell.setNext(currentCell.getNext());
                    }
                    row.getCells().remove(currentCell);
                    cellRepository.delete(currentCell);
                    rowRepository.save(row);

                    System.out.println("Deleted cell : " + data);
                    System.out.print("Current state of row " + hash + ":  ");
                    printRow(row);

                    return true;
                }
                previousCell = currentCell;
                currentCell = currentCell.getNext();
            }
        }
        return false;
    }

    public boolean contains(String data) {
        int hash = calculateHash(data);
        Optional<HashtableRowEntity> rowOptional = rowRepository.findByIndex(hash);
        if (rowOptional.isPresent()) {
            HashtableCellEntity cell = rowOptional.get().getFirstCell();
            while (cell != null) {
                if (cell.getData().equals(data)) {
                    return true;
                }
                cell = cell.getNext();
            }
        }
        return false;
    }


    public int calculateHash(String data) {
        int hash = 0;
        for (int i = 0; i < data.length(); i++) {
            hash = (hash * 31 + data.charAt(i)) % TABLE_SIZE;
        }
        return hash;
    }



    private HashtableRowEntity getOrCreateRow(int index) {
        return rowRepository.findByIndex(index).orElseGet(() -> {
            HashtableRowEntity newRow = new HashtableRowEntity();
            newRow.setIndex(index);
            rowRepository.save(newRow);
            return newRow;
        });
    }

    private boolean containsInRow(HashtableRowEntity row, String data) {
        HashtableCellEntity cell = row.getFirstCell();
        while (cell != null) {
            if (cell.getData().equals(data)) {
                return true;
            }
            cell = cell.getNext();
        }
        return false;
    }

    private HashtableCellEntity findLastCell(HashtableRowEntity row) {
        HashtableCellEntity cell = row.getFirstCell();
        if (cell == null) {
            return null;
        }
        while (cell.getNext() != null) {
            cell = cell.getNext();
        }
        return cell;
    }
    private void printRow(HashtableRowEntity row) {
        HashtableCellEntity cell = row.getFirstCell();
        while (cell != null) {
            System.out.print(cell.getData() + " ");
            cell = cell.getNext();
        }
        System.out.println();
    }
    public int size() {
        return (int) cellRepository.count();
    }

//    public int size() {
//        int totalSize = 0;
//        for (int i = 0; i < TABLE_SIZE; i++) {
//            Optional<HashtableRowEntity> rowOptional = rowRepository.findByIndex(i);
//            if (rowOptional.isPresent()) {
//                totalSize += rowOptional.get().getCells().size();
//            }
//        }
//        return totalSize;
//    }


    public List<HashtableRowEntity> getTable() {
        return rowRepository.findAll();
    }

    public void print() {
        for (int i = 0; i < TABLE_SIZE; i++) {
            Optional<HashtableRowEntity> rowOptional = rowRepository.findByIndex(i);
            if (rowOptional.isPresent()) {
                HashtableRowEntity row = rowOptional.get();
                System.out.print("Row " + i + ": ");
                HashtableCellEntity cell = row.getFirstCell();
                while (cell != null) {
                    System.out.print(cell.getData() + " ");
                    cell = cell.getNext();
                }
                System.out.println();
            } else {
                System.out.println("Row " + i + ": ");
            }
        }
    }
}
