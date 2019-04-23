
import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.XMLReader;
import util.Handler;

/**
 * @author Vladimir Danovskyi
 * @company UnitedThinkers
 * @since 2019/04/22
 */


public class Main {
    private static final String PATH = "D:\\test.xml";
    private static final int ALLOWED_FILE_SIZE = 1024 * 1024 * 1024;


    public static void main(final String[] args) throws Exception {
        File sourceFile = new File(PATH);
        if (sourceFile.exists()) {
            if (checkSize(sourceFile.length())){
                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser saxParser = spf.newSAXParser();
                XMLReader xmlReader = saxParser.getXMLReader();
                xmlReader.setContentHandler(new Handler());
                xmlReader.parse(PATH);
            }

        } else {
            System.out.println("File does not exists!");
        }
        System.exit(1);
    }

    public static boolean checkSize(long size) {
        if (size > ALLOWED_FILE_SIZE) {
            System.out.println("File greater than 1 GB");
            return false;
        }
        return true;
    }

}

