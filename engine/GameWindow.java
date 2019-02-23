package engine;

import characters.Player;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class GameWindow extends GraphicsInterface {

    private JLabel inventoryIcon;
    private JLabel equipmentIcon;
    private JLabel statsIcon;
    private JPanel inventoryPanel;
    private JPanel equipmentPanel;
    private JPanel statsPanel;
    private JPanel battleButtonPanel;
    private JPanel battlePanel;
    private boolean inventoryOpen = false;
    private boolean statsOpen = false;
    private boolean equipmentOpen = false;
    private boolean statsChecked = false;
    private boolean battleOnGoing = false;
    private Player player;
    private Battle battle;
    private JLabel saveButton;
    private JLabel[] stats;
    private JLabel[] items;
    private JLabel[] battleButtons;
    private Save save = new Save();

    public GameWindow(int width, int height, Player player) {
        super(width, height);

        setPlayer(player);

        getGui().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addInventory(-10, 460, 100, 70, getPanel(), "res/invicon.png", getPlayer());
        setupInventoryPanel(getPanel(), getPlayer());

        addEquipment(85, 457, 100, 70, getPanel(), "res/equipicon.png", getPlayer());
        setupEquipmentPanel(getPanel(), getPlayer());

        addStats(180, 457, 100, 70, getPanel(), "res/statsicon.png");
        setupStatsPanel(getPanel(), getPlayer());

        setupBattleButtonPanel(getPanel(), getPlayer());
        setupBattlePanel(getPanel());

        addSaveButton(520, 450, 168, 75, getBattleButtonPanel());

        getPanel().setOpaque(true);
        getPanel().setBackground(java.awt.Color.DARK_GRAY);

        getGui().setVisible(true);

        getPanel().setVisible(true);
    }

    /**
     * A fő panelhez hozzáad egy mellékpanelt amin gombok vannak a csatához
     *
     * @param panel A fő panel amin a sub panel lesz
     * @param player A játékos
     */
    public void setupBattleButtonPanel(JPanel panel, Player player) {

        setBattleButtons(new JLabel[3]);
        getBattleButtons()[0] = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("res/easyBattle.png")));
        getBattleButtons()[1] = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("res/mediumBattle.png")));
        getBattleButtons()[2] = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("res/hardBattle.png")));
        setBattleButtonPanel(new JPanel());
        getBattleButtonPanel().setLayout(null);
        getBattleButtonPanel().setOpaque(true);
        getBattleButtonPanel().setBackground(java.awt.Color.DARK_GRAY);
        getBattleButtonPanel().setBounds(310, 0, 700, 550);
        for (int i = 0; i < 3; i++) {
            getBattleButtons()[i].setBounds(i * 230 + 25, 225, 168, 78);
            getBattleButtonPanel().add(getBattleButtons()[i]);
            getBattleButtons()[i].setVisible(true);
            updatePlayerStats(player);
            updateInventory(getPanel(), player);
        }
        getBattleButtons()[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                getBattleButtonPanel().setVisible(false);
                startEasyBattle(getBattlePanel(), player);
                updatePlayerStats(player);
                setInventoryOpen(false);
                getInventoryPanel().setVisible(isInventoryOpen());
            }
        });
        getBattleButtons()[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                getBattleButtonPanel().setVisible(false);
                startMediumBattle(getBattlePanel(), player);
                updatePlayerStats(player);
                setInventoryOpen(false);
                getInventoryPanel().setVisible(isInventoryOpen());
            }
        });

        getBattleButtons()[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                getBattleButtonPanel().setVisible(false);
                startHardBattle(getBattlePanel(), player);
                updatePlayerStats(player);
                setInventoryOpen(false);
                getInventoryPanel().setVisible(isInventoryOpen());
            }
        });

        panel.add(getBattleButtonPanel());

        getBattleButtonPanel().setVisible(true);
    }

    /**
     * A mentés gombot adja hozzá
     *
     * @param posX gomb pozíciója
     * @param posY
     * @param width gomb mérete
     * @param height
     * @param panel a panel amin rajta van
     */
    private void addSaveButton(int posX, int posY, int width, int height, JPanel panel) {
        setSaveButton(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("res/saveButton.png"))));
        getSaveButton().setBounds(posX, posY, width, height);
        panel.add(getSaveButton());
        getSaveButton().setVisible(true);
        panel.repaint();

        getSaveButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser browser = new JFileChooser();
                browser.setCurrentDirectory(new java.io.File("/"));
                browser.setDialogTitle("Test");
                browser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (browser.showOpenDialog(getSaveButton()) == JFileChooser.APPROVE_OPTION) {
                    getSave().initSave(getPlayer(), browser.getSelectedFile().getAbsolutePath() + "/" + getPlayer().getName() + ".txt");
                }
            }
        });
    }

    private void setupBattlePanel(JPanel panel) {
        setBattlePanel(new JPanel());

        getBattlePanel().setLayout(null);

        getBattlePanel().setBounds(310, 0, 690, 550);

        getBattlePanel().setOpaque(true);

        getBattlePanel().setBackground(java.awt.Color.DARK_GRAY);

        panel.add(getBattlePanel());

        getBattlePanel().setVisible(false);
    }

    public void startEasyBattle(JPanel panel, Player player) {
        panel.setVisible(true);
        setBattle(new EasyBattle(player.getLevel(), getBattlePanel(), getBattleButtonPanel()));
        getBattle().battleStart(player, getBattlePanel(), getBattleButtonPanel(), this);

    }

    public void startMediumBattle(JPanel panel, Player player) {
        panel.setVisible(true);
        setBattle(new MediumBattle(player.getLevel(), getBattlePanel(), getBattleButtonPanel()));
        getBattle().battleStart(player, getBattlePanel(), getBattleButtonPanel(), this);

    }

    public void startHardBattle(JPanel panel, Player player) {
        panel.setVisible(true);
        setBattle(new HardBattle(player.getLevel(), getBattlePanel(), getBattleButtonPanel()));
        getBattle().battleStart(player, getBattlePanel(), getBattleButtonPanel(), this);
    }

    ////////////////////////Táska/Felszerelés/Statok gombjai////////////////////////////////////////
    public void addInventory(int posX, int posY, int width, int height, JPanel panel, String path, Player player) {
        setInventoryIcon(new JLabel(new ImageIcon(getClass().getClassLoader().getResource(path))));
        getInventoryIcon().setBounds(posX, posY, width, height);
        panel.add(getInventoryIcon());
        getInventoryIcon().setVisible(true);
        getInventoryIcon().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                bringUpInventory(panel, player);
                updateInventory(getInventoryPanel(), player);
            }
        });
    }

    public void addEquipment(int posX, int posY, int width, int height, JPanel panel, String path, Player player) {
        setEquipmentIcon(new JLabel(new ImageIcon(getClass().getClassLoader().getResource(path))));
        getEquipmentIcon().setBounds(posX, posY, width, height);
        panel.add(getEquipmentIcon());
        getEquipmentIcon().setVisible(true);
        panel.repaint();
        getEquipmentIcon().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                bringUpEquipment(panel, player);
                updateEquipment(getEquipmentPanel(), player);
            }
        });
    }

    public void addStats(int posX, int posY, int width, int height, JPanel panel, String path) {
        setStatsIcon(new JLabel(new ImageIcon(getClass().getClassLoader().getResource(path))));
        getStatsIcon().setBounds(posX, posY, width, height);
        panel.add(getStatsIcon());
        getStatsIcon().setVisible(true);
        getStatsIcon().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                updatePlayerStats(getPlayer());
                bringUpStats(panel, getPlayer());
            }
        });
    }
//////////////////// Táska/Felszerelés/Statok panel /////////////////////////////////////

    /**
     * Feltölti a táska panelt a játékos tárgyaival
     *
     * @param panel
     * @param player
     */
    public void setupInventoryPanel(JPanel panel, Player player) {
        setItems(new JLabel[20]);
        setInventoryPanel(new JPanel());
        getInventoryPanel().setLayout(null);
        getInventoryPanel().setOpaque(true);
        getInventoryPanel().setBackground(java.awt.Color.DARK_GRAY);
        getInventoryPanel().setBounds(0, 0, 300, 450);
        panel.add(getInventoryPanel());
        getInventoryPanel().setVisible(false);
        int k = 0;
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 4; i++) {
                if (player.getInventory()[k] == null) {
                    getItems()[k] = null;
                } else {
                    getItems()[k] = (player.getInventory()[k].addItem(75 * i, 96 * j, getInventoryPanel(), player, k, this));
                    getInventoryPanel().add(getItems()[k]);
                    getInventoryPanel().repaint();
                }
                k++;
            }
        }
    }

    public void setupEquipmentPanel(JPanel panel, Player player) {
        setEquipmentPanel(new JPanel());
        getEquipmentPanel().setLayout(null);
        getEquipmentPanel().setBounds(0, 0, 300, 450);
        getEquipmentPanel().setOpaque(true);
        getEquipmentPanel().setBackground(java.awt.Color.DARK_GRAY);
        panel.add(getEquipmentPanel());
        getEquipmentPanel().setVisible(false);
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0:
                    equipmentCheck(player, i, getEquipmentPanel(), "helmet");
                    break;
                case 1:
                    equipmentCheck(player, i, getEquipmentPanel(), "armor");
                    break;
                case 2:
                    equipmentCheck(player, i, getEquipmentPanel(), "boots");
                    break;
                case 3:
                    equipmentCheck(player, i, getEquipmentPanel(), "gloves");
                    break;
                case 4:
                    equipmentCheck(player, i, getEquipmentPanel(), "weapon");
                    break;
            }
        }
    }

    public void setupStatsPanel(JPanel panel, Player player) {
        setStatsPanel(new JPanel());
        getStatsPanel().setLayout(null);
        getStatsPanel().setBounds(0, 0, 300, 450);
        getStatsPanel().setOpaque(true);
        getStatsPanel().setBackground(java.awt.Color.DARK_GRAY);
        panel.add(getStatsPanel());
        getStatsPanel().setVisible(false);
        getPlayerStats(getStatsPanel(), player);
        getStatsPanel().repaint();
    }

    ////////////////////////////////////// Táska/Felszerelés/Statok tartalma//////////////////////////////////
    //Táska
    public void updateInventory(JPanel panel, Player player) {
        getInventoryPanel().removeAll();
        int k = 0;
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 4; i++) {
                if (player.getInventory()[k] != null) {
                    getItems()[k] = player.getInventory()[k].addItem(75 * i, 96 * j, panel, player, k, this);
                    panel.add(getItems()[k]);
                    panel.repaint();
                    k++;
                } else {
                    getItems()[k] = null;
                }
            }
        }
        k = 0;
    }

    public void updateEquipment(JPanel panel, Player player) {
        getEquipmentPanel().removeAll();
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0:
                    equipmentCheck(player, i, getEquipmentPanel(), "helmet");
                    break;
                case 1:
                    equipmentCheck(player, i, getEquipmentPanel(), "armor");
                    break;
                case 2:
                    equipmentCheck(player, i, getEquipmentPanel(), "boots");
                    break;
                case 3:
                    equipmentCheck(player, i, getEquipmentPanel(), "gloves");
                    break;
                case 4:
                    equipmentCheck(player, i, getEquipmentPanel(), "weapon");
                    break;
            }
        }

    }

    //Statok
    public void getPlayerStats(JPanel panel, Player player) {
        JLabel[] temp = {
            new JLabel(whiteText(player.getName() + " statjai:")),
            new JLabel(whiteText("Életerőd: " + player.getLifePoints())),
            new JLabel(whiteText("A szinted: " + player.getLevel())),
            new JLabel(whiteText("XP: " + player.getXp())),
            new JLabel(whiteText("Erő: " + player.getStr())),
            new JLabel(whiteText("Ügyesség: " + player.getAgi())),
            new JLabel(whiteText("Kitartás: " + player.getStam())),
            new JLabel(whiteText("Energia: " + player.getEnergy()))
        };
        setStats(temp);
        for (int i = 0; i < getStats().length; i++) {
            panel.add(getStats()[i]);
            getStats()[i].setBounds(40, i * 50 + 50, 200, 50);
            getStats()[i].setVisible(true);
            panel.repaint();
        }
    }

    public void updatePlayerStats(Player player) {
        getStats()[1].setText(whiteText("Életerőd: " + player.getLifePoints()));
        getStats()[2].setText(whiteText("A szinted: " + player.getLevel()));
        getStats()[3].setText(whiteText("XP: " + player.getXp()));
        getStats()[4].setText(whiteText("Erő: " + player.getStr()));
        getStats()[5].setText(whiteText("Ügyesség: " + player.getAgi()));
        getStats()[6].setText(whiteText("Kitartás: " + player.getStam()));
        getStats()[7].setText(whiteText("Energia: " + player.getEnergy()));
    }

    public void bringUpStats(JPanel panel, Player player) {
        if (isInventoryOpen()) {
            getInventoryPanel().setVisible(false);
            setInventoryOpen(false);
        } else if (isEquipmentOpen()) {
            getEquipmentPanel().setVisible(false);
            setEquipmentOpen(false);
        } else if (isStatsOpen()) {
            getStatsPanel().setVisible(false);
            setStatsOpen(false);
        } else if (!isStatsOpen()) {
            getStatsPanel().setVisible(true);
            setStatsOpen(true);
        }
    }

//Táska
    public void bringUpInventory(JPanel panel, Player player) {
        if (isInventoryOpen()) {
            getInventoryPanel().setVisible(false);
            setInventoryOpen(false);
        } else if (isEquipmentOpen()) {
            getEquipmentPanel().setVisible(false);
            setEquipmentOpen(false);
        } else if (isStatsOpen()) {
            getStatsPanel().setVisible(false);
            setStatsOpen(false);
        } else if (!isInventoryOpen()) {
            getInventoryPanel().setVisible(true);
            setInventoryOpen(true);
        }
    }
//Felszerelés

    public void bringUpEquipment(JPanel panel, Player player) {
        if (isInventoryOpen()) {
            getInventoryPanel().setVisible(false);
            setInventoryOpen(false);
        } else if (isEquipmentOpen()) {
            getEquipmentPanel().setVisible(false);
            setEquipmentOpen(false);
        } else if (isStatsOpen()) {
            getStatsPanel().setVisible(false);
            setStatsOpen(false);
        } else if (!isEquipmentOpen()) {
            getEquipmentPanel().setVisible(true);
            setEquipmentOpen(true);
        }
    }

    public void equipmentCheck(Player player, int i, JPanel panel, String iconName) {
        if (player.getEquipment()[i] == null) {
            JLabel emptySlot = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("res/" + iconName + "Slot.png")));
            emptySlot.setBounds(120, (i * 80) + 30, 70, 70);
            panel.add(emptySlot);
            emptySlot.setVisible(true);
            panel.repaint();
        } else {
            player.getEquipment()[i].addEquipmentItem(120, (i * 80) + 30, panel, player, i, this);
            panel.repaint();
        }
    }

    ///////////////////////////Getter/Setter//////////////////////////////
    public boolean isInventoryOpen() {
        return inventoryOpen;
    }

    public void setInventoryOpen(boolean inventoryOpen) {
        this.inventoryOpen = inventoryOpen;
    }

    public boolean isStatsOpen() {
        return statsOpen;
    }

    public void setStatsOpen(boolean statsOpen) {
        this.statsOpen = statsOpen;
    }

    public boolean isEquipmentOpen() {
        return equipmentOpen;
    }

    public void setEquipmentOpen(boolean equipmentOpen) {
        this.equipmentOpen = equipmentOpen;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public JLabel getInventoryIcon() {
        return inventoryIcon;
    }

    public void setInventoryIcon(JLabel inventoryIcon) {
        this.inventoryIcon = inventoryIcon;
    }

    public JLabel getEquipmentIcon() {
        return equipmentIcon;
    }

    public void setEquipmentIcon(JLabel equipmentIcon) {
        this.equipmentIcon = equipmentIcon;
    }

    public JLabel getStatsIcon() {
        return statsIcon;
    }

    public void setStatsIcon(JLabel statsIcon) {
        this.statsIcon = statsIcon;
    }

    public JPanel getInventoryPanel() {
        return inventoryPanel;
    }

    public void setInventoryPanel(JPanel inventoryPanel) {
        this.inventoryPanel = inventoryPanel;
    }

    public JPanel getEquipmentPanel() {
        return equipmentPanel;
    }

    public void setEquipmentPanel(JPanel equipmentPanel) {
        this.equipmentPanel = equipmentPanel;
    }

    public JPanel getStatsPanel() {
        return statsPanel;
    }

    public void setStatsPanel(JPanel statsPanel) {
        this.statsPanel = statsPanel;
    }

    public boolean isStatsChecked() {
        return statsChecked;
    }

    public void setStatsChecked(boolean statsChecked) {
        this.statsChecked = statsChecked;
    }

    public JLabel[] getStats() {
        return stats;
    }

    public void setStats(JLabel[] stats) {
        this.stats = stats;
    }

    public JLabel[] getItems() {
        return items;
    }

    public void setItems(JLabel[] items) {
        this.items = items;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public boolean isBattleOnGoing() {
        return battleOnGoing;
    }

    public void setBattleOnGoing(boolean battleOnGoing) {
        this.battleOnGoing = battleOnGoing;
    }

    public JPanel getBattleButtonPanel() {
        return battleButtonPanel;
    }

    public void setBattleButtonPanel(JPanel battleButtonPanel) {
        this.battleButtonPanel = battleButtonPanel;
    }

    public JLabel[] getBattleButtons() {
        return battleButtons;
    }

    public void setBattleButtons(JLabel[] battleButtons) {
        this.battleButtons = battleButtons;
    }

    public JPanel getBattlePanel() {
        return battlePanel;
    }

    public void setBattlePanel(JPanel battlePanel) {
        this.battlePanel = battlePanel;
    }

    public JLabel getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(JLabel saveButton) {
        this.saveButton = saveButton;
    }

    public Save getSave() {
        return save;
    }

    public void setSave(Save save) {
        this.save = save;
    }
}
