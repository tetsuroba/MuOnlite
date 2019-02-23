package items;

import characters.Player;
import engine.GameWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public abstract class Item {

    private String name;
    private int slot; // 0 - Sapka 1 - Mellvért 2 - Cipő 3 - Kesztyű 4 - Fegyver slot
    private int str;
    private int agi;
    private int stam;
    private int energy;
    private int enemyLevel;
    private JPopupMenu menu = new JPopupMenu();
    private JMenuItem equip = new JMenuItem("Felvevés");
    private JMenuItem unequip = new JMenuItem("Levevés");
    private JMenuItem drop = new JMenuItem("Eldobás");
    private JMenuItem nameMenu;
    private JMenuItem strMenu;
    private JMenuItem agiMenu;
    private JMenuItem stamMenu;
    private JMenuItem energyMenu;

    private JLabel icon;
    private boolean pressed = false;
    private boolean statsSet = false;

    public Item(String name, int slot, int enemyLevel) {
        setName(name);
        setSlot(slot);
        setStr(0);
        setAgi(0);
        setStam(0);
        setEnergy(0);
        setEnemyLevel(enemyLevel);
        statRoll(getEnemyLevel(), getStr(), getAgi(), getStam(), getEnergy());

    }

    public Item(String name, int slot, int str, int agi, int stam, int energy) {
        setName(name);
        setSlot(slot);
        setStr(str);
        setAgi(agi);
        setStam(stam);
        setEnergy(energy);
    }

    public JLabel addItem(int posX, int posY, JPanel panel, Player player, int inventorySlot, GameWindow w) {
        if (!isStatsSet()) {
            setupMenuItems(getMenu());
            setStatsSet(true);
        }
        setPressed(false);
        JLabel icon = getIcon();
        icon.setBounds(posX, posY, 50, 50);
        icon.setVisible(true);
        getMenu().remove(getUnequip());
        getMenu().add(getEquip());
        getMenu().add(getDrop());
        icon.setComponentPopupMenu(getMenu());
        getEquip().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                if (!isPressed()) {
                    icon.setVisible(false);
                    panel.repaint();
                    player.equip(player.getInventory()[inventorySlot].getSlot(), inventorySlot);
                    w.updateInventory(panel, player);
                    setPressed(true);
                }
            }
        });
        getDrop().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                if (!isPressed()) {
                    icon.setVisible(false);
                    panel.repaint();
                    player.drop(inventorySlot);
                    w.updateInventory(panel, player);
                    setPressed(true);
                }
            }
        });
        return getIcon();
    }

    public JLabel addEquipmentItem(int posX, int posY, JPanel panel, Player player, int equipmentSlot, GameWindow w) {
        if (!isStatsSet()) {
            setupMenuItems(getMenu());
            setStatsSet(true);
        }
        setPressed(false);
        JLabel icon = getIcon();
        icon.setBounds(posX, posY, 70, 70);
        icon.setVisible(true);
        getMenu().add(getUnequip());
        getMenu().remove(getDrop());
        getMenu().remove(getEquip());
        icon.setComponentPopupMenu(getMenu());
        panel.add(icon);
        getUnequip().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                if (!isPressed()) {
                    icon.setVisible(false);
                    panel.repaint();
                    player.unequip(equipmentSlot);
                    w.updateEquipment(panel, player);
                    setPressed(true);
                }
            }
        });
        return getIcon();
    }

    public void setupMenuItems(JPopupMenu menu) {
        setNameMenu(new JMenuItem(getName()));
        setStrMenu(new JMenuItem(String.valueOf(getStr())));
        setAgiMenu(new JMenuItem(String.valueOf(getAgi())));
        setStamMenu(new JMenuItem(String.valueOf(getStam())));
        setEnergyMenu(new JMenuItem(String.valueOf(getEnergy())));
        getNameMenu().setEnabled(false);
        getStrMenu().setEnabled(false);
        getAgiMenu().setEnabled(false);
        getStamMenu().setEnabled(false);
        getEnergyMenu().setEnabled(false);
        menu.add(nameMenu);
        menu.add(strMenu);
        menu.add(agiMenu);
        menu.add(stamMenu);
        menu.add(energyMenu);
    }

    public String nameRoll(String name) {
        Random rand = new Random();
        String[] jelzok = {"Erős", "Elavult", "Varázslatos", "Megkímélt"};
        int sorsz = rand.nextInt(4);
        return jelzok[sorsz] + " " + name;
    }

    public void statRoll(int enemyLevel, int str, int agi, int stam, int energy) {
        Random rand = new Random();
        int bonus = rand.nextInt(enemyLevel * 5) + 1;
        for (int i = 0; i <= bonus; i++) {
            int r = rand.nextInt(4);
            switch (r) {
                case 0:
                    setStr(++str);
                    break;
                case 1:
                    setAgi(++agi);
                    break;
                case 2:
                    setStam(++stam);
                    break;
                case 3:
                    setEnergy(++energy);
                    break;
            }
        }

    }

    public String printEquipment() {
        return (getName() + " " + getStr() + " " + getAgi() + " " + getStam() + " " + getEnergy());
    }

    @Override
    public String toString() {
        return (getSlot() + " " + getName() + " " + getStr() + " " + getAgi() + " " + getStam() + " " + getEnergy());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
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

    public int getEnemyLevel() {
        return enemyLevel;
    }

    public void setEnemyLevel(int enemyLevel) {
        this.enemyLevel = enemyLevel;
    }

    public JLabel getIcon() {
        return icon;
    }

    public void setIcon(String iconName) {
        this.icon = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("res/" + iconName + ".png")));
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public JMenuItem getDrop() {
        return drop;
    }

    public void setDrop(JMenuItem drop) {
        this.drop = drop;
    }

    public JMenuItem getNameMenu() {
        return nameMenu;
    }

    public void setNameMenu(JMenuItem nameMenu) {
        this.nameMenu = nameMenu;
    }

    public JMenuItem getStrMenu() {
        return strMenu;
    }

    public void setStrMenu(JMenuItem strMenu) {
        this.strMenu = strMenu;
    }

    public JMenuItem getAgiMenu() {
        return agiMenu;
    }

    public void setAgiMenu(JMenuItem agiMenu) {
        this.agiMenu = agiMenu;
    }

    public JMenuItem getStamMenu() {
        return stamMenu;
    }

    public void setStamMenu(JMenuItem stamMenu) {
        this.stamMenu = stamMenu;
    }

    public JMenuItem getEnergyMenu() {
        return energyMenu;
    }

    public void setEnergyMenu(JMenuItem energyMenu) {
        this.energyMenu = energyMenu;
    }

    public boolean isStatsSet() {
        return statsSet;
    }

    public void setStatsSet(boolean statsSet) {
        this.statsSet = statsSet;
    }

    public JPopupMenu getMenu() {
        return menu;
    }

    public void setMenu(JPopupMenu menu) {
        this.menu = menu;
    }

    public JMenuItem getEquip() {
        return equip;
    }

    public void setEquip(JMenuItem equip) {
        this.equip = equip;
    }

    public JMenuItem getUnequip() {
        return unequip;
    }

    public void setUnequip(JMenuItem unequip) {
        this.unequip = unequip;
    }
}
