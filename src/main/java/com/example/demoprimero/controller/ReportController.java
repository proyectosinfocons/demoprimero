package com.example.demoprimero.controller;

import com.example.demoprimero.model.Transaction;
import com.example.demoprimero.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> generateTransactionReport(
            @RequestParam(required = false) Long accountId,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            Authentication authentication) {

        return ResponseEntity.ok(reportService.generateReport(accountId,
            customerId,
            startDate,
            endDate,
            authentication));
    }
}

