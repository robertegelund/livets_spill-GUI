class Hovedprogram {
    public static void main(String[] args) {
        if(args.length != 0) {
            new ControllerGoL(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
        } else {
            new ControllerGoL(10, 10);
        }
    }
}
