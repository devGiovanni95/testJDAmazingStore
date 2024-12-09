package com.deere.theamazingstore.controller;

import com.deere.theamazingstore.model.StoreItem;
import com.deere.theamazingstore.service.StoreItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
public class StoreItemRestController {

    @Autowired
    private StoreItemService service;

    @GetMapping
    public ResponseEntity<List<StoreItem>> findAll(){
        List<StoreItem> item = service.getList();
        return ResponseEntity.ok().body(item);
    }

    @PostMapping
    public ResponseEntity<StoreItem> createStoreItem(@RequestBody StoreItem newItem) {
        StoreItem createdItem = service.create(newItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

}
