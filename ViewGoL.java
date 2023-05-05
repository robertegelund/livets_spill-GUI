import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class ViewGoL {
    ControllerGoL controller;
    JFrame vindu; JPanel hovedpanel, rutenett, statuspanel;
    JButton start, avslutt; JLabel antLevende; JLabel generasjon;
    int antRad, antKol;
    Celleknapp[][] knapper; 

    ViewGoL(ControllerGoL controller, int antRad, int antKol) {
        this.controller = controller;
        this.antRad = antRad; this.antKol = antKol;
        knapper = new Celleknapp[antRad][antKol];
        vindu = lagVindu("Game of Life");
        
        opprettAllePaneler();
        fyllStatuspanel();
        lagCelleknapper();
        fyllRutenettPanelMedStartinfo();

        vindu.pack();
        vindu.setLocationRelativeTo(null);
        vindu.setVisible(true);
    }

    private JFrame lagVindu(String navn) {
        try {
            UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName()
            );
        } catch(Exception e) {
            System.exit(1);
        }

        JFrame vindu = new JFrame(navn);
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return vindu;
    }

    public void oppdaterRutenett() {
        for(int i = 0; i < antRad; i++) {
            for(int j = 0; j < antKol; j++) {
                knapper[i][j].setText(controller.hentStatustegnCelle(i, j));
            }
        }
    }

    private void opprettAllePaneler() {
        hovedpanel = lagPanelMedBorderLayout();
        statuspanel = lagPanelMedGridLayout(1, 4);
        rutenett = lagPanelMedGridLayout(antRad, antKol);
        hovedpanel.add(statuspanel, BorderLayout.NORTH);
        hovedpanel.add(rutenett, BorderLayout.SOUTH);
        vindu.add(hovedpanel);
    }

    private JPanel lagPanelMedBorderLayout() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        return panel;
    }

    private JPanel lagPanelMedGridLayout(int antRad, int antKol) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(antRad, antKol));
        return panel;
    }

    private void fyllStatuspanel() {
        antLevende = new JLabel("# levende: --");
        generasjon = new JLabel("Generasjon #: --");
        start = new JButton("Start"); avslutt = new JButton("Avslutt");
        start.addActionListener(new startHaandtering());
        avslutt.addActionListener(new sluttHaandtering());
        statuspanel.add(antLevende); statuspanel.add(generasjon);
        statuspanel.add(start); statuspanel.add(avslutt);
    }

    public void oppdaterAntLevende(int nyttAntall) {
        antLevende.setText("# levende: " + Integer.toString(nyttAntall));
    }

    public void oppdaterGenerasjon(int nesteGenerasjon) {
        generasjon.setText("Generasjon #: " + Integer.toString(nesteGenerasjon));
    }

    private void fyllRutenettPanelMedStartinfo() {
        rutenett.setPreferredSize(new Dimension(50*antRad, 50*antKol));
        rutenett.add(new JLabel("Trykk Start for aa starte Game of Life!"));
    }

    private void lagCelleknapper() {
        for(int i = 0; i < antRad; i++) {
            for(int j = 0; j < antKol; j++) {
                knapper[i][j] = new Celleknapp(
                    controller.hentStatustegnCelle(i, j), 
                    i, j
                );
            }
        }
    }

    public void visCelleknapper() {
        for(int i = 0; i < antRad; i++) {
            for(int j = 0; j < antKol; j++) {
                Celleknapp knapp = knapper[i][j];
                knapp.setPreferredSize(new Dimension(50, 50));
                rutenett.add(knapp);
            }
        }
    }

    public void endreCelleknappTegn(String nyttStatustegn, int rad, int kol) {
        knapper[rad][kol].setText(nyttStatustegn);
    }

    class startHaandtering implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.startSpillet();
        }
    }

    class sluttHaandtering implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.avsluttSpillet();
        }
    }

    class Celleknapp extends JButton {
        int rad, kolonne;

        Celleknapp(String tekst, int rad, int kol) {
            super(tekst);
            this.rad = rad; this.kolonne = kol;
            this.addActionListener(new actionListenerCelleknapp());
        }

        class actionListenerCelleknapp implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.oppdaterCellestatus(rad, kolonne);
            }
        }
    }

    public void toemRutenettPanel() {
        rutenett.removeAll();
    }
}
