/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author davidzhang
 */
public class MainPanelTest {

    private MainPanel mainPanel;

    @Before
    public void setUp() {
        mainPanel = new MainPanel(15);

    }

    /**
     * The convertToInt function returns the integer passed in when the input
     * parameter is bigger or equal to zero, so if zero is passed in, zero
     * should be returned.
     */
    @Test
    public void convertToIntTestZero() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method;
        Class[] type = new Class[]{int.class};
        method = MainPanel.class.getDeclaredMethod("convertToInt", type);
        method.setAccessible(true);
        MainPanel mainPanel = new MainPanel(15);
        Object returnValue = method.invoke(mainPanel, 0);
        int result;
        result = ((Integer) returnValue);

        assertEquals(0, result);

    }

    /**
     * The convertToInt function returns the integer passed in when the input
     * parameter is bigger or equal to zero, so if number that bigger than zero
     * is passed in, the same number should be returned.
     */
    @Test
    public void convertToIntTestBiggerThanZero() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method;
        Class[] type = new Class[]{int.class};
        method = MainPanel.class.getDeclaredMethod("convertToInt", type);
        method.setAccessible(true);
        MainPanel mainPanel = new MainPanel(15);
        Object returnValue = method.invoke(mainPanel, 2);
        int result;
        result = ((Integer) returnValue);

        assertEquals(2, result);

    }

    /**
     * The convertToInt function returns the integer passed in when the input
     * parameter is bigger or equal to zero, so if number that smaller than zero
     * is passed in, it should throw the exception.
     */
    @Test
    public void convertToIntTestSmallerThanZero() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method;
        try {
            Class[] type = new Class[]{int.class};
            method = MainPanel.class.getDeclaredMethod("convertToInt", type);
            method.setAccessible(true);
            MainPanel mainPanel = new MainPanel(15);
            Object returnValue = method.invoke(mainPanel, -1);
            int result;
            result = ((Integer) returnValue);

            fail("No number format exception occurred!");
        } catch (NoSuchMethodException | IllegalAccessException e) {
            fail(e.getMessage());
        } catch (InvocationTargetException e) {

        }

    }

    /**
     * The convertToInt function returns the integer passed in when the input
     * parameter is greater than or equal to zero, so if Integer.MAX_VALUE is
     * passed in, Integer.MAX_VALUE should be returned.
     */
    @Test
    public void convertToIntTestMaxInt() throws IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        Method method;

        Class[] type = new Class[]{int.class};
        method = MainPanel.class.getDeclaredMethod("convertToInt", type);
        method.setAccessible(true);
        MainPanel mainPanel = new MainPanel(15);
        Object returnValue = method.invoke(mainPanel, Integer.MAX_VALUE);
        int result = ((Integer) returnValue);

        assertEquals(Integer.MAX_VALUE, result);
    }

    /**
     * With all cells dead in the grid, run continuously for 2 seconds to ensure
     * final state is reached (runContinous()) before stopping stop() via a
     * separate thread. The final state of the grid should be all dead (false).
     */
    @Test
    public void runContinuousTestAllFalse() throws Exception {
        Cell[][] f = new Cell[15][15];
        for (int i = 0; i < f.length; i++) {
            for (int j = 0; j < f[i].length; j++) {
                f[i][j] = new Cell(false);
            }
        }
        mainPanel.setCells(f);
        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                mainPanel.stop();
            }
        }, 2, TimeUnit.SECONDS);

        mainPanel.runContinuous();

        boolean[][] c = mainPanel.convertToBoolean(mainPanel.getCells());
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i].length; j++) {
                if (c[i][j]) {
                    fail();
                }
            }
        }
    }

    /**
     * With only the 3 cells in the top left corner alive, run continuously for
     * 2 seconds to ensure final state is reached (runContinous()) before
     * stopping stop() via a separate thread. The final state of the grid should
     * be only the 4 cells in the top, left corner forming a square.
     */
    @Test
    public void runContinuousTestCornerAlive() throws Exception {
        Cell[][] f = new Cell[15][15];

        /*
        Pattern of starting grid:
        XX...
        X....
        .....
        .....
        .....
         */
        for (int i = 0; i < f.length; i++) {
            for (int j = 0; j < f[i].length; j++) {
                if ((i == 0 && (j == 0 || j == 1)) || (i == 1 && j == 0)) {
                    f[i][j] = new Cell(true);
                } else {
                    f[i][j] = new Cell(false);
                }
            }
        }
        mainPanel.setCells(f);
        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                mainPanel.stop();
            }
        }, 2, TimeUnit.SECONDS);
        mainPanel.runContinuous();

        /*
        Pattern of resulting grid:
        XX...
        XX...
        .....
        .....
        .....
         */
        boolean[][] c = mainPanel.convertToBoolean(mainPanel.getCells());
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i].length; j++) {
                if ((i == 0 && (j == 0 || j == 1)) || (i == 1 && (j == 0 || j == 1))) {
                    if (!c[i][j]) {
                        fail();
                    }
                } else if (c[i][j]) {
                    fail();
                }
            }
        }
    }

    /**
     * With only the 4 cells forming a plus shape in the center of the grid
     * alive, run continuously for 2 seconds to ensure final state is reached
     * (runContinous()) before stopping stop() via a separate thread. The final
     * state of the grid should be all dead(false) due to cell interactions that
     * wrap around the grid.
     */
    @Test
    public void runContinuousTestPlusShapeAlive() throws Exception {
        mainPanel = new MainPanel(5);
        Cell[][] f = new Cell[5][5];

        /*
        Pattern of starting grid:
        .....
        ..X..
        .XXX.
        ..X..
        .....
         */
        for (int i = 0; i < f.length; i++) {
            for (int j = 0; j < f[i].length; j++) {
                if ((i == 2 && (j == 1 || j == 2 || j == 3) || (i == 1 && j == 2) || (i == 3 && j == 2))) {
                    f[i][j] = new Cell(true);
                } else {
                    f[i][j] = new Cell(false);
                }
            }
        }
        mainPanel.setCells(f);

        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                mainPanel.stop();
            }
        }, 2, TimeUnit.SECONDS);
        mainPanel.runContinuous();

        boolean[][] c = mainPanel.convertToBoolean(mainPanel.getCells());
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i].length; j++) {
                if (c[i][j]) {
                    fail();
                }
            }
        }
    }

}
