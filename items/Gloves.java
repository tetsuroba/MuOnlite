package items;

/**
 *
 * @author Robi
 */
public class Gloves extends Item {

    public Gloves(int enemyLevel) {
        super("Kesztyű", 3, enemyLevel);
        statRoll(enemyLevel, getStr(), getAgi(), getStam(), getEnergy());
        setName(nameRoll("Kesztyű"));
        setIcon("gloves");
    }

    public Gloves(String name, int str, int agi, int stam, int energy) {
        super(name, 3, str, agi, stam, energy);
        setIcon("gloves");
    }

}
