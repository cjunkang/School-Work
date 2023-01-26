//Name:Chong Jun Kang
//StudentID:6461542
import java.io.*;
import java.util.*;

public class knapsack {


    static int s1;
    static int sum;
    static int b[] = {7, 11, 19, 39, 79, 157, 313};
    static int bi;
    static int permute[] = {3, 1, 4, 2, 0, 6, 5};
    static int permute1[] = {3, 1, 4, 2, 0, 6, 5};
    static int[] t = new int[7];
    static int[] a = new int[7];
    static int[] x = new int[7];
    static int[] x1 = new int[7];

    public static void main(String[] args) throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        for (int i = 0; i < b.length; i++) {
            bi = bi + b[i];
        }
        System.out.println(bi);
        System.out.println("Enter the value of n:");
        int n = Integer.parseInt(br.readLine());
        System.out.println("Enter value of r(should be prime)");
        int r0 = 37;

        for (int i = 0; i < t.length; i++) {
            t[i] = (r0 * b[i]) % n;
        }

        for (int i = 0; i < 7; i++) {
            a[i] = t[permute[i]];
        }

        System.out.println("Enter the message to be send");
        String s = br.readLine();
        char c = s.charAt(0);
        int ascii = (int) c;
        String binary = Integer.toBinaryString(ascii);
        System.out.println(binary);

        for (int i = 0; i < binary.length(); i++) {
            x[i] = Integer.parseInt(binary.charAt(i) + "");
        }

        System.out.println(x[3]);
        for (int i = 0; i < 7; i++) {
            sum = sum + x[i] * a[i];
        }
        System.out.println(sum);

        int rin = extended_eucli(900, 37);

        System.out.println(rin);
        int rinn = rin % n;
        int sum1 = sum % n;
        int smul = sum1 * rinn;
        s1 = smul % n;
        System.out.println(s1);

        for (int i = 6; i >= 0; i--) {
            if (s1 >= b[i]) {
                x1[i] = 1;
            }
            if (s1 < b[i]) {
                x1[i] = 0;
            }

            s1 = s1 - b[i] * x1[i];
        }

        for (int i = 0; i < 7; i++) {
            System.out.print(x1[i]);
        }
        System.out.println("");
        for (int i = 0; i < 7; i++) {
            System.out.print(x1[permute1[i]]);
        }
    }

    public static int extended_eucli(int r0, int n) {
        int q, r, t, r1, r2, t1, t2, n1, b, bin = 0;

        n1 = r0;
        b = n;
        r1 = n1;
        r2 = b;
        t1 = 0;
        t2 = 1;
        while (r2 > 0) {
            q = r1 / r2;
            r = r1 - q * r2;
            r1 = r2;
            r2 = r;
            t = t1 - q * t2;
            t1 = t2;
            t2 = t;
        }
        if (r1 == 1) {
            bin = t1;
//printf("The inverse of b is %d",t1);
        }
        if (bin < 0) {
            bin = t2 + t1;

        }
        return bin;
    }

}