package interview.recursion.maze;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    09/01/2015 10:14
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class Maze {

  /*package*/final byte[][] matrix;   //for cleaner code in MazeWaze (instead of using get/set) // ugly but I am lazy ;-)
  private final Point start;
  private final Point end;

  public Maze(byte[][] matrix) {
    this.matrix = matrix;
    this.start = lookupValueInMatrix(MazeFileTranslator.START);
    this.end = lookupValueInMatrix(MazeFileTranslator.END);
  }

  public int rowsCount() {
    return this.matrix.length;
  }

  public int rowLength(int row) {
    return this.matrix[row].length;
  }

  public Point getStart() {
    return start;
  }

  public Point getEnd() {
    return end;
  }

  /**
   * Find the given value in the matrix and return its place
   * @param value what to look for. Example:
   * @return the coordinates of the start place
   */
  private Point lookupValueInMatrix(byte value) {
    for (int row = 0; row < rowsCount(); row++) {
      for (int col = 0; col < rowLength(row); col++) {
        if (this.matrix[row][col] == value) {
          return new Point(row, col);
        }
      }
    }
    return null;
  }



  public boolean isWall(Point p) {
    return (matrix[p.row][p.col] == MazeFileTranslator.WALL);
  }

  public boolean isMark(Point p) {
    return (matrix[p.row][p.col] == MazeFileTranslator.MARK);
  }

  @SuppressWarnings("RedundantIfStatement")
  public boolean outOfBoundaries(Point point) {
    if (point.getRow()<0 || point.getCol()<0) return true;
    if (point.getRow() >= rowsCount()) return true;
    if (point.getCol() >= rowLength(point.getRow())) return true;
    return false;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    for (byte[] singleRow : this.matrix) {

      //noinspection ForLoopReplaceableByForEach
      for (int col = 0; col < singleRow.length ; col++) {
        buf.append(convertToPrintable(singleRow[col]));
      }
      buf.append("\n");
    }
    return buf.toString();
  }

  protected String convertToPrintable(byte val) {
    if (val<0) {
      byte b = MazeFileTranslator.memory2file(val);
      return formatSingleChar((char)b);
    }
    else {
      return formatSingleInt(val);
    }
  }

  protected String formatSingleInt(int val) {return " " + String.format("%04d", val) + " ";}
  protected String formatSingleChar(char c) {return " " + c + c + c + c + " ";}

  public void visit(Point point) {
    //do nothing
    //System.out.println("visit: "+point);
  }
}
