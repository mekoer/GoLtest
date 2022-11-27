package jatek;

import static org.junit.Assert.*;

import jatek.Cell;
import jatek.Tabla;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class TablaTest {
    Tabla testTabla;

    @Before
    public void setUp() throws Exception {
        testTabla = new Tabla(25, 25);
    }

    @Test
    public void getAt() {
        Assert.assertSame(testTabla.getState().get(10).get(10), testTabla.getAt(10, 10));
        Assert.assertNotSame(testTabla.getState().get(0).get(0), testTabla.getAt(10, 10));
    }

    @Test
    public void calculateAliveNear() {
        Cell cell = new Cell(true, 1, 1);
        testTabla.calculateAliveNear(cell);
        Assert.assertEquals(0, cell.getAliveNear());
    }

    @Test
    public void stateRefresh() {
        testTabla.getAt(0, 0).setAlive(true);
        testTabla.getAt(0, 1).setAlive(true);
        testTabla.stateRefresh();
        Assert.assertEquals(2, testTabla.getAt(1, 1).getAliveNear());
        testTabla.getAt(0, 2).setAlive(true);
        testTabla.stateRefresh();
        Assert.assertEquals(3, testTabla.getAt(1, 1).getAliveNear());
    }

    @Test
    public void simTest1() {
        Tabla tablaSimTest = new Tabla(3, 3);
        tablaSimTest.getAt(0, 0).setAlive(true);
        tablaSimTest.getAt(0, 1).setAlive(true);
        tablaSimTest.getAt(0, 2).setAlive(true);
        tablaSimTest.stateRefresh();
        Assert.assertEquals(3, tablaSimTest.getAt(1, 1).getAliveNear());
        tablaSimTest.nextState();
        Assert.assertTrue(tablaSimTest.getAt(1, 1).isAlive());
    }

    @Test
    public void simTest2() {
        Tabla tablaSimTest = new Tabla(3, 3);
        tablaSimTest.getAt(0, 0).setAlive(true);
        tablaSimTest.getAt(0, 1).setAlive(true);
        tablaSimTest.getAt(0, 2).setAlive(true);
        tablaSimTest.getAt(1, 0).setAlive(true);
        tablaSimTest.stateRefresh();
        Assert.assertEquals(4, tablaSimTest.getAt(1, 1).getAliveNear());
        tablaSimTest.nextState();
        Assert.assertFalse(tablaSimTest.getAt(1, 1).isAlive());
    }

    @Test
    public void simTest3() {
        Tabla tablaSimTest = new Tabla(3, 3);
        tablaSimTest.getAt(0, 0).setAlive(true);
        tablaSimTest.getAt(0, 1).setAlive(true);
        tablaSimTest.stateRefresh();
        Assert.assertEquals(2, tablaSimTest.getAt(1, 1).getAliveNear());
        tablaSimTest.nextState();
        Assert.assertFalse(tablaSimTest.getAt(1, 1).isAlive());
    }

    @Test
    public void save() throws IOException, ClassNotFoundException {
        testTabla.save();
        Tabla tablaSaveTest = new Tabla(25, 25);
        tablaSaveTest.load();
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                assertEquals(testTabla.getAt(i, j).isAlive(),
                        tablaSaveTest.getAt(i, j).isAlive());
            }
        }
    }

    @Test
    public void kill() {
        testTabla.kill();
        for (ArrayList<Cell> column : testTabla.getState()) {
            for (Cell cell : column) {
                Assert.assertFalse(cell.isAlive());
            }
        }
    }
}