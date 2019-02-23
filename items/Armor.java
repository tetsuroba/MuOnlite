package items;

/**
 *
 * @author Robi
 */
public class Armor extends Item {

    public Armor(int enemyLevel) {
        super("Mellvért", 1, enemyLevel);
        statRoll(enemyLevel, getStr(), getAgi(), getStam(), getEnergy());
        setName(nameRoll("Mellvért"));
        setIcon("armor");
    }

    public Armor(String name, int str, int agi, int stam, int energy) {
        super(name, 1, str, agi, stam, energy);
        setIcon("armor");
    }

}
