package interview.recursion.maze;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
* <pre>
* <B>Copyright:</B>   Izik Golan
* <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
* <B>Creation:</B>    09/01/2015 10:23
* <B>Since:</B>       BSM 9.21
* <B>Description:</B>
*
* </pre>
*/
class Path implements Comparable<Path>{
  public static final Path DEAD_END = new Path() {
    public int length() {
      return Integer.MAX_VALUE;
    }

    @Override
    public void add(Point point) {
      throw new IllegalStateException("DEAD_END: " + point.toString());
    }
  };

  private final LinkedList<Point> pointList;

  public Path() {pointList = new LinkedList<>();}

  public List<Point> getPointList() {
    return pointList;
  }

  public int length() {
    return pointList.size();
  }

  public static Path createSinglePointPath(Point point) {
    Path path = new Path();
    path.add(point);
    return path;
  }

  public void add(Point point) {
    this.pointList.add(point);
  }

  @Override
  public String toString() {
    return "Path{" + Arrays.toString(pointList.toArray()) + '}';
  }

  @SuppressWarnings("NullableProblems")
  @Override
  public int compareTo(Path rhs) {
    return this.length() - rhs.length();
  }

  /**
   * Check if the last of the path changes direction.
   * For example if the path goes up and then turns right then it is a turn.
   * The way to check it is to verify that the last 3 points in the path are on the same line.
   * If they are then it is not a turn.
   * @return true/false
   */
  public boolean newTurn() {
    if (this.pointList.size()<3) return true;


    Iterator<Point> it = this.pointList.descendingIterator();
    Point cur = it.next();
    Point prev = it.next();

    if (cur.col==prev.col) {
      return it.next().col!=cur.col;
    }
    else if (cur.row==prev.row) {
      return it.next().row!=cur.row;
    }
    else {
      return false;   //not same row; not same col??
    }
  }

  public Path clonePath() {
    Path result = new Path();
    result.pointList.addAll(this.pointList);
    return result;
  }
}
