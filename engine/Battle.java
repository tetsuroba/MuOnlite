package engine;

import characters.DarkKnight;
import characters.DarkWizard;
import characters.Elf;
import characters.Enemy;
import characters.Player;
import items.Armor;
import items.Bow;
import items.Gloves;
import items.Helmet;
import items.Item;
import items.Shoes;
import items.Staff;
import items.Sword;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author Robi
 */
public abstract class Battle {

    private String difficulty;
    private int xpLost;
    private int xpWon;
    private Enemy enemy;
    private Item drop;
    private boolean outcome = false;
    private JLabel[] playerStats;
    private JLabel[] enemyStats;
    private JFrame battleReport;
    private JPanel battleReportPanel;

    public Battle(String difficulty, int xpLost, JPanel upperPanel, JPanel lowerPanel) {
        setDifficulty(difficulty);
        setXpLost(xpLost);
        setupBattleReport(upperPanel, lowerPanel);
    }

    /**
     * @description @param upperPanel A felső panel ami a játékos stajait és a
     * csata adatatit mutatja
     * @param lowerPanel Az alsó panel/főpanel amin minden van
     */
    public void setupBattleReport(JPanel upperPanel, JPanel lowerPanel) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setBattleReport(new JFrame("MuOnlite : Csata eredménye"));
        setBattleReportPanel(new JPanel());
        getBattleReport().setBounds((screenSize.width / 2) - (500 / 2), (screenSize.height / 2) - (350 / 2), 500, 350);
        getBattleReport().setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        getBattleReportPanel().setBounds((screenSize.width / 2) - (500 / 2), (screenSize.height / 2) - (350 / 2), 500, 350);
        getBattleReportPanel().setLayout(null);
        getBattleReportPanel().setOpaque(true);
        getBattleReportPanel().setBackground(Color.LIGHT_GRAY);
        getBattleReport().add(getBattleReportPanel());
        getBattleReportPanel().setVisible(true);

        resetBattleReport(upperPanel, lowerPanel);

        getBattleReport().setResizable(false);
        getBattleReport().setVisible(false);
    }

    public void resetBattleReport(JPanel upperPanel, JPanel lowerPanel) {
        JLabel outcomeLabel = new JLabel("Eredmény:");
        JLabel xpReceived = new JLabel("Kapott XP:");
        JLabel item = new JLabel("Talált tárgy: ");

        outcomeLabel.setBounds(30, 50, 200, 30);
        xpReceived.setBounds(30, 100, 200, 30);
        item.setBounds(30, 150, 200, 30);

        outcomeLabel.setVisible(true);
        xpReceived.setVisible(true);
        item.setVisible(true);

        getBattleReportPanel().add(outcomeLabel);
        getBattleReportPanel().add(xpReceived);
        getBattleReportPanel().add(item);

        JButton accept = new JButton("Rendben");

        accept.setBounds(175, 225, 150, 70);

        accept.setVisible(true);

        getBattleReportPanel().add(accept);

        accept.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                getBattleReport().setVisible(false);
                getBattleReportPanel().removeAll();
                upperPanel.setVisible(false);
                upperPanel.removeAll();
                upperPanel.repaint();
                lowerPanel.setVisible(true);
            }
        });
    }

    public void battleStart(Player player, JPanel upperPanel, JPanel lowerPanel, GameWindow w) {
        resetBattleReport(upperPanel, lowerPanel);
        getBattleReport().setVisible(true);
        Random rand = new Random();
        boolean playerWon;
        final double chanceSum = player.calcChance(player.getStr(), player.getAgi(), player.getStam(), player.getEnergy()) + getEnemy().getChance();
        double playerWinChance = ((player.calcChance(player.getStr(), player.getAgi(), player.getStam(), player.getEnergy())) / chanceSum) * 100;
        double enemyWinChance = ((getEnemy().getChance()) / chanceSum) * 100;

        setPlayerStats(new JLabel[6]);
        setEnemyStats(new JLabel[6]);
        getPlayerStats()[0] = new JLabel(whiteText(player.getName() + " statjai:"));
        getPlayerStats()[1] = new JLabel(whiteText(String.valueOf("Erő: " + player.getStr())));
        getPlayerStats()[2] = new JLabel(whiteText(String.valueOf("Ügyesség: " + player.getAgi())));
        getPlayerStats()[3] = new JLabel(whiteText(String.valueOf("Kitartás: " + player.getStam())));
        getPlayerStats()[4] = new JLabel(whiteText(String.valueOf("Energia: " + player.getEnergy())));
        getPlayerStats()[5] = new JLabel(whiteText(String.valueOf("Nyerés esélye: " + (int) playerWinChance + "%")));

        getEnemyStats()[0] = new JLabel(whiteText(enemy.getName() + " statjai:"));
        getEnemyStats()[1] = new JLabel(whiteText(String.valueOf("Erő: " + enemy.getStr())));
        getEnemyStats()[2] = new JLabel(whiteText(String.valueOf("Ügyesség: " + enemy.getAgi())));
        getEnemyStats()[3] = new JLabel(whiteText(String.valueOf("Kitartás: " + enemy.getStam())));
        getEnemyStats()[4] = new JLabel(whiteText(String.valueOf("Energia: " + enemy.getEnergy())));
        getEnemyStats()[5] = new JLabel(whiteText(String.valueOf("Nyerés esélye: " + (int) enemyWinChance) + "%"));

        for (int i = 0; i < 6; i++) {
            getPlayerStats()[i].setBounds(25, i * 50 + 75, 200, 50);
            getEnemyStats()[i].setBounds(420, i * 50 + 75, 200, 50);
            upperPanel.add(getPlayerStats()[i]);
            upperPanel.add(getEnemyStats()[i]);
            getPlayerStats()[i].setVisible(true);
            getEnemyStats()[i].setVisible(true);
            upperPanel.repaint();
        }

        int win = rand.nextInt(100) + 1;
        if (playerWinChance > enemyWinChance) {
            if (win <= enemyWinChance) {
                playerWon = false;

            } else {
                playerWon = true;
                roll(rand, player, getEnemy());

            }
        } else {
            if (win <= playerWinChance) {
                playerWon = true;
                roll(rand, player, getEnemy());
            } else {
                playerWon = false;
            }
        }
        if (playerWon == false) {
            addLoserLabel();
        } else {
            addWinnerLabel();
        }
        awardXp(player, playerWon, getEnemy(), w);
        upperPanel.revalidate();
        upperPanel.repaint();
    }

    public void addLoserLabel() {
        JLabel loser = new JLabel("Veszettél!");
        loser.setBounds(230, 50, 200, 30);
        loser.setVisible(true);
        JLabel itemFound = new JLabel("Semmi");
        itemFound.setBounds(230, 150, 300, 30);
        itemFound.setVisible(true);
        getBattleReportPanel().add(itemFound);
        getBattleReportPanel().add(loser);
    }

    public void addWinnerLabel() {
        JLabel winner = new JLabel("Nyertél!");
        winner.setBounds(230, 50, 200, 30);
        winner.setVisible(true);
        getBattleReportPanel().add(winner);
    }

    public void roll(Random rand, Player player, Enemy enemy) {
        int dropRoll = rand.nextInt(100) + 1;
        if (dropRoll <= 40) {
            itemDrop(player, enemy);
        } else {
            JLabel itemFound = new JLabel("Semmi");
            itemFound.setBounds(230, 150, 300, 30);
            itemFound.setVisible(true);
            getBattleReportPanel().add(itemFound);
        }
    }

    public void itemDrop(Player player, Enemy enemy) {
        Random r = new Random();
        int itemRoll = r.nextInt(5) + 1;
        switch (itemRoll) {
            case 1:
                setDrop(new Helmet(enemy.getLevel()));
                break;
            case 2:
                setDrop(new Armor(enemy.getLevel()));
                break;

            case 3:
                setDrop(new Shoes(enemy.getLevel()));
                break;

            case 4:
                setDrop(new Gloves(enemy.getLevel()));
                break;

            case 5:
                if (player instanceof DarkKnight) {
                    setDrop(new Sword(enemy.getLevel()));
                    break;
                } else if (player instanceof DarkWizard) {
                    setDrop(new Staff(enemy.getLevel()));
                    break;
                } else if (player instanceof Elf) {
                    setDrop(new Bow(enemy.getLevel()));
                    break;
                }
        }

        if (player.freeSlot() == 0) {
            JLabel itemFound = new JLabel("Tárgyat találtál, viszont tele van a táskád!");
            itemFound.setBounds(230, 150, 300, 30);
            itemFound.setVisible(true);
            getBattleReportPanel().add(itemFound);
        } else {
            JLabel itemFound = new JLabel(getDrop().getName());
            itemFound.setBounds(180, 150, 300, 30);
            itemFound.setVisible(true);
            getBattleReportPanel().add(itemFound);
            addStats(getDrop());
            player.getInventory()[player.smallestSlot()] = getDrop();
        }

    }

    public void addStats(Item drop) {
        //System.out.println(drop.toString());
        JLabel stats = new JLabel("Statok: ");
        JLabel str = new JLabel("Erő: " + String.valueOf(drop.getStr()));
        JLabel agi = new JLabel("Ügyesség: " + String.valueOf(drop.getAgi()));
        JLabel stam = new JLabel("Kitartás: " + String.valueOf(drop.getStam()));
        JLabel energy = new JLabel("Energia: " + String.valueOf(drop.getEnergy()));

        stats.setBounds(350, 150, 200, 30);
        str.setBounds(350, 170, 200, 30);
        agi.setBounds(350, 190, 200, 30);
        stam.setBounds(350, 210, 200, 30);
        energy.setBounds(350, 230, 200, 30);

        getBattleReportPanel().add(stats);
        getBattleReportPanel().add(str);
        getBattleReportPanel().add(agi);
        getBattleReportPanel().add(stam);
        getBattleReportPanel().add(energy);

        stats.setVisible(true);
        str.setVisible(true);
        agi.setVisible(true);
        stam.setVisible(true);
        energy.setVisible(true);

        getBattleReportPanel().repaint();

    }

    public abstract int xpCalc(Enemy enemy);

    public void awardXp(Player player, boolean playerWon, Enemy enemy, GameWindow w) {
        if (playerWon) {
            setXpWon(xpCalc(enemy));
            addXpWonLabel(getXpWon());
            if (player.getXp() + getXpWon() >= 100) {
                player.levelUp(player.getLifePoints(), player.getLevel(), player.getStr(), player.getAgi(), player.getStam(), player.getEnergy());
                player.setXp((player.getXp() + getXpWon()) - 100);
            } else {
                player.setXp(player.getXp() + getXpWon());
            }
        } else {
            int newHP = player.getLifePoints() - 1;
            player.setLifePoints(newHP);
            if (player.getLifePoints() <= 0) {
                GameOver gameOver = new GameOver(450, 350);
                getBattleReport().setVisible(false);
                w.disposeGui();

            }
            addXpWonLabel(getXpLost());
            if (player.getXp() + getXpLost() >= 100) {
                player.levelUp(player.getLifePoints(), player.getLevel(), player.getStr(), player.getAgi(), player.getStam(), player.getEnergy());
                player.setXp((player.getXp() + getXpLost()) - 100);
            } else {
                player.setXp(player.getXp() + getXpLost());
            }
        }
    }

    public void addXpWonLabel(int xpWon) {
        JLabel xpWonLabel = new JLabel(String.valueOf(xpWon));
        xpWonLabel.setBounds(230, 100, 200, 30);
        xpWonLabel.setVisible(true);
        getBattleReportPanel().add(xpWonLabel);
    }

    public String whiteText(String text) {
        return "<html><font color='white'>" + text + "</font></html>";
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getXpLost() {
        return xpLost;
    }

    public void setXpLost(int xpLost) {
        this.xpLost = xpLost;
    }

    public int getXpWon() {
        return xpWon;
    }

    public void setXpWon(int xpWon) {
        this.xpWon = xpWon;
    }

    public boolean isOutcome() {
        return outcome;
    }

    public void setOutcome(boolean outcome) {
        this.outcome = outcome;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public Item getDrop() {
        return drop;
    }

    public void setDrop(Item drop) {
        this.drop = drop;
    }

    public JLabel[] getPlayerStats() {
        return playerStats;
    }

    public void setPlayerStats(JLabel[] playerStats) {
        this.playerStats = playerStats;
    }

    public JLabel[] getEnemyStats() {
        return enemyStats;
    }

    public void setEnemyStats(JLabel[] enemyStats) {
        this.enemyStats = enemyStats;
    }

    public JFrame getBattleReport() {
        return battleReport;
    }

    public void setBattleReport(JFrame battleReport) {
        this.battleReport = battleReport;
    }

    public JPanel getBattleReportPanel() {
        return battleReportPanel;
    }

    public void setBattleReportPanel(JPanel battleReportPanel) {
        this.battleReportPanel = battleReportPanel;
    }

}
