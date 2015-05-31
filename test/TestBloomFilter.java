import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;


public class TestBloomFilter
{
    private HasherForTest myHasherForTest;
    private BloomFilter myBloomFilter;

    @Before
    public void setup() throws Exception
    {
        myHasherForTest = new HasherForTest();
        myBloomFilter = new BloomFilter( myHasherForTest );
    }

    @Test
    public void shouldAddWord() throws Exception
    {
        myHasherForTest.addTestHash( "foo", 4, 5, 6 );
        myBloomFilter.addWord( "foo" );
        assertFilterIndexesSetToTrue( 4, 5, 6 );
    }

    @Test
    public void shouldAddWords() throws Exception
    {
        myHasherForTest.addTestHash( "bar", 3, 8, 11 );
        myHasherForTest.addTestHash( "zot", 4, 8, 12 );
        myBloomFilter.addWords( "bar", "zot" );
        assertFilterIndexesSetToTrue( 3, 4, 8, 11, 12 );
    }

    @Test
    public void shouldCheckIfWordWasAdded() throws Exception
    {
        myHasherForTest.addTestHash( "foo", 2, 5, 22 );
        myBloomFilter.addWord( "foo" );
        assertTrue( "word should be in filter", myBloomFilter.exists( "foo" ) );
    }

    @Test
    public void shouldCheckIfWordaWereAdded() throws Exception
    {
        myHasherForTest.addTestHash( "bar", 3, 8, 11 );
        myHasherForTest.addTestHash( "zot", 4, 8, 12 );
        myBloomFilter.addWords( "bar", "zot" );
        assertTrue( "bar should be in filter", myBloomFilter.exists( "bar" ) );
        assertTrue( "zot should be in filter", myBloomFilter.exists( "zot" ) );
    }

    private void assertFilterIndexesSetToTrue( int... aTrueIndexes )
    {
        Arrays.sort( aTrueIndexes );
        for ( int i = 0; i < myBloomFilter.myArray.length; i++ )
        {
            boolean theExpectedBitValue = shouldThisIndexBeTrue( i, aTrueIndexes );
            assertEquals( "the bit at index " + i + " is not correct",
                          theExpectedBitValue,
                          myBloomFilter.myArray[i] );
        }
    }

    private boolean shouldThisIndexBeTrue( int aBitIndex, int... aTrueIndexes )
    {
        return Arrays.binarySearch( aTrueIndexes, aBitIndex ) >= 0;
    }
}
