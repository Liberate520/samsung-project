package com.storms.blast.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.storms.blast.R;
import com.storms.blast.main.Utils.Bitmaps;
import com.storms.blast.main.control.ButtonDown;
import com.storms.blast.main.control.ButtonFire;
import com.storms.blast.main.control.ButtonLeft;
import com.storms.blast.main.control.ButtonRight;
import com.storms.blast.main.control.ButtonUp;
import com.storms.blast.main.entities.Block;
import com.storms.blast.main.entities.HealthBar;
import com.storms.blast.main.entities.QueueShoot;
import com.storms.blast.main.entities.Gun;
import com.storms.blast.main.entities.Rocket;
import com.storms.blast.main.entities.PLayer;
import com.storms.blast.main.entities.Wall;
import com.storms.blast.main.entities.Water;

import java.util.ArrayList;
import java.util.List;

import static com.storms.blast.main.Constant.BLOCK_HEIGHT;
import static com.storms.blast.main.Constant.BLOCK_WIDTH;
import static com.storms.blast.main.Constant.BUTTON_DOWN_X;
import static com.storms.blast.main.Constant.BUTTON_DOWN_Y;
import static com.storms.blast.main.Constant.BUTTON_FIRE_X;
import static com.storms.blast.main.Constant.BUTTON_FIRE_Y;
import static com.storms.blast.main.Constant.BUTTON_LEFT_X;
import static com.storms.blast.main.Constant.BUTTON_LEFT_Y;
import static com.storms.blast.main.Constant.BUTTON_RIGHT_X;
import static com.storms.blast.main.Constant.BUTTON_RIGHT_Y;
import static com.storms.blast.main.Constant.BUTTON_UP_X;
import static com.storms.blast.main.Constant.BUTTON_UP_Y;
import static com.storms.blast.main.Constant.GUN_SIDE_X;
import static com.storms.blast.main.Constant.GUN_SIDE_Y;
import static com.storms.blast.main.Constant.HEALTH_HEIGHT;
import static com.storms.blast.main.Constant.HEALTH_WIDTH;
import static com.storms.blast.main.Constant.HEALTH_X;
import static com.storms.blast.main.Constant.HEALTH_Y;
import static com.storms.blast.main.Constant.PLAYER_X;
import static com.storms.blast.main.Constant.PLAYER_Y;
import static com.storms.blast.main.Constant.WALL_HEIGHT;
import static com.storms.blast.main.Constant.WALL_WIDTH;
import static com.storms.blast.main.Constant.WALL_X;
import static com.storms.blast.main.Constant.WALL_Y;
import static com.storms.blast.main.Constant.WATER_HEIGHT;
import static com.storms.blast.main.MainActivity.screenHeight;
import static com.storms.blast.main.MainActivity.screenWidth;
import static com.storms.blast.main.Constant.PLAYER_HEIGHT;
import static com.storms.blast.main.Constant.PLAYER_WIDTH;
import static com.storms.blast.main.Utils.Bitmaps.choiceRocketColor;
import static com.storms.blast.main.Utils.Bitmaps.getButtonDownBitmap;
import static com.storms.blast.main.Utils.Bitmaps.getButtonFireBitmap;
import static com.storms.blast.main.Utils.Bitmaps.getButtonLeftBitmap;
import static com.storms.blast.main.Utils.Bitmaps.getButtonRightBitmap;
import static com.storms.blast.main.Utils.Bitmaps.getButtonUpBitmap;
import static com.storms.blast.main.Utils.Bitmaps.getGunBitmap;
import static com.storms.blast.main.Utils.Bitmaps.getStormBitmap;
import static com.storms.blast.main.Utils.Bitmaps.getWallBitmap;
import static com.storms.blast.main.Utils.Bitmaps.getWaterBitmap;

public class Game extends View {

    public static List<String> commands = new ArrayList<>();

    private int healthOpponent;
    private PLayer player;
    private PLayer opponentPLayer;
    private Wall wall;
    private ButtonLeft buttonLeft;
    private ButtonRight buttonRight;
    private ButtonUp buttonUp;
    private ButtonDown buttonDown;
    private ButtonFire buttonFire;
    private Gun gun;
    private Gun opponentGun;
    private ArrayList<Rocket> bullets = new ArrayList<>();
    private ArrayList<Rocket> opponentBullets = new ArrayList<>();
    private Water waterL;
    private Water waterR;
    private QueueShoot queueShoot;
    private List<Block> blocks = new ArrayList<>();
    private HealthBar healthBar;
    private boolean firstLine;

    private final int timerInterval = 30;
    private final int timeToLine = 400;
    private int timeLine = 0;

    Bitmap bgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
    Rect destination = new Rect(0, 0, screenWidth, screenHeight);
    Paint paint = new Paint();

    private boolean firstPlayer = true;

    public Game(Context context) {
        super(context);

        Bitmaps bitmaps = new Bitmaps(context);

        player = new PLayer(getStormBitmap(), PLAYER_X, PLAYER_Y, PLAYER_HEIGHT, PLAYER_WIDTH);
        opponentPLayer = new PLayer(getStormBitmap(), screenWidth - PLAYER_X - PLAYER_WIDTH, PLAYER_Y, PLAYER_HEIGHT, PLAYER_WIDTH);
        wall = new Wall(getWallBitmap(), WALL_X, WALL_Y, WALL_HEIGHT, WALL_WIDTH);
        buttonLeft = new ButtonLeft(getButtonLeftBitmap(), BUTTON_LEFT_X, BUTTON_LEFT_Y);
        buttonRight = new ButtonRight(getButtonRightBitmap(), BUTTON_RIGHT_X, BUTTON_RIGHT_Y);
        buttonUp = new ButtonUp(getButtonDownBitmap(), BUTTON_UP_X, BUTTON_UP_Y);
        buttonDown = new ButtonDown(getButtonUpBitmap(), BUTTON_DOWN_X, BUTTON_DOWN_Y);
        buttonFire = new ButtonFire(getButtonFireBitmap(), BUTTON_FIRE_X, BUTTON_FIRE_Y);
        gun = new Gun(getGunBitmap());
        opponentGun = new Gun(getGunBitmap());
        waterL = new Water(getWaterBitmap(), 0);
        waterR = new Water(getWaterBitmap(), screenWidth/2);
        queueShoot = new QueueShoot(this, BUTTON_FIRE_X, BUTTON_FIRE_Y);
        healthBar = new HealthBar(HEALTH_X, HEALTH_Y, HEALTH_WIDTH, HEALTH_HEIGHT);
        healthOpponent = 4;
        commands = new ArrayList<>();

        commands.add("id " + MainActivity.android_id);

        firstLine = true;
    } //распределяет все битмапы и распологает обьекты на сцене

    public void startGame(boolean firstPlayer){
        this.firstPlayer = firstPlayer;
        if (firstPlayer) {
            createLine();
            createLine();
            createLine();
            createLine();
            createLine();
        }
        if (!firstPlayer){
            player.setX(screenWidth - PLAYER_X - PLAYER_WIDTH);
            opponentPLayer.setX(PLAYER_X);
            waterL.setX(screenWidth/2f);
            waterR.setX(0);
        }
        Timer t = new Timer();
        t.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: // если коснулся
                if (buttonLeft.isTouched(event.getX(), event.getY())) {
                    player.addVelocity(-1);
                } else if (buttonRight.isTouched(event.getX(), event.getY())) {
                    player.addVelocity(1);
                } else if (buttonUp.isTouched(event.getX(), event.getY())) {
                    gun.setRotate(5);
                    gun.setAngle(gun.getAngle() + 5);
                } else if (buttonDown.isTouched(event.getX(), event.getY())) {
                    gun.setRotate(-5);
                } else if (buttonFire.isTouched(event.getX(), event.getY())) {
                    buttonFire.isFiring = true;
                }
                return true;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (buttonLeft.isTouched(event.getX(1), event.getY(1))) {
                    player.addVelocity(-1);
                } else if (buttonRight.isTouched(event.getX(1), event.getY(1))) {
                    player.addVelocity(1);
                } else if (buttonUp.isTouched(event.getX(1), event.getY(1))) {
                    gun.setRotate(5);
                    gun.setAngle(gun.getAngle() + 5);
                } else if (buttonDown.isTouched(event.getX(1), event.getY(1))) {
                    gun.setRotate(-5);
                } else if (buttonFire.isTouched(event.getX(1), event.getY(1))) {
                    buttonFire.isFiring = true;
                }
                return true;
            case MotionEvent.ACTION_POINTER_UP:
                if (buttonFire.isTouched(event.getX(1), event.getY(1)) && buttonFire.isFiring){
                    shoot();
                } else {
                    player.stop();
                    gun.setRotate(0);
                }
                return true;
            case MotionEvent.ACTION_UP: // палец поднят
                if (buttonFire.isFiring){
                    shoot();
                } else {
                    player.stop();
                    gun.setRotate(0);
                }
                return true;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bgBitmap, null, destination, paint);

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(canvas);
        }
//
        for (int i = 0; i < opponentBullets.size(); i++) {
            opponentBullets.get(i).draw(canvas);
        }

        if (blocks.size() != 0) {
            for (int i = 0; i < blocks.size(); i++) {
                blocks.get(i).draw(canvas);
            }
        }
        player.draw(canvas);
        opponentPLayer.draw(canvas);
        gun.draw(canvas);
        opponentGun.draw(canvas);
        waterL.draw(canvas);
        waterR.draw(canvas);
        buttonFire.draw(canvas);
        buttonLeft.draw(canvas);
        buttonRight.draw(canvas);
        buttonUp.draw(canvas);
        buttonDown.draw(canvas);
        wall.draw(canvas);
        queueShoot.draw(canvas);
        healthBar.draw(canvas);
    }

    public void update() {
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).getBoundingBoxRect().intersect(opponentPLayer.getBoundingBoxRect())){
                Log.d("My", "было касание блока");
                commands.add("win 11");
                break;
            }
        }
        buttonFire.update();
        player.update(timerInterval, waterL.getY() - WATER_HEIGHT/5f);
        if (player.getBoundingBoxRect().intersect(wall.getBoundingBoxRect())){
            if (firstPlayer){
                player.setX(WALL_X - PLAYER_WIDTH);
            } else {
                player.setX(WALL_X + WALL_WIDTH);
            }
        }
        if (firstPlayer){
            if (player.getX()<0){
                player.setX(0);
            }
        } else {
            if (player.getX() + PLAYER_WIDTH > screenWidth){
                player.setX(screenWidth - PLAYER_WIDTH);
            }
        }
        opponentPLayer.setY(waterR.getY() - WATER_HEIGHT/5f);

        gun.update(player.getX(), player.getY());
        opponentGun.update(opponentPLayer.getX(), opponentPLayer.getY());
        if (bullets.size() != 0) {
            for (int i = 0; i < bullets.size(); i++) {

                Rocket rocket = bullets.get(i);

                if (rocket.getX() > screenWidth || rocket.getX() < -Constant.BULLET_SIDE_X || rocket.getY() > screenHeight){
                    bullets.remove(i);
                    continue;
                }
                if (rocket.getBoundingBoxRect().intersect(wall.getBoundingBoxRect())){
                    rocket.setSpeedX(-rocket.getSpeedX());
                }
                //отражение
                for (Block block : blocks) {
                    if (rocket.getBoundingBoxRect().intersect(block.getBoundingBoxRect())) {
                        Log.d("My", ""+ rocket.getColor() + "  " + block.getColor());
                        if (block.getColor() == rocket.getColor()) {
                            MainActivity.playerPop.start();
                            bullets.remove(i);
                            deleteAllNeighbours(block);
                            findAllWithoutAnyNeighbours();
                            break;
                        } else {
                            bullets.remove(i);
                            break;
                        }
                    }
                }
                if (rocket.getBoundingBoxRect().intersect(opponentPLayer.getBoundingBoxRect())){
                    MainActivity.playerDamage.start();
                    bullets.remove(i);
                    healthOpponent--;
                    if (healthOpponent<1){
                        commands.add("win 11");
                    }
                    continue;
                }
                rocket.update();
            }
        }
        if (opponentBullets.size() != 0) {
            for (int i = 0; i < opponentBullets.size(); i++) {

                Rocket rocket = opponentBullets.get(i);

                if (rocket.getX() > screenWidth || rocket.getX() < -Constant.BULLET_SIDE_X || rocket.getY() > screenHeight){
                    opponentBullets.remove(i);
                    continue;
                }
                if (rocket.getBoundingBoxRect().intersect(wall.getBoundingBoxRect())){
                    rocket.setSpeedX(-rocket.getSpeedX());
                }
                //отражение
                for (Block block : blocks) {
                    if (rocket.getBoundingBoxRect().intersect(block.getBoundingBoxRect())) {
                        Log.d("My", ""+ rocket.getColor() + "  " + block.getColor());
                        if (block.getColor() == rocket.getColor()) {
                            MainActivity.playerPop.start();
                            opponentBullets.remove(i);
                            deleteAllNeighbours(block);
                            findAllWithoutAnyNeighbours();
                            break;
                        } else {
                            opponentBullets.remove(i);
                            break;
                        }
                    }
                }
                if (rocket.getBoundingBoxRect().intersect(player.getBoundingBoxRect())){
                    MainActivity.playerDamage.start();
                    opponentBullets.remove(i);
                    healthBar.setHealth(healthBar.getHealth()-1);
                    continue;
                }
                rocket.update();
            }
        }
        waterL.update();
        waterR.update();

        if (player.getVelocityX() != 0){
            commands.add("plPos " + player.getX() / (screenWidth / 100f) );
        }
        if (gun.getRotate() != 0){
            commands.add("gunRot " + gun.getAngle());
        }

        timeLine++;
        if (timeLine >= timeToLine && firstPlayer){
            timeLine = 0;
            createLine();
        }
        invalidate();
    }

    public void shoot(){
        int color = queueShoot.getList().get(0);
        Rocket rocket = new Rocket(choiceRocketColor(color), buttonFire.timer, gun.getAngle(), gun.getX() + GUN_SIDE_X /2f, gun.getY()+ GUN_SIDE_Y /2f, color);
        bullets.add(rocket);
        buttonFire.isFiring = false;
        queueShoot.next();
        commands.add("shoot " + color + " " + buttonFire.timer + " " + gun.getAngle() + " " + Constant.readaptiveVarX(gun.getX() + GUN_SIDE_X /2f) + " " + Constant.readaptiveVarY(gun.getY()+ GUN_SIDE_Y /2f));
    }

    public void createLine(){
        if (blocks.size() != 0){
            for (Block block:blocks){
                block.setY(block.getY() + BLOCK_HEIGHT/2f);
            }
        }
        String command = "crLine";
        if (firstLine){
            for (int i = 0; i < 18; i++){
                Block block = new Block(i*3*BLOCK_WIDTH/2f, 0, (int)(Math.random()*3));
                blocks.add(block);
                command += " " + block.getColor();
            }
        } else {
            for (int i = 0; i < 17; i++){
                Block block = new Block(i*3*BLOCK_WIDTH/2f + BLOCK_WIDTH*3f/4f, 0, (int)(Math.random()*3));
                blocks.add(block);
                command += " " + block.getColor();
            }
        }
        commands.add(command);
        firstLine = !firstLine;
    }

    public void createLine(int[] colors){
        if (blocks.size() != 0){
            for (Block block:blocks){
                block.setY(block.getY() + BLOCK_HEIGHT/2f);
            }
        }
        if (firstLine){
            for (int i = 0; i < 18; i++){
                blocks.add(new Block(i*3*BLOCK_WIDTH/2f, 0, colors[i]));
            }
        } else {
            for (int i = 0; i < 17; i++){
                blocks.add(new Block(i*3*BLOCK_WIDTH/2f + BLOCK_WIDTH*3/4f, 0, colors[i]));
            }
        }
        firstLine = !firstLine;
    }

    private void deleteAllNeighbours(Block block) {
        ArrayList<Block> neighbours = new ArrayList<>();
        for (Block neighbour : blocks) {
            // если совпадает цвет
            if (block.getColor() == neighbour.getColor() && isNeighbour(block, neighbour)) {
                neighbours.add(neighbour);
            }
        }
        blocks.removeAll(neighbours);
        for (Block neighbour : neighbours) {
            deleteAllNeighbours(neighbour);
        }
        blocks.remove(block);
    }

    private boolean isNeighbour(Block block, Block neighbour) {
        // сосед справа
        if (Math.abs(block.getX() + BLOCK_WIDTH - neighbour.getX()) < 5
            && Math.abs(block.getY() - neighbour.getY()) < 5) {
            return true;
        }
        // сосед слева
        if (Math.abs(block.getX() - BLOCK_WIDTH - neighbour.getX()) < 5
                && Math.abs(block.getY() - neighbour.getY()) < 5) {
            return true;
        }
        // сосед сверху
        if (Math.abs(block.getX() - neighbour.getX()) < 5
                && Math.abs(block.getY() + BLOCK_HEIGHT - neighbour.getY()) < 5) {
            return true;
        }
        // сосед снизу
        if (Math.abs(block.getX() - neighbour.getX()) < 5
                && Math.abs(block.getY() - BLOCK_HEIGHT - neighbour.getY()) < 5) {
            return true;
        }
        // сосед сверху справа
        if (Math.abs(block.getX() + 3 * BLOCK_WIDTH / 4f - neighbour.getX()) < 5
                && Math.abs(block.getY() - BLOCK_HEIGHT / 2f - neighbour.getY()) < 5) {
            return true;
        }
        // сосед сверху слева
        if (Math.abs(block.getX() - 3 * BLOCK_WIDTH / 4f - neighbour.getX()) < 5
                && Math.abs(block.getY() - BLOCK_HEIGHT / 2f - neighbour.getY()) < 5) {
            return true;
        }
        // сосед снизу справа
        if (Math.abs(block.getX() + 3 * BLOCK_WIDTH / 4f - neighbour.getX()) < 5
                && Math.abs(block.getY() + BLOCK_HEIGHT / 2f - neighbour.getY()) < 5) {
            return true;
        }
        // сосед снизу слева
        if (Math.abs(block.getX() - 3 * BLOCK_WIDTH / 4f - neighbour.getX()) < 5
                && Math.abs(block.getY() + BLOCK_HEIGHT / 2f - neighbour.getY()) < 5) {
            return true;
        }
        return false;
    }

    private boolean isAnyNeighbours(Block block) {
        for (Block neighbour : blocks) {
            if (isNeighbour(block, neighbour)) {
                return true;
            }
        }
        return false;
    }

    private void findAllWithoutAnyNeighbours() {
        blocks.removeIf(block -> !isAnyNeighbours(block) && block.getY() != 0);
    }

    public PLayer getOpponentPLayer() {
        return opponentPLayer;
    }

    public Gun getOpponentGun() {
        return opponentGun;
    }

    public ArrayList<Rocket> getOpponentBullets() {
        return opponentBullets;
    }

    class Timer extends CountDownTimer {

        public Timer() {
            super(Integer.MAX_VALUE, timerInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            update ();
        }

        @Override
        public void onFinish() {

        }
    }

}
