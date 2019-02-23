package characters;

import java.util.Random;

/**
 *
 * @author Robi
 */
public class Spider extends Enemy {

    public Spider(int playerLevel) {
        super("Pók", playerLevel);
        calcLevel(getLevel(), playerLevel);
        rollStats(getLevel(), getStr(), getAgi(), getStam(), getEnergy());
        setChance(getLevel() * (getAgi() * 4 + getStr() * 3 + getStam() * 2 + getEnergy()));
    }

    @Override
    public void calcLevel(int level, int playerLevel) {
        if (playerLevel - 5 < 1) {
            setLevel(1);
        } else {
            setLevel(playerLevel - 5);
        }
    }

    @Override
    public void rollStats(int level, int str, int agi, int stam, int energy) {
        Random rand = new Random();
        for (int i = 0; i < (level * 5 + 20); i++) {
            int szam = rand.nextInt(4);
            switch (szam) {
                case 0:
                    setStr(str++);
                    break;
                case 1:
                    setAgi(agi++);
                    break;
                case 2:
                    setStam(stam++);
                    break;
                case 3:
                    setEnergy(energy++);
                    break;
            }
        }
    }

}
