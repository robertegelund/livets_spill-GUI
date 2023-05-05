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
        view.oppdaterAntLevende(antallLevende());
        view.oppdaterRutenett(); 
    }

    public void oppdaterCellestatus(int rad, int kol) {
        Celle celle = model.hentCelle(rad, kol);
        if(celle.erLevende()) {
            celle.settDoed();
            view.endreCelleknappTegn(".", rad, kol);
        } else {
            celle.settLevende();
            view.endreCelleknappTegn("O", rad, kol);    
        }
        view.oppdaterAntLevende(antallLevende());
    }

    public String hentStatustegnCelle(int rad, int kol) {
        return model.hentCelle(rad, kol).hentStatusTegn();
    }
    
    public void startSpillet() {
        if(!spilletKjoerer) {
            spilletKjoerer = true;
            new Thread(new BrettOppdaterer()).start();
            view.visCelleknapper();
        }
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
