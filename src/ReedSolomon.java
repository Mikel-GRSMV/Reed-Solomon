import java.util.*;

public class ReedSolomon {
    public static void main(String[] args) {
        Random rnd = new Random();
        GF28.init();
        char gen = 255;

        String input = args[0];
        System.out.println("Исходное сообщение: " + input);
        int s = 5;

        Encoder e = new Encoder(input, s, gen);

        char[] c28 = e.slow();

        Decoder d = new Decoder(gen, input.length());
        int[] bads = {};

        HashSet<Integer> bad = createSet(bads);
        System.out.print("Положение ошибки: ");
        for (int i = 0; i < 2 * s; i++) {
            int b = rnd.nextInt(input.length() + 2 * s);
            bad.add(b);
            System.out.print(b + ", ");
        }
        System.out.println(" ");

        char[] c2 = d.decode(c28, bad);
        String s2 = new String(c2);
        System.out.println("Декодированное сообщение: " + s2);

    }

    public static HashSet<Integer> createSet(int[] a) {
        HashSet<Integer> h = new HashSet<Integer>();
        for (int a1 : a) h.add(a1);
        return h;
    }
}

class Decoder {
    char gen;
    int k;

    public Decoder(char gen, int k) {
        this.gen = gen;
        this.k = k;
    }

    public char[] gaussianElimination(char[][] A, char[] c) {
        char[] m = new char[k];
        for (int i = 0; i < k; i++) {
            GF28 a = new GF28(A[i][i]);
            GF28 inv = a.getInverse();
            for (int j = i + 1; j < k; j++) {
                GF28 b = new GF28(A[j][i]);
                b = b.mult(inv);
                for (int k = 0; k < this.k; k++) {
                    GF28 v = new GF28(A[i][k]);
                    A[j][k] = v.mult(b).add(A[j][k]).val;
                }
                GF28 v = new GF28(c[i]);
                c[j] = v.mult(b).add(c[j]).val;
            }
        }

        for (int i = k - 1; i >= 0; i--) {

            GF28 sum = new GF28((char) c[i]);
            for (int j = k - 1; j > i; j--) {
                GF28 v = new GF28(A[i][j]);
                sum = v.mult(m[j]).add(sum);
            }

            GF28 a = new GF28(A[i][i]);
            GF28 inv = a.getInverse();
            m[i] = sum.mult(inv).val;
        }

        return m;
    }

    public char[] decode(char[] allC, HashSet<Integer> bad) {
        GF28 wn = new GF28((char) 1);
        char[][] A = new char[k][k];
        char[] c = new char[k];
        char[] m = new char[k];
        int cnt = 0;

        for (int i = 0; i < allC.length; i++) {
            if (i != 0) wn = wn.mult(gen);
            if (bad.contains(i)) continue;

            GF28 cur = new GF28((char) 1);
            for (int j = 0; j < k; j++) {
                A[cnt][j] = cur.val;
                cur = cur.mult(wn);
            }
            c[cnt] = allC[i];

            cnt++;
            if (cnt >= k) break;
        }

        m = gaussianElimination(A, c);

        return m;
    }
}

class Encoder {
    int k, n, s;
    String input;
    char gen;

    public Encoder(String m, int s, char gen) {
        input = m;
        k = m.length();
        this.s = s;
        n = 2 * s + k;
        this.gen = gen;
    }

    public char[] slow() {
        char[] cin = input.toCharArray();
        char[] m = new char[n];
        for (int i = 0; i < n; i++) {
            m[i] = 0;
            if (i < cin.length) m[i] = cin[i];
        }
        char[] c = new char[n];

        GF28 wn = new GF28((char) 1);
        char[][] A = new char[n][n];
        for (int i = 0; i < n; i++) {

            GF28 cur = new GF28((char) 1);

            GF28 tot = new GF28((char) 0);

            for (int j = 0; j < n; j++) {
                A[i][j] = cur.val;
                tot = cur.mult(m[j]).add(tot);
                cur = cur.mult(wn);
            }
            wn = wn.mult(gen);
            c[i] = tot.val;
        }
        return c;
    }

    public String toString(int[] a) {
        String s = "";
        for (int a1 : a) s = s + a1 + ", ";
        return s;
    }
}

class GF28 {
    public static char[][] adds, mults;
    public static char[] inverse;
    public static boolean[][] addsDone, multsDone;
    public static boolean[] inverseDone;

    public static final char PX = (char) 0x11B;

    public static void init() {
        int max = 1 << 8;
        adds = new char[max][max];
        mults = new char[max][max];
        addsDone = new boolean[max][max];
        multsDone = new boolean[max][max];
        inverse = new char[max];
        inverseDone = new boolean[max];
        for (int i = 0; i < adds.length; i++) {
            inverse[i] = 0;
            inverseDone[i] = false;
            for (int j = 0; j < adds[0].length; j++) {
                adds[i][j] = 0;
                mults[i][j] = 0;
                addsDone[i][j] = false;
                multsDone[i][j] = false;
            }
        }
    }

    public static char add(char a, char b) {
        return (char) (a ^ b);
    }

    public static char mult(char a, char b) {
        int p = a;
        int r = 0;
        while (b != 0) {
            if ((b & 1) == 1) r = r ^ p;
            b = (char) (b >>> 1);
            p = p << 1;
            if ((p & 0x100) == 0x100) p = p ^ PX;
        }
        return (char) r;
    }


    char val;

    public GF28(char val) {
        this.val = val;
    }

    public GF28 add(char b) {
        if (!addsDone[val][b]) {
            adds[val][b] = add(val, b);
            adds[b][val] = adds[val][b];
            addsDone[val][b] = true;
            addsDone[b][val] = true;
        }
        return new GF28(adds[val][b]);
    }

    public GF28 add(GF28 b) {
        return add(b.val);
    }

    public GF28 mult(char b) {
        if (!multsDone[val][b]) {
            mults[val][b] = mult(val, b);
            mults[b][val] = mults[val][b];
            multsDone[val][b] = true;
            multsDone[b][val] = true;
        }
        return new GF28(mults[val][b]);
    }

    public GF28 mult(GF28 b) {
        return mult(b.val);
    }

    public GF28 getInverse() {
        return inverse(val);
    }

    public static GF28 inverse(char a) {
        if (!inverseDone[a]) {
            GF28 ga = new GF28(a);
            for (char i = 0; i < (1 << 8); i++) {
                if (ga.mult(i).val == 1) {
                    inverseDone[a] = true;
                    inverseDone[i] = true;
                    inverse[a] = i;
                    inverse[i] = a;
                    break;
                }
            }
        }
        return new GF28(inverse[a]);
    }

}
