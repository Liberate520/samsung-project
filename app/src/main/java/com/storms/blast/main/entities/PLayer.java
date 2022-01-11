package com.storms.blast.main.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import static com.storms.blast.main.Constant.PLAYER_WIDTH;
import static com.storms.blast.main.Constant.WALL_X;
import static com.storms.blast.main.Constant.adaptiveVarY;

import com.storms.blast.main.Constant;

public class PLayer {

    private Bitmap bitmap;
    private Paint paint = new Paint();

    private float frameWidth;
    private float frameHeight;

    private float x;
    private float y;
    private float velocityX;
    private float velocityY;
    private final float speed = adaptiveVarY(50);
    private float addedVelocity = 0;

    private float givenVelocityX;
    private float givenVelocityY;

    public PLayer(Bitmap bitmap, float positionX, float positionY, float height, float width) {
        this.bitmap = bitmap;
        x = positionX;
        y = positionY;
        velocityX = 0;
        velocityY = 0;
        this.frameWidth = width;
        this.frameHeight = height;
    }

    public void update(int ms, float h) {
        if ((addedVelocity < 0 && velocityX > 0 && velocityX <= speed / 4f) || (addedVelocity > 0 && velocityX < 0 && velocityX >= -speed / 4f)) {
            addedVelocity = 0;
            velocityX = 0;
        }
        if ((velocityX <= 200) && (velocityX >= -200)) {
            velocityX += addedVelocity;
        } else if (velocityX > 200) {
            velocityX = 200;
        } else if (velocityX < -200) {
            velocityX = -200;
        }

        x = x + velocityX * ms / 1000.0f;
        y = h;
    }

    public void addVelocity(double addVelocity) {
        if (addVelocity > 0) {
            addedVelocity = speed;
        } else if (addVelocity < 0) {
            addedVelocity = -speed;
        }
    }

    public void stop() {
        if (velocityX > 0) {
            addedVelocity = -speed / 4f;
        } else if (velocityX < 0) {
            addedVelocity = speed / 4f;
        }
    }

    public void draw(Canvas canvas) {
        Rect destination = new Rect((int) x, (int) y, (int) (x + frameWidth), (int) (y + frameHeight));
        canvas.drawBitmap(bitmap, null, destination, paint);
    }

    public Rect getBoundingBoxRect() {
        return new Rect((int) (x), (int) (y + Constant.adaptiveVarY(100)), (int) (x + frameWidth), (int) (y + frameHeight));
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public float getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(float frameWidth) {
        this.frameWidth = frameWidth;
    }

    public float getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(float frameHeight) {
        this.frameHeight = frameHeight;
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

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public float getGivenVelocityX() {
        return givenVelocityX;
    }

    public void setGivenVelocityX(float givenVelocityX) {
        this.givenVelocityX = givenVelocityX;
    }

    public float getGivenVelocityY() {
        return givenVelocityY;
    }

    public void setGivenVelocityY(float givenVelocityY) {
        this.givenVelocityY = givenVelocityY;
    }
}
