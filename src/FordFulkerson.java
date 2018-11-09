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
    int s = 0;
    int t = n - 1;

    Path result = new Path();
    Path augmentedPath = null;
    while ((augmentedPath = bfs(s, t)) != null) {
      // result is set to the last successful path found
      result = augmentedPath;

      Edge bottleNeck = augmentedPath.min();
      int flow = bottleNeck.residualCapacity();

      // Note: edges in augmented path can either be forward or backward flows
      for (Edge edge : augmentedPath.path()) {
        int flowValueSoFar = edge.flowValue() + flow;
        int remaining = edge.residualCapacity() - flow;

        // Note: remaining is guaranteed >= 0
        // because bottleneck is min residual capacity for all edges in augmented path

        // update edge's residual capacity
        edge.residualCapacity(remaining);
        edge.flowValue(flowValueSoFar);

        // update directed edge in opposite direction
        Edge oppositeEdge = this.Gf[edge.dest()][edge.src()];
        if (oppositeEdge == null) {
          oppositeEdge = edge.opposite();
          this.Gf[edge.dest()][edge.src()] = oppositeEdge;
        }

        oppositeEdge.flowValue(flowValueSoFar);
        // TODO maybe we dont care about residual capacity for backward edges?
      }

    }

    int maxFlow = 0;
    for (Edge[] residualGraph : this.Gf) {
      Edge sinkEdge = residualGraph[t];
      if (sinkEdge != null) {
        maxFlow += sinkEdge.flowValue();
      }
    }

    return maxFlow;
  }

  private Path bfs(int start, int dest) {
    Queue<Integer> queue = new LinkedList<>();
    Set<Integer> visited = new HashSet<>();
    List<Integer> traversed = new ArrayList<>();

    queue.offer(start);
    visited.add(start);

    while (!queue.isEmpty()) {
      int currNode = queue.poll();
      traversed.add(currNode);

      if (currNode == dest) {
        return createEdgePath(traversed);
      }

      boolean queueIncreased = false;
      List<Integer> neighbors = neighbors(currNode);
      for (int neighbor : neighbors) {
        Edge edgeToNeighbor = this.Gf[currNode][neighbor];
        boolean capacityReached = edgeToNeighbor.flowValue() == edgeToNeighbor.capacity();

        // Note: only set next nodes to be visited
        // if its not yet visited and there is remaining capacity left for that edge
        if (!visited.contains(neighbor) && !capacityReached) {
          queue.offer(neighbor);
          visited.add(neighbor);
          queueIncreased = true;
        }
      }

      if (!queueIncreased) {
        traversed.remove(traversed.size() - 1);
      }
    }

    return null;
  }

  private Path createEdgePath(List<Integer> traversed) {
    Integer prev = null;
    List<Edge> edges = new ArrayList<>();
    Edge bottleneck = null;

    for (Integer node : traversed) {
      if (prev != null) {
        Edge edge = this.Gf[prev][node];
        edges.add(edge);

        if (bottleneck == null) {
          bottleneck = edge;
        } else {
          bottleneck = edge.residualCapacity() < bottleneck.residualCapacity() ? edge : bottleneck;
        }
      }

      prev = node;
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
