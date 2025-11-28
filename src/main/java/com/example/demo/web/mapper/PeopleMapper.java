package com.example.demo.web.mapper;

import java.util.List;

import com.example.demo.entity.People;
import com.example.demo.web.dto.ResponsePeopleDto;

public class PeopleMapper {

    public static ResponsePeopleDto toDto(People people) {
        return new ResponsePeopleDto(Math.toIntExact(people.getNumberAccount()), people.getName(), people.getBalance());
    }

    public static List<ResponsePeopleDto> toAllDto(List<People> list) {
        return list.stream().map(PeopleMapper::toDto).toList();
    }
}
