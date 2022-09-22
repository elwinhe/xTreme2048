import org.junit.*;
import static org.junit.Assert.*;

/**
 * Test classes LinkedList and LLNode
 */
public class ProjectTest {
  
  /**
   * Test the generate method
   */
  SlidingTiles tiles = new SlidingTiles();

  @Test
  public void generate() {
    assertEquals("Testing generate", tiles.generate());
  }
  
  /**
   * Test the getGrid method
   */
  @Test
  public void getGrid() {
    assertEquals("Testing getGrid()", tiles.getGrid());
  }
  
}
