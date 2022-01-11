package com.storms.blast.main;

import static com.storms.blast.main.MainActivity.screenHeight;
import static com.storms.blast.main.MainActivity.screenWidth;

public class Constant {
    public static final float PLAYER_HEIGHT = adaptiveVarY(384);
    public static final float PLAYER_WIDTH = adaptiveVarX(384);
    public static final float BUTTON_SIDE_Y = adaptiveVarY(220);
    public static final float BUTTON_SIDE_X = adaptiveVarX(220);
    public static final float PLAYER_Y = adaptiveVarY(800);
    public static final float PLAYER_X = adaptiveVarX(256);
    public static final float GUN_SIDE_Y = adaptiveVarY(192);
    public static final float GUN_SIDE_X = adaptiveVarX(192);
    public static final float WALL_X = screenWidth/2f - adaptiveVarX(32);
    public static final float WALL_Y = adaptiveVarY(512);
    public static final float WALL_HEIGHT = screenHeight;
    public static final float WALL_WIDTH = adaptiveVarX(64);
    public static final float BUTTON_LEFT_X = adaptiveVarX(1900);
    public static final float BUTTON_LEFT_Y = adaptiveVarY(1164);
    public static final float BUTTON_RIGHT_X = adaptiveVarX(2452);
    public static final float BUTTON_RIGHT_Y = adaptiveVarY(1164);
    public static final float BUTTON_UP_X = adaptiveVarX(2176);
    public static final float BUTTON_UP_Y = adaptiveVarY(1164);
    public static final float BUTTON_DOWN_X = adaptiveVarX(2176);
    public static final float BUTTON_DOWN_Y = adaptiveVarY(888);
    public static final float BUTTON_FIRE_X = adaptiveVarX(258);
    public static final float BUTTON_FIRE_Y = adaptiveVarY(1164);
    public static final float BULLET_SIDE_Y = adaptiveVarY(64);
    public static final float BULLET_SIDE_X = adaptiveVarX(64);
    public static final float WATER_HEIGHT = screenHeight;
    public static final float WATER_WIDTH = screenWidth / 2f;
    public static final float BLOCK_WIDTH = adaptiveVarX(100);
    public static final float BLOCK_HEIGHT = adaptiveVarY(100);
    public static final float HEALTH_X = adaptiveVarX(700);
    public static final float HEALTH_Y = adaptiveVarY(1250);
    public static final float HEALTH_WIDTH = adaptiveVarX(400);
    public static final float HEALTH_HEIGHT = adaptiveVarY(80);
    public static final float GRAVITY = adaptiveVarY(1);
    //все эти числа - пиксели для экрана с разрешением 1440 на 2701, но преобразованные для др. экранов

    public static float adaptiveVarY(float var) {
        return screenHeight/(1440f/var);
    }
    public static float adaptiveVarX(float var) {
        return screenWidth/(2701f/var);
    }

    public static float readaptiveVarY(float var) {
        return var * 1440f / screenHeight;
    }
    public static float readaptiveVarX(float var) {
        return var * 2701f / screenWidth;
    }
}
