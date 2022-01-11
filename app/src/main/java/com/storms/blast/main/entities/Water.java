package com.storms.blast.main.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import static com.storms.blast.main.Constant.WALL_HEIGHT;
import static com.storms.blast.main.Constant.WATER_HEIGHT;
import static com.storms.blast.main.Constant.WATER_WIDTH;
import static com.storms.blast.main.Constant.adaptiveVarY;

import com.storms.blast.main.MainActivity;

public class Water {

    private Bitmap bitmap;
    private Paint paint = new Paint();

    private float x;
    private float y;

    private final float velocityY = -adaptiveVarY(0.1f);

    public Water(Bitmap bitmap, int x) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = MainActivity.screenHeight*0.75f;
    }

    public void update(){
        y = y + velocityY;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, x, y, paint);
    }

    public Rect getBoundingBoxRect(){
        return new Rect((int)(x), (int)(y), (int)(WATER_WIDTH), (int)(WATER_HEIGHT));
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
