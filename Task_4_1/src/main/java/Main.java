import calculator.MyCalculator;

public class Main {
    public static void main(String[] args) {
        if(args.length != 1){
            throw new IllegalArgumentException("You have to pass only 1 string");
        }

        MyCalculator calculator = new MyCalculator();
        System.out.println(args[0]);
    }
}
