package com.storms.blast.main.entities;

import static com.storms.blast.main.Utils.Bitmaps.choiceRocketColor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.storms.blast.main.Constant;
import com.storms.blast.main.Game;

import java.util.ArrayList;

public class QueueShoot {
    private Game game;

    private final float x;
    private final float y;

    private Bitmap firstBitmap, secondBitmap;
    private Paint paint = new Paint();

    private ArrayList<Integer> list = new ArrayList<>();

    public QueueShoot(Game game, float positionX, float positionY) {
        this.game = game;
        x = positionX;
        y = positionY;
        list.add((int)(Math.random()*3));
        list.add((int)(Math.random()*3));
        updateColor();
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(firstBitmap, x + Constant.BUTTON_SIDE_X * 1.1f, y + Constant.BUTTON_SIDE_X * 0.5f, paint);
        canvas.drawBitmap(secondBitmap, x + Constant.BUTTON_SIDE_X * 1.3f, y + Constant.BUTTON_SIDE_X * 0.6f, paint);
    }

    public void next(){
        list.remove(0);
        list.add((int)(Math.random()*3));
        updateColor();
    }

    private void updateColor(){
        firstBitmap = choiceRocketColor(list.get(0));
        secondBitmap = choiceRocketColor(list.get(1));
    }

    public ArrayList<Integer> getList() {
        return list;
    }
}
