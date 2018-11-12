/**
 * CONFIDENTIAL INFORMATION
 * <p>
 * All Rights Reserved.  Unauthorized reproduction, transmission, or
 * distribution of this software is a violation of applicable laws.
 * <p>
 * Date: Nov 07, 2018
 * Copyright 2018 innoirvinge@gmail.com
 */

import java.util.*;

/**
 * @author irving09 <innoirvinge@gmail.com>
 */
public class FordFulkerson {

  private Edge[][] Gf;

  /**
   * Takes in a test in the form of an NxN integer matrix
   * where each cell in the matrix is the capacity of the of the edge
   * from node i to node j where 0 <= i < N and 0 <= j < n.
   *
   * @param capacities The capacities of the original test
   * */
  public FordFulkerson(int[][] capacities) {
    int n = capacities.length;

    this.Gf = new Edge[n][n];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        int capacity = capacities[i][j];
        if (capacity > 0) {
          this.Gf[i][j] = new Edge(i, j, 0, capacity, EdgeType.FORWARD);
        }
      }
    }
  }

  public int maxFlow() {
    int n = this.Gf.length;
    int source = 0;
    int sink = n - 1;
    int maxFlow = 0;

    Path augmentedPath;
    while ((augmentedPath = bfs(source, sink)) != null) {

      // because bottleneck is min residual capacity for all edges in augmented path
      Edge bottleNeck = augmentedPath.min();
      int flow = bottleNeck.capacity() - bottleNeck.flowValue();
      maxFlow += flow;

      // Note: edges in augmented path can either be forward or backward flows
      for (Edge edge : augmentedPath.path()) {
        int flowValueSoFar = edge.flowValue() + flow;
        // Note: edge.capacity() must be >= flowValueSoFar because of bottleneck found

        // update directed edge in opposite direction
        Edge oppositeEdge = this.Gf[edge.dest()][edge.src()];
        if (oppositeEdge == null) {
          oppositeEdge = edge.opposite();
          this.Gf[edge.dest()][edge.src()] = oppositeEdge;
        }

        // update edge flows both forward and backwards
        edge.flowValue(flowValueSoFar);
        oppositeEdge.flowValue(flowValueSoFar);
        // TODO maybe we dont care about residual capacity for backward edges?
      }

    }

    return maxFlow;
  }

  private Path bfs(int source, int sink) {
    List<Integer> traversed = new ArrayList<>();

    Set<Integer> visited = new HashSet<>();
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(source);

    while (!queue.isEmpty()) {
      int node = queue.poll();
      traversed.add(node);
      visited.add(node);

      if (node == sink) {
        return createEdgePathFrom(traversed);
      }

      boolean deadEnd = true;
      for (int neighbor : neighbors(node)) {
        Edge edgeToNeighbor = this.Gf[node][neighbor];
        boolean edgeExists = edgeToNeighbor != null;

        if (edgeExists &&
            unvisited(visited, neighbor) &&
            edgeToNeighbor.hasLeftOverCapacity()) {
          queue.offer(neighbor);
          visited.add(neighbor);

          deadEnd = false;
        }
      }

      if (deadEnd) {
        traversed.remove(traversed.size() - 1);
      }
    }

    return null;
  }

  private boolean unvisited(Set<Integer> visited, int node) {
    return !visited.contains(node);
  }

  private Path createEdgePathFrom(List<Integer> traversed) {
    Integer prevNode = null;
    List<Edge> edges = new ArrayList<>();
    Edge bottleneck = null;

    int minResidualCapacity = Integer.MAX_VALUE;

    for (Integer currNode : traversed) {
      if (prevNode != null) {
        Edge edge = this.Gf[prevNode][currNode];
        edges.add(edge);

        int residualCapacity = edge.capacity() - edge.flowValue();

        if (bottleneck == null) {
          bottleneck = edge;
          minResidualCapacity = residualCapacity;
        } else {
          bottleneck = residualCapacity < minResidualCapacity ? edge : bottleneck;
        }
      }

      prevNode = currNode;
    }

    return new Path(edges, bottleneck);
  }

  // Can be optimized if we keep track of neighbors for each iteration in main FF while loop
  private List<Integer> neighbors(int node) {
    List<Integer> neighbors = new ArrayList<>();
    int n = this.Gf.length;
    for (int i = 0; i < n; i++) {
      boolean edgeExists = this.Gf[node][i] != null;
      if (edgeExists) {
        neighbors.add(i);
      }
    }

    return neighbors;
  }

}
