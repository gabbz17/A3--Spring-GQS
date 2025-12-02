package com.example.demo.Service;

import com.example.demo.entity.People;
import com.example.demo.entity.Transaction;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.web.dto.RequestTransactionCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository repository;

    @Autowired
    PeopleService peopleService;

    public List<Transaction> findAll() {
        return repository.findAll();
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public Transaction create(RequestTransactionCreate dto) {

        People sender = peopleService.findById(dto.idSender());
        People recipient = peopleService.findById(dto.idRecipient());
        Transaction transaction = new Transaction();


        if (sender.getBalance().compareTo(dto.value()) < 0) {
            throw new RuntimeException("Saldo insuficiente para transação!");
        }

        sender.setBalance(sender.getBalance().subtract(dto.value()));
        recipient.setBalance(recipient.getBalance().add(dto.value()));

        transaction.setValue(dto.value());
        transaction.setIdSender(sender);
        transaction.setDateTime(LocalDateTime.now());
        transaction.setIdRecipient(recipient);

        return repository.save(transaction);
    }
}
