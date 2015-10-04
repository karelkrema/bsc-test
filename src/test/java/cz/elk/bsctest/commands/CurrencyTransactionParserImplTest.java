package cz.elk.bsctest.commands;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

import cz.elk.bsctest.model.Currency;
import cz.elk.bsctest.model.CurrencyTransaction;
import cz.elk.bsctest.parsing.CurrencyTransactionParser;
import cz.elk.bsctest.parsing.CurrencyTransactionParserImpl;

public class CurrencyTransactionParserImplTest {
    
    CurrencyTransactionParser parser = new CurrencyTransactionParserImpl();

    private final BigDecimal VALUE = new BigDecimal("30.34");
    private final String[] VALID_TX_DEFINITIONS = new String[] { "EUR 30.34", 
                                                                 " EUR\t30.34   ", 
                                                                 "\tEUR\t\t\t\t30.34  \t", 
                                                                 " EUR 30.34 ", 
                                                                 " \t \t EUR \t \t 30.34", 
                                                                 "EUR 30.34 \t \t" };

    @Test
    public void testParseValues() {
        
        Stream.of(VALID_TX_DEFINITIONS)
              .map(line -> {
                  final Optional<CurrencyTransaction> tx = parser.parse(line);
                  Assert.assertTrue("Not a valid input: " + line, tx.isPresent());
                  return tx;
              })
              .map(tx -> tx.get())
              .peek(tx -> Assert.assertEquals(VALUE, tx.getAmount()))
              .forEach(tx -> Assert.assertEquals(Currency.EUR, tx.getCurrency()));
    }

    @Test
    public void testParseUnknownCurrency() {
        final Optional<CurrencyTransaction> tx = parser.parse("EURR 10");
        Assert.assertFalse(tx.isPresent());
    }

    @Test
    public void testTooManyParams() {
        final Optional<CurrencyTransaction> tx = parser.parse("EUR 10 EUR");
        Assert.assertFalse(tx.isPresent());
    }
    
    @Test
    public void testParseInvalidAmount() {
        final Optional<CurrencyTransaction> tx = parser.parse("EUR 10x");
        Assert.assertFalse(tx.isPresent());
    }

    @Test
    public void testParseInvalidDecimalPoint() {
        final Optional<CurrencyTransaction> tx = parser.parse("EUR 10,43");
        Assert.assertFalse(tx.isPresent());
    }
    
}
