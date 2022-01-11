package com.storms.blast.main.Utils;

import static com.storms.blast.main.Constant.BLOCK_HEIGHT;
import static com.storms.blast.main.Constant.BLOCK_WIDTH;
import static com.storms.blast.main.Constant.BULLET_SIDE_X;
import static com.storms.blast.main.Constant.BULLET_SIDE_Y;
import static com.storms.blast.main.Constant.GUN_SIDE_X;
import static com.storms.blast.main.Constant.GUN_SIDE_Y;
import static com.storms.blast.main.Constant.WATER_HEIGHT;
import static com.storms.blast.main.Constant.WATER_WIDTH;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.storms.blast.R;
import com.storms.blast.main.Constant;

public class Bitmaps extends View {

    private static Bitmap rocketBitmap, blueBitmap, redBitmap, greenBitmap, yellowBitmap;
    private Bitmap destroyBitmap;
    private static Bitmap blueBlockBitmap, redBlockBitmap, greenBlockBitmap, yellowBlockBitmap;
    private static Bitmap stormBitmap, buttonUpBitmap, buttonDownBitmap, getButtonUpBitmap, buttonRightBitmap, buttonLeftBitmap,
    buttonFireBitmap, wallBitmap, gunBitmap, waterBitmap;

    public Bitmaps(Context context) {
        super(context);

        stormBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.storm);
        buttonUpBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.button_up);
        buttonDownBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.button_down);
        buttonRightBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.button_right);
        buttonLeftBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.button_left);
        wallBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wall);
        gunBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gun);
        buttonFireBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.button_fire);
        redBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball_red);
        greenBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball_green);
        blueBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball_blue);
        yellowBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball_yellow);
        destroyBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bang);
        waterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wave1);

        redBlockBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.block_red);
        greenBlockBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.block_green);
        blueBlockBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.block_blue);
        yellowBlockBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.block_yellow);

        destroyBitmap = Bitmap.createScaledBitmap(destroyBitmap, (int) BULLET_SIDE_X, (int) BULLET_SIDE_Y, true);
        redBitmap = Bitmap.createScaledBitmap(redBitmap, (int) BULLET_SIDE_X, (int) BULLET_SIDE_Y, true);
        greenBitmap = Bitmap.createScaledBitmap(greenBitmap, (int) BULLET_SIDE_X, (int) BULLET_SIDE_Y, true);
        blueBitmap = Bitmap.createScaledBitmap(blueBitmap, (int) BULLET_SIDE_X, (int) BULLET_SIDE_Y, true);
        yellowBitmap = Bitmap.createScaledBitmap(yellowBitmap, (int) BULLET_SIDE_X, (int) BULLET_SIDE_Y, true);
        gunBitmap = Bitmap.createScaledBitmap(gunBitmap, (int) GUN_SIDE_X, (int) GUN_SIDE_Y, true);
        waterBitmap = Bitmap.createScaledBitmap(waterBitmap, (int) WATER_WIDTH, (int) WATER_HEIGHT, true);

        redBlockBitmap = Bitmap.createScaledBitmap(redBlockBitmap, (int) BLOCK_WIDTH, (int) BLOCK_HEIGHT, true);
        greenBlockBitmap = Bitmap.createScaledBitmap(greenBlockBitmap, (int) BLOCK_WIDTH, (int) BLOCK_HEIGHT, true);
        blueBlockBitmap = Bitmap.createScaledBitmap(blueBlockBitmap, (int) BLOCK_WIDTH, (int) BLOCK_HEIGHT, true);
        yellowBlockBitmap = Bitmap.createScaledBitmap(yellowBlockBitmap, (int) BLOCK_WIDTH, (int) BLOCK_HEIGHT, true);
    }

    public static Bitmap choiceRocketColor(int color) {
        switch (color) {
            case 0:
                return redBitmap;
            case 1:
                return yellowBitmap;
            case 2:
                return greenBitmap;
            case 3:
                return blueBitmap;
        }
        return null;
    }

    public static Bitmap choiceBlockColor(int color) {
        switch (color) {
            case 0:
                return redBlockBitmap;
            case 1:
                return yellowBlockBitmap;
            case 2:
                return greenBlockBitmap;
            case 3:
                return blueBlockBitmap;
        }
        return null;
    }

    public static Bitmap getRocketBitmap() {
        return rocketBitmap;
    }

    public static Bitmap getStormBitmap() {
        return stormBitmap;
    }

    public static Bitmap getWallBitmap() {
        return wallBitmap;
    }

    public static Bitmap getGunBitmap() {
        return gunBitmap;
    }

    public static Bitmap getWaterBitmap() {
        return waterBitmap;
    }

    public static Bitmap getButtonUpBitmap() {
        return buttonUpBitmap;
    }

    public static Bitmap getButtonDownBitmap() {
        return buttonDownBitmap;
    }

    public static Bitmap getButtonRightBitmap() {
        return buttonRightBitmap;
    }

    public static Bitmap getButtonLeftBitmap() {
        return buttonLeftBitmap;
    }

    public static Bitmap getButtonFireBitmap() {
        return buttonFireBitmap;
    }
}
