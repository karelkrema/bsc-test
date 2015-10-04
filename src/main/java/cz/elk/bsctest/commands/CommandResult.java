package cz.elk.bsctest.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Result of a command.<br/>
 * 
 * Indicates whether the program should terminate or continue with another
 * command and contains result message, which should be displayed to the user.
 */
@Getter
@AllArgsConstructor
public class CommandResult {

    public static final CommandResult SILENT_CONTINUE = new CommandResult("", false);
    public static final CommandResult SILENT_TERMINATE = new CommandResult("", true);

    final String outputMessage;
    final boolean terminateProgram;

}
