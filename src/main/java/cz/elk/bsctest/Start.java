package cz.elk.bsctest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import cz.elk.bsctest.io.CurrencyTransactionReader;

/** Yes, I made it into BSC */
public class Start {

	public static void main(final String[] args) throws FileNotFoundException {
		final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("cz.elk.bsctest");
		final CurrencyTransactionReader txReader = ctx.getBean(CurrencyTransactionReader.class);

        // Small change in devel
		System.out.println("Git rulezz (added in b002");
		// Process input file, if parameter was given
		if(args.length > 0) {
		    final File file = new File(args[0]);
            if((file != null) && file.exists() && file.isFile()) {
                txReader.readAndProcessCommands(new FileInputStream(file));
            }
		}

        txReader.readAndProcessCommands(System.in);
		
		System.exit(0); // TODO terminate running scheduler more elegantly
	}

}
