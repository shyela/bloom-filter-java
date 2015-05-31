import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class TestHasher
{

    @Test
    public void shouldPropertlyConvertSectionsOfHashToIndex() throws Exception
    {
        byte[] theTestHash = { 0x0, 0x0, 0x1,
                               0x0, 0x1, 0x0,
                               0x0, 0x0, 0xf,
                               0x0, 0xf, 0x0,
                               0x0, 0x1, 0x1,
                               0x0, 0xf, 0xf};

        Hasher theHasher = new Hasher();
        assertEquals( 1, theHasher.convertHashSectionToIndex( 0, theTestHash ) );
        assertEquals( 256, theHasher.convertHashSectionToIndex( 1, theTestHash ) );
        assertEquals( 15, theHasher.convertHashSectionToIndex( 2, theTestHash ) );
        assertEquals( 3840, theHasher.convertHashSectionToIndex( 3, theTestHash ) );
        assertEquals( 257, theHasher.convertHashSectionToIndex( 4, theTestHash ) );
        assertEquals( 3855, theHasher.convertHashSectionToIndex( 5, theTestHash ) );
    }
}
