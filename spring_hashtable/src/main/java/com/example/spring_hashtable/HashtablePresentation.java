package com.example.spring_hashtable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hashtable")
public class HashtablePresentation {
    @Autowired
    HashTableService hashTableService;

    @PostMapping("/add")
    public ResponseEntity<Void> add(@RequestParam String name) {
        hashTableService.add(name);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/contains")
    public ResponseEntity<Boolean> contains(@RequestParam String name) {
        boolean result = hashTableService.contains(name);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Boolean> remove(@RequestParam String name) {
        boolean result = hashTableService.remove(name);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/size")
    public ResponseEntity<Integer> size() {
        int size = hashTableService.size();
        return ResponseEntity.ok(size);
    }

//    @GetMapping("/print")
//    public ResponseEntity<Void> print() {
//        hashTableService.print();
//        return ResponseEntity.ok().build();
//    }

//    @GetMapping
//    public ResponseEntity<Map<String, Object>> print() {
//        Map<String, Object> table = hashTableService.getTable();
//        return ResponseEntity.ok(table);
//    }

    @GetMapping("/print")
    public ResponseEntity<List<HashtableRowEntity>> print() {
        List<HashtableRowEntity> table = hashTableService.getTable();
        return ResponseEntity.ok(table);
    }
}
