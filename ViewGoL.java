import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class ViewGoL {
    ControllerGoL controller;
    JFrame vindu; JPanel hovedpanel, rutenett, statuspanel;
    int antRad, antKol;

    ViewGoL(ControllerGoL controller, int antRad, int antKol) {
        this.controller = controller;
        this.antRad = antRad; this.antKol = antKol;
        vindu = lagVindu("Game of Life");
        hovedpanel = lagPanelMedBorderLayout();
        statuspanel = lagPanelMedGridLayout(1, 3);
        
        statuspanel.add(new JLabel("Antall levende: " + controller.antallLevende()));
        statuspanel.add(new JButton("Start"));
        statuspanel.add(new JButton("Avslutt"));
        
        rutenett = lagPanelMedGridLayout(antRad, antKol);
        hovedpanel.add(statuspanel, BorderLayout.NORTH);
        hovedpanel.add(rutenett, BorderLayout.SOUTH);
        fyllRutenettMedCelleKnapper(rutenett, controller.hentRutene());
        vindu.add(hovedpanel);
        vindu.pack();
        vindu.setVisible(true);
    }

    public JFrame lagVindu(String navn) {
        try {
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName()
            );
        } catch(Exception e) {
            System.exit(1);
        }

        JFrame vindu = new JFrame(navn);
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vindu.setLocationRelativeTo(null);
        return vindu;
    }

    public JPanel lagPanelMedBorderLayout() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        return panel;
    }

    public JPanel lagPanelMedGridLayout(int antRad, int antKol) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(antRad, antKol));
        return panel;
    }

    public void fyllRutenettMedCelleKnapper(JPanel panel, Celle[][] rutene) {
        for(int i = 0; i < antRad; i++) {
            for(int j = 0; j < antKol; j++) {
                JButton knapp = new JButton(rutene[i][j].hentStatusTegn());
                panel.add(knapp);
            }
        }
    }
}
