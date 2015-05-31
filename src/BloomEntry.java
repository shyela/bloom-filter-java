

public class BloomEntry
{

    private String myWord;
    private int[] myIndexes;
    private Hasher myHasher;

    public BloomEntry( String aWord, Hasher aHasher )
    {
        myWord = aWord;
        myHasher = aHasher;
        myIndexes = new int[myHasher.getNumberOfHashFunctions()];
    }

    public void updateBits( boolean[] aBitArray )
    {
        generateIndexes();
        for ( int i = 0; i < myHasher.getNumberOfHashFunctions(); i++ )
        {
            try
            {
                aBitArray[myIndexes[i]] = true;
            }
            catch( Exception e )
            {
                System.err.println( "Error for hash " + i + " set index " + myIndexes[i] + " to true");
                e.printStackTrace();
            }
        }
    }

    public boolean checkBits( boolean[] aBitArray )
    {
        generateIndexes();
        for ( int i = 0; i < myHasher.getNumberOfHashFunctions(); i++ )
        {
            //System.err.println( "for hash " + i + " index " + myIndexes[i] + " = " + aBitArray[myIndexes[i]] );
            if ( ! aBitArray[myIndexes[i]] )
            {
                return false;
            }
        }
        return true;
    }

    private void generateIndexes()
    {
        try
        {
            myHasher.setIndexesFromWordHash( myWord, myIndexes );
        }
        catch( Exception e )
        {
            // This is here to help during implementation.
            // This should not be encountered now.
            e.printStackTrace();
        }
    }
}
