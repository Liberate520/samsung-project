package com.storms.blast.main.control;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import static com.storms.blast.main.Constant.BUTTON_SIDE_X;
import static com.storms.blast.main.Constant.BUTTON_SIDE_Y;

abstract class AbstractButton {

    private Bitmap bitmap;

    private final float x;
    private final float y;

    public AbstractButton(Bitmap bitmap, float positionX, float positionY) {
        this.bitmap = bitmap;
        x = positionX;
        y = positionY;
    }

    public boolean isTouched(float touchX, float touchY){
        return ((touchX >= x)&&(touchX <= x + BUTTON_SIDE_X))&&((touchY >= y)&&(touchY <= y + BUTTON_SIDE_Y));
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        Rect destination = new Rect((int)x, (int)y, (int) (x+BUTTON_SIDE_X), (int) (y+BUTTON_SIDE_Y));
        canvas.drawBitmap(bitmap, null, destination, paint);
    }
}
