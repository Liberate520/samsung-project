package com.storms.blast.main.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import static com.storms.blast.main.Constant.GUN_SIDE_X;
import static com.storms.blast.main.Constant.GUN_SIDE_Y;
import static com.storms.blast.main.Constant.PLAYER_X;
import static com.storms.blast.main.Constant.PLAYER_Y;

import java.io.Serializable;

public class Gun implements Serializable {

    transient private Bitmap bitmap;
    private Paint paint = new Paint();

    private float frameWidth;
    private float frameHeight;

    transient Matrix position;

    private float x;
    private float y;
    private int angle = 0;
    private int rotate = 0;

    public Gun(Bitmap bitmap) {
        this.bitmap = bitmap;
        x = PLAYER_X/2 - GUN_SIDE_X /2;
        y = PLAYER_Y + GUN_SIDE_Y /2;
        this.frameWidth = GUN_SIDE_X;
        this.frameHeight = GUN_SIDE_Y;
        position = new Matrix();
    }

    public void update(float x, float y){
        Matrix m = new Matrix();
        angle += rotate;
        m.postRotate(angle, bitmap.getWidth()/2f, bitmap.getHeight()/2f);
        m.postTranslate(x + GUN_SIDE_X /2f, this.y);
        position.set(m);
        this.x = x + GUN_SIDE_X /2f;
        this.y = y + GUN_SIDE_Y /2f;
        if (angle > 90){
            angle = 90;
        }
        else if (angle < -90){
            angle = -90;
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, position, paint);
    }

    public Rect getBoundingBoxRect(){
        return new Rect((int)(x), (int)(y), (int)(x+frameWidth), (int)(y+frameHeight));
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

    public int getRotate() {
        return rotate;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
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

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        Matrix m = new Matrix();
        this.angle = angle;
        m.postRotate(angle, (int)(bitmap.getWidth()/2), (int)(bitmap.getHeight()/2));
        m.postTranslate((float)(x + GUN_SIDE_X /2), (float)(this.y));
        position.set(m);
    }
}