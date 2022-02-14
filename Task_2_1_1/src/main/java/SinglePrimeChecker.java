public class SinglePrimeChecker {
    public static boolean isPrime(Integer t) {
        for (int i = 2; i <= Math.sqrt(t); i++) {
            if (t % i == 0) {
                return false;
            }
        }
        return true;
    }
}
