package cz.elk.bsctest;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class CurrencyTransactionTest {

    @Test
    public void testCreation() {
        final CurrencyTransaction tx = new CurrencyTransaction(Currency.EUR, BigDecimal.TEN);
        assertEquals(Currency.EUR, tx.getCurrency());
        assertEquals(BigDecimal.TEN, tx.getAmount());
    }
    
    @Test(expected = NullPointerException.class)
    public void testNullCurrency() {
        new CurrencyTransaction(null, BigDecimal.ONE);
    }

    @Test(expected = NullPointerException.class)
    public void testNullAmount() {
        new CurrencyTransaction(Currency.EUR, null);
    }
}
