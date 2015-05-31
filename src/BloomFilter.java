

public class BloomFilter
{
    boolean myArray[];
    private Hasher myHasher;

    public BloomFilter() throws Exception
    {
        this( new Hasher() );
    }

    public BloomFilter( Hasher aHasher )
    {
        myHasher = aHasher;
        myArray = new boolean[myHasher.getBitFieldSize()];
    }

    public void addWord( String aWord ) throws Exception
    {
        BloomEntry theEntry = new BloomEntry( aWord, myHasher );
        theEntry.updateBits( myArray );
    }

    public void addWords( String... aWords ) throws Exception
    {
        for ( String theWord : aWords )
        {
            addWord( theWord );
        }
    }

    public boolean exists( String aWord )
    {
        BloomEntry theEntry = new BloomEntry( aWord, myHasher );
        return theEntry.checkBits( myArray );
    }
}
