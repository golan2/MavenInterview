package interview.recursion.maze;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * <pre>
 * <B>Copyright:</B>   HP Software IL
 * <B>Owner:</B>       <a href="mailto:izik.golan@hp.com">Izik Golan</a>
 * <B>Creation:</B>    07/01/2015 20:37
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * https://www.cs.bu.edu/teaching/alg/maze/
 * http://www.geeksforgeeks.org/practice-questions-for-linked-list-and-recursion/
 *
 *
 * </pre>
 */
public class MazeWaze {

  private static final String FILENAME = "Maze3";
  private static final String MAZE_FILE = "C:\\Users\\golaniz\\Documents\\Izik\\Dropbox\\Java\\Projects\\123Test\\static_content\\"+FILENAME+".txt";
  private static final String MAZE_LOG  = "C:\\Users\\golaniz\\Documents\\Izik\\Dropbox\\Java\\Projects\\123Test\\static_content\\"+FILENAME+".log";

  private static PrintStream log = createPrintStream();
  private int callStackCount = 0;

  private enum Direction {None, North, South, East, West};

  private static PrintStream createPrintStream() {
    try {
      return new PrintStream(new FileOutputStream(MAZE_LOG));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  private final Maze maze;

  public MazeWaze(Maze maze) {
    this.maze = maze;
  }

  public static void main(String[] args) throws IOException {
    Maze maze = MazeFileReader.readMatrixFromFile(MAZE_FILE);
    MazeWaze mazeWaze = new MazeWaze(maze);
    System.out.println(maze);
    System.out.println("Start=" + maze.getStart() + " End=" + maze.getEnd());
    Path shortestPath = mazeWaze.findShortestPath(maze.getStart(), maze.getEnd(), new Path(), Direction.None);
    System.out.println(shortestPath);
    //mazeWaze.drawPath(shortestPath);
    System.out.println(maze);
  }


  /**
   * The idea of the method is to use recursive calls.
   * We invoke 4 recursive calls to find the shortest path from {North, South, East, West}
   * We end up with 4 paths and what we need is to choose the shortest from these 4 as the winner.
   * We add current "start" to the winner path and this is the result.
   * <p>
   * Pay attention that "start" is not the "original start" of the maze; it changes each time we call it recursively.
   * The "end" is always the "original" end.
   *
   * @param start where we start
   * @param end destination cell
   * @return the path from start to end
   */
  private Path findShortestPath(Point start, Point end, Path pathSoFar, Direction direction) {

    if (start.equals(end)) {
      return Path.createSinglePointPath(end);
    }
    if (maze.outOfBoundaries(start) || maze.isWall(start) || maze.isMark(start)) {
      return Path.DEAD_END;
    }

    byte prevValue = this.maze.matrix[start.row][start.col];         //a corner case is where we are in the "real" start and we MARK it so when we finish we want to put back the START and not FREE so we store the value here to know what to put later.
    this.maze.matrix[start.row][start.col] = MazeFileTranslator.MARK;                   //MARK this cell to not repeat it later

    this.maze.visit(start);

    writeToLog(start, pathSoFar, direction);

    ArrayList<Path> paths = new ArrayList<>(4);
    paths.add(  findShortestPath(start.moveNorth(), end, pathSoFar.clonePath(), Direction.North )  );
    paths.add(  findShortestPath(start.moveSouth(), end, pathSoFar.clonePath(), Direction.South )  );
    paths.add(  findShortestPath(start.moveEast() , end, pathSoFar.clonePath(), Direction.East  )  );
    paths.add(  findShortestPath(start.moveWest() , end, pathSoFar.clonePath(), Direction.West  )  );
    Path shortest = Collections.min(paths);                    //Try {North, South, East, West} and return the shortest of all

    this.maze.matrix[start.row][start.col] = prevValue;              //restore MARKed value

    if (shortest == Path.DEAD_END)
      return Path.DEAD_END;          //if the shortest is DEAD_END (i.e. all paths are DEAD_END) then it is indeed a DEAD_END

    shortest.add(start);                                        //if not DEAD_END then add this Point and return the new path


    return shortest;
  }

  private void writeToLog(Point start, Path pathSoFar, Direction direction) {
    callStackCount++;
    pathSoFar.add(start);
    if (pathSoFar.newTurn()) {
      log.println("callStackCount=["+ callStackCount +"] direction=["+direction+"]");
      log.println(maze);
    }
  }

}