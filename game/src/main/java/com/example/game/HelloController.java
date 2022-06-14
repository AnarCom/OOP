package com.example.game;

import com.example.game.field.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class HelloController implements Initializable {

    private GameField field = new GameField(Config.getySize(), Config.getxSize(), Config.getFoodNumber());


    @FXML
    private Pane parent;

    private Rectangle[][] rectangles;

    private Orientation orientation = Orientation.TOP;

    Timeline timeline;

    boolean block = false;

    public void onButtonClick(KeyEvent keyEvent) {

        boolean safety = oldField
                .stream()
                .map(Pair::getSecond)
                .anyMatch((i) -> i == PointType.SNAKE);

        if (block)
            return;
        switch (keyEvent.getCode()) {
            case W -> {
                if (orientation == Orientation.BOTTOM && safety)
                    break;
                orientation = Orientation.TOP;
            }
            case A -> {
                if (orientation == Orientation.RIGHT && safety)
                    break;
                orientation = Orientation.LEFT;
            }
            case S -> {
                if (orientation == Orientation.TOP && safety)
                    break;
                orientation = Orientation.BOTTOM;
            }
            case D -> {
                if (orientation == Orientation.LEFT && safety)
                    break;
                orientation = Orientation.RIGHT;
            }
            default -> {
            }
        }
        block = true;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rectangles = new Rectangle[Config.getxSize()][Config.getySize()];

        for (int i = 0; i < Config.getxSize(); i++) {
            for (int j = 0; j < Config.getySize(); j++) {
                var rectangle = new Rectangle();
                parent.getChildren().add(rectangle);

                rectangle.setHeight(Config.getHeight());
                rectangle.setWidth(Config.getWidth());
                rectangle.setX(Config.getWidth() * i);
                rectangle.setY(Config.getHeight() * j);

                rectangle.setFill(Color.LIMEGREEN);
                rectangles[i][j] = rectangle;
                rectangles[i][j].setOnKeyPressed(this::onButtonClick);
            }
        }

        timeline = new Timeline(new KeyFrame(Duration.millis(500), t -> {
            try {
                drawField(field.getNewField(orientation));
                block = false;
            } catch (DeadException e) {
                timeline.stop();
                var alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Game over");
                alert.setContentText("Shake is dead");
                alert.show();
            } catch (WinException e) {
                timeline.stop();
                var alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("You win!");
                alert.setContentText("Congratulation");
                alert.show();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private List<Pair<Position, PointType>> oldField = new LinkedList<>();

    private void drawField(List<Pair<Position, PointType>> newField) {

        if(Config.isUseNewDrawAlg()){
            var toDelete = oldField.stream()
                    .filter(i -> newField.stream()
                            .noneMatch(j -> j.getFirst().equals(i.getFirst()) && j.getSecond() == i.getSecond()))
                    .collect(Collectors.toList());

            for (var i : toDelete) {
                rectangles[i.getFirst().getY()][i.getFirst().getX()].setFill(Color.LIMEGREEN);
            }

            var toAdd = newField.stream()
                    .filter(i -> oldField.stream()
                            .noneMatch(j -> j.equals(i)))
                    .collect(Collectors.toList());

            for(var i : toAdd){
                Color color;

                switch (i.getSecond()) {
                    case FOOD -> color = Color.PINK;
                    case SNAKE -> color = Color.BROWN;
                    case SNAKE_HEAD -> color = Color.BLACK;
                    case WALL -> color = Color.RED;
                    default -> color = Color.AQUA;
                }
                rectangles[i.getFirst().getY()][i.getFirst().getX()].setFill(color);
            }
            oldField = newField;
        } else {
            for (var i : rectangles) {
                for (var j : i) {
                    j.setFill(Color.LIMEGREEN);
                }
            }

            for (var i : newField) {

                Color color;

                switch (i.getSecond()) {
                    case FOOD -> color = Color.PINK;
                    case SNAKE -> color = Color.BROWN;
                    case SNAKE_HEAD -> color = Color.BLACK;
                    case WALL -> color = Color.RED;
                    default -> color = Color.AQUA;
                }
                rectangles[i.getFirst().getY()][i.getFirst().getX()].setFill(color);
            }
        }
    }

}
