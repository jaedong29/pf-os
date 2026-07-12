package com.personalfinance.pf_os.transaction;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/buy")
    public ResponseEntity<Void> buy(@RequestBody BuyRequest request) {
        transactionService.buy(
                request.portfolioId(),
                request.assetId(),
                request.currencyId(),
                request.price(),
                request.quantity(),
                request.exchange(),
                request.executedAt(),
                request.fxRateSnapshot()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/sell")
    public ResponseEntity<Void> sell(@RequestBody SellRequest request) {
        transactionService.sell(
                request.portfolioId(),
                request.assetId(),
                request.currencyId(),
                request.price(),
                request.quantity(),
                request.exchange(),
                request.executedAt(),
                request.fxRateSnapshot()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
