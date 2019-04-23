import static org.junit.Assert.*;


import org.junit.Test;

/**
 * @author Vladimir Danovskyi
 * @company UnitedThinkers
 * @since 2019/04/22
 */


public class MainTest {
    private static final long allowedFileSize = 1024 * 1024 * 1024;
    private static final long notAllowedFileSize = 1024 * 1024 * 1024 + 1;

    @Test
    public void main() throws Exception {
    }

    @Test
    public void checkSize() throws Exception {
        Main main = new Main();
        assertEquals(main.checkSize(allowedFileSize), true);
        assertEquals(main.checkSize(notAllowedFileSize), false);
    }

}