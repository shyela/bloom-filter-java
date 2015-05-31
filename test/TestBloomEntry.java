

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;


public class TestBloomEntry
{
    private static final String TEST_WORD = "foo";

    private HasherForTest myHasherForTest;

    @Before
    public void setup() throws Exception
    {
        myHasherForTest = new HasherForTest();
    }

    @Test
    public void shouldUpdateBits() throws Exception
    {
        myHasherForTest.addTestHash( TEST_WORD, 1, 2, 3 );

        BloomEntry theBloomEntry = new BloomEntry( TEST_WORD, myHasherForTest );
        boolean[] theBitArray = new boolean[myHasherForTest.getBitFieldSize()];
        theBloomEntry.updateBits( theBitArray );

        assertIndexesAreTrue( theBitArray, 1, 2, 3 );
    }

    @Test
    public void shouldCheckBitsTrue()
    {
        myHasherForTest.addTestHash( TEST_WORD, 2, 5, 8 );

        BloomEntry theBloomEntry = new BloomEntry( TEST_WORD, myHasherForTest );
        boolean[] theBitArray = createBitArrayWithIndexesSetToTrue( 2, 5, 8 );

        assertTrue( theBloomEntry.checkBits( theBitArray ) );
    }

    @Test
    public void shouldCheckBitsFalse()
    {
        myHasherForTest.addTestHash( TEST_WORD, 2, 5, 8 );

        BloomEntry theBloomEntry = new BloomEntry( TEST_WORD, myHasherForTest );
        boolean[] theBitArray = createBitArrayWithIndexesSetToTrue( 3, 7, 8 );

        assertFalse( theBloomEntry.checkBits( theBitArray ) );
    }

    private boolean[] createBitArrayWithIndexesSetToTrue( int... aTrueIndexes )
    {
        boolean[] theBitArray = new boolean[myHasherForTest.getBitFieldSize()];
        for ( int i = 0; i < aTrueIndexes.length; i++ )
        {
            theBitArray[ aTrueIndexes[i] ] = true;
        }
        return theBitArray;
    }

    private void assertIndexesAreTrue( boolean[] aBitArray, int... aTrueIndexes )
    {
        Arrays.sort( aTrueIndexes );
        for ( int i = 0; i < aBitArray.length; i++ )
        {
            boolean theExpectedBitValue = shouldThisIndexBeTrue( i, aTrueIndexes );
            assertEquals( "the bit at index " + i + " is not correct", theExpectedBitValue, aBitArray[i] );
        }
    }

    private boolean shouldThisIndexBeTrue( int aBitIndex, int... aTrueIndexes )
    {
        return Arrays.binarySearch( aTrueIndexes, aBitIndex ) >= 0;
    }
}
