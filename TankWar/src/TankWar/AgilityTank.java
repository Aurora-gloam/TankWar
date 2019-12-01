package TankWar;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

public class AgilityTank extends Tank {
	private static Toolkit tk=Toolkit.getDefaultToolkit();
	private static Image[] tankImages=null;
	private static Map<String,Image> imgs=new HashMap<String,Image>();
	
	static {
		tankImages=new Image[] {
				tk.getImage(Tank.class.getClassLoader().getResource("images/p1tankU.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/p1tankL.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/p1tankR.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/p1tankD.gif"))
		};
		
		imgs.put("L", tankImages[1]);
		imgs.put("R", tankImages[2]);
		imgs.put("U", tankImages[0]);
		imgs.put("D", tankImages[3]);
	}
	public AgilityTank(int x, int y, boolean good, Direction dir, TankClient tc) {
		super(x, y, good, dir, tc);
		this.setLife(1);
	}
}
