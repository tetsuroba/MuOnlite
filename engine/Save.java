package engine;

import characters.DarkKnight;
import characters.DarkWizard;
import characters.Elf;
import characters.Player;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Save {

    private String newLine = System.getProperty("line.separator");
    private BufferedWriter saveWriter = null;
    private FileWriter fileW = null;

    public void initSave(Player player, String path) {
        System.out.println("Mentés: " + path);
        try {
            setFileW(new FileWriter(path));
            setSaveWriter(new BufferedWriter(fileW));
            createSave(player, getSaveWriter());
        } catch (IOException ex) {
            System.err.println("Hiba");
        }
    }

    public void createSave(Player player, BufferedWriter w) throws IOException {
        try {
            if (player instanceof DarkKnight) {
                w.write("DK" + getNewLine());
            } else if (player instanceof Elf) {
                w.write("ELF" + getNewLine());
            } else if (player instanceof DarkWizard) {
                w.write("DW" + getNewLine());
            }
            w.write(player.getName() + getNewLine());
            w.write(player.getLevel() + getNewLine());
            w.write(player.getXp() + getNewLine());
            w.write(player.getStr() + getNewLine());
            w.write(player.getAgi() + getNewLine());
            w.write(player.getStam() + getNewLine());
            w.write(player.getEnergy() + getNewLine());
            for (int i = 0; i < 20; i++) {
                if (player.getInventory()[i] == null) {
                    w.write("Ures" + getNewLine());
                } else {
                    w.write(player.getInventory()[i] + getNewLine());
                }
            }
            for (int i = 0; i < 5; i++) {
                if (player.getEquipment()[i] == null) {
                    w.write("Ures" + getNewLine());
                } else {
                    w.write(player.getEquipment()[i].printEquipment() + getNewLine());
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            w.close();
        }

    }

    public BufferedWriter getSaveWriter() {
        return saveWriter;
    }

    public void setSaveWriter(BufferedWriter saveWriter) {
        this.saveWriter = saveWriter;
    }

    public FileWriter getFileW() {
        return fileW;
    }

    public void setFileW(FileWriter fileW) {
        this.fileW = fileW;
    }

    public String getNewLine() {
        return newLine;
    }

    public void setNewLine(String newLine) {
        this.newLine = newLine;
    }

}
