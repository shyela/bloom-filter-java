import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;


public class SpellCheck
{

    public static void main( String[] args ) throws Exception
    {
        BloomFilter theBloomFilter = new BloomFilter();
        loadDictionaryIntoBloomFilter( theBloomFilter );

        Scanner theInputScanner = new Scanner( System.in );

        while ( true )
        {
            System.out.println( "Enter a word: " );
            String theWordToCheck = theInputScanner.nextLine();

            if ( theBloomFilter.exists( theWordToCheck ) )
            {
                System.out.println( theWordToCheck + "... Correct!" );
            }
            else
            {
                System.out.println( theWordToCheck + "... Wrong!" );
            }
        }
    }

    private static void loadDictionaryIntoBloomFilter( BloomFilter aBloomFilter ) throws Exception
    {
        BufferedReader theReader = new BufferedReader( new FileReader( "/usr/share/dict/words" ) );
        String theLine;
        while ( ( theLine = theReader.readLine() ) != null )
        {
            aBloomFilter.addWord( theLine );
        }
        theReader.close();
    }

}
