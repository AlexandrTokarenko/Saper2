package com.example.saper.view;

import com.example.saper.model.Game;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class GameView {

    private Canvas canvas;
    private Game game;

    private final int w = 32; // константа размера текстуры с файла

    public GameView(Game game, Canvas canvas) {

        this.game = game;
        this.canvas = canvas;
    }

    public void draw() {

        Image image = new Image("A:\\CourseWork\\Saper2\\image\\saper.jpg"); // загружается текстура
        clear();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                // обрезается часть с текстуры с коориднаты (значение элемента gridView * w(32);0) размером w(32) на w(32)
                WritableImage writableImage = new WritableImage(image.getPixelReader(), game.getGridView().getGrid()[i][j] * w, 0, w, w);
                ImageView imageView = new ImageView(writableImage);
                //рисуется текстура
                gc.drawImage(imageView.getImage(), (i - 1) * w, (j - 1) * w);
            }
        }
    }

    //очищается игровое поле
    private void clear() {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.web("#c1c1c1"));// устанавливается цвет
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight()); // зарисовуется область с точки (0,0) шириной canvas.getWidth() и высотой getHeight()
    }
}
