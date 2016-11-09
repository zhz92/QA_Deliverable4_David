/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author davidzhang
 */
public class CellTest {

    /**
     * Test of toString method, of class Cell. To test when the cell is alive,
     * the method should return "X"
     */
    @Test
    public void testToStringAlive() {

        Cell instance = new Cell(true);
        String expResult = "X";
        String result = instance.toString();
        assertEquals(expResult, result);

    }

    /**
     * Test of toString method, of class Cell. To test when the cell is dead,
     * the method should return "."
     */
    @Test
    public void testToStringDead() {

        Cell instance = new Cell(false);
        String expResult = ".";
        String result = instance.toString();
        assertEquals(expResult, result);

    }

    /**
     * Test of toString method, of class Cell. To test when the cell is dead
     * firstly, then set it to alive, the method should return "X"
     */
    @Test
    public void testToStringDeadToAlive() {

        Cell instance = new Cell(false);
        instance.setAlive(true);
        String expResult = "X";
        String result = instance.toString();
        assertEquals(expResult, result);

    }

}
