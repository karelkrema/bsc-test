package cz.elk.bsctest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cz.elk.bsctest.io.CurrencyTransactionReader;

public class Start {

	public static void main(final String[] args) throws FileNotFoundException {
		final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("cz.elk.bsctest");

		if(args.length > 0) {
		    processExternalFile(args, ctx);
		}

		processManualInput(ctx);
		
		System.exit(0); // TODO terminate running scheduler more elegantly
	}

    private static void processManualInput(final AnnotationConfigApplicationContext ctx) {
        final CurrencyTransactionReader txReader = ctx.getBean(CurrencyTransactionReader.class);
		txReader.readAndProcessCommands(System.in);
    }

    private static void processExternalFile(final String[] args,
                                            final AnnotationConfigApplicationContext ctx) throws FileNotFoundException {
        final File file = getFileFromFilenameIfExists(args[0]);
        if(file != null) {
            final CurrencyTransactionReader txReader = ctx.getBean(CurrencyTransactionReader.class);
            txReader.readAndProcessCommands(new FileInputStream(file));
        }
    }

    private static File getFileFromFilenameIfExists(final String fileName) {
        final File file = new File(fileName);
        if(file.exists()) {
            return file;
        }
        
        return null;
    }


}
