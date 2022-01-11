package com.storms.blast.main.entities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class HealthBar {
    private int health = 4;
    private final int maxHealth = 4;
    private float x, y;
    private float width, height;
    private Paint paintBound, paintFront, paintBack;

    public HealthBar(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        paintBound = new Paint();
        paintBound.setColor(Color.BLACK);
        paintFront = new Paint();
        paintFront.setColor(Color.RED);
        paintBack = new Paint();
        paintBack.setColor(Color.GRAY);
    }

    public void draw(Canvas canvas){
        canvas.drawRect(x, y, x + width, y+ height, paintBound);
        canvas.drawRect(x+2, y+2, x + width -2, y+ height-2, paintBack);
        canvas.drawRect(x+2, y+2, (x + width -2)*health/maxHealth, y+ height-2, paintFront);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
