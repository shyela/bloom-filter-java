import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class AcceptanceTestBloomFilter
{
    public static void main( String[] args ) throws Exception
    {
        BloomFilter theBloomFilter = new BloomFilter();
        loadDictionaryIntoBloomFilter( theBloomFilter );

        RandomString theRandomStringer = new RandomString( 5 );

        ArrayList<String> theWrongWords = new ArrayList<String>();

        int theCountOfRandomWords = 0;
        while ( theWrongWords.size() < 10000 )
        {
            theCountOfRandomWords++;
            String theRandomString = theRandomStringer.nextString();

            if ( theBloomFilter.exists( theRandomString ) )
            {
                theWrongWords.add( theRandomString );
            }
        }

        checkForWordsInDictionary( theWrongWords );
        System.out.println( "# random words checked: " + theCountOfRandomWords );
    }

    private static void checkForWordsInDictionary( ArrayList<String> aWordsToFind ) throws Exception
    {
        BufferedReader theReader = new BufferedReader( new FileReader( "/usr/share/dict/words" ) );
        String theLine;
        while ( ( theLine = theReader.readLine() ) != null )
        {
            if ( aWordsToFind.contains( theLine ) )
            {
                aWordsToFind.remove( theLine );
            }
        }
        theReader.close();

        System.out.println( "These words were false positives: " + aWordsToFind );
        System.out.println( "# false positives: " + aWordsToFind.size() );
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
