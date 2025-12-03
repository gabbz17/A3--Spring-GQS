package com.example.demo.JUnit;

import com.example.demo.Service.PeopleService;
import com.example.demo.entity.People;
import com.example.demo.repository.PeopleRepository;
import com.example.demo.web.dto.RequestNameUpdate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {

    //anotacao usada na classe principal a ser testada
    @InjectMocks
    PeopleService service;

    //anotacao usada nas dependencias que estao na classe a ser testada
    @Mock
    PeopleRepository repository;

    private final Long PEOPLE_ID = 1L;
    private People mockPeople;

    @BeforeEach
    void setUp() {
        mockPeople = new People(PEOPLE_ID, "Gabriel", "gabriel@gmail.com", "11111111111", BigDecimal.valueOf(1000.00));
    }

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

    @Test
    void findByIdTest() {
        Mockito.when(repository.findById(PEOPLE_ID)).thenReturn(Optional.of(mockPeople));

        People found = service.findById(PEOPLE_ID);

        Assertions.assertNotNull(found);
        Assertions.assertEquals(PEOPLE_ID, found.getNumberAccount()); // CORRIGIDO
        Assertions.assertEquals("Gabriel", found.getName());
    }

    @Test
    void updateNameTest() {
        String newName = "Gabriel Silva";
        RequestNameUpdate newNameDto = new RequestNameUpdate(newName);

        Mockito.when(repository.findById(PEOPLE_ID)).thenReturn(Optional.of(mockPeople));

        People peopleAfterUpdate = new People(PEOPLE_ID, newName, mockPeople.getEmail(), mockPeople.getCpf(), mockPeople.getBalance());
        Mockito.when(repository.save(Mockito.any(People.class))).thenReturn(peopleAfterUpdate);

        People updated = service.updateName(PEOPLE_ID, newNameDto);

        Assertions.assertEquals(newName, updated.getName());
        Mockito.verify(repository, Mockito.times(1)).save(updated);
    }

    @Test
    void deleteByIdTest() {
        service.deleteById(PEOPLE_ID);

        Mockito.verify(repository, Mockito.times(1)).deleteById(PEOPLE_ID);
    }
}

