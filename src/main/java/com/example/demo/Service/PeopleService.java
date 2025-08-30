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
        list.add(people);
        people.setId(list.size());

        return people;
    }

    /*public List<People> findAll() {
        for (People people : list) {

        }
    }*/

    public People findById(Integer id) {
        People guy = null;

        for(People people : list) {
            if (Objects.equals(people.getId(), id)) {
                guy = new People(people.getId(), people.getName(), people.getEmail(), people.getNumber());
            }
        }

        return guy;
    }
}
