package com.example.game;

public class Config {
    private static Integer xSize = 25;
    private static Integer ySize = 25;

    private static Integer height = 25;
    private static Integer width = 25;

    private static Integer foodNumber = 10;

    private static boolean useNewDrawAlg = true;

    public static boolean isUseNewDrawAlg() {
        return useNewDrawAlg;
    }

    public static Integer getxSize() {
        return xSize;
    }

    public static Integer getySize() {
        return ySize;
    }

    public static Integer getHeight() {
        return height;
    }

    public static Integer getWidth() {
        return width;
    }

    public static Integer getFoodNumber() {
        return foodNumber;
    }
}
