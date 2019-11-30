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
 * @author ������
 * ѧ�ţ�031702411
 * ������Ǵ��ڵ�������
 *
 */
public class TankClient extends Frame{
	public static final int GAME_WIDTH=800;//���ڿ��
	public static final int GAME_HEIGHT=600;//���ڸ߶�
	
	Tank myTank=new Tank(400,600,true,Direction.STOP,this);//����̹��
	
	Wall w1=new Wall(100,200,20,150,this),w2=new Wall(300,100,300,20,this);//�ϰ���
	
	Blood bl=new Blood();//����̹��������Ѫ��
	
	List<Tank> tanks=new ArrayList<Tank>();//�з�̹��
	List<Explode> explodes=new ArrayList<Explode>();//��ըЧ��
	List<Missile> missiles=new ArrayList<Missile>();//̹�˷������ӵ�
	
	Image offScreenImage=null;
	
	/**
	 * ����������ǻ��������е�̹��-�ӵ�-��ըЧ��-�ϰ���
	 * �Լ���ʾ��ʾ�Ե����
	 */
	public void paint(Graphics g) {
		g.drawString("missiles count:"+missiles.size(), 10, 50);
		g.drawString("explodes count:"+explodes.size(), 10, 70);
		g.drawString("enemy tanks count:"+tanks.size(), 10, 90);
		g.drawString("mytank life:"+myTank.getLife(), 10, 110);
		
		if(!myTank.isLive())
			g.drawString("L O S T !", 400, 300);
		else if(tanks.size()==0)
			g.drawString("W I N !", 400, 300);
		
		if(tanks.size()<=0) {
			for(int i=0;i<Integer.parseInt(PropertyMgr.getProperty("reProduceTankCount"));i++) {
				tanks.add(new Tank(50+40*(i+1),50,false,Direction.D,this));
			}
		}
		
		for(int i=0;i<missiles.size();i++){
			Missile m=missiles.get(i);
			m.hitTank(tanks);//������з�̹��֮�����ײ��⣬ʹ֮�޷����ഩ͸
			m.hitTank(myTank);//�����뼺��̹�˵���ײ��⣬ʹ֮�޷����ഩ͸
			m.hitWall(w1);//�������ϰ������ײ��⣬ʹ֮�޷������ϰ���
			m.hitWall(w2);
			m.draw(g);//�����ӵ�
		}
		
		for(int i=0;i<explodes.size();i++) {
			Explode e=explodes.get(i);
			e.draw(g);//������ըЧ��
		}
		
		for(int i=0;i<tanks.size();i++) {
			Tank t=tanks.get(i);
			t.collideaWithTank(tanks);//���ез�̹��֮�����ײ��⣬ʹ֮�޷����ഩ͸
			t.collidesWithWall(w1);//�������ϰ������ײ��⣬ʹ֮�޷������ϰ���
			t.collidesWithWall(w2);
			t.draw(g);
		}
		
		 myTank.draw(g);//��������̹��
		 myTank.collidesWithWall(w1);//�������ϰ������ײ��⣬ʹ֮�޷������ϰ���
		 myTank.collidesWithWall(w2);
		 myTank.eat(bl);//����̹�˳Ե�����ֵ�����ĵ���
		 
		 w1.draw(g);//�����ϰ���
		 w2.draw(g);
		 
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
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	public void lauchFrame() {
		int initTankCount=Integer.parseInt(PropertyMgr.getProperty("initTankCount"));
		for(int i=0;i<initTankCount;i++) {
			tanks.add(new Tank(50+40*(i+1),50,false,Direction.D,this));
		}
		
		this.setSize(GAME_WIDTH,GAME_HEIGHT);
		this.setTitle("TankWar");
		
		this.addWindowListener(new WindowAdapter() {//�رմ���
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		this.setResizable(false);//��ֹ�ı䴰�ڴ�С
		
		this.setBackground(Color.GREEN);//���ڱ�������
		
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
}
