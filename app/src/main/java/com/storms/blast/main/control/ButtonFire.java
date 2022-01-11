package com.storms.blast.main.control;

import android.graphics.Bitmap;

public class ButtonFire extends AbstractButton {

    public boolean isFiring = false;
    public int timer = 0;

    public ButtonFire(Bitmap bitmap, float positionX, float positionY) {
        super(bitmap, positionX, positionY);
    }
    public void update(){
        if (isFiring){
            timer += 1;
        }
        if (timer > 80){
            timer = 80;
        }
        if (!isFiring){
            timer = 0;
        }
    }
}
