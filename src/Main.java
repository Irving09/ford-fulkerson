public class Main {

    public static void main(String[] args) {
//        int[][] capacities = test2();
        int[][] capacities = test1();
//        int[][] capacities = test3();
//        int[][] capacities = test4();
        FordFulkerson ff = new FordFulkerson(capacities);

        int maxFlow = ff.maxFlow();

        System.out.println(maxFlow);
    }

    public static int[][] test4() {
        int[][] graph = {
                // S  A   B  C   D   T
          /*S*/ {  0, 16, 0, 13,  0,  0 }, /*0*/
          /*A*/ {  0,  0,12,  6,  0,  0 }, /*1*/
          /*B*/ {  0,  0, 0,  9,  0, 20 }, /*2*/
          /*C*/ {  0,  0, 0,  0, 14,  0 }, /*3*/
          /*D*/ {  0,  0, 7,  0,  0,  4 }, /*4*/
          /*T*/ {  0,  0, 0,  0,  0,  0 }, /*5*/
                // 0   1  2   3   4   5
        };

        return graph;
    }

    public static int[][] test1() {
        int[][] graph = {
            // S  A   B  C   D  T
            {  0, 10, 0, 10,  0,  0 },
            {  0,  0, 4,  9,  8,  0 },
            {  0,  0, 0,  0,  0, 10 },
            {  0,  0, 0,  0,  9,  0 },
            {  0,  0, 6,  0,  0, 10 },
            {  0,  0, 0,  0,  0,  0 },
        };

        return graph;
    }

    public static int[][] test2() {
        int[][] graph = {
            // S   A   B  C   D   T
      /*S*/ {  0, 16, 0, 13,  0,  0 },
      /*A*/ {  0,  0,12,  6,  0,  0 },
      /*B*/ {  0,  0, 0,  9,  0, 20 },
      /*C*/ {  0,  0, 0,  0, 14,  0 },
      /*D*/ {  0,  0, 7,  0,  0,  4 },
      /*T*/ {  0,  0, 0,  0,  0,  0 },
        };

        return graph;
    }

    public static int[][] test3() {
        int[][] graph = {
            // s   1  3   2   4   t
      /*s*/ {  0,  7, 0, 10,  0,  0 },
      /*1*/ {  0,  0, 9,  3,  0,  0 },
      /*3*/ {  0,  0, 0,  0,  0, 15 },
      /*2*/ {  0,  0, 5,  0,  8,  0 },
      /*4*/ {  0,  0, 4,  0,  0, 12 },
      /*t*/ {  0,  0, 0,  0,  0,  0 },
        };

        return graph;
    }
}
