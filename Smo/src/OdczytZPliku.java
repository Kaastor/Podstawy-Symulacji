/**
 * Created by Przemek on 2016-01-21.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class OdczytZPliku{
    private Scanner in;
    private File file;

    public OdczytZPliku(String plik) throws FileNotFoundException
    {
        file = new File(plik);
        in = new Scanner(file);
    }
    public String pobierzDana() {
        String prawdopodobienstwo = in.nextLine();
        return  prawdopodobienstwo;
    }
}
