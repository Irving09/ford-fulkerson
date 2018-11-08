/**
 * CONFIDENTIAL INFORMATION
 *
 * All Rights Reserved.  Unauthorized reproduction, transmission, or
 * distribution of this software is a violation of applicable laws.
 *
 * Date: Nov 08, 2018
 * Copyright 2018 innoirvinge@gmail.com
 */

/**
 * @author irving09 <innoirvinge@gmail.com>
 */
public class Edge {

  private int src;

  private int dest;

  private int flowValue;

  private int capacity;

  private int residualCapacity;

  private EdgeType type;

  public Edge(int src, int dest, int capacity) {
    this(src, dest, 0, capacity);
  }

  public Edge(int src, int dest, int flowValue, int capacity) {
    this(src, dest, flowValue, capacity, EdgeType.FORWARD);
  }

  public Edge(int src, int dest, int flowValue, int capacity, EdgeType type) {
    this.src = src;
    this.dest = dest;
    this.flowValue = flowValue;
    this.capacity = capacity;
    this.residualCapacity = capacity;
    this.type = type;
  }

  public int src() {
    return src;
  }

  public void src(int src) {
    this.src = src;
  }

  public int dest() {
    return dest;
  }

  public void dest(int dest) {
    this.dest = dest;
  }

  public int flowValue() {
    return flowValue;
  }

  public void flowValue(int flowValue) {
    this.flowValue = flowValue;
  }

  public int capacity() {
    return capacity;
  }

  public void capacity(int capacity) {
    this.capacity = capacity;
  }

  public int residualCapacity() {
    return residualCapacity;
  }

  public void residualCapacity(int residualCapacity) {
    this.residualCapacity = residualCapacity;
  }

  public EdgeType type() {
    return type;
  }

  public void type(EdgeType type) {
    this.type = type;
  }

  public Edge opposite() {
    return new Edge(
        this.dest(),
        this.src(),
        this.flowValue,
        this.capacity(),
        this.type == EdgeType.FORWARD ? EdgeType.BACKWARD : EdgeType.FORWARD
    );
  }
}
