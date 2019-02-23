package items;

/**
 *
 * @author Robi
 */
public class Shoes extends Item {

    public Shoes(int enemyLevel) {
        super("Cipő", 2, enemyLevel);
        statRoll(enemyLevel, getStr(), getAgi(), getStam(), getEnergy());
        setName(nameRoll("Cipő"));
        setIcon("shoes");
    }

    public Shoes(String name, int str, int agi, int stam, int energy) {
        super(name, 2, str, agi, stam, energy);
        setIcon("shoes");
    }

}
