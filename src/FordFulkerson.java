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
          this.Gf[i][j] = new Edge(i, j, capacity);
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
      int flow = bottleNeck.residualCapacity();

      // flow must be > 0 because of line 96 and line 139
      maxFlow += flow;

      // Note: edges in augmented path can either be forward or backward edges
      for (Edge edge : augmentedPath.path()) {
        // update directed edge in opposite direction
        Edge oppositeEdge = this.Gf[edge.dest()][edge.src()];
        if (oppositeEdge == null) {
          oppositeEdge = edge.opposite();
          oppositeEdge.residualCapacity(0);
          this.Gf[edge.dest()][edge.src()] = oppositeEdge;
        }

        // Note: edge.capacity() must be >= flowValueSoFar because of bottleneck found
        // update edge flows both forward and backwards
        edge.residualCapacity(edge.residualCapacity() - flow);
        oppositeEdge.residualCapacity(oppositeEdge.residualCapacity() + flow);
      }

    }

    return maxFlow;
  }

  private Path bfs(int source, int sink) {
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(source);

    int[] parent = new int[this.Gf.length];
    Arrays.fill(parent, -1);
    parent[source] = source; // mark source as visited

    while (!queue.isEmpty()) {
      int node = queue.poll();

      if (node == sink) {
        break;
      }

      for (int neighbor : neighbors(node)) {
        Edge edgeToNeighbor = this.Gf[node][neighbor];
        boolean edgeExists = edgeToNeighbor != null;
        boolean unvisited = parent[neighbor] == -1;

        if (edgeExists && unvisited && edgeToNeighbor.hasLeftOverCapacity()) {
          parent[neighbor] = node; // mark neighbors to be visited as visited to avoid adding same neighbors twice in queue
          queue.offer(neighbor);
        }
      }
    }

    List<Integer> traversed = new ArrayList<>();


    int backtrack = sink;
    while (parent[backtrack] != -1) {
      traversed.add(backtrack);
      if (backtrack == source) {
        return createEdgePathFrom(traversed);
      }

      backtrack = parent[backtrack];
    }

    return null;
  }

  private Path createEdgePathFrom(List<Integer> traversed) {
    Integer prevNode = null;
    List<Edge> edges = new ArrayList<>();
    Edge bottleneck = null;

    int minResidualCapacity = Integer.MAX_VALUE;
    int n = traversed.size();
    for (int i = n - 1; i >= 0; i--) {
      int currNode = traversed.get(i);
      if (prevNode != null) {
        Edge edge = this.Gf[prevNode][currNode];
        edges.add(edge);

        int residualCapacity = edge.residualCapacity();

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
