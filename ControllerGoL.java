class ControllerGoL {
    ModelGoL model;
    ViewGoL view;
    int genNr = 0;
    
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

    public void tegn() {
        System.out.println("\nGenerasjon nr. " + genNr);
        model.tegnRutenett();
        System.out.println("Det er " + model.antallLevende() + " levende celler.\n");
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
}
