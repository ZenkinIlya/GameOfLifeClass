package ru.sbr.zenkin.file.openFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImgFileOpener implements FileOpenerImpl {

    private String pathFile;
    private String nameFile;

    public ImgFileOpener(String pathFile, String nameFile){
        this.pathFile = pathFile;
        this.nameFile = nameFile;
    }

    @Override
    public char[][] openFile() {
        File file = new File(pathFile, nameFile);
        char[][]arrImg = null;
        try {
            BufferedImage img = ImageIO.read(file);
            arrImg = new char[img.getHeight()][img.getWidth()];

            for (int i = 0; i < img.getHeight(); i++){
                for (int j = 0; j < img.getHeight(); j++){
                    if (img.getRGB(j,i) == -1){
                        arrImg[i][j] = '*';
                    }else{
                        arrImg[i][j] = '#';
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Не удалось открыть bmp файл");
        }
        return arrImg;
    }
}
