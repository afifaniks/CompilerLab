public class GregLeibniz {
    public static void main(String[] args) {
        double val = 0.0;
        int denom = 1;

        for (int i = 0; i < 100000000; i++) {
            if (i % 2 == 0)
                val += (double)(1.0/denom);
            else
                val -= (double)(1.0/denom);
            denom += 2;
        }

        double pi = 4*val;

        System.out.println(pi);
    }
}
