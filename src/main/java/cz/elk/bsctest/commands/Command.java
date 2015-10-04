package cz.elk.bsctest.commands;

/**
 * When a command string is entered, all registered 
 * instances of this interface are, one after another,
 * checked for match with this string. <br/>
 * 
 *  If a match is found, an action is executed.<br/>
 *  
 *  In fact, all the business logic is invoked via commands.
 */
public interface Command {
    
    /**
     * Returns true, when the command instance matches
     * the input.
     */
    boolean matches(final String input);

    /**
     * Execute a logic of the command.
     */
    CommandResult execute(final String input);
}
