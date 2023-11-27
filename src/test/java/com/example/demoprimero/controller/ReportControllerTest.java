package com.example.demoprimero.controller;

import com.example.demoprimero.model.Transaction;
import com.example.demoprimero.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportControllerTest {

    @Mock
    private ReportService reportService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ReportController reportController;

    private Date startDate;
    private Date endDate;
    private Long accountId = 1L;
    private Long customerId = 1L;

    @BeforeEach
    void setUp() {
        startDate = new Date();
        endDate = new Date();
    }

    @Test
    void generateTransactionReportTest() {
        List<Transaction> transactions = Arrays.asList(new Transaction());
        when(reportService.generateReport(accountId, customerId, startDate, endDate, authentication)).thenReturn(transactions);

        ResponseEntity<List<Transaction>> response = reportController.generateTransactionReport(accountId, customerId, startDate, endDate, authentication);

        assertAll(
                () -> assertNotNull(response),
                () -> assertFalse(response.getBody().isEmpty()),
                () -> assertEquals(response.getStatusCodeValue(), 200)
        );
    }

}
