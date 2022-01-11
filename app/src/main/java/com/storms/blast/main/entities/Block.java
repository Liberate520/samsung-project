package com.storms.blast.main.entities;

import static com.storms.blast.main.Utils.Bitmaps.choiceBlockColor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Block {
    private boolean anchored;
    private int color;
    private float x,y;

    private Bitmap bitmap;
    private Paint paint = new Paint();

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Block(float x, float y, int color) {
        this.color = color;
        this.x = x;
        this.y = y;
        bitmap = choiceBlockColor(color);
    }

    public void update(){
        if (isAnchored()){
            return;
        }
        y += 20;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, paint);
    }

    public Rect getBoundingBoxRect(){
        return new Rect((int)(x), (int)(y), (int)(x+bitmap.getWidth()), (int)(y+bitmap.getHeight()));
    }

    public int getColor() {
        return color;
    }

    public boolean isAnchored() {
        return anchored;
    }

    public void setAnchored(boolean anchored) {
        this.anchored = anchored;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
