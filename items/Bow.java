package items;

/**
 *
 * @author Robi
 */
public class Bow extends Item {

    public Bow(int enemyLevel) {
        super("Íj", 4, enemyLevel);
        statRoll(enemyLevel, getStr(), getAgi(), getStam(), getEnergy());
        setName(nameRoll("Íj"));
        setIcon("bow");
    }

    public Bow(String name, int str, int agi, int stam, int energy) {
        super(name, 4, str, agi, stam, energy);
        setIcon("bow");
    }

}
