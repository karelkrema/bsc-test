package cz.elk.bsctest.balances;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

import cz.elk.bsctest.dump.BalancesDumpAssembler;
import cz.elk.bsctest.dump.BalancesDumpAssemblerImpl;
import cz.elk.bsctest.model.Currency;

public class BalanceDumpAssemblerTest {

    private static final String EXPECTED_DUMP = 
          "========= Balances dump =========\n"
        + "  CZK: 1234.56\n"
        + "  USD: 492.9035\n"
        + "========= Dump end =========\n";
    
    // TODO Spring test?
    private final BalancesDumpAssembler bda = new BalancesDumpAssemblerImpl();

    @Test
    public void testAssembleDumpNoZeroValues() {
        final Map<Currency, BigDecimal> balances = new TreeMap<Currency, BigDecimal>();
        balances.put(Currency.CZK, new BigDecimal("1234.56"));
        balances.put(Currency.USD, new BigDecimal("492.9035"));

        final String actualDump = bda.assembleDump(balances);


        Assert.assertEquals(EXPECTED_DUMP, actualDump);
    }

    /**
     * 
     */
    @Test
    public void testAssembleDumpWithZeroValues() {
        final Map<Currency, BigDecimal> balances = new TreeMap<Currency, BigDecimal>();
        balances.put(Currency.CZK, new BigDecimal("1234.56"));
        balances.put(Currency.GBP, new BigDecimal("0.0"));
        balances.put(Currency.USD, new BigDecimal("492.9035"));
        balances.put(Currency.HUF, new BigDecimal("0"));

        final String actualDump = bda.assembleDump(balances);

        Assert.assertEquals(EXPECTED_DUMP, actualDump);
    }

}
