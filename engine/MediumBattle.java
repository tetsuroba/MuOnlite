package engine;

import characters.Enemy;
import characters.Bull;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Robi
 */
public class MediumBattle extends Battle {

    public MediumBattle(int playerLevel, JPanel upperPanel, JPanel lowerPanel) {
        super("Közepes", 10, upperPanel, lowerPanel);
        setEnemy(new Bull(playerLevel));
    }

    @Override
    public int xpCalc(Enemy enemy) {
        Random r = new Random();
        return r.nextInt(50) + getXpLost() + 1;
    }

}
