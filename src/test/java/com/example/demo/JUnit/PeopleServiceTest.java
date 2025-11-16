package com.example.demo.JUnit;

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
import java.util.List;

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

    @Test
    void findAllUserTest() {

        People gabriel = new People(null, "Gabriel", "gabriel@gmail.com", "11111111111", BigDecimal.valueOf(1000));
        People ana = new People(null, "Ana", "ana@gmail.com", "22222222222", BigDecimal.valueOf(1000));

        Mockito.when(repository.findAll()).thenReturn(List.of(gabriel, ana));

        List<People> findAll = service.findAll();

        Assertions.assertEquals("Gabriel", findAll.getFirst().getName());
        Assertions.assertNotNull(findAll);
    }
}
