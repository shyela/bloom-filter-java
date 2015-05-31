import java.util.HashMap;


public class HasherForTest extends Hasher
{
    private HashMap<String, int[]> myTestData;

    public HasherForTest() throws Exception
    {
        super();
        myTestData = new HashMap<String, int[]>();
    }

    public void addTestHash( String aWord, int... anIndexes )
    {
        myTestData.put( aWord, anIndexes );
    }

    @Override
    public int getNumberOfHashFunctions()
    {
        return 3;
    }

    @Override
    public int getBitFieldSize()
    {
        return 200;
    }

    @Override
    public void setIndexesFromWordHash( String aWord, int[] anIndexes )
    {
        if ( myTestData.containsKey( aWord ) )
        {
            int[] theTestIndexes = myTestData.get( aWord );
            System.arraycopy( theTestIndexes, 0, anIndexes, 0, getNumberOfHashFunctions() );
        }
        else
        {
            super.setIndexesFromWordHash( aWord, anIndexes );
        }
    }

}
