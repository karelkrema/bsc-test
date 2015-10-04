package cz.elk.bsctest.io;


/**
 * A writer. It writes the content <br/>
 * 
 * Write operations are synchronized.
 */
public interface Writer {

    void write(final String content);

    void writeln(final String content);
}
