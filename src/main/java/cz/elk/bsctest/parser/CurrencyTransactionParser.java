package cz.elk.bsctest.parser;

import java.util.Optional;

import cz.elk.bsctest.CurrencyTransaction;

/**
 * A class responsible for parsing CurrencyTransaction
 * from a textual input.
 *
 */
public interface CurrencyTransactionParser {
    
    /**
     * Parses currency transaction from string (command). 
     */
    Optional<CurrencyTransaction> parse(final String line);
}
