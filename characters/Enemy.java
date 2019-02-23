package characters;

/**
 *
 * @author Robi
 */
public abstract class Enemy {

    private String name;
    private int level;
    private int str;
    private int agi;
    private int stam;
    private int energy;
    private int playerLevel;
    private int chance;

    public Enemy(String name, int playerLevel) {
        setName(name);
        setPlayerLevel(playerLevel);
        setLevel(0);
        setStr(0);
        setAgi(0);
        setStam(0);
        setEnergy(0);
    }

    public abstract void calcLevel(int level, int playerLevel);

    public abstract void rollStats(int level, int str, int agi, int stam, int energy);

    @Override
    public String toString() {
        String newLine = System.getProperty("line.separator");
        return ("Neve: " + getName() + newLine
                + "Szintje: " + getLevel() + newLine
                + "Erőssége: " + getStr() + newLine
                + "Ügyessége: " + getAgi() + newLine
                + "Kitartása: " + getStam() + newLine
                + "Energiája: " + getEnergy() + newLine
                + "Esélye: " + getChance() + newLine);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStr() {
        return str;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public int getAgi() {
        return agi;
    }

    public void setAgi(int agi) {
        this.agi = agi;
    }

    public int getStam() {
        return stam;
    }

    public void setStam(int stam) {
        this.stam = stam;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public int getPlayerLevel() {
        return playerLevel;
    }

    public void setPlayerLevel(int playerLevel) {
        this.playerLevel = playerLevel;
    }
}
