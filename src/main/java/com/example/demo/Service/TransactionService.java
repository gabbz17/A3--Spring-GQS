package com.example.demo.Service;

import com.example.demo.entity.People;
import com.example.demo.entity.Transaction;
import com.example.demo.web.dto.RequestTransactionCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TransactionService {

    List<Transaction> transactionList = new ArrayList<>();

    @Autowired
    PeopleService peopleService;

    public RequestTransactionCreate create(RequestTransactionCreate transaction) {

        People sender = peopleService.findById(transaction.idSender());
        People recipient = peopleService.findById(transaction.idRecipient());


        if (sender.getBalance().compareTo(transaction.value()) < 0) {
            throw new RuntimeException("Saldo insuficiente para transação!");
        }

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        recipient.setBalance(recipient.getBalance().add(transaction.value()));

        Random idTransaction = new Random();

        transactionList.add(new Transaction(Math.abs(idTransaction.nextInt()), sender, recipient, transaction.value(), LocalDateTime.now()));

        return transaction;
    }

    public List<Transaction> findAll() {
        return transactionList;
    }
    public void deleteById(int id) {
      transactionList.removeIf(transaction -> transaction.getId() == id);
    }
}
