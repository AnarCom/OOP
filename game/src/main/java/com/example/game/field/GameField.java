package com.example.game.field;

import com.example.game.Config;
import com.example.game.Orientation;

import java.util.ArrayList;
import java.util.List;

public class GameField {
    private final Integer xSize;
    private final Integer ySize;

    private final List<Pair<Position, PointType>> wallsPosition;

    private List<Pair<Position, PointType>> oldField = new ArrayList<>();

    private final SnakeLogic snakeLogic;

    private final FoodGenerator foodGenerator;

    public GameField(Integer xSize, Integer ySize, Integer foodNumber) {
        this.xSize = xSize;
        this.ySize = ySize;

        snakeLogic = new SnakeLogic(xSize, ySize);
        foodGenerator = new FoodGenerator(xSize, ySize, foodNumber);

        wallsPosition = new ArrayList<>();
        for (int i = 0; i < xSize; i++) {
            wallsPosition.add(new Pair<>(new Position(i, 0), PointType.WALL));
            wallsPosition.add(new Pair<>(new Position(i, ySize - 1), PointType.WALL));
        }
        for (int i = 0; i < ySize; i++) {
            wallsPosition.add(new Pair<>(new Position(0, i), PointType.WALL));
            wallsPosition.add(new Pair<>(new Position(xSize - 1, i), PointType.WALL));
        }

        oldField.addAll(wallsPosition);
    }

    public List<Pair<Position, PointType>> getNewField(Orientation orientation) {
        var field = new ArrayList<>(wallsPosition);


        var snake = snakeLogic.getSnakePositions(orientation, oldField);
        field.addAll(foodGenerator.getFoodPosition(oldField));

        field.addAll(snake);
        oldField = field;
        return field;
    }


}
