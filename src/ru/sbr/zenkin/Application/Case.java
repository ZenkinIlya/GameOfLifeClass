package ru.sbr.zenkin.Application;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Case extends Parent {

    Color getCellColor() {
        return cellColor;
    }

    private Color cellColor;

    Case(Color cellColor){
        this.cellColor = cellColor;

        Rectangle fond = new Rectangle(1,1,10,10);
        fond.setFill(cellColor);
        this.getChildren().add(fond);
    }
}
