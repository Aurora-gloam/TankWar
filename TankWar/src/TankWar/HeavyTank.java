package TankWar;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

public class HeavyTank extends Tank {

	private static Toolkit tk=Toolkit.getDefaultToolkit();
	private static Image[] tankImages=null;
	private static Map<String,Image> imgs=new HashMap<String,Image>();
	
	static {
		tankImages=new Image[] {
				tk.getImage(Tank.class.getClassLoader().getResource("images/enemy2U.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/enemy2L.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/enemy2R.gif")),
				tk.getImage(Tank.class.getClassLoader().getResource("images/enemy2D.gif"))
		};
		
		imgs.put("L", tankImages[1]);
		imgs.put("R", tankImages[2]);
		imgs.put("U", tankImages[0]);
		imgs.put("D", tankImages[3]);
	}
	public HeavyTank(int x, int y, boolean good, Direction dir, TankClient tc) {
		super(x, y, good, dir, tc);
		this.setLife(3);
	}


}
