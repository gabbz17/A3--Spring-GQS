package com.example.demo.Service;

import com.example.demo.entity.People;

import com.example.demo.web.dto.RequestNameUpdate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PeopleService {

    List<People> peopleList = new ArrayList<>();


    public People create(People people) {
        people.setNumberAccount(peopleList.size() + 1);
        peopleList.add(people);

        return people;
    }

    public List<People> findAll() {
        return peopleList;
    }

    public People findById(int id) {
        return peopleList.get(id - 1);
    }
    
    public People updateName(int id, RequestNameUpdate nome) {
        peopleList.get(id - 1).setName(nome.name());

        return peopleList.get(id - 1);
    }
    public boolean deleteById(int id) {
        return peopleList.removeIf(people -> Objects.equals(people.getNumberAccount(), id));
    }
}
