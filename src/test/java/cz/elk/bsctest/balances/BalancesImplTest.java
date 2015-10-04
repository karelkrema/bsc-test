package cz.elk.bsctest.balances;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.Test;

import cz.elk.bsctest.Currency;
import cz.elk.bsctest.CurrencyTransaction;

public class BalancesImplTest {

    @Test
    public void testAcceptValues() {
        final BalancesPerCurrency balance = new BalancesPerCurrencyImpl();
        balance.increaseBalance(new CurrencyTransaction(Currency.CZK, new BigDecimal("10.0")));
        balance.increaseBalance(new CurrencyTransaction(Currency.CZK, new BigDecimal("10.0")));
        balance.increaseBalance(new CurrencyTransaction(Currency.CZK, new BigDecimal("10.0")));
        
        balance.increaseBalance(new CurrencyTransaction(Currency.EUR, new BigDecimal("20.0")));
        balance.increaseBalance(new CurrencyTransaction(Currency.EUR, new BigDecimal("20.0")));
        balance.increaseBalance(new CurrencyTransaction(Currency.EUR, new BigDecimal("20.0")));

        balance.increaseBalance(new CurrencyTransaction(Currency.USD, new BigDecimal("100.501")));
        balance.increaseBalance(new CurrencyTransaction(Currency.USD, new BigDecimal("0.0000000000")));
        balance.increaseBalance(new CurrencyTransaction(Currency.USD, new BigDecimal("-50.501")));
        
        final Map<Currency, BigDecimal> actualBalances = balance.createBalancesSnapshot();
        
        assertEquals(new BigDecimal("30.0"), actualBalances.get(Currency.CZK));
        assertEquals(new BigDecimal("60.0"), actualBalances.get(Currency.EUR));
        assertEquals(new BigDecimal("50.0000000000"), actualBalances.get(Currency.USD));
        assertNull(actualBalances.get(Currency.HUF));
    }

    @Test(expected = NullPointerException.class)
    public void testDenyNullValue() {
        final BalancesPerCurrency balance = new BalancesPerCurrencyImpl();
        balance.increaseBalance(new CurrencyTransaction(Currency.CZK, null));
    }

    @Test(expected = NullPointerException.class)
    public void testDenyNullCurrency() {
        final BalancesPerCurrency balance = new BalancesPerCurrencyImpl();
        balance.increaseBalance(new CurrencyTransaction(null, BigDecimal.ONE));
    }

}
