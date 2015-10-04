# bsc-test

## Stažení a spuštění

V cílovém adresáři spustit příkaz

<code>git clone https://github.com/karelkrema/bsc-test.git</code>

<code>cd bsc-test</code>

Pro snadný build a spouštění v unixovém prostředí je možné spustit 

<code>chmod u+x *.sh</code>

Pak jsou k dispozici příkazy <code>build.sh</code>, <code>run.sh</code> a <code>buildandrun.sh</code>.

Build volá <code>mvn clean compile assembly:single</code>, což v /target vytvoří jar se všemi dependencemi a spouštěcí třídou v manifestu.

Run pak samozřejmě tento soubor spouští a předává mu jeden parametr z příkazové řádky (soubor s transakcemi, jeden je předpřipravený).

Buildandrun je pak kombinací obojího, takže spuštění <code>buildandrun.sh transactions.txt</code> hned po vyklonování by mělo fungovat - tj. zkompilovat program a spustit ho s předpřipraveným souborem.

## Funkce programu
Program je tolerantní, neznámý, nebo prázdný příkaz vypíše nápovědu.
Přijímá jeden argument - název souboru se sadou příkazů. Pokud soubor neexistuje, je argument ignorován.

## Kód

Celá aplikace se skládá z několika komponent (ty jsou členěny po balíčcích) - a ve výsledku je z toho poměrně dost přeinženýrované řešení.

Komponenty tedy jsou následující:

1. *BalancesPerCurrency* v balíčku "balances": Komponenta, která drží aktuální výši částky pro každou měnu. Umožňuje zvyšovat částky u jednotlivých měn, případně vytvořit snapshot aktuálního stavu, přičemž tyto dvě akce by neměly interferovat (řešeno jednoduše synchronizací nad instancí).
2. *Command* v balíčku "commands" a "commands.impl". Má několik implementací a podstatou je propojení textového příkazu s určitou akcí. Obecně vzato bych tyto dvě věci držel spíš oddělené, ale takhle je to zase relativně jednoduché. Každá implementace commandu v metodě "matches" ověřuje, zda zadaný textový řetězec má spouštět daný command. Metoda execute pak obsahuje samotný kód, který se má vykonat.
Celé to funguje tak, že při načtení řetězce (příkazu) se postupně prochází zaregistrované commandy, a pokud některý "matchuje", je vykonán.
O toto se stará komponenta *CommandMatcher*.
3. *BalancesDumpAssembler* v balíčku "dump" je zodpovědný za poskládání řetězce s výpisem aktuálních zůstatků.
4. *CurrencyTransactionReader* v balíčku "io" přijímá input stream, prochází ho řádek po řádku a každý řádek zpracovává. Tentýž balíček obsahuje ještě *Writer*, což je jenom převlečené System.out.println(...).
5. *CurrencyTransactionParser* z balíčku "parsing" je zodpovědný za parsování transakce z řetězce, tj. např "EUR 30".
6. *ScheduledDump* z balíčku "scheduling" periodicky vypisuje dump.
7. Balíček "model" pak obsahuje enum *Currency* a objekt *CurrencyTransaction*

Říkali jste, že jeden z kandidátů to měl napsané za dvě hodiny v jedné třídě na 300 řádek. Já zdaleka tak rychlý nebyl. Řádek bude cca stejně - ale těch tříd mám trochu víc :-)

## Testy
Kdybych měl program rozvíjet dál, určitě bych prozkoumal a využil spring test knihovnu a napsal nějaké end to end integrační testy.

Takhle jsem testoval jednotlivé části, coverage je 61%.

## Použité knihovny
Typické: Spring, JUnit, Guava.

Méně typické (aspoň pro mě, zkoušel jsem ji prvně): Project lombok pro eliminaci boilerplate kódu (https://projectlombok.org/features/index.html)

Viz. třída CurrencyTransaction, což je v podstaě immutabilní DTO objekt s gettery, setery a toString(). S lombokem vypadá takto:
```java
@Getter
@AllArgsConstructor
@ToString
public class CurrencyTransaction {
    private final @NonNull Currency currency;
    private final @NonNull BigDecimal amount;
}
```

## TODOs
Je zajímavé, že i na takovémto jednoduchém zadání se toho dá hodně ladit.
Několik TODOs mi zbylo, například při neexistujícím vstupním souboru by se měla místo ignorace vypsat hláška.

Také chybí např. logování a bonusová část, která by ale v praxi byla poměrně jednoduchá - jedna komponenta s kurzy měn k dolaru a jeden command.


# bsc-test - English

## Download and run

In a desired directory, run followint command:

<code>git clone https://github.com/karelkrema/bsc-test.git</code>

<code>cd bsc-test</code>

For an easy build&run on unix platform, you can run:

<code>chmod u+x *.sh</code>

This makes <code>build.sh</code>, <code>run.sh</code> a <code>buildandrun.sh</code> commands runnable.

Build calls <code>mvn clean compile assembly:single</code>, which creates a jar file with all necessarry dependencies and a main class in manifest in /target directory.

Run obviously runs this jar file and passes a single command line parameter to it (there is an example file ready to use).

Buildandrun is a combination of both so running <code>buildandrun.sh transactions.txt</code> right after the cloning should work, ie. compile the program and run it with the sample file.

## Application features
The application is tolerant so unknown (or empty) command prints help on the screen.

As mentioned above, it accepts one command line argument - a name of a file with predefined commands set. If the file does not exist, it is simply ignored.

## The code

A whole apllication is composed of several components (placed in several packages). As a result, the solution is a little bit overengineered.

So the components are following:

1. *BalancesPerCurrency* in "balances" package: A component holding actual balance for each currency. It enables to increase a balance of particular currency, or create a snapshot of the current state. These two actions should not interfere, which is achieved by a simple synchronization on the component instance.
2. *Command* in "commands" and "commands.impl" packages. There are several implementations and the purpose is a bond betwen textual command and a particular action. In general, it would be better to keep these two thing separated, but for sake of simplicity, i decided to couple them. Each implementation is able to check whether a given string matches the command and, eventually, execute the action of the command.
The way it works is that when a command is read, a component called "CommandMatcher" iterates over registerd commands and the one who matches is executed.
If no matching command is found, "FallbackCommand" is executed (it prints out a help message).
3. *BalancesDumpAssembler* in "dump" package is responsible for an assembly of the string with actual balances dump.
4. *CurrencyTransactionReader* in "io" io package accepts an input streams, walks through it line by line and processes each line. The same package also contains *Writer* component, which is just a wrapped System.out.println(...).
5. *CurrencyTransactionParser* in "parsing" package is responsible for parsing a transaction out of a strings, ie. "EUR 30".
6. *ScheduledDump* in "scheduling" package dumps the balances periodically.
7. Package "model" then contains *Currency* enum and *CurrencyTransaction* objects.

You stated that one of the candidates had this exercise done in two hours, one class and 300 lines. I was definitely not that quick. The amount of lines is probably something similar, but the classel... I definitely have a little bit more of them :-)

## Test
If further development was necessary, I would definitely explore and use the spring test library and write down some end to end integration tests.

So far, I have tested particular units, the coverage is 61%.

## Libraries used
Typical ones: Spring, JUnit, Guava.

Less typical ones (at least for me, I have tried it for the first time): Project lombok for boilerplate code elimination (https://projectlombok.org/features/index.html).

See. CurrencyTransaction class, which is in fact just an immutable dto with getters and toString(). With lombok, it looks like this:
```java
@Getter
@AllArgsConstructor
@ToString
public class CurrencyTransaction {
    private final @NonNull Currency currency;
    private final @NonNull BigDecimal amount;
}
```

## TODOs
It's interesting that even such a simple exercise could be tuned forever.
I still have (quite) a few TODOs to complete, e.g. nonexistent input file should at least print some error message out.

Logging is missing as well, plus the bonus part (balances in USD - this would in fact be pretty simple to implement - one component to maintain the rates and one command).













