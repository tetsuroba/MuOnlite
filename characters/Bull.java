package characters;

import java.util.Random;

/**
 *
 * @author Robi
 */
public class Bull extends Enemy {

    public Bull(int playerLevel) {
        super("Bika", playerLevel);
        calcLevel(getLevel(), playerLevel);
        rollStats(getLevel(), getStr(), getAgi(), getStam(), getEnergy());
        setChance(getLevel() * (getAgi() * 3 + getStr() * 4 + getStam() * 2 + getEnergy()));
    }

    @Override
    public void calcLevel(int level, int playerLevel) {
        setLevel(playerLevel);
    }

    @Override
    public void rollStats(int level, int str, int agi, int stam, int energy) {
        Random rand = new Random();
        for (int i = 0; i < (level * 6 + 20); i++) {
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
