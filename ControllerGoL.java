class ControllerGoL {
    private ModelGoL model; ViewGoL view;
    private int genNr = 0;
    private boolean spilletKjoerer = false;

    ControllerGoL(int antRad, int antKol) {
        model = new ModelGoL(antRad, antKol);
        model.fyllMedTilfeldigeCeller();
        model.kobleAlleCeller();
        view = new ViewGoL(this, antRad, antKol);
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
            view.toemRutenettPanel();
            view.visCelleknapper();
            new Thread(new BrettOppdaterer()).start();
        }
    }

    public void avsluttSpillet() {
        spilletKjoerer = false;
        System.exit(0);
    }
    
    class BrettOppdaterer implements Runnable {
        @Override
        public void run() {
            int spillrunde = 0;
            while(spilletKjoerer) {
                if(spillrunde != 0) {
                    oppdatering();
                    view.oppdaterRutenett(); 
                } 
                view.oppdaterAntLevende(antallLevende());
                view.oppdaterGenerasjon(genNr);
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException ie) {
                    System.out.println("[ERROR] Brettoppdatereren ble avbrutt. Spillet avsluttes.");
                    System.exit(1);
                }
                spillrunde++;
            }
        }
    }

}
