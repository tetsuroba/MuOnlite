package engine;

import characters.Player;

/**
 *
 * @author Vass Róbert
 */
public class Start {

    static Player player = null;

    public void start() {

        GraphicsInterface start = new ClassSelection(600, 350);
        while (!start.isValidStart()) {
            System.out.print("");
            if (start.isValidStart()) {
                player = start.getPlayer();
                start.disposeGui();
                GraphicsInterface game = new GameWindow(1000, 550, player);
            }
        }
    }

}
