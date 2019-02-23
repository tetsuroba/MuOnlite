package engine;

import characters.Player;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class GraphicsInterface {

    private JFrame gui;
    private JPanel panel;
    private Player player = null;
    private TextField name = new TextField("Játékosnév ide", 20);
    private boolean validStart = false;

    public GraphicsInterface(int width, int height) {
        setValidStart(false);
        setGui(new JFrame("MuOnlite"));
        setPanel(new JPanel());
        getGui().add(getPanel());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        gui.setBounds((screenSize.width / 2) - (width / 2), (screenSize.height / 2) - (height / 2), width, height);
        panel.setBounds((screenSize.width / 2) - (width / 2), (screenSize.height / 2) - (height / 2), width, height);
        getGui().setResizable(false);
        getPanel().setLayout(null);
    }

    public void disposeGui() {
        getGui().dispose();
        getPanel().setEnabled(false);
    }

    public static String whiteText(String text) {
        return "<html><font color='white'>" + text + "</font></html>";
    }

    public JFrame getGui() {
        return gui;
    }

    public void setGui(JFrame gui) {
        this.gui = gui;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public TextField getName() {
        return name;
    }

    public void setName(TextField name) {
        this.name = name;
    }

    public boolean isValidStart() {
        return validStart;
    }

    public void setValidStart(boolean validStart) {
        this.validStart = validStart;
    }

}
