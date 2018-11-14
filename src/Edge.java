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

  private int capacity;

  private int residualCapacity;

  private EdgeType type;

  public Edge(int src, int dest, int capacity) {
    this(src, dest, capacity, capacity, EdgeType.FORWARD);
  }

  public Edge(int src, int dest, int capacity, int residualCapacity, EdgeType type) {
    this.src = src;
    this.dest = dest;
    this.capacity = capacity;
    this.residualCapacity = residualCapacity;
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

  public int capacity() {
    return capacity;
  }

  public void capacity(int capacity) {
    this.capacity = capacity;
  }

  public EdgeType type() {
    return type;
  }

  public void type(EdgeType type) {
    this.type = type;
  }

  public boolean hasLeftOverCapacity() {
    return this.residualCapacity > 0;
  }

  public Edge opposite() {
    return new Edge(
        this.dest,
        this.src,
        this.capacity,
        this.residualCapacity,
        this.type == EdgeType.FORWARD ? EdgeType.BACKWARD : EdgeType.FORWARD
    );
  }

  public String toString() {
    return this.src + "->" +  this.dest;
  }

  public int residualCapacity() {
    return residualCapacity;
  }

  public void residualCapacity(int residualCapacity) {
    this.residualCapacity = residualCapacity;
  }
}
