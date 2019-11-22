package ru.sbr.zenkin.file.saveFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImgFileSaver implements FileSaverImpl {

    private String pathFile;
    private String nameFile;

    public ImgFileSaver(String pathFile, String nameFile) {
        this.pathFile = pathFile;
        this.nameFile = nameFile;
    }

    @Override
    public void saveFile(char[][] chars) {
        File file = new File(pathFile, nameFile);
        try {
            BufferedImage im = new BufferedImage(chars.length, chars[0].length, BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < chars.length; i++){
                for (int j = 0; j < chars[i].length; j++){
                    if (chars[i][j] == '*'){
                        im.setRGB(j,i,-1);
                    }else{
                        im.setRGB(j,i,-16777215);
                    }
                }
            }
            ImageIO.write(im,"png", file);  //в bmp не сохраняет, ImageIO.write возвращает false
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
