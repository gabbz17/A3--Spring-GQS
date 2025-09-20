package com.example.demo;

import com.example.demo.Service.PeopleService;
import com.example.demo.entity.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PeopleService peopleService;

    @Override
    public void run(String... args) throws Exception {

        People gabriel = new People(1, "Gabriel Coutinho", "gabriel@gmail.com", "11111111111", "11111111111", BigDecimal.valueOf(1000.00));
        People maria = new People(2, "Maria Antonia", "maria@gmail.com", "2322222222", "22222222222", BigDecimal.valueOf(0));
        People rafael = new People(3, "Rafael Marques", "rafael@gmail.com", "3333333333", "33333333333", BigDecimal.valueOf(300.00));


        PeopleService.peopleList.addAll(List.of(gabriel, maria, rafael));
    }
}
