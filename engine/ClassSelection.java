package engine;

import characters.DarkKnight;
import characters.DarkWizard;
import characters.Elf;
import characters.Player;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class ClassSelection extends GraphicsInterface {

    private Player player = null;
    private boolean usingSave;
    private boolean pressed = false;
    private JLabel loadButton;
    LoadSave load = new LoadSave();

    public ClassSelection(int width, int height) {
        super(width, height);
        getGui().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addClassLabels(getPanel());
        createLabel(220, 160, 200, 50, whiteText("Melyik osztályt választod?"), getPanel());
        addNameField(205, 110, 200, 30, getPanel());
        addLoadButton(220, 25, 168, 75, getPanel());

        getPanel().setOpaque(true);
        getPanel().setBackground(Color.DARK_GRAY);
        getGui().setVisible(true);
        getPanel().setVisible(true);

    }

    public void addLoadButton(int posX, int posY, int width, int height, JPanel panel) {
        setLoadButton(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("res/loadButton.png"))));
        getLoadButton().setBounds(posX, posY, width, height);
        panel.add(getLoadButton());
        getLoadButton().setVisible(true);
        panel.repaint();

        getLoadButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    JFileChooser browser = new JFileChooser();
                    browser.setCurrentDirectory(new java.io.File("/"));
                    browser.setDialogTitle("Test");
                    browser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    if (browser.showOpenDialog(getLoadButton()) == JFileChooser.APPROVE_OPTION) {
                        load.openFile(browser.getSelectedFile().getAbsolutePath());
                        load.readFile();
                        if (load.isCorrect()) {
                            setPlayer(load.getPlayer());
                            setValidStart(true);
                        }
                    }
                } catch (Exception exc) {
                    System.err.println("Hibás fájl");
                }

            }
        });
    }

    public void addNameField(int posX, int posY, int width, int height, JPanel panel) {
        getName().setBounds(posX, posY, width, height);
        getName().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                getName().setText("");
                getName().setForeground(Color.BLACK);
            }
        });
        panel.add(getName());
        getName().setVisible(true);
    }

    public void createLabel(int posX, int posY, int width, int height, String contains, JPanel panel) {
        JLabel label = new JLabel();
        label.setBounds(posX, posY, width, height);
        label.setText(contains);
        label.setVisible(true);
        panel.add(label);
    }

    public void addClassLabels(JPanel panel) {
        JLabel dw = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("res/darkWizard.png")));
        JLabel elf = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("res/elf.png")));
        JLabel dk = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("res/darkKnight.png")));

        dw.setBounds(25, 230, 168, 75);
        elf.setBounds(210, 230, 168, 75);
        dk.setBounds(395, 230, 168, 75);

        panel.add(dw);
        panel.add(elf);
        panel.add(dk);

        dw.setVisible(true);
        elf.setVisible(true);
        dk.setVisible(true);
        panel.repaint();
        dw.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (permittedName() == false) {
                    getName().setText("Nem megengedett név!");
                    getName().setForeground(Color.RED);
                } else if (getName().getText().length() > 15) {
                    getName().setText("Túl hosszú név!");
                    getName().setForeground(Color.RED);
                } else {
                    setPlayer(new DarkWizard(getName().getText()));
                    getPlayer().setName(getName().getText());
                    setValidStart(true);
                }
            }
        });

        elf.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if ((permittedName() == false)) {
                    getName().setText("Nem megengedett név!");
                    getName().setForeground(Color.RED);
                } else if (getName().getText().length() > 15) {
                    getName().setText("Túl hosszú név!");
                    getName().setForeground(Color.RED);
                } else {
                    setPlayer(new Elf(getName().getText()));
                    getPlayer().setName(getName().getText());
                    setValidStart(true);
                }
            }
        });

        dk.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if ((permittedName() == false)) {
                    getName().setText("Nem megengedett név!");
                    getName().setForeground(Color.RED);
                } else if (getName().getText().length() > 15) {
                    getName().setText("Túl hosszú név!");
                    getName().setForeground(Color.RED);
                } else {
                    setPlayer(new DarkKnight(getName().getText()));
                    getPlayer().setName(getName().getText());
                    setValidStart(true);
                }
            }
        });
    }

    public boolean permittedName() {
        boolean permitted = true;
        if (getName().getText().equals("") || getName().getText().equals("Nem megengedett név!") || getName().getText().equals("Túl hosszú név!") || getName().getText().equals("Játékosnév ide")) {
            permitted = false;
        }
        return permitted;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isUsingSave() {
        return usingSave;
    }

    public void setUsingSave(boolean usingSave) {
        this.usingSave = usingSave;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public JLabel getLoadButton() {
        return loadButton;
    }

    public void setLoadButton(JLabel loadButton) {
        this.loadButton = loadButton;
    }

}
