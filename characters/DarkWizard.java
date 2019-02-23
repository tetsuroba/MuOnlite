package characters;

/**
 *
 * @author Robi
 */
public class DarkWizard extends Player {

    public DarkWizard(String name) {
        super(name, "Dark Wizard", 3, 4, 5, 8);
        setChance(calcChance(getStr(), getAgi(), getStam(), getEnergy()));
    }

    @Override
    public int calcChance(int str, int agi, int stam, int energy) {
        return ((str * 1) + (agi * 3) + (stam * 2) + (energy * 4));
    }

}
