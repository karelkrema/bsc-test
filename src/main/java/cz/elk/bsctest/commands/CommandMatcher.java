package cz.elk.bsctest.commands;


/**
 * A simple class used to lookup a {@link Command} instance matching a given
 * String.<br/>
 * 
 * If no registered command matches, fallback command is returned.
 */
public interface CommandMatcher {

    public abstract Command parse(final String line);

}