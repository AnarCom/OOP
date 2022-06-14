package com.example.game.field;

import com.example.game.Orientation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SnakeLogic {
    public SnakeLogic(Integer xSize, Integer ySize) {
        this.xSize = xSize;

        this.ySize = ySize;

        snakePositions.add(new Position(3, 4));
//        snakePositions.add(new Position(4, 4));
//        snakePositions.add(new Position(5, 4));
    }

    private boolean isMoving = true;

    private final Integer xSize;
    private final Integer ySize;

    private final LinkedList<Position> snakePositions = new LinkedList<>();
//    public

    private void generateNextSnakePosition(Orientation orientation, List<Pair<Position, PointType>> field) {
        var t = new Position(snakePositions.getFirst().getX(), snakePositions.getFirst().getY());
        switch (orientation) {
            case TOP -> t.setX(t.getX() - 1);
            case BOTTOM -> t.setX(t.getX() + 1);
            case LEFT -> t.setY(t.getY() - 1);
            case RIGHT -> t.setY(t.getY() + 1);
        }
        snakePositions.add(0, t);

        boolean isFood = field.stream()
                .anyMatch(i -> i.getFirst().equals(t) && i.getSecond() == PointType.FOOD);
        boolean isDead = field.stream().anyMatch(
                i -> i.getFirst().equals(t) &&
                        (
                                i.getSecond() == PointType.WALL ||
                                        i.getSecond() == PointType.SNAKE ||
                                        i.getSecond() == PointType.SNAKE_HEAD
                        )
        );
        if (!isFood) {
            snakePositions.remove(snakePositions.size() - 1);
        }

        if(isDead){
            isMoving = false;
            throw new DeadException();
        }

        if(snakePositions.size() > 10){
            throw new WinException();
        }

    }
    public List<Pair<Position, PointType>> getSnakePositions(Orientation orientation,
                                                             List<Pair<Position, PointType>> field
    ) {
        if(!isMoving){
            return List.of();
        }
        generateNextSnakePosition(orientation, field);


        var list = new ArrayList<Pair<Position, PointType>>();
        list.add(new Pair<>(snakePositions.getFirst(), PointType.SNAKE_HEAD));

        for (int i = 1; i < snakePositions.size(); i++) {
            list.add(new Pair<>(snakePositions.get(i), PointType.SNAKE));
        }

        return list;
    }

}
