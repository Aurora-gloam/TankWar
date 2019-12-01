package TankWar;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * 
 * @author 杨文燕
 * 学号：031702411
 * 这个类是坦克子弹类
 * 
 */

public interface Tank {
	/**
	 * 这个方法是画出坦克
	 * @param g  窗口的画笔
	 */
	public void draw(Graphics g);
	/**
	 * 这个方法是坦克的移动
	 */
	public void move();
	public void stay() ;
	public void setY(int y);

	public void setX(int x);
	
	
	void locateDirection();

	public boolean isLive();
	public void setLive(boolean live);

	/**
	 * 这个方法是坦克发射子弹
	 * @return  返回发射出的子弹
	 */
	public Missile fire();
	
	public Rectangle getRect();
	public boolean isGood() ;
	/**
	 * 这个方法是用来判断坦克是否撞击到墙体
	 * @param w   撞击的墙体
	 * @return    是否撞击到墙体，若是返回true，否则返回false
	 */
	public boolean collideaWithWall(java.util.List<Wall> walls);
	/**
	 * 这个方法是用来判断坦克是否撞击到坦克
	 * @param w   撞击的坦克
	 * @return    是否撞击到坦克，若是返回true，否则返回false
	 */
	public boolean collideaWithTank(java.util.List<Tank> tanks) ;
	
	public int getLife() ;

	public void setLife(int life) ;
	/**
	 * 这个方法是判断是否吃到增长生命的道具，若吃到生命值进行增加
	 * @param b  增长生命值的道具
	 * @return   是否吃到道具，若是返回true，否则返回false
	 */
	public boolean eat(Blood b) ;
	/**
	 * 这个方法是当抬起键盘不再改变坦克方向
	 * 以及抬起空格 键时发射子弹
	 * @param e
	 */
	public void keyReleased(KeyEvent e);
	/**
	 * 
	 * 这个方法是通过按下键盘改变坦克方向
	 */
	public void keyPressed(KeyEvent e);
	
}
