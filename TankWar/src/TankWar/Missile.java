package TankWar;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author 杨文燕
 * 学号：031702411
 * 这个类是子弹类
 * 
 */
public class Missile {
	public static final int XSPEED=10;//子弹沿X轴移动方向
	public static final int YSPEED=10;//子弹沿X轴移动方向
	
	public static final int WIDTH=10;//子弹宽度
	public static final int HEIGHT=10;//子弹高度
	
	int x,y;//子弹位置
	Direction dir;//子弹方向
	
	private boolean good;//子弹与坦克的统一类型
	
	private boolean live=true;//子弹的生命
	
	TankClient tc;//TankClient的引用
	
	private static Toolkit tk=Toolkit.getDefaultToolkit();
	private static Image[] missileImages=null;
	private static Map<String,Image> imgs=new HashMap<String,Image>();
	
	static {
		missileImages=new Image[] {
				tk.getImage(Tank.class.getClassLoader().getResource("images/MissileU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/MissileL.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/MissileR.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/MissileD.gif"))
		};
		
		imgs.put("L", missileImages[1]);
		imgs.put("R", missileImages[2]);
		imgs.put("U", missileImages[0]);
		imgs.put("D", missileImages[3]);
	}
	
	public boolean isLive() {//获取live
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	public boolean isGood() {
		return good;
	}

	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	public Missile(int x, int y,boolean good, Direction dir,TankClient tc) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tc=tc;
		this.good=good;
	}
	/**
	 * 这个方法是画出子弹
	 * @param g  窗口的画笔
	 */
	public void draw(Graphics g) {
		if(!live) {
			tc.missiles.remove(this);
			return;
		}
		
		switch(dir){
		case L:
			g.drawImage(imgs.get("L"),x,y,null);
			break;
		case R:
			g.drawImage(imgs.get("R"),x,y,null);
			break;
		case U:
			g.drawImage(imgs.get("U"),x,y,null);
			break;
		case D:
			g.drawImage(imgs.get("D"),x,y,null);
			break;
		case STOP:
			break;
	}
		
		move();
	}
	/**
	 * 这个方法是子弹的移动
	 */
	void move() {
		switch(dir){
			case L:
				x-=XSPEED;
				break;
			case R:
				x+=XSPEED;
				break;
			case U:
				y-=YSPEED;
				break;
			case D:
				y+=YSPEED;
				break;
		}
		if(x<0||y<0||x>TankClient.GAME_WIDTH||y>TankClient.GAME_HEIGHT) {//当子弹飞出窗口，子弹消失，不在画出该子弹
			live=false;
		}
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	/**
	 * 这个方法是打击坦克，打中子弹与坦克消失
	 * @param t   打击的坦克
	 * @return    是否打中，若是返回true，否则false
	 */
	public boolean hitTank(Tank t) {
		if(this.live&&this.getRect().intersects(t.getRect())&&t.isLive()&&this.good!=t.isGood()) {
				t.setLife(t.getLife()-1);
				if(t.getLife()<=0)
					t.setLive(false);
				else {
					if(t.isGood()) {
						t.setX(400);
						t.setY(600);
					}
				}
	
			this.live=false;
			Explode e=new Explode(x,y,tc);
			tc.explodes.add(e);
			return true;
		}
		return false;
	}
	
	public boolean hitTank(List<Tank> tanks) {
		for(int i=0;i < tanks.size();i++)
		{
			if(hitTank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 这个方法是撞击墙体，撞到墙体则子弹消失
	 * @param w   撞击到的墙体
	 * @return    是否撞到墙体，若是返回true，否则返回false
	 */
	public boolean hitWall(Wall w) {
		if(this.live&&this.getRect().intersects(w.getRect())) {
			this.live=false;
			return true;
		}
		return false;
	}
	/**
	 * 这个方法是判断撞击子弹，如敌我子弹相撞，两发子弹都消失
	 * @param w   撞击到的墙体
	 * @return    是否撞到墙体，若是返回true，否则返回false
	 */
	public boolean hitMissile(Missile m) {
		if(this.live&&this.getRect().intersects(m.getRect())&&this.good!=m.isGood()&&this!=m&&m.isLive()) {
			this.live=false;
			m.setLive(false);
			return true;
		}
		return false;
	}
	public boolean hitMissile(List<Missile> missiles) {
		for(int i=0;i < missiles.size();i++)
		{
			if(hitMissile(missiles.get(i))) {
				return true;
			}
		}
		return false;
	}
}
