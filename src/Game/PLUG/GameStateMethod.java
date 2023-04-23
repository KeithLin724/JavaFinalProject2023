package Game.PLUG;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Game.PLUG.gameDrawer.GameAnimatedDrawer;

public interface GameStateMethod extends GameAnimatedDrawer, MouseListener, MouseMotionListener, KeyListener {

}
