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

public class Tank {
	//坦克移动速度
	public static final int XSPEED=5;
	public static final int YSPEED=5;
	//坦克的生命状态
	private boolean live=true;
	private int life=2;//坦克的生命值,普通坦克为2，敏捷型坦克为1，重型坦克为3

	TankClient tc;
	
	private boolean good;//判断敌我坦克，true为我方坦克，false为敌方坦克
	
	//坦克位置
	private int x,y;
	private int oldX,oldY;//坦克上一步的位置
	
	private static Random r=new Random();
	
	private boolean bL=false,bU=false,bR=false,bD=false;//键盘按键情况
	
	private Direction dir=Direction.STOP;//坦克状态起始为STOP
	private Direction ptDir;//炮筒的方向
	
	private int step=r.nextInt(12)+3;//存储3-15的一个随机数
	
	private static Toolkit tk=Toolkit.getDefaultToolkit();
	private static Image[] tankImages=null;
	private static Map<String,Image> imgs=new HashMap<String,Image>();
	
	static {
		tankImages=new Image[] {
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankL.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankR.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/tankD.gif"))
		};
		
		imgs.put("L", tankImages[1]);
		imgs.put("R", tankImages[2]);
		imgs.put("U", tankImages[0]);
		imgs.put("D", tankImages[3]);
	}
	
	public Tank(int x,int y,boolean good) {
		this.x=x;
		this.y=y;
		this.good=good;
		if(good)ptDir=Direction.U;
		else ptDir=Direction.D;
		this.oldX=x;
		this.oldY=y;
	}
	
	public Tank(int x,int y,boolean good,Direction dir,TankClient tc) {
		this(x,y,good);
		this.tc=tc;
		this.dir=dir;
	}
	/**
	 * 这个方法是画出坦克
	 * @param g  窗口的画笔
	 */
	public void draw(Graphics g) {
		if(!live) {
			if(!good) {
				tc.tanks.remove(this);
			}
			return;
		}
		 
		 switch(ptDir){
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
	 * 这个方法是坦克的移动
	 */
	void move() {
		this.oldX=x;
		this.oldY=y;
		
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
			case STOP:
				break;
		}
		
		if(this.dir!=Direction.STOP) {
			this.ptDir=this.dir;
		}
		/*
		 * 坦克到达窗口边缘时停在原地
		 */
		if(x<0)x=0;
		if(y<30)y=30;
		if(x+tankImages[0].getWidth(null) > TankClient.GAME_WIDTH)
			x=TankClient.GAME_WIDTH-tankImages[0].getWidth(null);
		if(y+tankImages[0].getHeight(null)>TankClient.GAME_HEIGHT)
			y=TankClient.GAME_HEIGHT-tankImages[0].getHeight(null);
		/*
		 * 让敌方坦克在经过step次的重画后随机改变方向
		 */
		if(!good) {
			Direction[] dirs=Direction.values();
			if(step==0){
				step=r.nextInt(12)+3;
				int rn=r.nextInt(dirs.length);
				dir=dirs[rn];
			}
			step--;
			if(r.nextInt(40)>38)this.fire();//让敌方坦克随机发射子弹
		}
		
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	private void stay() {
		this.x=oldX;
		this.y=oldY;
	}
	/**
	 * 
	 * 这个方法是通过按下键盘改变坦克方向
	 */
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		if(key==KeyEvent.VK_RIGHT) {
			bR=true;
		}
		else if(key==KeyEvent.VK_LEFT) {
			bL=true;
		}
		else if(key==KeyEvent.VK_UP) {
			bU=true;
		}
		else if(key==KeyEvent.VK_DOWN) {
			bD=true;
		}
		else if(key==KeyEvent.VK_F2) {
			if(!this.live) {
				this.live=true;
				this.life=5;
				this.x=300;
				this.y=800;
			}
		}
		
		locateDirection();
	}
	
	void locateDirection() {
		if(bL&&!bR&&!bU&&!bD) dir=Direction.L;
		else if(!bL&&bR&&!bU&&!bD) dir=Direction.R;
		else if(!bL&&!bR&&bU&&!bD) dir=Direction.U;
		else if(!bL&&!bR&&!bU&&bD) dir=Direction.D;
		else if(!bL&&!bR&&!bU&&!bD) dir=Direction.STOP;
	}
/**
 * 这个方法是当抬起键盘不再改变坦克方向
 * 以及抬起ctrl 键时发射子弹
 * @param e
 */
	public void keyReleased(KeyEvent e) {
		int key=e.getKeyCode();
		if(key==KeyEvent.VK_RIGHT) {
			bR=false;
		}
		else if(key==KeyEvent.VK_LEFT) {
			bL=false;
		}
		else if(key==KeyEvent.VK_UP) {
			bU=false;
		}
		else if(key==KeyEvent.VK_DOWN) {
			bD=false;
		}
		else if(key==KeyEvent.VK_CONTROL) {
			fire();
		}
		locateDirection();
	}


	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	/**
	 * 这个方法是坦克发射子弹
	 * @return  返回发射出的子弹
	 */
	public Missile fire(){
		if(!live)return null;
		int x=this.x+tankImages[0].getWidth(null)/2-Missile.WIDTH/2;
		int y=this.y+tankImages[0].getHeight(null)/2-Missile.HEIGHT/2;
		Missile m=new Missile(x,y,good,ptDir,this.tc);
		tc.missiles.add(m);
		return m;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,tankImages[0].getWidth(null),tankImages[0].getHeight(null));
	}
	
	public boolean isGood() {
		return good;
	}
	/**
	 * 这个方法是用来判断坦克是否撞击到墙体
	 * @param w   撞击的墙体
	 * @return    是否撞击到墙体，若是返回true，否则返回false
	 */
	public boolean collidesWithWall(Wall w) {
		if(this.live&&this.getRect().intersects(w.getRect())) {
			this.stay();
			return true;
		}
		return false;
	}
	/**
	 * 这个方法是用来判断坦克是否撞击到坦克
	 * @param w   撞击的坦克
	 * @return    是否撞击到坦克，若是返回true，否则返回false
	 */
	public boolean collideaWithTank(java.util.List<Tank> tanks) {
		for(int i=0;i<tanks.size();i++) {
			Tank t=tanks.get(i);
			if(this!=t) {
				if(this.live&&t.isLive()&&this.getRect().intersects(t.getRect())) {
					this.stay();
					t.stay();
					return true;
				}
			}
		}
		return false;
	}
	

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	/**
	 * 这个方法是判断是否吃到增长生命的道具，若吃到生命值进行增加
	 * @param b  增长生命值的道具
	 * @return   是否吃到道具，若是返回true，否则返回false
	 */
	public boolean eat(Blood b) {
		if(this.live&&b.isLive()&&this.getRect().intersects(b.getRect())) {
			this.life+=1;
			b.setLive(false);
			return true;
		}
		return false;
	}
	
}
