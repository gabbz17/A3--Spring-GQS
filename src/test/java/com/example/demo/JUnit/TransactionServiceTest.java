package com.example.demo.JUnit;

import com.example.demo.Service.PeopleService;
import com.example.demo.Service.TransactionService;
import com.example.demo.entity.People;
import com.example.demo.entity.Transaction;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.web.dto.RequestTransactionCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService;

    @Mock
    PeopleService peopleService;

    @Mock
    TransactionRepository repository;

    private People sender;
    private People recipient;

    @BeforeEach
    void setUp() {
        sender = new People(1L, "Gabriel", "gabriel@gmail.com", "11111111111", BigDecimal.valueOf(2000.00));
        recipient = new People(2L, "Ana", "ana@gmail.com", "22222222222", BigDecimal.valueOf(1000.00));

        Mockito.when(peopleService.findById(sender.getNumberAccount())).thenReturn(sender);
        Mockito.when(peopleService.findById(recipient.getNumberAccount())).thenReturn(recipient);
    }

    @Test
    void createTransactionSuccessTest() {
        BigDecimal transactionValue = BigDecimal.valueOf(500.00);

        RequestTransactionCreate request = new RequestTransactionCreate(sender.getNumberAccount(), recipient.getNumberAccount(), transactionValue);

        Transaction newTransaction = new Transaction(1, sender, recipient, transactionValue, LocalDateTime.now());
        Mockito.when(repository.save(Mockito.any(Transaction.class))).thenReturn(newTransaction);

        Transaction createdTransaction = transactionService.create(request);

        Assertions.assertNotNull(createdTransaction);
        Assertions.assertEquals(BigDecimal.valueOf(1500.00), sender.getBalance());
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Transaction.class));
    }

    @Test
    void createTransactionInsufficientBalanceTest() {
        BigDecimal valueTooHigh = BigDecimal.valueOf(3000.00);

        RequestTransactionCreate requestTooHigh = new RequestTransactionCreate(sender.getNumberAccount(), recipient.getNumberAccount(), valueTooHigh);

        Assertions.assertThrows(
                RuntimeException.class,
                () -> transactionService.create(requestTooHigh),
                "A exceção de saldo insuficiente deveria ter sido lançada"
        );

        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void findAllTransactionsTest() {
        Transaction transaction1 = new Transaction(1, sender, recipient, BigDecimal.valueOf(100.00), null);
        Transaction transaction2 = new Transaction(2, recipient, sender, BigDecimal.valueOf(50.00), null);
        List<Transaction> transactionsList = List.of(transaction1, transaction2);

        Mockito.when(repository.findAll()).thenReturn(transactionsList);

        List<Transaction> foundTransactions = transactionService.findAll();

        Assertions.assertEquals(2, foundTransactions.size());
    }



}