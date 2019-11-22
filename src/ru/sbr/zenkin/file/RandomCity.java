package ru.sbr.zenkin.file;

public class RandomCity {

    public char[][] createRandomCity(int lengthCity, int widthCity){

        char[][] randomCity = new char[widthCity][lengthCity];
        for (int i = 0; i < widthCity; i++){
            for (int j = 0; j < lengthCity; j++){
                int rand = (int)(Math.random() * 10);
                if (rand < 2){
                    randomCity[i][j] = '#';
                }else {
                    randomCity[i][j] = '*';
                }
            }
        }
        return randomCity;
    }
}
