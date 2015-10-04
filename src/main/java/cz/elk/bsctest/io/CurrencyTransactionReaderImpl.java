package cz.elk.bsctest.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.elk.bsctest.commands.Command;
import cz.elk.bsctest.commands.CommandMatcher;
import cz.elk.bsctest.commands.CommandResult;

@Component
public class CurrencyTransactionReaderImpl implements CurrencyTransactionReader {

    @Autowired
    CommandMatcher commandParser;
    
    @Autowired 
    Writer writer;
    
    @Override
    public void readAndProcessCommands(final InputStream inputStream) {
        try {
            readAndProcessCommandsInternal(inputStream);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void readAndProcessCommandsInternal(final InputStream inputStream) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            final Command command = commandParser.parse(line);
            final CommandResult commandResult = command.execute(line);
            
            printCommandOutputMessage(commandResult);
            if(commandResult.isTerminateProgram()) {
                break;
            }

        }
    }

    private void printCommandOutputMessage(final CommandResult commandResult) {
        if(commandResult.getOutputMessage() != null) {
            writer.writeln(commandResult.getOutputMessage());
        }
    }
}
