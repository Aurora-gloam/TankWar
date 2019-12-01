package TankWar;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

public class HeavyTank extends EnemyTank {

	public HeavyTank(int x, int y, boolean good, Direction dir, TankClient tc) {
		super(x, y, good, dir, tc);
		this.setLife(3);
	}


}
