package ru.sbr.zenkin;

import ru.sbr.zenkin.Algorithm.CityChanger;
import ru.sbr.zenkin.Output.CityOuter;
import ru.sbr.zenkin.file.openFile.ImgFileOpener;
import ru.sbr.zenkin.file.RandomCity;
import ru.sbr.zenkin.file.openFile.TxtFileOpener;

import java.util.Scanner;

public class Major {

    private static char[][] arrCity;

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        //Выбор способа создания "вселенной"
        System.out.print("'t' - glider txt file; 'b' - bmp file; 'r' - random city: ");
        String enter = input.next();
        while (!enter.equals("t") && !enter.equals("b") && !enter.equals("r")){
            System.out.print("Enter Correct type city: ");
            enter = input.next();
        }
        //Создание "вселенной"
        switch (enter){
            case "t":
                TxtFileOpener txtFileOpener = new TxtFileOpener(System.getProperty("user.dir"),"glider.txt");
                arrCity = txtFileOpener.openFile();
                break;
            case "b":
                ImgFileOpener imgFileOpener = new ImgFileOpener(System.getProperty("user.dir"),"glider.bmp");
                arrCity = imgFileOpener.openFile();
                break; //bmp
            case "r":
                System.out.print("Enter length of city: ");
                int lengthCity = input.nextInt();
                System.out.print("Enter width of city: ");
                int widthCity = input.nextInt();
                RandomCity randomCity = new RandomCity();
                arrCity = randomCity.createRandomCity(lengthCity, widthCity);
                break;
        }

        //Отображение начальной стадии "вселенной"
        CityOuter cityOuter = new CityOuter();
        cityOuter.viewCity(arrCity);

        CityChanger cityChanger = new CityChanger();

        for (int i = 1; i < 30; i++){
            cityOuter.viewCity(cityChanger.changeCity(arrCity));
        }
    }
}
