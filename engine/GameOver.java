package engine;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 *
 * @author Robi
 */
public class GameOver extends GraphicsInterface {

    private final JLabel lost = new JLabel(whiteText("Vesztettél!"));
    private JButton exit;

    public GameOver(int width, int height) {
        super(width, height);
        getGui().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addLostText();
        addRestartButton();
        getPanel().setOpaque(true);
        getPanel().setBackground(Color.DARK_GRAY);
        getGui().setVisible(true);
        getPanel().setVisible(true);
    }

    public void addLostText() {
        lost.setBounds(183, 70, 200, 80);
        getPanel().add(lost);
        lost.setVisible(true);
        getPanel().repaint();
    }

    public void addRestartButton() {
        exit = new JButton("Kilépés");
        exit.setBounds(175, 180, 80, 30);
        getPanel().add(exit);
        exit.setVisible(true);
        getPanel().repaint();
        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
    }

}
