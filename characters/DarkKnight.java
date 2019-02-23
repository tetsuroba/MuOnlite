package characters;

/**
 *
 * @author Robi
 */
public class DarkKnight extends Player {

    public DarkKnight(String name) {
        super(name, "Dark Knight", 8, 2, 7, 3);
        setChance(calcChance(getStr(), getAgi(), getStam(), getEnergy()));
    }

    @Override
    public int calcChance(int str, int agi, int stam, int energy) {
        return ((str * 4) + (agi * 3) + (stam * 2) + (energy * 1));
    }

}
