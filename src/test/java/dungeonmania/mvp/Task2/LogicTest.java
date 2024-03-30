package dungeonmania.mvp.Task2;

import dungeonmania.DungeonManiaController;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogicTest {

    @Test
    @Tag("2-3-1")
    @DisplayName("Test the OR rule")
    public void or() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicTest_or", "c_logicTest");

        // Initially, the light bulb should be off
        assertFalse(checkBulb(res));

        // Move the boulder onto the first switch
        res = dmc.tick(Direction.RIGHT);
        assertTrue(checkBulb(res));

        // We are at (2,1), we move to (9,1) to push the other boulder
        dmc.tick(Direction.DOWN);
        for (int i = 2; i < 9; i++) {
            dmc.tick(Direction.RIGHT);
        }
        dmc.tick(Direction.UP);

        // Push the second boulder
        res = dmc.tick(Direction.LEFT);
        assertTrue(checkBulb(res));
    }

    @Test
    @Tag("2-3-2")
    @DisplayName("Test the XOR rule")
    public void xor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicTest_xor", "c_logicTest");

        // Initially, the light bulb should be off
        assertFalse(checkBulb(res));

        // Move the boulder onto the first switch
        res = dmc.tick(Direction.RIGHT);
        assertTrue(checkBulb(res));

        // We are at (2,1), we move to (9,1) to push the other boulder
        dmc.tick(Direction.DOWN);
        for (int i = 2; i < 9; i++) {
            dmc.tick(Direction.RIGHT);
        }
        dmc.tick(Direction.UP);

        // Push the second boulder
        res = dmc.tick(Direction.LEFT);
        assertFalse(checkBulb(res));
    }

    @Test
    @Tag("2-3-4")
    @DisplayName("Test the AND rule (1 wires)")
    public void and1() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicTest_and2", "c_logicTest");

        // Initially, the light bulb should be off
        assertFalse(checkBulb(res));

        // Move the boulder onto the first switch
        res = dmc.tick(Direction.RIGHT);
        assertFalse(checkBulb(res));
    }

    @Test
    @Tag("2-3-4")
    @DisplayName("Test the AND rule (2 wires)")
    public void and3() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicTest_and2", "c_logicTest");

        // Initially, the light bulb should be off
        assertFalse(checkBulb(res));

        // Move the boulder onto the first switch
        res = dmc.tick(Direction.RIGHT);
        assertFalse(checkBulb(res));

        // We are at (2,1), we move to (9,1) to push the other boulder
        dmc.tick(Direction.DOWN);
        for (int i = 2; i < 9; i++) {
            dmc.tick(Direction.RIGHT);
        }
        dmc.tick(Direction.UP);

        // Push the second boulder
        res = dmc.tick(Direction.LEFT);
        assertTrue(checkBulb(res));
    }

    @Test
    @Tag("2-3-5")
    @DisplayName("Test the AND rule (3 wires)")
    public void and2() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicTest_and3", "c_logicTest");

        // Initially, the light bulb should be off
        assertFalse(checkBulb(res));

        // Move the boulder onto the first switch
        res = dmc.tick(Direction.RIGHT);
        assertFalse(checkBulb(res));

        // We are at (2,1), we move to (9,1) to push the other boulder
        dmc.tick(Direction.DOWN);
        for (int i = 2; i < 9; i++) {
            dmc.tick(Direction.RIGHT);
        }
        dmc.tick(Direction.UP);

        // Push the second boulder
        res = dmc.tick(Direction.LEFT);
        assertFalse(checkBulb(res));

        // We are at (8,1), we want to move to (7, -2)
        for (int i = 1; i > -2; i--) {
            dmc.tick(Direction.UP);
        }
        dmc.tick(Direction.LEFT);

        // Push the third boulder
        res = dmc.tick(Direction.LEFT);
        assertTrue(checkBulb(res));
    }

    @Test
    @Tag("2-3-6")
    @DisplayName("Test the CO_AND rule incorrectly (2 wires)")
    public void coand2False() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicTest_coand2", "c_logicTest");

        // Initially, the light bulb should be off
        assertFalse(checkBulb(res));

        // Move the boulder onto the first switch
        res = dmc.tick(Direction.RIGHT);
        assertFalse(checkBulb(res));

        // We are at (1,-1), we move to (4,-4) to push the other boulder
        for (int i = -1; i > -4; i--) {
            dmc.tick(Direction.UP);
        }
        for (int i = 1; i < 4; i++) {
            dmc.tick(Direction.RIGHT);
        }

        // Push the second boulder
        res = dmc.tick(Direction.DOWN);
        assertFalse(checkBulb(res));
    }

    @Test
    @Tag("2-3-7")
    @DisplayName("Test the CO_AND rule correctly (2 wires)")
    public void coand2True() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicTest_coand2", "c_logicTest");

        // Initially, the light bulb should be off
        assertFalse(checkBulb(res));

        // We are at (0,-1), we move to (4,-4) to push the other boulder
        for (int i = -1; i > -4; i--) {
            dmc.tick(Direction.UP);
        }
        for (int i = 0; i < 4; i++) {
            dmc.tick(Direction.RIGHT);
        }

        // Push the first boulder
        res = dmc.tick(Direction.DOWN);
        assertTrue(checkBulb(res));

        // We are at (4,-3), we move to (0,-1) to push the other boulder
        for (int i = 4; i > 0; i--) {
            dmc.tick(Direction.LEFT);
        }
        for (int i = -3; i < -1; i++) {
            dmc.tick(Direction.DOWN);
        }

        // Move the second boulder switch
        res = dmc.tick(Direction.RIGHT);
        assertTrue(checkBulb(res));
    }

    @Test
    @Tag("2-3-8")
    @DisplayName("Test the CO_AND rule incorrectly (3 wires)")
    public void coand3False() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicTest_coand3", "c_logicTest");

        // Initially, the light bulb should be off
        assertFalse(checkBulb(res));

        // Move the boulder onto the first switch
        res = dmc.tick(Direction.RIGHT);
        assertFalse(checkBulb(res));

        // We are at (1,-1), we move to (4,-4) to push the other boulder
        for (int i = -1; i > -4; i--) {
            dmc.tick(Direction.UP);
        }
        for (int i = 1; i < 4; i++) {
            dmc.tick(Direction.RIGHT);
        }

        // Push the second boulder
        res = dmc.tick(Direction.DOWN);
        assertFalse(checkBulb(res));
    }

    @Test
    @Tag("2-3-9")
    @DisplayName("Test the CO_AND rule correctly (3 wires)")
    public void coand3True() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicTest_coand3", "c_logicTest");

        // Initially, the light bulb should be off
        assertFalse(checkBulb(res));

        // We are at (0,-1), we move to (4,-4) to push the other boulder
        for (int i = -1; i > -4; i--) {
            dmc.tick(Direction.UP);
        }
        for (int i = 0; i < 4; i++) {
            dmc.tick(Direction.RIGHT);
        }

        // Push the first boulder
        res = dmc.tick(Direction.DOWN);
        assertTrue(checkBulb(res));

        // We are at (4,-3), we move to (0,-1) to push the other boulder
        for (int i = 4; i > 0; i--) {
            dmc.tick(Direction.LEFT);
        }
        for (int i = -3; i < -1; i++) {
            dmc.tick(Direction.DOWN);
        }

        // Move the second boulder switch
        res = dmc.tick(Direction.RIGHT);
        assertTrue(checkBulb(res));
    }

    @Test
    @Tag("2-3-10")
    @DisplayName("Test the logic bomb")
    public void logicBomb() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicTest_logicBomb", "c_logicTest");

        // Initially, the bomb should not have exploded
        assertFalse(bombExploded(res));

        // Press the first switch
        dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);

        // This is a logic bomb, so it should not have exploded
        assertFalse(bombExploded(res));

        // Press the second switch
        dmc.tick(Direction.LEFT);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        // The and condition should be met, the bomb should explode
        assertTrue(bombExploded(res));
    }

    @Test
    @Tag("2-3-11")
    @DisplayName("Test the switch door")
    public void switchDoor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicTest_switchDoor", "c_logicTest");

        // Initially, the door is locked
        assertFalse(checkDoor(res));

        // Attempt to move through the (locked) door to the exit
        dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // The door's not activated so we should fail
        assertTrue(TestUtils.getGoals(res).contains(":exit"));

        // Press the switch to activate the door
        dmc.tick(Direction.LEFT);
        dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);

        // The door should now be unlocked
        assertTrue(checkDoor(res));

        // We now attempt to move through the (unlocked) door to the exit
        dmc.tick(Direction.UP);
        dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // We should succeed
        assertEquals("", TestUtils.getGoals(res));
    }

    public boolean checkBulb(DungeonResponse res) {
        // As long as there's only one light bulb in the test, this will work
        return TestUtils.getEntities(res, "light_bulb_on").size() != 0;
    }

    public boolean checkDoor(DungeonResponse res) {
        // As long as there's only one switch door in the test, this will work
        return TestUtils.getEntities(res, "switch_door_open").size() != 0;
    }

    public boolean bombExploded(DungeonResponse res) {
        // As long as there's only one light bulb in the test, this will work
        return TestUtils.getEntities(res, "bomb").size() == 0;
    }

}
