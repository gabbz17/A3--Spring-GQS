package com.example.demo.Service;

import com.example.demo.entity.People;
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
        People guy = null;

        for(People people : list) {
            if (Objects.equals(people.getId(), id)) {
                guy = new People(people.getId(), people.getName(), people.getEmail(), people.getNumber());
            }
        }

        return guy;
    }
    
    public People updateName(int id, String nome) {
        People guy = null;

        for(People people : list) {
            if (Objects.equals(people.getId(), id)) {
                guy = new People(people.getId(), people.getName(), people.getEmail(), people.getNumber());
                guy.setName(nome);
            }
        }

        return guy;
    }


}
