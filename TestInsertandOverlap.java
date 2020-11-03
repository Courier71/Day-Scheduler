import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestInsertandOverlap {
  TimeScheduler sced = new TimeScheduler();

  @BeforeEach
  // Add a few tasks to the TimeScheduler for basic testing
  void setUp() throws Exception {


    sced.insertTask("Clean", 900, 1000);
    sced.insertTask("Laundry", 600, 800);
    sced.insertTask("School", 1200, 1500);
  }

  /*
   * Runs several tests for inserting tasks and the recoloring of those tasks
   */
  @Test
  void test() {
    // Test if the root is properly changed to the color black
    if (sced.getColor(sced.tree.root).equals("Red")) {
      fail("Black root property was not properly enforced.");
    }

    // Tests if the added node red property has been applied to the two children nodes
    if (sced.getColor(sced.tree.root.leftChild).equals("Black")
        || sced.getColor(sced.tree.root.leftChild).equals("Black")) {
      fail("One or both child nodes were not added as a red node.");

    }
    //The following checks several cases of possible overlaps
    if(sced.insertTask("Morning Routine", 800, 950)) {
      fail("The schedule did not properly refuse an overlapping task.");
    }
    //Tests a task being added that ends at another tasks start time
    if(!sced.insertTask("Lunch Prep", 1100, 1200)) {
      fail("The scheduler wrongly rejected a task that does not overlap.");
    }
    //Tests adding a task that is completely within the time frame of another task
    if(sced.insertTask("Appoinment", 1300, 1400)) {
      fail("The scheduler improperly added a task that overlaps with another.");
    }

  }

}
