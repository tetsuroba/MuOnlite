 package items;

/**
 *
 * @author Robi
 */
public class Staff extends Item {

    public Staff(int enemyLevel) {
        super("Bot", 4, enemyLevel);
        setName(nameRoll("Bot"));
        setIcon("staff");
    }

    public Staff(String name, int str, int agi, int stam, int energy) {
        super(name, 4, str, agi, stam, energy);
        setIcon("staff");
    }

}
