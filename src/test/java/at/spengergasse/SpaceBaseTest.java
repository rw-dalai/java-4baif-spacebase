package at.spengergasse;

import at.spengergasse.domain.SpaceBase;
import at.spengergasse.domain.SpaceShip;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class SpaceBaseTest {

    @Test
    void testSetName_shouldNotWork_nameNull_throwsApplicationException() {
        try {
            //GIVEN
            SpaceBase spacebase = new SpaceBase();
            assertNotNull(spacebase.getName());

            // WHEN
            spacebase.setName(null);
            fail();

        } catch (ApplicationException e) {
            // THEN
            assertEquals("setName: null-value for name", e.getMessage());
            assertThrowsExactly(ApplicationException.class, () -> {
                throw new ApplicationException("setName: null-value for name");
            });
        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testSetName_shouldWork_nameValid_noExceptionExpected() {
        try {
            //GIVEN
            SpaceBase spacebase = new SpaceBase();
            String name = spacebase.getName();

            // WHEN
            String newName = "Mir";
            assertNotEquals(name, newName);
            spacebase.setName(newName);

            // THEN
            assertEquals(newName, spacebase.getName());

        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testDocking_shouldNotWork_spaceShipIsNull_throwsApplicationException() {
        try {
            //GIVEN
            SpaceBase spacebase = new SpaceBase();

            // WHEN
            // THEN
            assertFalse(spacebase.docking(null));
            fail();

        } catch (ApplicationException e) {
            // THEN
            assertEquals("docking: value of spaceship is null", e.getMessage());
            assertThrowsExactly(ApplicationException.class, () -> {
                throw new ApplicationException("docking: value of spaceship is null");
            });
        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testDocking_shouldNotWork_spaceShipAlreadyDockedAtThisSpaceBase_throwsApplicationException() {
        try {
            //GIVEN
            SpaceBase spacebase = new SpaceBase();
            SpaceShip spaceship = new SpaceShip(spacebase, 0, 0, 2000.0);
            assertTrue(spacebase.docking(spaceship));
            assertEquals(spacebase, spaceship.getDockingBase());

            // WHEN
            spacebase.docking(spaceship);
            fail();

        } catch (ApplicationException e) {
            // THEN
            assertTrue(e.getMessage().contains("already docked at this spacebase"));
            assertThrowsExactly(ApplicationException.class, () -> {
                throw new ApplicationException("docking: spaceship id already docked at this spacebase");
            });
        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testDocking_shouldWork_spaceShipDockingAtNewStation_returnsTrue() {
        try {
            //GIVEN
            SpaceBase spacebase = new SpaceBase();
            SpaceShip spaceship = new SpaceShip(spacebase, 0, 0, 2000.0);
            assertTrue(spacebase.docking(spaceship));
            assertEquals(spacebase, spaceship.getDockingBase());
            SpaceBase spacebase1 = new SpaceBase("MIR", 0, 0);

            // WHEN
            // THEN
            assertTrue(spacebase1.docking(spaceship));
            assertEquals(spacebase1, spaceship.getDockingBase());

        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testMoveShipTo_shouldWork_spaceShipDockingAtNewStation_returnsSpaceShip() {
        try {
            //GIVEN
            SpaceBase spacebase = new SpaceBase();
            SpaceShip spaceship = new SpaceShip(spacebase, 0, 0, 2000.0);
            assertTrue(spacebase.docking(spaceship));
            assertEquals(spacebase, spaceship.getDockingBase());
            assertEquals(1, spacebase.arrangeShips().size());
            SpaceBase spacebase1 = new SpaceBase("MIR", 0, 0);
            assertEquals(0, spacebase1.arrangeShips().size());

            // WHEN
            // THEN
            assertEquals(spaceship, spacebase.moveShipTo(spaceship.getId(), spacebase1));
            assertEquals(spacebase1, spaceship.getDockingBase());
            assertEquals(0, spacebase.arrangeShips().size());
            assertEquals(1, spacebase1.arrangeShips().size());

        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testMoveShipTo_shouldNotWork_spaceShipIdNotValid_returnsNull() {
        try {
            //GIVEN
            SpaceBase spacebase = new SpaceBase();
            SpaceShip spaceship = new SpaceShip(spacebase, 0, 0, 2000.0);
            assertTrue(spacebase.docking(spaceship));
            assertEquals(spacebase, spaceship.getDockingBase());
            assertEquals(1, spacebase.arrangeShips().size());
            SpaceBase spacebase1 = new SpaceBase("MIR", 0, 0);
            assertEquals(0, spacebase1.arrangeShips().size());

            // WHEN
            // THEN
            assertNull(spacebase.moveShipTo(-100L, spacebase1));
            assertEquals(spacebase, spaceship.getDockingBase());
            assertEquals(1, spacebase.arrangeShips().size());
            assertEquals(0, spacebase1.arrangeShips().size());

        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testMoveShipTo_shouldNotWork_spaceBaseNull_returnsNull() {
        try {
            //GIVEN
            SpaceBase spacebase = new SpaceBase();
            SpaceShip spaceship = new SpaceShip(spacebase, 0, 0, 2000.0);
            assertTrue(spacebase.docking(spaceship));
            assertEquals(spacebase, spaceship.getDockingBase());
            assertEquals(1, spacebase.arrangeShips().size());

            // WHEN
            // THEN
            assertNull(spacebase.moveShipTo(spaceship.getId(), null));
            assertEquals(spacebase, spaceship.getDockingBase());
            assertEquals(1, spacebase.arrangeShips().size());

        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testBuildShip_shouldWork_returnSpaceShipId() {
        try {
            //GIVEN
            SpaceBase spacebase = new SpaceBase();

            //WHEN
            Long spaceshipId = spacebase.buildShip();
            LinkedList<SpaceShip> arrangedShips = (LinkedList<SpaceShip>) spacebase.arrangeShips();
            assertEquals(spaceshipId, arrangedShips.getFirst().getId());

            //THEN
            assertEquals(1, arrangedShips.size());
            assertEquals(spacebase, arrangedShips.getFirst().getHomeBase());
            assertEquals(spacebase, arrangedShips.getFirst().getDockingBase());
            assertEquals(2000.0, arrangedShips.getFirst().getFuel());
            assertEquals(spacebase.getPosX(), arrangedShips.getFirst().getPosX());
            assertEquals(spacebase.getPosY(), arrangedShips.getFirst().getPosY());

        } catch (ApplicationException e) {
            System.out.println("Unexpected ApplicationException: " + e.getMessage());
            fail();
        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testArrangeShipsByFuel_shouldWork_returnsSortedList() {
        try {
            //GIVEN
            SpaceBase spacebase = new SpaceBase();
            SpaceBase spacebaseHome = new SpaceBase();
            SpaceShip spaceship1 = new SpaceShip(spacebaseHome, 0, 0, 2000.0);
            SpaceShip spaceship2 = new SpaceShip(spacebaseHome, 0, 0, 1500.0);
            SpaceShip spaceship3 = new SpaceShip(spacebaseHome, 0, 0, 1000.0);
            SpaceShip spaceship4 = new SpaceShip(spacebaseHome, 0, 0, 500.0);
            spacebase.docking(spaceship1);
            spacebase.docking(spaceship2);
            spacebase.docking(spaceship3);
            spacebase.docking(spaceship4);
            LinkedList<SpaceShip> arrangedShips = (LinkedList<SpaceShip>) spacebase.arrangeShips();
            assertEquals(4, arrangedShips.size());
            assertEquals(2000, arrangedShips.getFirst().getFuel());
            assertEquals(1500, arrangedShips.get(1).getFuel());
            assertEquals(1000, arrangedShips.get(2).getFuel());
            assertEquals(500, arrangedShips.get(3).getFuel());

            //WHEN
            LinkedList<SpaceShip> arrangedShipsByFuel = (LinkedList<SpaceShip>) spacebase.arrangeShipsByFuel();
            assertEquals(4, arrangedShipsByFuel.size());

            // THEN
            assertEquals(500, arrangedShipsByFuel.getFirst().getFuel());
            assertEquals(1000, arrangedShipsByFuel.get(1).getFuel());
            assertEquals(1500, arrangedShipsByFuel.get(2).getFuel());
            assertEquals(2000, arrangedShipsByFuel.get(3).getFuel());

        } catch (ApplicationException e) {
            System.out.println("Unexpected ApplicationException: " + e.getMessage());
            fail();
        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testToString() {
        try {
            //GIVEN
            SpaceBase spacebase = new SpaceBase();
            SpaceShip spaceship = new SpaceShip(spacebase, 0, 0, 2000.0);

            //WHEN
            System.out.println(spacebase);
            System.out.println();
            //THEN
            assertEquals(0, spacebase.arrangeShips().size());

            //WHEN
            spacebase.docking(spaceship);
            System.out.println(spacebase);
            System.out.println();
            //THEN
            assertEquals(1, spacebase.arrangeShips().size());

            //WHEN
            spacebase.docking(spaceship);
            fail();

        } catch (ApplicationException e) {
            assertThrowsExactly(ApplicationException.class, () -> {
                throw new ApplicationException("docking: spaceship already docked at this spacebase");
            });
        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }
}