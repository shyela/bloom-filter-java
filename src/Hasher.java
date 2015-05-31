import java.nio.ByteBuffer;
import java.security.MessageDigest;


public class Hasher
{
    private static final int NUM_BYTES_IN_SECTION = 3;
    private static final int BIT_FIELD_SIZE = (int)Math.pow( 2, NUM_BYTES_IN_SECTION * 8 );
    private static final int NUM_HASH_PARTS = 10;

    private final MessageDigest myMessageDigest;

    public Hasher() throws Exception
    {
        myMessageDigest = MessageDigest.getInstance( "SHA-256" );
    }

    public int getNumberOfHashFunctions()
    {
        return NUM_HASH_PARTS;
    }

    public int getBitFieldSize()
    {
        return BIT_FIELD_SIZE;
    }

    public void setIndexesFromWordHash( String aWord, int[] anIndexes )
    {
        byte[] theHash = generateHash( aWord );

        for ( int i = 0; i < getNumberOfHashFunctions(); i++ )
        {
            anIndexes[i] = convertHashSectionToIndex( i, theHash );
        }
    }

    int convertHashSectionToIndex( int aSectionIndex, byte[] aHash )
    {
        int theSectionStartIndex = aSectionIndex*NUM_BYTES_IN_SECTION;
        if ( theSectionStartIndex + NUM_BYTES_IN_SECTION > aHash.length )
        {
            return 0;
        }

        ByteBuffer theByteBuffer = ByteBuffer.wrap( aHash, theSectionStartIndex, NUM_BYTES_IN_SECTION );

        if ( NUM_BYTES_IN_SECTION == 3 )
        {
            return (int)( ( (theByteBuffer.get() & 0xFF) << 16 )
                    | ( (theByteBuffer.get() & 0xFF) << 8 )
                    | (theByteBuffer.get() & 0xFF) );
        }
        else
        {
            return (int)( ( (theByteBuffer.get() & 0xFF) << 8 )
                    | (theByteBuffer.get() & 0xFF) );

        }
    }

    private byte[] generateHash( String aWord )
    {
        return myMessageDigest.digest( aWord.getBytes() );
    }

  //System.out.println(Arrays.toString(theBytes));
  //System.out.println(Integer.toHexString(theBytes[0]));

}
