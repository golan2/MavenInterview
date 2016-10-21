package interview.recursion.maze;

import java.util.Arrays;

/**
 * <pre>
 * <B>Copyright:</B>   HP Software IL
 * <B>Owner:</B>       <a href="mailto:izik.golan@hp.com">Izik Golan</a>
 * <B>Creation:</B>    09/01/2015 10:49
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class MazeFileTranslator {
  public  static final byte   START       = -1;
  public  static final byte   END         = -2;
  public  static final byte   WALL        = -3;
  public  static final byte   MARK        = -4;
  public  static final byte   FREE        =  0;
  private static final byte   F_START     = 'S';
  private static final byte   F_END       = 'E';
  private static final byte   F_WALL      = '#';
  public  static final byte   F_MARK      = 'X';
  private static final byte   F_FREE      = ' ';
  private static final byte[] VALID_CHARS = {F_FREE, F_WALL, F_START, F_END}; //when reading from file the MARK is not relevant
  private static final int    NOT_FOUND   = -1;

  public static byte file2memory(byte bb) {
    if (F_END   == bb) return END;
    if (F_START == bb) return START;
    if (F_END   == bb) return END;
    if (F_FREE  == bb) return FREE;
    if (F_WALL  == bb) return WALL;
    throw new IllegalAccessError("Invalid value in file = ["+bb+"]");
  }

  public static boolean invalidValue(byte bb) {
    return Arrays.binarySearch(VALID_CHARS, bb) == NOT_FOUND;
  }

  public static byte memory2file(byte bb) {
    if (END   == bb) return F_END;
    if (START == bb) return F_START;
    if (END   == bb) return F_END;
    if (FREE  == bb) return F_FREE;
    if (WALL  == bb) return F_WALL;
    if (MARK  == bb) return F_MARK;
    throw new IllegalAccessError("Invalid value in memory = ["+bb+"]");
  }
}
