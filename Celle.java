class Celle{
    boolean levende = false;
    Celle[] naboer = new Celle[8];
    int antNaboer = 0;
    int antLevendeNaboer = 0;

    public void settLevende() {
        levende = true;
    }

    public void settDoed() {
        levende = false;
    }

    public boolean erLevende() {
        return levende;
    }

    public char hentStatusTegn() {
        // Bruker ternary operator til if-sjekk
        return levende ? 'O' : '.';
    }

    public void leggTilNabo(Celle nabo) {
        try {
            naboer[antNaboer] = nabo;
            antNaboer++;
        } catch(ArrayIndexOutOfBoundsException e) {
            // Kastes en feil hvis det allerede er 8 naboer i arrayet
            System.out.println("Cellen har allerede 8 naboer!");
        }
    }

    public void tellLevendeNaboer() {
        antLevendeNaboer = 0;
        for(Celle nabo : naboer) {
            if(nabo != null && nabo.erLevende()) {
                antLevendeNaboer++;
            }
        }
    }

    public void oppdaterStatus() {
        boolean skalSettesDoed = levende && (antLevendeNaboer < 2 || antLevendeNaboer > 3);
        boolean skalSettesLevende = !levende && antLevendeNaboer == 3;
        if(skalSettesDoed) {
            settDoed(); 
        } else if(skalSettesLevende) {
            settLevende();
        }
    }
}