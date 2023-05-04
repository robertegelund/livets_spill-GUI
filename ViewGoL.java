import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class ViewGoL {
    ControllerGoL controller;
    JFrame vindu; JPanel hovedpanel, rutenett, statuspanel;
    JButton start, avslutt; JLabel antLevende;
    int antRad, antKol;
    Celleknapp[][] knapper; 

    ViewGoL(ControllerGoL controller, int antRad, int antKol) {
        this.controller = controller;
        this.antRad = antRad; this.antKol = antKol;
        knapper =  new Celleknapp[antRad][antKol];

        vindu = lagVindu("Game of Life");
        hovedpanel = lagPanelMedBorderLayout();
        statuspanel = lagPanelMedGridLayout(1, 3);
        rutenett = lagPanelMedGridLayout(antRad, antKol);
        
        fyllStatuspanel();
        lagCelleknapper(controller.hentRutene()); 
        visCelleknapper();

        hovedpanel.add(statuspanel, BorderLayout.NORTH);
        hovedpanel.add(rutenett, BorderLayout.SOUTH);
        vindu.add(hovedpanel);
        vindu.pack();
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
        vindu.setLocationRelativeTo(null);
        return vindu;
    }

    public void oppdaterRutenett() {
        for(int i = 0; i < antRad; i++) {
            for(int j = 0; j < antKol; j++) {
                knapper[i][j].setText(controller.hentRutene()[i][j].hentStatusTegn());
            }
        }
    }

    public void oppdaterAntLevende() {
        antLevende.setText(Integer.toString(controller.antallLevende()));
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
        antLevende = new JLabel("Antall levende: " + controller.antallLevende());
        start = new JButton("Start"); avslutt = new JButton("Avslutt");
        start.addActionListener(new startHaandtering());
        avslutt.addActionListener(new sluttHaandtering());
        statuspanel.add(antLevende);
        statuspanel.add(start); statuspanel.add(avslutt);
    }

    private void lagCelleknapper(Celle[][] rutene) {
        for(int i = 0; i < antRad; i++) {
            for(int j = 0; j < antKol; j++) {
                knapper[i][j] = new Celleknapp(rutene[i][j].hentStatusTegn(), i, j);
            }
        }
    }

    private void visCelleknapper() {
        for(int i = 0; i < antRad; i++) {
            for(int j = 0; j < antKol; j++) {
                rutenett.add(knapper[i][j]);
            }
        }
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

        Celleknapp(String tekst, int rad, int kolonne) {
            super(tekst);
            this.rad = rad; this.kolonne = kolonne;
            this.addActionListener(new actionListenerCelleknapp());
        }

        class actionListenerCelleknapp implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                Celle celle = controller.hentRutene()[rad][kolonne];
                if(celle.erLevende()) {
                    celle.settDoed();
                    Celleknapp.this.setText(".");
                } else {
                    celle.settLevende();
                    Celleknapp.this.setText("O");
                }
                oppdaterAntLevende();
            }
        }
    }
}
