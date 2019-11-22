package ru.sbr.zenkin.file.saveFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TxtFileSaver implements FileSaverImpl {

    private String pathFile;
    private String nameFile;

    public TxtFileSaver(String pathFile, String nameFile) {
        this.pathFile = pathFile;
        this.nameFile = nameFile;
    }

    @Override
    public void saveFile(char[][] chars) {
        File file = new File(pathFile, nameFile);
        try {
            //Очистка файла если он существует
            if (file.exists()){
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.close();
            }
            FileWriter fileWriter = new FileWriter(file);
            for (char[] aChar : chars) {
                fileWriter.write(String.valueOf(aChar) + "\n");
            }

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
