package com.example.demo.controller;

import com.example.demo.Service.PeopleService;
import com.example.demo.entity.People;
import com.example.demo.entity.RequestNameUpdate;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/people")
public class PeopleController {

    @Autowired
    PeopleService service;

    @PostMapping
    public ResponseEntity<People> create(@RequestBody @Valid People people){
        People guy = service.create(people);
        return ResponseEntity.status(201).body(guy);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<People> findById(@PathVariable int id){
        People guy = service.findById(id);
        return ResponseEntity.ok().body(guy);
    }

    @GetMapping
    public ResponseEntity<List<People>> findAll(){
        List<People> guy = service.findAll();
        return ResponseEntity.ok().body(guy);
    }

    @PatchMapping("/id/{id}")
    public ResponseEntity<People> updateName(@PathVariable int id, @RequestBody @Valid RequestNameUpdate nome){
        People guy = service.updateName(id, nome);
        return ResponseEntity.ok().body(guy);
    }
}
