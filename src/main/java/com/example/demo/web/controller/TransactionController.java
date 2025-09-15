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
    public ResponseEntity<RequestTransactionCreate> create(@RequestBody @Valid RequestTransactionCreate transaction){
        RequestTransactionCreate transaction1 = service.create(transaction);
        return ResponseEntity.status(201).body(transaction1);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> findAll(){
        List<Transaction> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }
}
