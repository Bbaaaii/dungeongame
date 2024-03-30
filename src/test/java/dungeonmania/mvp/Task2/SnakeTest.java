package dungeonmania.mvp.Task2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SnakeTest {
    @Test
    @Tag("2-2-1")
    @DisplayName("Basic snake movement")
    public void snakeMovement() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeSimpleMovement", "c_snakes");

        assertEquals(1, getSnakes(res).size());

        res = dmc.tick(Direction.UP);
        // snake starts at 0,0
        // treasure at 0,3 and 4,0 - should move down as the 0,3 tresure is closest
        assertEquals(new Position(0, 1), TestUtils.getEntities(res, "snake_head").get(0).getPosition());

        res = dmc.tick(Direction.UP);
        assertEquals(new Position(0, 2), TestUtils.getEntities(res, "snake_head").get(0).getPosition());

        // goes to eat the other treasure then stops moving!
        for (int i = 0; i < 10; i++) {
            res = dmc.tick(Direction.UP);
        }
        // snake doesnt move, all the treasure has been eaten
        Position snakePos = TestUtils.getEntities(res, "snake_head").get(0).getPosition();
        res = dmc.tick(Direction.UP);
        assertEquals(snakePos, TestUtils.getEntities(res, "snake_head").get(0).getPosition());
    }

    @Test
    @Tag("2-2-2")
    @DisplayName("Snake Growth and Advanced Pathfinding")
    public void snakeGrowthAndPathfinding() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeBasicGrowth", "c_snakes");

        // no bodies intially
        assertEquals(0, getSnakeBodies(res).size());

        // go down 3 times to reach 0,3 the location of the treasure
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);

        // after eating treasure 1 body
        assertEquals(new Position(0, 3), TestUtils.getEntities(res, "snake_head").get(0).getPosition());
        assertEquals(1, getSnakeBodies(res).size());

        // body should follow head
        Position prevHeadPos = TestUtils.getEntities(res, "snake_head").get(0).getPosition();
        res = dmc.tick(Direction.UP);
        assertEquals(prevHeadPos, TestUtils.getEntities(res, "snake_body").get(0).getPosition());
        // despite treasure being down, head should go left to go around wall
        assertEquals(new Position(-1, 3), TestUtils.getEntities(res, "snake_head").get(0).getPosition());

        // reach next treasure piece
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);

        // 1 body...
        assertEquals(1, getSnakeBodies(res).size());
        res = dmc.tick(Direction.UP);
        // now theres 2 bodies!
        assertEquals(2, getSnakeBodies(res).size());

        // check they are both following the snake
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(0, 5), TestUtils.getEntities(res, "snake_body").get(0).getPosition());
        assertEquals(new Position(-1, 5), TestUtils.getEntities(res, "snake_body").get(1).getPosition());
    }

    @Test
    @Tag("2-2-3")
    @DisplayName("No Invinc Snake Destruction")
    public void snakeDestruction() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeBasicGrowth", "c_snakes");

        // move 12 spaces down
        for (int i = 0; i < 12; i++) {
            res = dmc.tick(Direction.DOWN);
        }
        // snake should have eaten 4 treasure, so 4 body parts
        assertEquals(4, getSnakeBodies(res).size());

        // move 4 spaces up
        for (int i = 0; i < 4; i++) {
            res = dmc.tick(Direction.UP);
        }
        // snake eats one extra treasure, 5 bodyparts now
        assertEquals(5, getSnakeBodies(res).size());

        // destroy tick, eats 3 pieces but one is created as the snake moves
        // onto a treasure so the snake should be 6 pieces long
        res = dmc.tick(Direction.UP);
        assertEquals(3, getSnakeBodies(res).size());

        // go up to meet snake at last treasure and destroy snakes head
        for (int i = 0; i < 10; i++) {
            res = dmc.tick(Direction.UP);
        }
        for (int i = 0; i < 3; i++) {
            res = dmc.tick(Direction.RIGHT);
        }
        assertEquals(0, getSnakes(res).size());
        assertEquals(0, getSnakeBodies(res).size());
    }

    @Test
    @Tag("2-2-4")
    @DisplayName("Tests Simple Snake Buffs")
    public void snakeSimpleBuffs() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeSimpleBuffs", "c_snakeComplex");

        // let the snake buff itself up
        for (int i = 0; i < 4; i++) {
            res = dmc.tick(Direction.DOWN);
        }

        // fight the snake - should lose as the buffs allow the snake to win
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);

        // the buffed snake can now defeat the player!2
        assertEquals(true, TestUtils.getPlayer(res).isEmpty());
    }

    @Test
    @Tag("2-2-5")
    @DisplayName("Tests Invisibility and Invincibility Potion Buffs")
    public void snakeInvisAndInvinc() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_snakeComplexBuffs", "c_snakes");

        // snake buffs itself from a potion and should move onto a wall
        for (int i = 0; i < 5; i++) {
            res = dmc.tick(Direction.DOWN);
        }
        // snake should move through a wall
        assertEquals(new Position(0, 5), TestUtils.getEntities(res, "snake_head").get(0).getPosition());

        // body can also move through walls
        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(0, 5), TestUtils.getEntities(res, "snake_body").get(0).getPosition());

        // player splits the snake into 2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.LEFT);

        // checks there are 2 snakes
        assertEquals(2, getSnakes(res).size());

        // after this movement the second snake should be inside the body of the first,
        // this is testing whether invisibility carries over ()
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(0, 8), TestUtils.getEntities(res, "snake_body").get(0).getPosition());
        assertEquals(new Position(0, 8), TestUtils.getEntities(res, "snake_head").get(0).getPosition());
    }

    private List<EntityResponse> getSnakes(DungeonResponse res) {
        return TestUtils.getEntities(res, "snake_head");
    }

    private List<EntityResponse> getSnakeBodies(DungeonResponse res) {
        return TestUtils.getEntities(res, "snake_body");
    }
}
