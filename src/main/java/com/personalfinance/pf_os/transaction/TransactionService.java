package com.personalfinance.pf_os.transaction;

import com.personalfinance.pf_os.asset.AssetRepository;
import com.personalfinance.pf_os.currency.CurrencyRepository;
import com.personalfinance.pf_os.holding.Holding;
import com.personalfinance.pf_os.holding.HoldingRepository;
import com.personalfinance.pf_os.portfolio.PortfolioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final HoldingRepository holdingRepository;
    private final PortfolioRepository portfolioRepository;
    private final AssetRepository assetRepository;
    private final CurrencyRepository currencyRepository;


    public TransactionService(TransactionRepository transactionRepository, HoldingRepository holdingRepository, PortfolioRepository portfolioRepository, AssetRepository assetRepository, CurrencyRepository currencyRepository){
        this.transactionRepository = transactionRepository;
        this.holdingRepository = holdingRepository;
        this.portfolioRepository = portfolioRepository;
        this.assetRepository = assetRepository;
        this.currencyRepository = currencyRepository;

    }

    @Transactional
    public Transaction buy(Long portfolioId, Long assetId, Long currencyId,
                           BigDecimal price, BigDecimal quantity,
                           String exchange, LocalDateTime executedAt,
                           BigDecimal fxRateSnapshot) {
        int updated = holdingRepository.applyBuy(portfolioId, assetId, price, quantity);
        if (updated == 0) {
            Holding holding = new Holding();
            holding.setPortfolio(portfolioRepository.findById(portfolioId)
                    .orElseThrow(() -> new RuntimeException("포트폴리오를 찾을수 없습니다.")));
            holding.setAsset(assetRepository.findById(assetId)
                    .orElseThrow(() -> new RuntimeException("자산을 찾을수 없습니다.")));
            holding.setQuantity(quantity);
            holding.setAvgCost(price);
            holding.setRealizedGain(BigDecimal.ZERO);
            try {
                holdingRepository.save(holding);
            } catch (DataIntegrityViolationException e) {
                holdingRepository.applyBuy(portfolioId, assetId, price, quantity);
            }
        }
        Transaction transaction = new Transaction();
        transaction.setPortfolio(portfolioRepository.findById(portfolioId).orElseThrow());
        transaction.setAsset(assetRepository.findById(assetId).orElseThrow());
        transaction.setCurrency(currencyRepository.findById(currencyId).orElseThrow());
        transaction.setType(TransactionType.BUY);
        transaction.setPrice(price);
        transaction.setQuantity(quantity);
        transaction.setExchange(exchange);
        transaction.setExecutedAt(executedAt);
        transaction.setFxRateSnapshot(fxRateSnapshot);

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction sell(Long portfolioId, Long assetId, Long currencyId,
                            BigDecimal price, BigDecimal quantity,
                            String exchange, LocalDateTime executedAt,
                            BigDecimal fxRateSnapshot) {
        int sell_update = holdingRepository.applySell(portfolioId, assetId, price, quantity);
        if (sell_update == 0) {
            throw new RuntimeException("보유수량이 부족합니다");
        }
        Transaction transaction = new Transaction();
        transaction.setPortfolio(portfolioRepository.findById(portfolioId).orElseThrow());
        transaction.setAsset(assetRepository.findById(assetId).orElseThrow());
        transaction.setCurrency(currencyRepository.findById(currencyId).orElseThrow());
        transaction.setType(TransactionType.SELL);
        transaction.setPrice(price);
        transaction.setQuantity(quantity);
        transaction.setExchange(exchange);
        transaction.setExecutedAt(executedAt);
        transaction.setFxRateSnapshot(fxRateSnapshot);

        return transactionRepository.save(transaction);
    }

    }
