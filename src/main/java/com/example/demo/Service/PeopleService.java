package com.example.demo.Service;

import com.example.demo.entity.People;

import com.example.demo.repository.PeopleRepository;
import com.example.demo.web.dto.RequestNameUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeopleService {

     @Autowired
    PeopleRepository repository;

    public People create(People people) {
        return repository.save(people);
    }

    public List<People> findAll() {
        return repository.findAll();
    }

    public People findById(Long id) {
        return repository.findById(id).get();
    }
    
    public People updateName(Long id, RequestNameUpdate nome) {
        People people = findById(id);

        people.setName(nome.name());

        return repository.save(people);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
