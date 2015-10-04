package cz.elk.bsctest.io;

import org.springframework.stereotype.Component;

@Component
public class WriterImpl implements Writer {
    
    @Override
    synchronized public void write(final String content) {
        System.out.print(content);
    }

    @Override
    synchronized public void writeln(final String content) {
        write(content + "\n");
    }

}
