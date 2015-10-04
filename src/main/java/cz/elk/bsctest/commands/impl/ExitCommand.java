package cz.elk.bsctest.commands.impl;

import org.springframework.stereotype.Component;

import cz.elk.bsctest.commands.Command;
import cz.elk.bsctest.commands.CommandResult;

@Component("exitCommand")
public class ExitCommand implements Command {
    
    public static final String COMMAND_STRING = "exit";

    @Override
    public boolean matches(final String input) {
        final boolean retVal = COMMAND_STRING.equals(input);
        return retVal;
    }

    @Override
    public CommandResult execute(final String input) {
        return CommandResult.SILENT_TERMINATE;
    }

}
