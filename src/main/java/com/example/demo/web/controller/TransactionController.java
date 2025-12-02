package com.example.demo.web.controller;

import com.example.demo.Service.TransactionService;
import com.example.demo.entity.Transaction;
import com.example.demo.web.dto.RequestTransactionCreate;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/transaction")
public class TransactionController {

    @Autowired
    TransactionService service;

    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody @Valid RequestTransactionCreate dto){
        Transaction transaction = service.create(dto);
        return ResponseEntity.status(201).body(transaction);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> findAll(){
        List<Transaction> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delebeById(@PathVariable int id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
