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







