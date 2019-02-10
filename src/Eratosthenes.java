public class Eratosthenes {
    final static int SIZE = 10000001;
    static boolean prime[] = new boolean[SIZE];

    public static void main(String[] args) {

        for (int i = 0; i < SIZE; i++)
            prime[i] = true;

        double len = Math.sqrt(SIZE);

        for (int i = 2; i <= len; i++) {
            if (prime[i]) {
                for (int j = 2; i * j < SIZE; j++) {
                    prime[i*j] = false;
                }
            }
        }

        int ctr = 0;

        for (int i = 1; i < SIZE; i++)
            if (prime[i]) {
                System.out.println(i);
                ctr++;
            }

        System.out.println(ctr);
    }
}
