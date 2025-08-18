import org.example.Ship;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShipTest {


    @Test
    @DisplayName("tests basic ship functionality")

    void testShipFunctionality() {

        Ship ac = new Ship("Aircraft Carrier", 5);
        Ship bat = new Ship("Battleship", 4);

        assertEquals("Aircraft Carrier", ac.getName());
        assertEquals(5, ac.getLength());
        assertEquals("Battleship", bat.getName());
        assertEquals(4, bat.getLength());

        ac.setPosition(0, 2, 3);
        ac.setPosition(1, 2, 4);
        ac.setPosition(2, 2, 5);
        ac.setPosition(3, 2, 6);
        ac.setPosition(4, 2, 7);

        ac.registerHit(2,3);
        ac.registerHit(2,4);
        ac.registerHit(2,5);

        assertFalse(ac.isSunk());

        ac.registerHit(2,6);
        ac.registerHit(2,7);

        assertTrue(ac.isSunk());}
}