package top;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileOperation{

    public String Read(String path) throws IOException{
        String context = "";
        try {
            FileReader fileReader = new FileReader(path);
            StringBuffer stringBuffer = new StringBuffer();
            char[] chars = new char[1];
            while (fileReader.read(chars) != -1) {
                stringBuffer.append(String.valueOf(chars));
            }
            context = String.valueOf(stringBuffer);
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return context;
    }

    public void Write(String path,String text) {
        try {
            FileWriter fileWriter = new FileWriter(new File(path));
            fileWriter.write(text);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void create(String path,String fileName) {
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
