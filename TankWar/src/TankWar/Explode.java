package TankWar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;


/**
 * 这个类是爆炸效果类
 * @author 杨文燕
 * 学号：031702411
 *
 */
public class Explode {
	int x,y;//爆炸的位置
	private boolean live=true;//爆炸效果的生命状态
	
	static TankClient tc;
	
	private static boolean init = false; //定义变量表示图片是否已经被加载到内存了
	private static Toolkit tk=Toolkit.getDefaultToolkit();
	private static Image[] imgs= {
			/*
			 * 以下使用反射来获取爆炸图片
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
	
	int step=0; //定义变量代表画爆炸的圆画到第几个了
	
	public Explode(int x,int y,TankClient tc) {
		this.x=x;
		this.y=y;;
		Explode.tc=tc;
	}
	/**
	 * 这个类是画出爆炸的图片
	 * @param 窗口的画笔
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
