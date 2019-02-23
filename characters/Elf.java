package characters;

/**
 *
 * @author Robi
 */
public class Elf extends Player {

    public Elf(String name) {
        super(name, "Elf", 5, 7, 5, 3);
        setChance(calcChance(getStr(), getAgi(), getStam(), getEnergy()));
    }

    @Override
    public int calcChance(int str, int agi, int stam, int energy) {
        return ((str * 2) + (agi * 4) + (stam * 3) + (energy * 1));
    }

}
