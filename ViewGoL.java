import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class ViewGoL {
    ControllerGoL controller;
    JFrame vindu;

    ViewGoL(ControllerGoL controller) {
        this.controller = controller;
        vindu = opprettVindu("Game of Life");
    }

    public JFrame opprettVindu(String navn) {
        try {
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName()
            );
        } catch(Exception e) {
            System.exit(1);
        }

        JFrame vindu = new JFrame(navn);
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        vindu.pack();
        vindu.setLocationRelativeTo(null);
        vindu.setVisible(true);
        return vindu;
    }

    

}
