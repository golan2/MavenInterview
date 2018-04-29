package interview.recursion.maze;

import org.apache.poi.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    09/01/2015 10:05
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class MazeFileReader {

  private static final byte LF       =   10;
  private static final byte CR       =   13;
  private static final int  MAX_SIZE = 1000;


  /**
   * Read from file and return a matrix
   *
   * [1] Read all file into a one long byte array
   * [2] Handle bytes one-by-one while distinguishing between:
   *     [a] Newline1 - Copy the singleRow that has just ended into the correct location in matrix
   *     [b] Newline2 - Since new line is represented by 2 chars (CR and LF) we skip this one
   *     [c] Invalid  - in case the current byte represents an invalid character we parish
   *     [d] Valid    - add the byte in the correct place in the singleRow and move on to next byte
   * [3] Handle the last singleRow
   * [4] Shrink the matrix to actual size needed instead of {@link #MAX_SIZE}
   * @throws java.io.IOException
   */
  public static Maze readMatrixFromFile(String mazeFile) throws IOException {
    byte[][] matrix = new byte[MAX_SIZE][];

    //[1]
    InputStream inputStream = new FileInputStream(new File(mazeFile));
    byte[] bytes = IOUtils.toByteArray(inputStream);

    byte[] singleRow = new byte[MAX_SIZE];
    int row=0;
    int col=0;

    //[2]
    for (byte bb : bytes) {

      //[a]
      if (bb == LF) {
        //end of singleRow - put whatever we have in "singleRow" into the relevant row in the matrix
        matrix[row] = Arrays.copyOf(singleRow, col);
        row++;
        col = 0;
      }

      //[b]
      else //noinspection StatementWithEmptyBody
        if (bb == CR) {
          //skip this one since "newline" is 10 and 13 so we handle it only once
        }

        //[c]
        else if (MazeFileTranslator.invalidValue(bb)) {
          throw new IllegalArgumentException("Byte=["+bb+"]");
        }

        //[d]
        else {
          singleRow[col] = MazeFileTranslator.file2memory(bb);
          col++;
        }
    }

    //[3]
    if (matrix[row]==null) {
      matrix[row] = Arrays.copyOf(singleRow, col);
    }

    //[4]
    return new MazeWithCounter(Arrays.copyOf(matrix, row + 1));
  }


}
