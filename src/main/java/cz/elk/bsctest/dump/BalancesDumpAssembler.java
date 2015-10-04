package cz.elk.bsctest.dump;

import java.math.BigDecimal;
import java.util.Map;

import cz.elk.bsctest.model.Currency;

/**
 * A simple class responsible for assembling a balance dump.
 */
public interface BalancesDumpAssembler {

    /**
     * Assembles balances dump based on given balances map. Only non zero
     * balances are dumped.
     */
    String assembleDump(final Map<Currency, BigDecimal> balances);

}