public class SequenceAligner {
    private String s1;
    private String s2;

    private int[][] memo;
    private int[][] traceback;

    SequenceAligner(final String s1, final String s2) {
        this.s1 = s1;
        this.s2 = s2;
        this.memo = new int[s1.length() + 1][s2.length() + 1];
        this.traceback = new int[s1.length() + 1][s2.length() + 1];
    }

    int evaluate() {
        return sa(this.s1.length(), this.s2.length());
    }

    private int sa(int i, int j) {
        if (memo[i][j] != 0) {
            return memo[i][j];
        }

        if (j == 0) {
            memo[i][j] = -i;
            traceback[i][j] = 6;
            return memo[i][j];
        } else if (i == 0) {
            memo[i][j] = -j;
            traceback[i][j] = 7;
            return memo[i][j];
        }

        if (this.s1.charAt(i - 1) == this.s2.charAt(j - 1)) {
            memo[i][j] = 1 + sa(i - 1, j - 1);
            traceback[i][j] = 1;
            return memo[i][j];
        }

        int a, b, c;
        a = -1 + sa(i - 1, j);
        b = -1 + sa(i, j - 1);
        c = -2 + sa(i - 1, j - 1);
        if (a >= b && a >= c) {
            memo[i][j] = a;
            traceback[i][j] = 2;
            return a;
        } else if (b >= c) {
            memo[i][j] = b;
            traceback[i][j] = 3;
            return b;
        } else {
            memo[i][j] = c;
            traceback[i][j] = 4;
            return c;
        }
    }

    void showAlignment() {
        StringBuilder s1_aligned = new StringBuilder();
        StringBuilder s2_aligned = new StringBuilder();
        int i = s1.length();
        int j = s2.length();
        while (i >= 0 && j >= 0) {
            int tb = traceback[i][j];
            int str_i = i > 0 ? i - 1 : 0;
            int str_j = j > 0 ? j - 1 : 0;
            if (tb == 1 || tb == 4) {
                s1_aligned.insert(0, s1.charAt(str_i));
                s2_aligned.insert(0, s2.charAt(str_j));
                i--;
                j--;
            } else if (tb == 2) {
                s1_aligned.insert(0, s1.charAt(str_i));
                s2_aligned.insert(0, '-');
                i--;
            } else if (tb == 3) {
                s2_aligned.insert(0, s2.charAt(str_j));
                s1_aligned.insert(0, '-');
                j--;
            } else if (tb == 6) {
                s1_aligned.insert(0, s1.charAt(str_i));
                s2_aligned.insert(0, '-');
                i--;
                j--;
            } else if (tb == 7) {
                s2_aligned.insert(0, s2.charAt(str_j));
                s1_aligned.insert(0, '-');
                i--;
                j--;
            } else {
                return;
            }
        }
        System.out.println(s1_aligned.toString());
        System.out.println(s2_aligned.toString());
    }

    static void printDPTable2(int[][] table) {
        for (int[] x : table) {
            for (int y : x) {
                System.out.print(" " + y);
            }
            System.out.println();
        }
    }
}
