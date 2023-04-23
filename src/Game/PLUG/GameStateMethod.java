package Game.PLUG;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Game.PLUG.gameDrawer.GameAnimatedDrawer;

/**
 * This code is defining a Java interface named `GameStateMethod` that extends
 * four other interfaces:
 * `GameAnimatedDrawer`, `MouseListener`, `MouseMotionListener`, and
 * `KeyListener`. By extending these
 * interfaces, `GameStateMethod` inherits their methods and requires any class
 * that implements it to
 * also implement those methods. This interface is likely used to define the
 * behavior of a game state
 * in a larger game application.
 */
public interface GameStateMethod extends GameAnimatedDrawer, MouseListener, MouseMotionListener, KeyListener {

}
