package TankWar;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * 
 * @author ������
 * ѧ�ţ�031702411
 * ������Ǵ��ڵ�������
 *
 */
public class TankClient extends Frame{
	public static final int GAME_WIDTH=1000;//���ڿ��
	public static final int GAME_HEIGHT=800;//���ڸ߶�
	
	private int level=1;
	
	Tank myTank=new MyTank(300,800,true,Direction.STOP,this);//����̹��
	Tank protectedTank=new MyTank(500,800,true,Direction.STOP,this);//�����ػ���̹��
	
	Blood bl=new Blood();//����̹��������Ѫ��
	
	List<Wall> walls=new ArrayList<Wall>();//ǽ��
	List<Tank> tanks=new ArrayList<Tank>();//�з�̹��
	List<Explode> explodes=new ArrayList<Explode>();//��ըЧ��
	List<Missile> missiles=new ArrayList<Missile>();//̹�˷������ӵ�
	
	Image offScreenImage=null;
	
	/**
	 * ����������ǻ��������е�̹��-�ӵ�-��ըЧ��-�ϰ���
	 * �Լ���ʾ��ʾ�Ե����
	 */
	public void paint(Graphics g) {
		if(tanks.size()<=0) {//���������ез�̹��ʱ���������ɵ�ͼ���Ҽ����µ�̹��
			createWalls();
			for(int i=0;i<Integer.parseInt(PropertyMgr.getProperty("initTankCount"))-level;i++) {
				tanks.add(new EnemyTank(50+60*(i+1),50,false,Direction.D,this));
			}
			for(int i=0;i<level;i++) {
				if(i%2==0)
				tanks.add(new HeavyTank(50+40*(i+1),50,false,Direction.D,this));
				else
					tanks.add(new AgilityTank(50+40*(i+1),50,false,Direction.D,this));
			}
			level+=1;
		}
		
		for(int i=0;i<missiles.size();i++){
			Missile m=missiles.get(i);
			m.hitTank(tanks);//������з�̹��֮�����ײ��⣬ʹ֮�޷����ഩ͸
			m.hitTank(myTank);//�����뼺��̹�˵���ײ��⣬ʹ֮�޷����ഩ͸
			m.hitTank(protectedTank);//�����뼺���ػ���̹�˵���ײ��⣬ʹ֮�޷����ഩ͸
			m.hitWall(walls);//�������ϰ������ײ��⣬����ש��������ͬʱ�ӵ���ʧ�����ǲݵ����ӵ��ɹ������Ǹֿ����ӵ���ʧ
			m.draw(g);//�����ӵ�
		}
		
		for(int i=0;i<explodes.size();i++) {
			Explode e=explodes.get(i);
			e.draw(g);//������ըЧ��
		}
		
		for(int i=0;i<tanks.size();i++) {
			Tank t=tanks.get(i);
			t.eat(bl);
			t.collideaWithTank(tanks);//���ез�̹��֮�����ײ��⣬ʹ֮�޷����ഩ͸
			t.collideaWithWall(walls);//�������ϰ������ײ��⣬ʹ֮�޷������ϰ���
			t.draw(g);
		}
		
		if(protectedTank.isLive()) {
			myTank.draw(g);//��������̹��
			myTank.collideaWithWall(walls);//�������ϰ������ײ��⣬ʹ֮�޷������ϰ���
			myTank.eat(bl);//����̹�˳Ե�����ֵ�����ĵ���
		}
		
		 protectedTank.setLife(1);
		 protectedTank.draw(g);
		 
		 for(int i=0;i<walls.size();i++) {
				Wall w=walls.get(i);
				w.draw(g);//�����ϰ���
			}
		 
		 bl.draw(g);//��������ֵ�����ĵ���
	}

	/**
	 * ���������ʹ��˫�����������˸������
	 */
public void update(Graphics g) {
		if(offScreenImage==null){
			offScreenImage=this.createImage( GAME_WIDTH,GAME_HEIGHT);
		}
		Graphics gOffScreen=offScreenImage.getGraphics();
		Color c=gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	public void lauchFrame() {
		int initTankCount=Integer.parseInt(PropertyMgr.getProperty("initTankCount"));
		for(int i=0;i<initTankCount;i++) {
			tanks.add(new AgilityTank(50+60*(i+1),50,false,Direction.D,this));
		}
		
		createWalls();
		
		this.setSize(GAME_WIDTH,GAME_HEIGHT);
		this.setTitle("TankWar");
		
		this.addWindowListener(new WindowAdapter() {//�رմ���
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		this.setResizable(false);//��ֹ�ı䴰�ڴ�С
		
		this.setBackground(Color.BLACK);//���ڱ�������
		
		this.addKeyListener(new KeyMonitor());//���ü��̵ļ���
		
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
					Thread.sleep(50);//ÿ��50ms�����ػ�����
				}catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
/**
 * 
 * ��������ڲ���
 * �ǶԼ��̵ļ���
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
	/**
	 * ��������Ǵ�����ͼ��ǽ��ķֲ�
	 */
	public void createWalls() {
		Random r=new Random();
		int step=r.nextInt(20)+20;
		String type;
		int x;
		int y;
		Wall w4=new Wall(441,680,"wall"),w5=new Wall(561,680,"wall"),w3=new Wall(441,740,"wall");
		Wall w1=new Wall(561,740,"wall"),w2=new Wall(501,680,"wall");//�ϰ���
		walls.add(w1);
		walls.add(w2);
		walls.add(w3);
		walls.add(w4);
		walls.add(w5);
		for(int i=0;i<step;i++) {
			x=r.nextInt(900)+50;
			y=r.nextInt(550)+100;
			if(i%4==0)
				type="steel";
			else if(i%5==0)
				type="grass";
			else
				type="wall";
			walls.add(new Wall(x,y,type));
		}
	}
}
