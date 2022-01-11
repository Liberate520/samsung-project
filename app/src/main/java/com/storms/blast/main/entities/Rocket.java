package com.storms.blast.main.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import static com.storms.blast.main.Constant.BULLET_SIDE_X;
import static com.storms.blast.main.Constant.BULLET_SIDE_Y;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import com.storms.blast.main.Constant;

public class Rocket {

    transient private Bitmap bitmap;
    private Paint paint = new Paint();
    private int color;
    private int power;
    private float speedX;
    private float speedY;
    private int angle = 0;
    private final float g = Constant.GRAVITY;

    transient Matrix position;

    private float x;
    private float y;

    public Rocket(Bitmap bitmap, int power, int angle, float x, float y, int color) {
        this.bitmap = bitmap;
        this.x = x - BULLET_SIDE_X;
        this.y = y - BULLET_SIDE_Y;
        position = new Matrix();
        this.power = power + 25;
        this.angle = angle - 90;
        speedX = (float) cos(toRadians(this.angle))*this.power;
        speedY = (float) sin(toRadians(this.angle))*this.power;
        speedX = Constant.adaptiveVarX(speedX);
        speedY = Constant.adaptiveVarY(speedY);
        this.color = color;
    }

    public void update(){
        Matrix m = new Matrix();
        speedY += g;
        x = x + speedX;
        y = y + speedY;
        m.postTranslate(x + BULLET_SIDE_X /2f, y + BULLET_SIDE_Y /2f);
        position.set(m);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, position, paint);
    }

    public Rect getBoundingBoxRect(){
        return new Rect((int)(x), (int)(y), (int)(x+ BULLET_SIDE_X), (int)(y+ BULLET_SIDE_Y));
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Matrix getPosition() {
        return position;
    }

    public void setPosition(Matrix position) {
        this.position = position;
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

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public float getG() {
        return g;
    }

    public int getColor() {
        return color;
    }

}
