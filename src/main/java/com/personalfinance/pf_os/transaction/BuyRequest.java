package com.personalfinance.pf_os.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BuyRequest(Long portfolioId, Long assetId, Long currencyId,
                         BigDecimal price, BigDecimal quantity,
                         String exchange, LocalDateTime executedAt,
                         BigDecimal fxRateSnapshot)
{
}
