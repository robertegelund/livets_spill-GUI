class ModelGoL {
    int antRader, antKolonner;
    Celle[][] rutene;

    ModelGoL(int antRader, int antKolonner) {
        this.antRader = antRader;
        this.antKolonner = antKolonner;
        rutene = new Celle[antRader][antKolonner];
    }

    public void lagCelle(int rad, int kol) {
        if(rutene[rad][kol] != null) {
            System.out.println("[ERROR] Det finnes en celle paa angitt plass. Ny celle ikke opprettet.");
            return;
        }
        
        Celle nyCelle = new Celle();
        if(Math.random() <= 0.3333) nyCelle.settLevende();
        
        try {
            rutene[rad][kol] = nyCelle;
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("[ERROR] Ugyldige cellekoordinater. Ny celle ikke opprettet.");   
        }
    }
    
    public void fyllMedTilfeldigeCeller() {
        for(int rad = 0; rad < antRader; rad++) {
            for(int kol = 0; kol < antKolonner; kol++) {
                lagCelle(rad, kol);
            }
        }
    }

    public Celle hentCelle(int rad, int kol) {
        try {
            Celle celle = rutene[rad][kol];
            if(celle == null) throw new NullPointerException();
            return celle;
        } catch (Exception e) {
            // ArrayIndexOutOfBoundsException eller NullointerException
            return null;
        }
    }

    public void settNaboer(int rad, int kol) {
        Celle celle = hentCelle(rad, kol);
        
        try {
            if(celle == null) throw new NullPointerException();
        } catch(NullPointerException e) {
            System.out.println("[ERROR] Cellen eksisterer ikke, og kan ikke ha naboer.");
            return;
        }
        
        for(int r = -1; r <= 1; r++) {
            for (int k = -1; k <= 1; k++) {
                
                // Hopper over cellen selv (celler er ikke sin egen nabo)
                if(r == 0 && k == 0) continue;
                
                Celle nabo = hentCelle(rad+r, kol+k);
                if(nabo != null) {
                    // Legger til nabo hvis man er innenfor brettet
                    celle.leggTilNabo(nabo);
                }
            }
        }
    }

    public void kobleAlleCeller() {
        for(int rad = 0; rad < antRader; rad++) {
            for(int kol = 0; kol < antKolonner; kol++) {
                settNaboer(rad, kol);
            }
        }
    }

    public int antallLevende() {
        int antallLevende = 0;
        for(int rad = 0; rad < antRader; rad++) {
            for(int kol = 0; kol < antKolonner; kol++) {
                Celle celle = rutene[rad][kol];
                if(celle != null && celle.erLevende()) {
                    antallLevende++;
                }
            }
        }
        return antallLevende;
    }

    public Celle[][] hentRutene() {
        return rutene;
    }
}
