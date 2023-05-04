class ControllerGOL {
    ModelGOL rutenett;
    int genNr = 0;
    
    ControllerGOL(int antRader, int antKolonner) {
        rutenett = new ModelGOL(antRader, antKolonner);
        rutenett.fyllMedTilfeldigeCeller();
        rutenett.kobleAlleCeller();
    }

    public void tegn() {
        System.out.println("\nGenerasjon nr. " + genNr);
        rutenett.tegnRutenett();
        System.out.println("Det er " + rutenett.antallLevende() + " levende celler.\n");
    }

    public void oppdatering() {
        for(int rad = 0; rad < rutenett.antRader; rad++) {
            for(int kol = 0; kol < rutenett.antKolonner; kol++) {
                Celle celle = rutenett.hentCelle(rad, kol);
                celle.tellLevendeNaboer();
                celle.oppdaterStatus();
            }
        }
        genNr++;
    }
}
