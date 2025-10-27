package com.example.demo;

import com.example.demo.Service.PeopleService;
import com.example.demo.entity.People;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {

    @InjectMocks
    PeopleService service;

    List<People> peopleList = new ArrayList<>();

    @BeforeEach
    void setup() {
        People gabriel = new People(0, "Gabriel", "gabriel@gmail.com", "1111111111", "11111111111", BigDecimal.valueOf(1000));
        People ana = new People(2, "Ana", "ana@gmail.com", "2222222222", "22222222222", BigDecimal.valueOf(500));

        peopleList.addAll(List.of(gabriel, ana));
    }

    @Test
    void findByIdUserTest() {
        People people = peopleList.get(1);

        assertNotNull(people);
        assertEquals("Ana", people.getName());
    }

    @Test
    void createUserTest() {
        People gabriel = new People(1, "Gabriel", "gabriel@gmail.com", "1111111111", "11111111111", BigDecimal.valueOf(1000));

        when(service.create(any())).thenReturn(gabriel);

        People create = service.create(peopleList.getFirst());

        assertNotNull(create);
    }
}
