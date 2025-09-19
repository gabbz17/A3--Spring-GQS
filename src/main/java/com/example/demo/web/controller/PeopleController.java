package com.example.demo.web.controller;

import com.example.demo.Service.PeopleService;
import com.example.demo.entity.People;

import com.example.demo.web.dto.RequestNameUpdate;
import com.example.demo.web.dto.ResponsePeopleDto;
import com.example.demo.web.mapper.PeopleMapper;

import jakarta.validation.Valid;

import org.apache.catalina.mapper.Mapper;
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
    public ResponseEntity<ResponsePeopleDto> findById(@PathVariable int id){
        People guy = service.findById(id);
        ResponsePeopleDto dto = PeopleMapper.toDto(guy);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    public ResponseEntity<List<ResponsePeopleDto>> findAll(){
        List<People> guy = service.findAll();
        List<ResponsePeopleDto> dto = PeopleMapper.toAllDto(guy);
        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("/id/{id}")
    public ResponseEntity<People> updateName(@PathVariable int id, @RequestBody @Valid RequestNameUpdate nome){
        People guy = service.updateName(id, nome);
        return ResponseEntity.ok().body(guy);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delebeById(@PathVariable int id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    
}
