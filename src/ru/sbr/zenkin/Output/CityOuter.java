package ru.sbr.zenkin.Output;

public class CityOuter implements CityDisplayImpl {


    @Override
    public void viewCity(char[][] arrCity) {
        System.out.println("------------------------");
        for (int i = 0; i < arrCity.length; i++) {
            System.out.println();
            for (int j = 0; j < arrCity[0].length; j++) {
                System.out.print(arrCity[i][j]);
            }
        }
        System.out.println();
    }
}
