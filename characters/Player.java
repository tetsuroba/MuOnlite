package characters;

import items.Item;
import java.util.Random;

/**
 *
 * @author Robi
 */
public abstract class Player {

    private String name;
    private String race;
    private int level;
    private int xp;
    private int lifePoints;
    private int str;
    private int agi;
    private int stam;
    private int energy;
    private int chance;
    private Item[] inventory = new Item[20];
    private Item[] equipment = new Item[5];

    public Player(String name, String race, int str, int agi, int stam, int energy) {
        setName(name);
        setRace(race);
        setLevel(1);
        setXp(0);
        setLifePoints(5);
        setStr(str);
        setAgi(agi);
        setStam(stam);
        setEnergy(energy);
    }

    /**
     * A játékos jelenlegi statjai
     *
     * @param lifePoints életpontja
     * @param level szintje
     * @param str ereje
     * @param agi ügyessége
     * @param stam kitartása
     * @param energy energiája
     */
    public void levelUp(int lifePoints, int level, int str, int agi, int stam, int energy) {
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            int lvlup = rand.nextInt(4);
            switch (lvlup) {
                case 0:
                    this.setStr(++str);
                    break;
                case 1:
                    this.setAgi(++agi);
                    break;
                case 2:
                    this.setStam(++stam);
                    break;
                case 3:
                    this.setEnergy(++energy);
                    break;
            }
        }
        this.setLifePoints(lifePoints + 3);
        this.setLevel(++level);
    }

    /**
     * Az inventory és a felszerelés is egy-egy tömbök, ezeknek a sorszámát kéri
     * ez a metódus
     *
     * @param equipmentSlot
     * @param inventorySlot
     */
    public void equip(int equipmentSlot, int inventorySlot) {

        if (getEquipment()[equipmentSlot] != null) //kicseréli a felszerelést
        {
            swap(equipmentSlot, inventorySlot);
        } else {
            getEquipment()[equipmentSlot] = getInventory()[inventorySlot]; //leveszi a felszerelést
            giveStats(getEquipment()[equipmentSlot]);
            getInventory()[inventorySlot] = null;
            sortInventory();
        }

    }

    /**
     *
     * @param equipmentSlot
     */
    public void unequip(int equipmentSlot) {
        if (freeSlot() == 0) {
            //System.out.println("Nincs elég tárhelyed hogy levedd ezt a tárgyat"); 
        } else {
            //System.out.println("Levetted: " + getEquipment()[equipmentSlot].getName());
            takeStats(getEquipment()[equipmentSlot]);
            getInventory()[smallestSlot()] = getEquipment()[equipmentSlot];
            getEquipment()[equipmentSlot] = null;
        }
    }

    /**
     * Kicseréli a felszerelést
     *
     * @param equipmentSlot
     * @param inventorySlot
     */
    public void swap(int equipmentSlot, int inventorySlot) {
        //System.out.println("Felszerelés lecserélve: " + getEquipment()[equipmentSlot].getName() + " -> " + getInventory()[inventorySlot].getName());
        takeStats(getEquipment()[equipmentSlot]);
        giveStats(getInventory()[inventorySlot]);
        Item item = getEquipment()[equipmentSlot];
        getEquipment()[equipmentSlot] = getInventory()[inventorySlot];
        getInventory()[inventorySlot] = item;
    }

    /*
    Felszerelés eldobása
     */
    public void drop(int inventorySlot) {
        getInventory()[inventorySlot] = null;
        sortInventory();
    }

    /**
     *
     * @param str
     * @param agi
     * @param stam
     * @param energy
     * @return A játékos esélye a nyerésre
     */
    public abstract int calcChance(int str, int agi, int stam, int energy);

    /**
     * Rendezi a táska tartalmát,az üres rész mindig a végén van
     */
    public void sortInventory() {
        for (int i = 0; i < 20; i++) {
            for (int j = i; j < 19; j++) {
                if (getInventory()[i] == null) {
                    getInventory()[i] = getInventory()[i + 1];
                    getInventory()[i + 1] = null;
                }
            }
        }
    }

    /*
    Kiszámolja hány üres hely van a táskában
     */
    public int freeSlot() {
        int space = 0;
        for (int i = 0; i < 20; i++) {
            if (getInventory()[i] == null) {
                space++;
            }
        }
        return space;
    }

    /**
     *
     * @return A táskában lévő legkissebb sorszám ahova tárgy jöhet
     */
    public int smallestSlot() {
        int space = 20;
        for (int i = 0; i < 20; i++) {
            if (getInventory()[i] == null && i < space) {
                space = i;
            }
        }
        return space;
    }

    /**
     *
     * @param item A tárgy statjait odaadja a játékosnak
     */
    public void giveStats(Item item) {
        setStr(getStr() + item.getStr());
        setAgi(getAgi() + item.getAgi());
        setStam(getStam() + item.getStam());
        setEnergy(getEnergy() + item.getEnergy());
    }

    /**
     *
     * @param item A tárgy statjait elveszi a játékostól
     */
    public void takeStats(Item item) {
        setStr(getStr() - item.getStr());
        setAgi(getAgi() - item.getAgi());
        setStam(getStam() - item.getStam());
        setEnergy(getEnergy() - item.getEnergy());
    }

    @Override
    public String toString() {
        String newLine = System.getProperty("line.separator");
        return ("A statjaid: " + newLine
                + "Életerőd: " + getLifePoints() + newLine
                + "A szinted: " + getLevel() + newLine
                + "XP: " + getXp() + newLine
                + "Erő: " + getStr() + newLine
                + "Ügyesség: " + getAgi() + newLine
                + "Kitartás: " + getStam() + newLine
                + "Energia: " + getEnergy() + newLine);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
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

    public Item[] getInventory() {
        return inventory;
    }

    public void setInventory(Item[] inventory) {
        this.inventory = inventory;
    }

    public Item[] getEquipment() {
        return equipment;
    }

    public void setEquipment(Item[] equipment) {
        this.equipment = equipment;
    }

}
