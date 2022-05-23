package com.example.saper.model;

import com.example.saper.matrix.GridLogic;
import com.example.saper.matrix.GridView;
import com.example.saper.model.items.GameTimer;
import com.example.saper.model.items.MoveResult;

import javafx.scene.control.Button;

import java.util.Arrays;


public class Game {

    private GridLogic gridLogic;
    private GridView gridView;
    private MoveResult moveResult;
    private int countFlag;
    private GameTimer gameTimer;

    //конструктор
    public Game() {
        this.gridLogic = new GridLogic();
        this.gridView = new GridView();
        moveResult = MoveResult.START;
        countFlag = 10;
        gameTimer = new GameTimer();
    }

    public GameTimer getGameTimer() {
        return gameTimer;
    }

    // функция, "открывает" ячейку
    public void selectArea(int x1, int y1, Button button) {

        gridView.getGrid()[x1][y1] = gridLogic.getGrid()[x1][y1];

        //если пользователь нажал на ячейку с миной, то массив gridView сохраняет все значения мины
        if (gridView.getGrid()[x1][y1] == 9) {
            for (int i = 1; i <= 10; i++) {
                for (int j = 1; j <= 10; j++) {
                    if (gridLogic.getGrid()[i][j] == 9) {
                        gridView.getGrid()[i][j] = gridLogic.getGrid()[i][j];
                    }
                }
            }
            // устанавливает состояние IMPOSSIBLE
            moveResult = MoveResult.IMPOSSIBLE;
            button.setStyle("-fx-background-image: url('file:image/SecondSmile.png')"); // устанавливает стиль грустного смайлика (проигрыш)
            gameTimer.reset(); // ост. время
        }

        //просматривает всю игровую сетку
        if (Arrays.stream(gridView.getGrid()).allMatch(i -> Arrays.stream(i).allMatch(j -> j != 9))) {
            label:
            for (int i = 1; i <= 10; i++) {
                for (int j = 1; j <= 10; j++) {
                    if(gridView.getGrid()[i][j] == 10 && gridLogic.getGrid()[i][j] == 9) { // если все элементы сетки не есть минами,
                        // и не есть не нажатым полем
                        moveResult = MoveResult.WIN;
                    } else if (gridView.getGrid()[i][j] == 10 && gridLogic.getGrid()[i][j] != 9) { // проверяет, есть ли хоть одна не нажатая ячейка
                        moveResult = MoveResult.SIMPLE;
                        break label;
                    }
                }
            }
        }
    }

    public MoveResult getMoveResult() {
        return moveResult;
    }

    public void setMoveResult(MoveResult moveResult) {
        this.moveResult = moveResult;
    }

    public void changeCountFlag(int i) {
        countFlag += i;
    }

    public int getCountFlag(){

        return countFlag;
    }

    public GridLogic getGridLogic() {
        return gridLogic;
    }

    public GridView getGridView() {
        return gridView;
    }
}
