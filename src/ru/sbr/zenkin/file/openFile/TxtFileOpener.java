package ru.sbr.zenkin.file.openFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TxtFileOpener implements FileOpenerImpl {

    private String pathFile;
    private String nameFile;

    public TxtFileOpener(String pathFile, String nameFile){
        this.pathFile = pathFile;
        this.nameFile = nameFile;
    }

    //        При сохранении TXT файла в формате UTF-8, в списке для считывания в нулевой строчке на нулевой позиции будет лишний символ
    //        если считывать посимвольно -> stringList.get(0).charAt(0)
    private char[][] listConverting(List<String> stringList){
        int length = stringList.get(0).length();
        int width = stringList.size();
        char[][] arrCity = new char[width][length];
        for (int i = 0; i < width; i++){
            for (int j = 0; j < length; j++){
                arrCity[i][j] = stringList.get(i).charAt(j);
            }
        }
        return arrCity;
    }

    @Override
    public char[][] openFile() {
        File file = new File(pathFile, nameFile);
        List<String> stringList = new ArrayList<>();
        if (file.exists()){
            try {
                FileReader fileReader = new FileReader(file);
                Scanner scanner = new Scanner(fileReader);
                while (scanner.hasNextLine()){
                    stringList.add(scanner.nextLine());
                }
            } catch (IOException e) {
                System.out.println("Не удалось открыть txt файл");
            }
        }
        return listConverting(stringList);
    }
}
