package TankWar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;


/**
 * ������Ǳ�ըЧ����
 * @author ������
 * ѧ�ţ�031702411
 *
 */
public class Explode {
	int x,y;//��ը��λ��
	private boolean live=true;//��ըЧ��������״̬
	
	static TankClient tc;
	
	private static boolean init = false; //���������ʾͼƬ�Ƿ��Ѿ������ص��ڴ���
	private static Toolkit tk=Toolkit.getDefaultToolkit();
	private static Image[] imgs= {
			/*
			 * ����ʹ�÷�������ȡ��ըͼƬ
			 */
			tk.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/9.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/10.gif"))
	};
	
	int step=0; //���������������ը��Բ�����ڼ�����
	
	public Explode(int x,int y,TankClient tc) {
		this.x=x;
		this.y=y;;
		Explode.tc=tc;
	}
	/**
	 * ������ǻ�����ը��ͼƬ
	 * @param ���ڵĻ���
	 * 
	 */
	public void draw(Graphics g) {
		if(!init) {
			for (int i = 0; i < imgs.length; i++) {
				g.drawImage(imgs[i], -100, -100, null);
			}
			init = true;
		}
		if(!live) {
			tc.explodes.remove(this);
			return;
		}
		
		if(step==imgs.length) {
			live=false;
			step=0;
			return;
		}
		g.drawImage(imgs[step], x, y, null);
		
		step++;
		
		
	}
}