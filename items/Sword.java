package items;

/**
 *
 * @author Robi
 */
public class Sword extends Item {

    public Sword(int enemyLevel) {
        super("Kard", 4, enemyLevel);
        statRoll(enemyLevel, getStr(), getAgi(), getStam(), getEnergy());
        setName(nameRoll("Kard"));
        setIcon("sword");
    }

    public Sword(String name, int str, int agi, int stam, int energy) {
        super(name, 4, str, agi, stam, energy);
        setIcon("sword");
    }

}
