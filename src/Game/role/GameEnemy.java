package Game.role;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import Game.role.ABC.GameEnemyABC;

import static base.BaseGameConstant.TILES_SIZE;

public class GameEnemy extends GameEnemyABC {

    public static final int levelDataID = 0;
    private static float drawXOffset;

    public GameEnemy() {
        super();
    }

    public GameEnemy(String folderName, float x, float y, int enemyType) {
        super(enemyType);
        this.setXY(x, y);

        try {
            this.setAnimationImage(folderName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameEnemy(float x, float y, BufferedImage[][] image) {
        super();
        this.setXY(x, y);
        this.setAnimation(image);
    }

    public static void passOffset(float xOffset) {
        GameEnemy.drawXOffset = xOffset;
    }

    @Override
    public void update() {
        super.update();

    }

    @Override
    public void render(Graphics g) {
        var nowImage = this.getAnimationImage(this.gameCharacterState, this.aniIndex);
        var fromPoint = this.point.toIntPoint();

        g.drawImage(nowImage,
                (int) (fromPoint.x - GameEnemy.drawXOffset), fromPoint.y,
                TILES_SIZE, TILES_SIZE,
                null);

        this.drawHitBox(g, GameEnemy.drawXOffset);
    }

    @Override
    public void setAnimationImage() {

    }

    @Override
    public void setAnimationState() {

    }

    @Override
    public void updatePosition() {

    }

}
