package com.example.saper.controller;

import com.example.saper.Main;
import com.example.saper.model.Game;
import com.example.saper.model.items.MoveResult;
import com.example.saper.view.GameView;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;


// контроллер это обычный класс java, который может взамодействовать с FXML
public class SaperController {

    //поля отображения елементов в окне
    @FXML public Label countFlag;
    @FXML private Text time;
    @FXML private Button button;

    @FXML private Canvas canvas; // поле,  на котором мы отображаем элементы

    private GameView gameView;
    private Game game;

    DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    // функция инициализатор (по типу конструктора). хз что делает
    public void initialize() {

        // не знаю, что здесь происходит, такое было в примере у Беркунского
        canvas.heightProperty().addListener(e -> gameView.draw());
        canvas.widthProperty().addListener(e -> gameView.draw());


        button.setStyle("-fx-background-image: url('file:image/FirstSmile.png')"); // загружает стиль кнопки - картинку смайлика
        // "Перезагрузить"(та, которая с улыбающимся смайликом)

        game = new Game();
        gameView = new GameView(game,canvas);
        gameView.draw();

        initializeTexts();
    }

    // функция устанавливает шрифт, размер, цвет к тексту. загружает с файла иконку рядом с текстом(флажок)
    private void initializeTexts() {

        countFlag.setFont(Font.font("Cambria",22));
        countFlag.setTextFill(Color.web("#000000"));
        countFlag.setGraphic(new ImageView(new Image("A:\\CourseWork\\Saper2\\image\\flag.png")));

        time.setFont(Font.font("Cambria",22));

        setTimeText(0); // функция, которая что-то делает с полем время. я не знаю. нашел в Интернете
        writeText();
    }

    // функция, которая что-то делает с полем время. я не знаю. нашел в Интернете
    private void setTimeText(long elapsedSeconds) {

        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        final Date date = new Date(elapsedSeconds * 1000);
        time.setText(timeFormat.format(date));
        game.getGameTimer().setTimeListener(this::setTimeText);

    }

    //выводит на экран количество флажков
    private void writeText() {

        countFlag.setText("" + game.getCountFlag());
    }

    // функция, которая контролирует нажатия мышки
    public void processMouse(MouseEvent mouseEvent) {

        // считает по координам нажатия, на какой участок поля нажимает пользователь
        int x1 = (int) (mouseEvent.getX()/32) + 1;
        int y1 = (int) (mouseEvent.getY()/32) + 1;

        //когда пользователь нажал на участок игрвой сетки, запускается время
        if (game.getMoveResult() == MoveResult.START) {
            game.getGameTimer().start();
            game.setMoveResult(MoveResult.SIMPLE);
        }

        //проверяет состояние игры
        if (game.getMoveResult() != MoveResult.IMPOSSIBLE) {
            //проверка нажатия левой кнопки мышки
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (game.getGridView().getGrid()[x1][y1] == 11) {
                    game.changeCountFlag(1);
                    game.selectArea(x1,y1,button);
                } else game.selectArea(x1, y1,button);
            } else if (mouseEvent.getButton() == MouseButton.SECONDARY) { //проверка нажатия правой кнопки мышки
                if (game.getGridView().getGrid()[x1][y1] == 11) {
                        game.getGridView().getGrid()[x1][y1] = 10; // изменяет значение элемента
                        game.changeCountFlag(1);
                    } else if (game.getCountFlag() != 0 && game.getGridView().getGrid()[x1][y1] == 10) {
                    game.getGridView().getGrid()[x1][y1] = 11;
                    game.changeCountFlag(-1);
                }
            }
        }
        gameView.draw(); // отрисовка игровой сетки
        writeText();
        //проверка состояния игры
        if (game.getMoveResult() == MoveResult.WIN) {
            game.getGameTimer().reset(); // останавливает время
            showAlert(); // выводит диалоговое окно
        }
    }

    //диалоговое окно
    private void showAlert() {

        ButtonType foo = new ButtonType("Спочатку", ButtonBar.ButtonData.OK_DONE);
        ButtonType bar = new ButtonType("Вихід", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"",foo,bar);
        alert.setTitle("Молодець!"); // заголовок окна
        alert.setHeaderText("Гра пройдена за: " + time.getText()); // текст в окне
        Optional<ButtonType> option = alert.showAndWait();
        if (option.orElse(foo) == foo) {// проверка нажатия кнопки "Спочатку"
            alert.close(); // закрывает это диалоговое окно
            ((Stage) button.getScene().getWindow()).close(); // закрывает основное игровое окно
            Main main = new Main(); // создается объект класса Main
            try {
                main.start(new Stage()); // исключение
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (option.orElse(bar) == bar) {// проверка нажатия кнопки "Выход"
            alert.close(); // закрывает это диалоговое окно
            ((Stage) button.getScene().getWindow()).close();// закрывает основное игровое окно
        }
    }

    //  функция, которая контролирует нажатия на смайлик. если нажали на смайлик, перезагружает игру
    public void clickOnRestart() {
        ((Stage) button.getScene().getWindow()).close(); // закрывает окно
        Main main = new Main(); // создается новый объект класса Мейн
        try {
            main.start(new Stage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}