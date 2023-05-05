class Hovedprogram {
    public static void main(String[] args) {
        if(args.length != 0) {
            new ControllerGoL(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        } else {
            new ControllerGoL(10, 10);
        }
    }
}
