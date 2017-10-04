import fc.potluck.model.FoodItem;
import fc.potluck.model.FoodItem.FoodType;
import fc.potluck.model.Participant;
import fc.potluck.Potluck;
import fc.potluck.PotluckPlanner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PotluckPlannerTest {
    ByteArrayOutputStream mockOut;
    PrintStream oldOut;
    PotluckPlanner potluckPlanner;
    Potluck potluck;

    @Before
    public void setUp() {
        mockOut = new ByteArrayOutputStream();
        oldOut = System.out;

        potluckPlanner = new PotluckPlanner();

        potluck = new Potluck(Arrays.asList(
                new Participant("Tom", 45, Arrays.asList(
                        new FoodItem("Chips", 5.00, FoodType.FOOD),
                        new FoodItem("Beef patties", 15.00, FoodType.FOOD))), //19.5
                new Participant("Sally", 3, Arrays.asList(
                        new FoodItem("Goldfish Crackers", 2.50, FoodType.FOOD))),
                new Participant("Kim", 38, Arrays.asList(
                        new FoodItem("Sparkling Water", 3.00, FoodType.BEVERAGE),
                        new FoodItem("Anchor Steam", 12.00, FoodType.ADULT_BEVERAGE))),
                new Participant("George", 28, Arrays.asList(
                        new FoodItem("Salad", 20, FoodType.FOOD),
                        new FoodItem("Soda", 15, FoodType.FOOD),
                        new FoodItem("Hot dogs", 50, FoodType.FOOD),
                        new FoodItem("Chip dip", 20, FoodType.FOOD),
                        new FoodItem("Wine", 160, FoodType.FOOD)))));

        System.setOut(new PrintStream(mockOut));
    }

    @After
    public void tearDown() {
        System.setOut(oldOut);
    }

    @Test
    public void testNoByob() {
        boolean byob = false;
        List<String> attendanceRoster = Arrays.asList("Tom", "Sally", "Kim", "George");

        potluckPlanner.splitCosts(potluck, byob, attendanceRoster);

        assertEquals(
                "Tom: -80.0\n" +
                "Kim: -85.0\n" +
                "George: 165.0\n", mockOut.toString());
    }

    @Test
    public void testByob() {
        boolean byob = true;
        List<String> attendanceRoster = Arrays.asList("Tom", "Sally", "Kim", "George");

        potluckPlanner.splitCosts(potluck, byob, attendanceRoster);

        assertEquals(
                "Tom: -76.0\n" +
                "Kim: -93.0\n" +
                "George: 169.0\n", mockOut.toString());
    }

    @Test
    public void testPartialAttendance() {
        boolean byob = false;
        List<String> attendanceRoster = Arrays.asList("Sally", "Kim", "George");

        potluckPlanner.splitCosts(potluck, byob, attendanceRoster);

        assertEquals(
                "Kim: -125.0\n" +
                "George: 125.0\n", mockOut.toString());
    }

}
