package ru.sbr.zenkin.Application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.sbr.zenkin.Algorithm.CityChanger;
import ru.sbr.zenkin.file.openFile.ImgFileOpener;
import ru.sbr.zenkin.file.RandomCity;
import ru.sbr.zenkin.file.openFile.TxtFileOpener;
import ru.sbr.zenkin.file.saveFile.ImgFileSaver;
import ru.sbr.zenkin.file.saveFile.TxtFileSaver;

public class VisualCity extends Application {

    private static char[][] arrCity;
    private static final int ValH = 15; //высота
    private static final int ValW = 15;  //ширина
    private static Case[][] plateauTab = new Case[ValW][ValH];
    private static GridPane gridPane;
    private Task<Void> task;

    //Заполнение сетки
    //Выполняется один раз в начале работы программы
    private void initializationEmptyCity(){
        for (int i = 0; i < ValH; i++){
            for (int j = 0; j < ValW; j++){
                plateauTab[j][i] = new Case(Color.WHITE);
                gridPane.add(plateauTab[j][i], j , i);
            }
        }
    }

    private void fieldClear(){
        int plateauTabH = plateauTab[0].length;
        int plateauTabW = plateauTab.length;
        for (int i = 0; i < plateauTabW; i++){
            for (int j = 0; j < plateauTabH; j++){
                plateauTab[j][i] = new Case(Color.WHITE);
                gridPane.add(plateauTab[j][i], j , i);

            }
        }
    }

    private void viewGeneration(char[][]arrCity){
        for (int i = 0; i < arrCity.length; i++) {
            for (int j = 0; j < arrCity[0].length; j++) {

                //если есть житель и на предыдущий генерации его не было
                if (arrCity[i][j] == '#' && plateauTab[j][i].getCellColor() != Color.ORANGE){
                    plateauTab[j][i] = new Case(Color.ORANGE);
                    gridPane.add(plateauTab[j][i], j , i);
                }
                //если нет жителя и на предыдущий генерации он был
                if (arrCity[i][j] == '*' && plateauTab[j][i].getCellColor() != Color.DARKBLUE){
                    plateauTab[j][i] = new Case(Color.DARKBLUE);
                    gridPane.add(plateauTab[j][i], j , i);
                }
            }
        }
    }

    //Проверка введенного текста на число. Без проброса исключения тоже работает
    private int checkDigits(TextField textField, String nameOfTextField, int maxValue)throws NumberFormatException{
        int digit;
        digit = Integer.parseUnsignedInt(textField.getText());
        if (digit > maxValue){
            digit = maxValue;
            textField.setText(String.valueOf(digit));
            System.out.println("MaxValue of " + nameOfTextField + " = " + maxValue);
        }
        return digit;
    }

    private boolean checkTaskStart(){
        if(task != null) {
            System.out.println("Action not available when autoGeneration launched");
            return true;
        }
        return false;
    }
    @Override
    public void start(Stage stage) {
        try {
            VBox root = new VBox();  //создание контейнера
            Scene scene = new Scene(root, 210,500);  //создание сцены с указанием размеров
            gridPane = new GridPane();  //создание сетки
            gridPane.setVgap(3);  //расстояние между соседями по вертикали
            gridPane.setHgap(3);  //расстояние между соседями по горизонтали
            root.getChildren().add(gridPane);  //добавление сетки на главное окно

            //Создание панели на которой будут находиться кнопки
            FlowPane flowPaneButton = new FlowPane();
            Button load = new Button("Load");
            Button nextGeneration = new Button("Next");
            Button autoGeneration = new Button("Generates");
            Button stopGenerate = new Button("Stop");
            flowPaneButton.getChildren().add(load);
            flowPaneButton.getChildren().add(nextGeneration);
            flowPaneButton.getChildren().add(autoGeneration);
            flowPaneButton.getChildren().add(stopGenerate);
            root.getChildren().addAll(flowPaneButton);  //Добавление панельки с элементами на главное окно
            flowPaneButton.setBorder(new Border(new BorderStroke(Color.GREEN,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            flowPaneButton.setPadding(new Insets(5));

            //Создание панели на которой будут находиться checkBox
            VBox vBoxCheckBox = new VBox();
            CheckBox txtGlider = new CheckBox("glider.txt");
            CheckBox bmpGlider = new CheckBox("glider.bmp");
            vBoxCheckBox.getChildren().add(txtGlider);
            vBoxCheckBox.getChildren().add(bmpGlider);
            root.getChildren().addAll(vBoxCheckBox);  //Добавление панельки с элементами на главное окно
            txtGlider.setSelected(true);    //Первоначально txtGlider.isSelected = true;
            //Создание ограничивающей рамки
            vBoxCheckBox.setBorder(new Border(new BorderStroke(Color.BLUE,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            vBoxCheckBox.setPadding(new Insets(5));

            VBox vBoxRandomCity = new VBox();
            CheckBox randomCity = new CheckBox("Random city");
            vBoxRandomCity.getChildren().add(randomCity);
            Label highLabel = new Label("High");
            TextField textHighGrid = new TextField("15");
            Label wightLabel = new Label("Wight");
            TextField textWightGrid = new TextField("15");
            vBoxRandomCity.getChildren().add(highLabel);
            vBoxRandomCity.getChildren().add(textHighGrid);
            vBoxRandomCity.getChildren().add(wightLabel);
            vBoxRandomCity.getChildren().add(textWightGrid);
            vBoxRandomCity.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            vBoxRandomCity.setPadding(new Insets(2));
            vBoxCheckBox.getChildren().addAll(vBoxRandomCity);

            VBox vBoxSave = new VBox();
            Button saveCurrentGeneration = new Button("Save current generation");
            saveCurrentGeneration.setPrefSize(scene.getWidth(),Button.USE_PREF_SIZE);
            CheckBox txtSave = new CheckBox("Save in txt format");
            CheckBox bmpSave = new CheckBox("Save in bmp format");
            vBoxSave.getChildren().add(saveCurrentGeneration);
            vBoxSave.getChildren().add(txtSave);
            vBoxSave.getChildren().add(bmpSave);
            root.getChildren().add(vBoxSave);
            txtSave.setSelected(true);    //Первоначально txtGlider.isSelected = true;
            //Создание ограничивающей рамки
            vBoxSave.setBorder(new Border(new BorderStroke(Color.RED,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            vBoxSave.setPadding(new Insets(5));

            CityChanger cityChanger = new CityChanger();
            initializationEmptyCity();  //Создание ячеек и заполнение их белым цветом
            stage.setTitle("Game of life");
            stage.setScene(scene);
            stage.show();

            txtGlider.setOnAction(actionEvent -> {
                txtGlider.setSelected(true);
                bmpGlider.setSelected(false);
                randomCity.setSelected(false);
            });

            bmpGlider.setOnAction(actionEvent -> {
                txtGlider.setSelected(false);
                bmpGlider.setSelected(true);
                randomCity.setSelected(false);
            });

            randomCity.setOnAction(actionEvent -> {
                txtGlider.setSelected(false);
                bmpGlider.setSelected(false);
                randomCity.setSelected(true);
            });

            txtSave.setOnAction(actionEvent -> {
                txtSave.setSelected(true);
                bmpSave.setSelected(false);
            });

            bmpSave.setOnAction(actionEvent -> {
                txtSave.setSelected(false);
                bmpSave.setSelected(true);
            });

            //Загрузка вселенной
            load.setOnAction(actionEvent -> {
                try {
                    if (checkTaskStart()){return;}
                    //Создание "вселенной"
                    if (txtGlider.isSelected()){
                        TxtFileOpener txtFileOpener = new TxtFileOpener(System.getProperty("user.dir"),"glider.txt");
                        arrCity = txtFileOpener.openFile();
                    }else if (bmpGlider.isSelected()){
                        ImgFileOpener imgFileOpener = new ImgFileOpener(System.getProperty("user.dir"),"glider.bmp");
                        arrCity = imgFileOpener.openFile();
                    }else if (randomCity.isSelected()){
                        RandomCity randomCity1 = new RandomCity();
                        arrCity = randomCity1.createRandomCity(checkDigits(textHighGrid, "High", ValH),checkDigits(textWightGrid, "Wight", ValW));
                    }
                    fieldClear();   //Очистка всех существующих ячеек
                    viewGeneration(arrCity);    //отображение начальной стадии
                    System.out.println("Load was successful");
                }catch(NumberFormatException e) {
                    System.out.println("Load was failed, NumberFormatException");
                }
            });

            nextGeneration.setOnAction(actionEvent -> {
                if (checkTaskStart()){return;}
                arrCity = cityChanger.changeCity(arrCity);
                viewGeneration(arrCity);
                System.out.println("Manual nextGeneration complete");
            });

            autoGeneration.setOnAction(actionEvent -> {
                if (checkTaskStart()){return;}
                System.out.println("autoGeneration started");
                task = new Task<Void>() {
                    @Override
                    protected Void call() {
                        while (task != null) {
                            arrCity = cityChanger.changeCity(arrCity);

                            Platform.runLater(() -> viewGeneration(arrCity));
                            //viewGeneration(arrCity);  //Без runLater не отображается в реальном времени
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                System.out.println("Thread has been interrupted");
                            }
                        }
                        return null;
                    }
                };
                Thread thread = new Thread(task);
                thread.setDaemon(true);
                thread.start();
            });

            stopGenerate.setOnAction(event -> {
                if(task == null) {
                    System.out.println("autoGeneration not running");
                    return;
                }
                System.out.println("autoGeneration stopped");

                task.cancel();
                task = null;
            });

            saveCurrentGeneration.setOnAction(event -> {
                if (checkTaskStart()){return;}
                    //Сохранение "вселенной"
                if (txtSave.isSelected()) {
                    TxtFileSaver txtFileSaver = new TxtFileSaver(System.getProperty("user.dir"), "glider.txt");
                    txtFileSaver.saveFile(arrCity);
                }else {
                    ImgFileSaver imgFileSaver = new ImgFileSaver(System.getProperty("user.dir"), "glider.bmp");
                    imgFileSaver.saveFile(arrCity);
                }
                System.out.println("Save was successful");
            });

        }catch(Exception e){
            System.out.println("error Application");
        }
    }
}
