package cz.elk.bsctest.commands.impl;

import java.math.BigDecimal;
import java.util.Map;

import lombok.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.elk.bsctest.Currency;
import cz.elk.bsctest.balances.BalancesPerCurrency;
import cz.elk.bsctest.commands.Command;
import cz.elk.bsctest.commands.CommandResult;
import cz.elk.bsctest.dump.BalancesDumpAssembler;
import cz.elk.bsctest.io.Writer;

/**
 * Dumps current balances per currency.
 */
@Component("dumpCommand")
public class DumpCommand implements Command {

    public static final String COMMAND_STRING = "dump";

    @Autowired
    Writer writer;

    @Autowired
    BalancesPerCurrency balancesPerCurrency;

    @Autowired
    BalancesDumpAssembler balancesDumpAssembler;

    @Override
    public boolean matches(final String input) {
        final boolean retVal = COMMAND_STRING.equals(input);
        return retVal;
    }

    @Override
    public CommandResult execute(@NonNull final String input) {
        final Map<Currency, BigDecimal> balancesSnapshot = balancesPerCurrency.createBalancesSnapshot();
        final String dump = balancesDumpAssembler.assembleDump(balancesSnapshot);
        writer.write(dump);
        
        return CommandResult.SILENT_CONTINUE;
    }

}
