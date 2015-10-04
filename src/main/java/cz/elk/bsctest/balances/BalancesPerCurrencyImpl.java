package cz.elk.bsctest.balances;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import lombok.NonNull;

import org.springframework.stereotype.Component;

import cz.elk.bsctest.Currency;
import cz.elk.bsctest.CurrencyTransaction;

@Component
public class BalancesPerCurrencyImpl implements BalancesPerCurrency {

    private final Map<Currency, BigDecimal> balances = new TreeMap<>();

    @Override
    synchronized public void increaseBalance(@NonNull final CurrencyTransaction tx) {
        final Currency txCurrency = tx.getCurrency();
        final BigDecimal txAmount = tx.getAmount();

        final Optional<BigDecimal> currentBalance = Optional.ofNullable(balances.get(txCurrency));
        final BigDecimal increasedBalance = currentBalance.orElse(BigDecimal.ZERO).add(txAmount);

        balances.put(txCurrency, increasedBalance);
    }

    @Override
    synchronized public Map<Currency, BigDecimal> createBalancesSnapshot() {
        final Map<Currency, BigDecimal> retVal = Collections.unmodifiableMap(new TreeMap<>(balances));
        return retVal;
    }


}
