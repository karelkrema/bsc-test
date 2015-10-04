package cz.elk.bsctest.commands.impl;

import org.springframework.stereotype.Component;

import cz.elk.bsctest.commands.Command;
import cz.elk.bsctest.commands.CommandResult;


/**
 * When no other command is recognized, fallback command 
 * will be used.
 */
@Component("fallbackCommand")
public class FallbackCommand implements Command {

    @Override
    public boolean matches(final String input) {
        return true;
    }

    @Override
    public CommandResult execute(final String input) {
        final CommandResult retVal = new CommandResult(getListOfAllowedCommands(input), false);
        return retVal;
    }

    private String getListOfAllowedCommands(final String input) {
        return "Unknown command: '" + input + "'\n"
               + "List of commands: \n"
               + "  'dump' dumps the balances immediately\n"
               + "  'exit' exits the program\n"
               + "  '{CUR} {AMOUNT}' increases balance of currency {CUR} by amount {AMOUNT}";
    }

}
