public class Main {

    public static void main(String[] args) {
        int[][] capacities = test();
        FordFulkerson ff = new FordFulkerson(capacities);

        int maxFlow = ff.maxFlow();

        System.out.println(maxFlow);
    }

    public static int[][] test() {
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
}
