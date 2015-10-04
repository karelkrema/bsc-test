package cz.elk.bsctest.io;

import java.io.InputStream;

/**
 * CurrencyTransactionReader is responsible from reading, parsing
 * and executing textual commands found in a given inputStream.
 */
public interface CurrencyTransactionReader {

    /**
     * Reads, one after another, lines of the input stream, maps
     * those lines to commands and executes those commands.<br/>
     */
    public void readAndProcessCommands(final InputStream inputStream);
    
}
