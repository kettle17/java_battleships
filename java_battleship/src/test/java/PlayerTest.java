import org.example.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    @DisplayName("tests basic player functionality")

    void testPlayerFunctionality() {

        Player p1 = new Player("Player 1");

        assertEquals("Player 1", p1.getName());
        assertEquals("~", p1.getPlayerBoardCoordinate(0, 0));
        p1.setPlayerBoardCoordinate(0, 3, "O");
        assertEquals("O", p1.getPlayerBoardCoordinate(0, 3));

        assertFalse(p1.allShipsSunk());
    }
}