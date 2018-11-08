/**
 * CONFIDENTIAL INFORMATION
 *
 * All Rights Reserved.  Unauthorized reproduction, transmission, or
 * distribution of this software is a violation of applicable laws.
 *
 * Date: Nov 08, 2018
 * Copyright 2018 innoirvinge@gmail.com
 */

import java.util.ArrayList;
import java.util.List;

/**
 * @author irving09 <innoirvinge@gmail.com>
 */
public class Path {

  private List<Edge> path;

  private Edge min;

  public Path() {
      this(new ArrayList<>(), null);
  }

  public Path(List<Edge> path) {
      this(path, null);
  }

  public Path(List<Edge> path, Edge min) {
    this.path = path;
    this.min = min;
  }

  public List<Edge> path() {
    return path;
  }

  public void path(List<Edge> path) {
    this.path = path;
  }

  public Edge min() {
    return min;
  }

  public void min(Edge min) {
    this.min = min;
  }

  public void append(Edge edge) {
    this.path.add(edge);

    if (this.min == null) {
      this.min = edge;
    } else {
      this.min = edge.residualCapacity() < min.residualCapacity() ? edge : this.min;
    }
  }
}
