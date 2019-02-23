package engine;

import characters.DarkKnight;
import characters.DarkWizard;
import characters.Elf;
import characters.Player;
import items.Armor;
import items.Bow;
import items.Gloves;
import items.Helmet;
import items.Item;
import items.Shoes;
import items.Staff;
import items.Sword;
import java.io.File;
import java.util.Scanner;

public class LoadSave {

    private Player player;
    private Scanner loader;
    private boolean correct = false;
    private String name;
    private String playerClass;

    public void openFile(String route) {
        try {
            setLoader(new Scanner(new File(route)));
        } catch (Exception e) {
            System.err.println("Hibás fájl");
        }
    }

    public void readFile() {
        try {
            int k = 0;
            while (getLoader().hasNextLine()) {
                switch (loader.next()) {

                    case "DK":
                        setPlayer(new DarkKnight(""));
                        setPlayerClass("DK");
                        break;

                    case "ELF":
                        setPlayer(new Elf(""));
                        setPlayerClass("ELF");
                        break;

                    case "DW":
                        setPlayer(new DarkWizard(""));
                        setPlayerClass("DW");
                        break;
                }
                getPlayer().setName(loader.next());
                getPlayer().setLevel(loader.nextInt());
                getPlayer().setXp(loader.nextInt());
                getPlayer().setStr(loader.nextInt());
                getPlayer().setAgi(loader.nextInt());
                getPlayer().setStam(loader.nextInt());
                getPlayer().setEnergy(loader.nextInt());
                loader.nextLine();
                for (int i = 0; i < 20; i++) {
                    String temp = loader.nextLine();
                    if (temp.length() == 4) {

                    } else {
                        Item tempItem = null;

                        String[] splitted = temp.split(" ");

                        switch (Integer.parseInt(splitted[0])) {
                            case 0:
                                tempItem = new Helmet(splitted[1] + " " + splitted[2], Integer.parseInt(splitted[3]), Integer.parseInt(splitted[4]), Integer.parseInt(splitted[5]), Integer.parseInt(splitted[6]));
                                break;
                            case 1:
                                tempItem = new Armor(splitted[1] + " " + splitted[2], Integer.parseInt(splitted[3]), Integer.parseInt(splitted[4]), Integer.parseInt(splitted[5]), Integer.parseInt(splitted[6]));
                                break;
                            case 2:
                                tempItem = new Shoes(splitted[1] + " " + splitted[2], Integer.parseInt(splitted[3]), Integer.parseInt(splitted[4]), Integer.parseInt(splitted[5]), Integer.parseInt(splitted[6]));
                                break;
                            case 3:
                                tempItem = new Gloves(splitted[1] + " " + splitted[2], Integer.parseInt(splitted[3]), Integer.parseInt(splitted[4]), Integer.parseInt(splitted[5]), Integer.parseInt(splitted[6]));
                                break;
                            case 4:
                                switch (getPlayerClass()) {
                                    case "DK":
                                        tempItem = new Sword(splitted[1] + " " + splitted[2], Integer.parseInt(splitted[3]), Integer.parseInt(splitted[4]), Integer.parseInt(splitted[5]), Integer.parseInt(splitted[6]));
                                        break;
                                    case "ELF":
                                        tempItem = new Bow(splitted[1] + " " + splitted[2], Integer.parseInt(splitted[3]), Integer.parseInt(splitted[4]), Integer.parseInt(splitted[5]), Integer.parseInt(splitted[6]));
                                        break;
                                    case "DW":
                                        tempItem = new Staff(splitted[1] + " " + splitted[2], Integer.parseInt(splitted[3]), Integer.parseInt(splitted[4]), Integer.parseInt(splitted[5]), Integer.parseInt(splitted[6]));
                                        break;
                                }
                                break;
                        }
                        getPlayer().getInventory()[k] = tempItem;
                        System.out.println(k + ". " + getPlayer().getInventory()[k]);
                        k++;
                    }
                }

                for (int i = 0; i < 5; i++) {
                    Item tempItem = null;
                    String temp = loader.nextLine();
                    if (temp.length() == 4) {
                        getPlayer().getEquipment()[i] = null;
                    } else {
                        String[] splitted = temp.split(" ");
                        switch (i) {
                            case 0:
                                tempItem = new Helmet(splitted[0] + " " + splitted[1], Integer.parseInt(splitted[2]), Integer.parseInt(splitted[3]), Integer.parseInt(splitted[4]), Integer.parseInt(splitted[5]));
                                break;
                            case 1:
                                tempItem = new Armor(splitted[0] + " " + splitted[1], Integer.parseInt(splitted[2]), Integer.parseInt(splitted[3]), Integer.parseInt(splitted[4]), Integer.parseInt(splitted[5]));
                                break;
                            case 2:
                                tempItem = new Shoes(splitted[0] + " " + splitted[1], Integer.parseInt(splitted[2]), Integer.parseInt(splitted[3]), Integer.parseInt(splitted[4]), Integer.parseInt(splitted[5]));
                                break;
                            case 3:
                                tempItem = new Gloves(splitted[0] + " " + splitted[1], Integer.parseInt(splitted[2]), Integer.parseInt(splitted[3]), Integer.parseInt(splitted[4]), Integer.parseInt(splitted[5]));
                                break;
                            case 4:
                                switch (getPlayerClass()) {
                                    case "DK":
                                        tempItem = new Sword(splitted[0] + " " + splitted[1], Integer.parseInt(splitted[2]), Integer.parseInt(splitted[3]), Integer.parseInt(splitted[4]), Integer.parseInt(splitted[5]));
                                        break;
                                    case "ELF":
                                        tempItem = new Bow(splitted[0] + " " + splitted[1], Integer.parseInt(splitted[2]), Integer.parseInt(splitted[3]), Integer.parseInt(splitted[4]), Integer.parseInt(splitted[5]));
                                        break;
                                    case "DW":
                                        tempItem = new Staff(splitted[0] + " " + splitted[1], Integer.parseInt(splitted[2]), Integer.parseInt(splitted[3]), Integer.parseInt(splitted[4]), Integer.parseInt(splitted[5]));
                                        break;
                                }
                                break;
                        }
                    }
                    getPlayer().getEquipment()[i] = tempItem;
                }
            }
            setCorrect(true);
        } catch (NullPointerException e) {
            System.err.println("Hibás fájl");
        }
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }

    public Scanner getLoader() {
        return loader;
    }

    public void setLoader(Scanner loader) {
        this.loader = loader;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
