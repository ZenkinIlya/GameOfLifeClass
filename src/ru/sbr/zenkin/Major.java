package ru.sbr.zenkin;

import java.util.Scanner;

public class Major {

    private static final int SIZE_COL = 15;
    private static final int SIZE_ROW = 10;
    private static final int COUNT_REPEATS = 15;

    private static char[][] arrField = new char[SIZE_ROW][SIZE_COL];

    //первоначальное заполнение матрицы
    private static void feelArr(){
        for (int i = 0; i < SIZE_ROW; i++){
            for (int j = 0; j < SIZE_COL; j++){
                arrField[i][j] = '*';
            }
        }
    }

    //преобразование элементов матрицы типа char в строку
    private static StringBuilder rowInString(char[] rowArrField){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < SIZE_COL; i++){
            str.append(rowArrField[i]);
        }
        return str;
    }

    //отображение матрицы
    private static void displayField(){
        for (int i = 0; i < SIZE_ROW; i++){
            System.out.println(rowInString(arrField[i]));
        }
        System.out.println("--------------------");
    }

    //поиск соседей
    //!!!поиск соседей не осуществляется у крайних элементов в матрице
    private static char searchEssence(int i, int j){
        int countNearEssence = 0;
        for (int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++){
                if ((x == 1) && (y == 1)) continue;
                if (arrField[i - 1 + x][j - 1 + y] == '#'){
                    countNearEssence++;
                }
            }
        }
        if (countNearEssence < 2 || countNearEssence > 3 && arrField[i][j] == '#'){
            return '*';
        }else if (countNearEssence == 3 && arrField[i][j] == '*') {
            return '#';
        }else return arrField[i][j];
    }

    //получение следующей матрицы
    private static void checkAroundCells(){
        char[][] temporaryArrField = new char [SIZE_ROW][SIZE_COL];
        for (int i = 1; i < SIZE_ROW - 1; i++){
            for (int j = 1; j < SIZE_COL - 1; j++){
                temporaryArrField[i][j] = searchEssence(i, j);
            }
        }
        //arrField = temporaryArrField; - не сработает так как матрица temporaryArrField
        //обрезана по краям
        for (int i = 0; i < SIZE_ROW; i++){
            for (int j = 0; j < SIZE_COL; j++){
                arrField[i][j] = temporaryArrField[i][j];
            }
        }
    }

    //случайное расположение "жителей"
    private static void randomEssence(){
        for (int i = 0; i < SIZE_ROW; i++){
            for (int j = 0; j < SIZE_COL; j++){
                int rand = (int)(Math.random() * 10);
                if (rand < 2){
                    arrField[i][j] = '#';
                }else {
                    arrField[i][j] = '*';
                }
            }
        }
    }

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.print("If u want glider, press 'y': ");
        if (input.hasNext("y")){
            feelArr();
            arrField[4][4] = '#';
            arrField[4][5] = '#';
            arrField[4][6] = '#';
            arrField[3][6] = '#';
            arrField[2][5] = '#';
        }else{
            randomEssence();
        }
        displayField();
        for (int i = 1; i < COUNT_REPEATS; i++){
            checkAroundCells();
            displayField();
        }
    }
}
