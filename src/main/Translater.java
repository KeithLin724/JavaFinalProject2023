package main;
import java.awt.Graphics;
import entities.Player;

public class Translater extends Online{

    private Player player;

    public Translater() {
        player = new Player(200, 200);
    }

    public void updateLogic(){
        player.update();
    }

    public void render(Graphics g){
        player.render(g);
    }

    public Player getPlayer(){
        return player;
    }


}
