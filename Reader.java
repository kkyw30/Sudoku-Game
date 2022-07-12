import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator; 
import java.io.BufferedReader;

public class Reader {
    private BufferedReader reader; 
    private String newLine; 

public Reader(BufferedReader reader) {
    if (reader == null) {
        throw new IllegalArgumentException("can't have null reader");
    }
    // try to read in the lines using the reader
    else {
        this.reader = reader; 
        try {
            this.newLine = reader.readLine();
        } catch (IOException ex) {
            newLine = null; 
        }
    }
}

public static BufferedReader fromFile(String filePath) {
    if (filePath == null) {
        throw new IllegalArgumentException("can't have null file");
    } else {
        try {
            return new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException ex) {
            throw new IllegalArgumentException("no such file");
        }
    }
}

}
