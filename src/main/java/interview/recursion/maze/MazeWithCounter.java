package interview.recursion.maze;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * <B>Copyright:</B>   HP Software IL
 * <B>Owner:</B>       <a href="mailto:izik.golan@hp.com">Izik Golan</a>
 * <B>Creation:</B>    09/01/2015 14:28
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * Holds a maze but every time we visit a cell we update {@link #visitCount} accordingly
 *
 * </pre>
 */
public class MazeWithCounter extends Maze {
  private final Map<Point, Integer> visitCount = new HashMap<>();

  public MazeWithCounter(byte[][] matrix) {
    super(matrix);
  }


  public void visit(Point point) {
    super.visit(point);
    Integer counter = visitCount.get(point);
    if (counter==null) {
      counter = 0;
    }
    counter++;
    visitCount.put(point, counter);
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();

    for (int row = 0; row < this.matrix.length; row++) {
      byte[] singleRow = this.matrix[row];

      for (int col = 0; col < singleRow.length ; col++) {
        if (singleRow[col] == MazeFileTranslator.FREE) {
          Integer visitCount = this.visitCount.get(new Point(row, col));
          if (visitCount==null) {
            buf.append(formatSingleInt(0));

          }
          else {
            buf.append(formatSingleInt(visitCount));
          }
        }
        else {
          buf.append(convertToPrintable(singleRow[col]));
        }
      }
      buf.append("\n");
    }
    return buf.toString();
  }
}
