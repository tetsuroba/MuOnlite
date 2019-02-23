package items;

/**
 *
 * @author Robi
 */
public class Helmet extends Item {

    public Helmet(int enemyLevel) {
        super("Sisak", 0, enemyLevel);
        statRoll(enemyLevel, getStr(), getAgi(), getStam(), getEnergy());
        setName(nameRoll("Sisak"));
        setIcon("helmet");
    }

    public Helmet(String name, int str, int agi, int stam, int energy) {
        super(name, 0, str, agi, stam, energy);
        setIcon("helmet");
    }

}
