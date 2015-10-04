package cz.elk.bsctest.balances;

import java.math.BigDecimal;
import java.util.Map;

import cz.elk.bsctest.model.Currency;
import cz.elk.bsctest.model.CurrencyTransaction;

/**
 * Keeps track of balances per currency.<br/>
 * 
 * Must be thread safe, no new value can be added while current balances snapshot
 * is being created or other value is being accepted.
 * 
 * @author elk
 *
 */
public interface BalancesPerCurrency {

    /**
     * Based on currencyTransactions values, it increases 
     * appropriate balance.
     */
    void increaseBalance(final CurrencyTransaction currencyTransaction);

    /**
     * Returns a snapshot (an immutable map) of current balances.
     */
    Map<Currency, BigDecimal> createBalancesSnapshot();
}
