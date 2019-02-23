package engine;

import characters.Dragon;
import characters.Enemy;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Robi
 */
public class HardBattle extends Battle {

    public HardBattle(int playerLevel, JPanel upperPanel, JPanel lowerPanel) {
        super("Nehéz", 20, upperPanel, lowerPanel);
        setEnemy(new Dragon(playerLevel));
    }

    @Override
    public int xpCalc(Enemy enemy) {
        Random r = new Random();
        return r.nextInt(120) + getXpLost() + 1;
    }

}
