class GameOfLife {
    public static void main(String[] args) {
        ControllerGOL verden = new ControllerGOL(8, 12);

        // Tegner 0-te generasjon
        verden.tegn();

        // Oppdaterer til foerste generasjon, og tegner
        verden.oppdatering();
        verden.tegn();

        // Oppdaterer til andre generasjon, og tegner
        verden.oppdatering();
        verden.tegn();

        // Oppdaterer til tredje generasjon, og tegner
        verden.oppdatering();
        verden.tegn();
    }   
}
