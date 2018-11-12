public class Main {

    public static void main(String[] args) {
        int[][] capacities = test2();
//        int[][] capacities = test1();
        FordFulkerson ff = new FordFulkerson(capacities);

        int maxFlow = ff.maxFlow();

        System.out.println(maxFlow);
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
}
