package interview.recursion.maze;

/**
 * Represents a single place in the maze
 */
class Point {
  int row;
  int col;

  public Point(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public Point moveNorth() {
    return new Point(row-1, col);
  }

  public Point moveSouth() {
    return new Point(row+1, col);
  }

  public Point moveEast() {
    return new Point(row, col+1);
  }

  public Point moveWest() {
    return new Point(row, col-1);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Point)) {
      return false;
    }

    Point point = (Point) o;

    if (col != point.col) {
      return false;
    }
    //noinspection RedundantIfStatement
    if (row != point.row) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = row;
    result = 31 * result + col;
    return result;
  }

  @Override
  public String toString() {
    return "(" + row + "," + col + ')';
  }
}
