package com.example.demo.integration;

import com.example.demo.Service.PeopleService;
import com.example.demo.entity.People;
import com.example.demo.repository.PeopleRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

//menciona que esta classe é um ambiente para teste de integracao
@SpringBootTest
@Transactional
public class PeopleServiceTest {

    //injeta dependencias
    @Autowired
    PeopleService service;
    
    @Autowired
    PeopleRepository repository;
    
    @Test
    void createUserTest() {
        //fluxo de teste: instancia do objeto -> chamada do metodo de teste -> verificacao com o JUnit = assert ->
        // chama metodo na camada mais proxima do banco e valida os dados
        People people = new People(null, "Gabriel", "gabriel@gmail.com", "11111111111", BigDecimal.valueOf(1000));

        People create = service.create(people);
        Assertions.assertEquals("Gabriel", create.getName());

        People find = repository.findById(1L).get();
        Assertions.assertNotNull(find);
    }

    @Test
    void findAllUserTest() {

        People gabriel = new People(null, "Gabriel", "gabriel@gmail.com", "11111111111", BigDecimal.valueOf(1000));


        People create = service.create(gabriel);
        Assertions.assertEquals("11111111111", create.getCpf());

        List<People> findAll = repository.findAll();
        Assertions.assertNotNull(findAll);
        Assertions.assertEquals("Gabriel", findAll.getFirst().getName());


    }
}
