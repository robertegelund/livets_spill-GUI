class ControllerGoL {
    private ModelGoL model; ViewGoL view;
    private int genNr = 0;
    private boolean spilletKjoerer = false;

    ControllerGoL(int antRader, int antKolonner) {
        model = new ModelGoL(antRader, antKolonner);
        model.fyllMedTilfeldigeCeller();
        model.kobleAlleCeller();
        view = new ViewGoL(this, antRader, antKolonner);
    }

    public Celle[][] hentRutene() {
        return model.hentRutene();
    }

    public int antallLevende() {
        return model.antallLevende();
    }

    public void oppdatering() {
        for(int rad = 0; rad < model.antRader; rad++) {
            for(int kol = 0; kol < model.antKolonner; kol++) {
                Celle celle = model.hentCelle(rad, kol);
                celle.tellLevendeNaboer();
                celle.oppdaterStatus();
            }
        }
        genNr++;
        view.oppdaterAntLevende();
        view.oppdaterRutenett(); 
    }
    
    public void startSpillet() {
        spilletKjoerer = true;
        new Thread(new BrettOppdaterer()).start();
    }

    public void avsluttSpillet() {
        spilletKjoerer = false;
        System.exit(0);
    }
    class BrettOppdaterer implements Runnable {
        @Override
        public void run() {
            while(spilletKjoerer) {
                oppdatering();
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException ie) {
                    System.out.println("[ERROR] Brettoppdatereren ble avbrutt. Spillet avsluttes.");
                    System.exit(1);
                }
            }
        }
    }

}
