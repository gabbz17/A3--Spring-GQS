package com.example.demo.Service;

import com.example.demo.entity.People;
import com.example.demo.entity.RequestNameUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PeopleService {

    List<People> list = new ArrayList<>();


    public People create(People people) {
        people.setId(list.size() + 1);
        list.add(people);

        return people;
    }

    public List<People> findAll() {
        return list;
    }

    public People findById(int id) {
        People people = list.get(id - 1);

        return people;
    }
    
    public People updateName(int id, RequestNameUpdate nome) {
        list.get(id - 1).setName(nome.getName());

        People people = list.get(id - 1);
        
        return people;
    }


}
