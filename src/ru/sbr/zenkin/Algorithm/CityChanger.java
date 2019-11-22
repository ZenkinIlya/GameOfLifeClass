package ru.sbr.zenkin.Algorithm;

public class CityChanger {

    private int lengthArrCity;
    private int widthArrCity;
    private char[][] toroidArrCity;

    //поиск соседей
    private char searchEssence(int i, int j){
        int countNearEssence = 0;
        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++){
                if ((x == 1) && (y == 1)) continue;
                if (toroidArrCity[i - 1 + x][j - 1 + y] == '#'){
                    countNearEssence++;
                }
            }
        }
        if (countNearEssence < 2 || countNearEssence > 3 && toroidArrCity[i][j] == '#'){
            return '*';
        }else if (countNearEssence == 3 && toroidArrCity[i][j] == '*') {
            return '#';
        }else return toroidArrCity[i][j];
    }

    //Добавление участков по краям "вселенной"
    private void modificationArr(char[][] arrCity, int firstStrEl, int lastStrEl, int firstColEl, int lastColEl, String param){
        int lengthToroidArr = toroidArrCity[0].length - 1;
        int widthToroidArr = toroidArrCity.length - 1;
        for (int i = firstColEl; i < lastColEl; i++){
            for (int j = firstStrEl; j < lastStrEl; j++){
                switch (param){
                    case "top":{
                        toroidArrCity[0][j + 1] = arrCity[i][j];
                        break;
                    }
                    case "down":{
                        toroidArrCity[widthToroidArr][j + 1] = arrCity[i][j];
                        break;
                    }
                    case "left":{
                        toroidArrCity[i + 1][0] = arrCity[i][j];
                        break;
                    }
                    case "right":{
                        toroidArrCity[i + 1][lengthToroidArr] = arrCity[i][j];
                        break;
                    }
                }
            }
        }
    }

    //первоначальное заполнение матрицы
    private void feelArr(){
        for (int i = 0; i < toroidArrCity.length; i++){
            for (int j = 0; j < toroidArrCity[0].length; j++){
                toroidArrCity[i][j] = '*';
            }
        }
    }

    //создание массива toroidArrCity для эмуляции тороидальной поверхности
    private void createToroidArrCity(char[][] arrCity){

        toroidArrCity = new char[widthArrCity + 2][lengthArrCity + 2];
        feelArr();

        //заполнение верхнего левого угла
        toroidArrCity[0][0] = arrCity[widthArrCity - 1][lengthArrCity - 1];
        //заполнение верхнего правого угла
        toroidArrCity[0][lengthArrCity + 1] = arrCity[widthArrCity - 1][0];
        //заполнение нижнего левого угла
        toroidArrCity[widthArrCity + 1][0] = arrCity[0][lengthArrCity - 1];
        //заполнение верхнего правого угла
        toroidArrCity[widthArrCity + 1][lengthArrCity + 1] = arrCity[0][0];

        //заполнение верхней строчки
        modificationArr(arrCity, 0, lengthArrCity,(widthArrCity - 1),widthArrCity, "top");
        //заполнение нижней строчки
        modificationArr(arrCity, 0, lengthArrCity,0,1, "down");
        //заполнение левого столбца
        modificationArr(arrCity, (lengthArrCity - 1), lengthArrCity,0,widthArrCity, "left");
        //заполнение правого столбца
        modificationArr(arrCity, 0, 1,0,widthArrCity, "right");

        //помещаем arrCity в toroidArrCity
        for (int i = 0; i < widthArrCity; i++){
            for (int j = 0; j < lengthArrCity; j++){
                toroidArrCity[i + 1][j + 1] = arrCity[i][j];
            }
        }
    }

    //получение следующей матрицы
    public char[][] changeCity(char[][]arrCity){
        lengthArrCity = arrCity[0].length;
        widthArrCity = arrCity.length;

        //создание массива для эмуляции тороидальной поверхности
        createToroidArrCity(arrCity);

        //Расчет следующего поколения
        for (int i = 1; i < (widthArrCity + 1); i++){
            for (int j = 1; j < (lengthArrCity + 1); j++){
                arrCity[i - 1][j - 1] = searchEssence(i, j);
            }
        }
        return arrCity;
    }
}
