package com.example.game.field;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FoodGenerator {
    private final Integer xSize;
    private final Integer ySize;

    private final Integer foodNumber;

    private List<Position> foodPositions = new ArrayList<>();

    public FoodGenerator(Integer xSize, Integer ySize, Integer foodNumber) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.foodNumber = foodNumber;
    }

    public List<Pair<Position, PointType>> getFoodPosition(
            List<Pair<Position, PointType>> field
    ) {
        var snake = field.stream()
                .filter(i -> i.getSecond() == PointType.SNAKE || i.getSecond() == PointType.SNAKE_HEAD)
                .map(Pair::getFirst)
                .collect(Collectors.toList());

        foodPositions = foodPositions.stream()
                .filter((i) -> snake.
                        stream()
                        .noneMatch(j -> j.equals(i)))
                .collect(Collectors.toList());

        for (int i = 0; i < foodNumber - foodPositions.size(); i++) {
            if (Math.random() < 0.3D)
                continue;

            int x = (int) (xSize * Math.random());
            int y = (int) (ySize * Math.random());

            var position = new Position(x, y);

            if (
                    field.stream().anyMatch(it -> it.getFirst().equals(position))
            ) {
                i--;
            } else {
                foodPositions.add(position);
            }
        }

        return foodPositions.stream()
                .map(it -> new Pair<>(it, PointType.FOOD))
                .collect(Collectors.toList());
    }

}
