package TankWar;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
/**
 * 
 * @author 杨文燕
 * 学号：031702411
 * 这个类是窗口的主体类
 *
 */
public class TankClient extends Frame{
	public static final int GAME_WIDTH=800;//窗口宽度
	public static final int GAME_HEIGHT=600;//窗口高度
	
	Tank myTank=new Tank(280,600,true,Direction.STOP,this);//己方坦克
	Tank protectedTank=new Tank(400,600,true,Direction.STOP,this);//己方守护的坦克
	
	Wall w1=new Wall(330,540,"wall"),w2=new Wall(450,540,"wall"),w3=new Wall(330,480,"wall");
	Wall w4=new Wall(450,480,"wall"),w5=new Wall(390,480,"wall");//障碍物
	
	Blood bl=new Blood();//增长坦克生命的血块
	
	List<Wall> walls=new ArrayList<Wall>();//墙体
	List<Tank> tanks=new ArrayList<Tank>();//敌方坦克
	List<Explode> explodes=new ArrayList<Explode>();//爆炸效果
	List<Missile> missiles=new ArrayList<Missile>();//坦克发出的子弹
	
	Image offScreenImage=null;
	
	/**
	 * 这个方法类是画出窗口中的坦克-子弹-爆炸效果-障碍物
	 * 以及显示提示性的语句
	 */
	public void paint(Graphics g) {
		g.drawString("missiles count:"+missiles.size(), 10, 50);
		g.drawString("explodes count:"+explodes.size(), 10, 70);
		g.drawString("剩余敌方坦克数:"+tanks.size(), 10, 90);
		g.drawString("生命值mytank life:"+myTank.getLife(), 10, 110);
		
		if(!myTank.isLive())
			g.drawString("很遗憾，您输了", 400, 300);
		else if(tanks.size()==0)
			g.drawString("恭喜，获得了胜利！", 400, 300);
		
		if(tanks.size()<=0) {
			for(int i=0;i<Integer.parseInt(PropertyMgr.getProperty("reProduceTankCount"));i++) {
				tanks.add(new Tank(50+40*(i+1),50,false,Direction.D,this));
			}
		}
		
		for(int i=0;i<missiles.size();i++){
			Missile m=missiles.get(i);
			m.hitTank(tanks);//进行与敌方坦克之间的碰撞检测，使之无法互相穿透
			m.hitTank(myTank);//进行与己方坦克的碰撞检测，使之无法互相穿透
			m.hitWall(w1);//进行与障碍物的碰撞检测，使之无法穿过障碍物
			m.hitWall(w2);
			m.draw(g);//画出子弹
		}
		
		for(int i=0;i<explodes.size();i++) {
			Explode e=explodes.get(i);
			e.draw(g);//画出爆炸效果
		}
		
		for(int i=0;i<tanks.size();i++) {
			Tank t=tanks.get(i);
			t.collideaWithTank(tanks);//进行敌方坦克之间的碰撞检测，使之无法互相穿透
			t.collideaWithWall(walls);//进行与障碍物的碰撞检测，使之无法穿过障碍物
			t.draw(g);
		}
		
		 myTank.draw(g);//画出己方坦克
		 myTank.collideaWithWall(walls);//进行与障碍物的碰撞检测，使之无法穿过障碍物
		 myTank.eat(bl);//己方坦克吃掉生命值增长的道具
		 protectedTank.draw(g);
		 
		 walls.add(w1);
		 walls.add(w2);
		 walls.add(w3);
		 walls.add(w4);
		 walls.add(w5);
		 for(int i=0;i<walls.size();i++) {
				Wall w=walls.get(i);
				w.draw(g);//画出障碍物
			}
		 
		 bl.draw(g);//画出生命值增长的道具
	}

	/**
	 * 这个方法是使用双缓冲来解决闪烁的问题
	 */
public void update(Graphics g) {
		if(offScreenImage==null){
			offScreenImage=this.createImage( GAME_WIDTH,GAME_HEIGHT);
		}
		Graphics gOffScreen=offScreenImage.getGraphics();
		Color c=gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	public void lauchFrame() {
		int initTankCount=Integer.parseInt(PropertyMgr.getProperty("initTankCount"));
		for(int i=0;i<initTankCount;i++) {
			tanks.add(new AgilityTank(50+40*(i+1),50,false,Direction.D,this));
		}
		
		this.setSize(GAME_WIDTH,GAME_HEIGHT);
		this.setTitle("TankWar");
		
		this.addWindowListener(new WindowAdapter() {//关闭窗口
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		this.setResizable(false);//禁止改变窗口大小
		
		this.setBackground(Color.GREEN);//窗口背景设置
		
		this.addKeyListener(new KeyMonitor());//设置键盘的监听
		
		setVisible(true);
		
		new Thread(new PaintThread()).start();
	}
	
	public static void main(String[] args) {
		TankClient tc=new TankClient();
		tc.lauchFrame();
	}
	
	private class PaintThread implements Runnable{
		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(50);//每隔50ms进行重画窗口
				}catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
/**
 * 
 * 这个类是内部类
 * 是对键盘的监听
 *
 */
	private class KeyMonitor extends KeyAdapter{

		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}
		
	}
}
