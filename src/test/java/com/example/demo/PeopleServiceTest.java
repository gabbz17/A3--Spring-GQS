package com.example.demo;

import com.example.demo.Service.PeopleService;
import com.example.demo.entity.People;
import com.example.demo.repository.PeopleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {

    //anotacao usada na classe principal a ser testada
    @InjectMocks
    PeopleService service;

    //anotacao usada nas dependencias que estao na classe a ser testada
    @Mock
    PeopleRepository repository;

    @Test
    void createUserTest() {

        //fluxo de teste: instancia do objeto -> condicao do mock -> chamada do metodo a ser testado -> verificacao com o JUnit = assert
        People people = new People(1L, "Gabriel", "gabriel@gmail.com", "11111111111", BigDecimal.valueOf(1.000));

        Mockito.when(repository.save(Mockito.any())).thenReturn(people);

        People create = service.create(people);

        Assertions.assertNotNull(create);
    }
}
