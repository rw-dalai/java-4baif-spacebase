package at.spengergasse;

import static org.junit.jupiter.api.Assertions.*;

import at.spengergasse.domain.SpaceBase;
import at.spengergasse.domain.SpaceShip;
import org.junit.jupiter.api.Test;

class SpaceShipTest {

    @Test
    void testRefuel_shouldWork_fuel1000_refuelAmount1000() {
        try {
            //GIVEN
            SpaceShip spaceship = new SpaceShip();
            assertEquals(1000.0, spaceship.getFuel());

            //WHEN
            double refuelAmount = 1000.0;
            double expectedAmount = 1000.0 + refuelAmount;
            spaceship.refuel(refuelAmount);

            //THEN
            assertEquals(expectedAmount, spaceship.getFuel());

        } catch (ApplicationException e) {
            System.out.println("Unexpected Exception: " + e.getMessage());
            fail();
        }
    }

    @Test
    void testRefuel_shouldNotWork_fuel1000_refuelAmount1001_throwsException() {
        try {
            //GIVEN
            SpaceShip spaceship = new SpaceShip();
            assertEquals(1000.0, spaceship.getFuel());

            //WHEN
            double refuelAmount = 1001.0;
            spaceship.refuel(refuelAmount);
            fail();

        } catch (ApplicationException e) {
            //THEN
            assertTrue(e.getMessage().contains("refuel: amount 1001.0 is too much"));
            assertThrowsExactly(ApplicationException.class, () -> {
                throw new ApplicationException("refuel: amount 1001.0 is too much");
            });
        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testRefuel_shouldWork_fuel1000_refuelAmountZero_noExceptionExpected() {
        try {
            //GIVEN
            SpaceShip spaceship = new SpaceShip();
            double fuelAmount = spaceship.getFuel();
            assertEquals(1000.0, fuelAmount);

            //WHEN
            spaceship.refuel(0.0);
            assertEquals(fuelAmount, spaceship.getFuel());

        } catch (ApplicationException e) {
            System.out.println("Unexpected Exception: " + e.getMessage());
            fail();
        }
    }

    @Test
    void testRefuel_shouldWork_maxFuel2000_refuelAmountZero_noExceptionExpected() {
        try {
            //GIVEN
            SpaceBase spaceBase = new SpaceBase("ISS", 0, 0);
            SpaceShip spaceship = new SpaceShip(spaceBase, 0, 0, 2000.0);
            assertEquals(2000.0, spaceship.getFuel());

            //WHEN
            double refuelAmount = 0.0;
            spaceship.refuel(refuelAmount);

        } catch (ApplicationException e) {
            System.out.println("Unexpected Exception: " + e.getMessage());
            fail();
        }
    }

    @Test
    void testSetDockingBase_shouldWork_isHomeBase_fuel1000_refuelAmount1000_noExceptionExpected() {
        try {
            //GIVEN
            SpaceShip spaceship = new SpaceShip();
            assertEquals(1000.0, spaceship.getFuel());

            //WHEN
            double refuelAmount = 1000.0;
            double expectedAmount = 1000.0 + refuelAmount;
            spaceship.setDockingBase(spaceship.getHomeBase());

            //THEN
            assertEquals(expectedAmount, spaceship.getFuel());

        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testSetDockingBase_shouldWork_isNotHomeBase_noExceptionExpected() {
        try {
            //GIVEN
            SpaceShip spaceship = new SpaceShip();
            SpaceBase spacebase = new SpaceBase("MIR", 0, 0);
            assertNotEquals(spaceship.getHomeBase(), spacebase);

            //WHEN
            double expectedAmount = spaceship.getFuel();
            spaceship.setDockingBase(spacebase);

            //THEN
            assertEquals(expectedAmount, spaceship.getFuel());
            assertEquals(spaceship.getDockingBase(), spacebase);

        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testSetDockingBase_shouldWork_null_noExceptionExpected() {
        try {
            //GIVEN
            SpaceShip spaceship = new SpaceShip();
            assertNotNull(spaceship.getHomeBase());

            //WHEN
            double expectedAmount = spaceship.getFuel();
            spaceship.setDockingBase(null);

            //THEN
            assertEquals(expectedAmount, spaceship.getFuel());
            assertNull(spaceship.getDockingBase());

        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testMoveTo_shouldNotWork_posXNegative_throwsApplicationException() {
        try {
            //GIVEN
            SpaceShip spaceship = new SpaceShip();
            assertEquals(0, spaceship.getPosX());
            assertEquals(0, spaceship.getPosY());

            //WHEN
            int posX = -1;
            int posY = 0;
            spaceship.moveTo(posX, posY);
            fail();

        } catch (ApplicationException e) {
            //THEN
            assertTrue(e.getMessage().contains("moveTo: wrong target-position: -1/0"));
            assertThrowsExactly(ApplicationException.class, () -> {
                throw new ApplicationException("moveTo: wrong target-position: -1/0");
            });
        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testMoveTo_shouldNotWork_posYNegative_throwsApplicationException() {
        try {
            //GIVEN
            SpaceShip spaceship = new SpaceShip();
            assertEquals(0, spaceship.getPosX());
            assertEquals(0, spaceship.getPosY());

            //WHEN
            int posX = 0;
            int posY = -1;
            spaceship.moveTo(posX, posY);
            fail();

        } catch (ApplicationException e) {
            // THEN
            assertTrue(e.getMessage().contains("moveTo: wrong target-position: 0/-1"));
            assertThrowsExactly(ApplicationException.class, () -> {
                throw new ApplicationException("moveTo: wrong target-position: 0/-1");
            });
        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testMoveTo_shouldWork_posX0PosY0_noExceptionExpected() {
        try {
            //GIVEN
            SpaceShip spaceship = new SpaceShip();
            assertEquals(0, spaceship.getPosX());
            assertEquals(0, spaceship.getPosY());
            assertEquals(1000.0, spaceship.getFuel());

            //WHEN
            int posX = 0;
            int posY = 0;
            spaceship.moveTo(posX, posY);

            //THEN
            assertEquals(posX, spaceship.getPosX());
            assertEquals(posY, spaceship.getPosY());
            assertEquals(1000.0, spaceship.getFuel());

        } catch (ApplicationException e) {
            System.out.println("Unexpected Exception: " + e.getMessage());
            fail();
        }
    }

    @Test
    void testMoveTo_shouldWork_posX1PosY2_noExceptionExpected() {
        try {
            //GIVEN
            SpaceShip spaceship = new SpaceShip();
            assertEquals(0, spaceship.getPosX());
            assertEquals(0, spaceship.getPosY());
            assertEquals(1000.0, spaceship.getFuel());

            //WHEN
            int posX = 1;
            int posY = 2;
            spaceship.moveTo(posX, posY);

            //THEN
            assertEquals(posX, spaceship.getPosX());
            assertEquals(posY, spaceship.getPosY());
            assertNotEquals(1000.0, spaceship.getFuel());
            assertEquals(991.9948766405507, spaceship.getFuel());

        } catch (ApplicationException e) {
            System.out.println("Unexpected Exception: " + e.getMessage());
            fail();
        }
    }

    @Test
    void testMoveTo_shouldNotWork_posX150PosY250_lessFuel_throwsApplicationException() {
        try {
            //GIVEN
            SpaceShip spaceship = new SpaceShip();
            assertEquals(0, spaceship.getPosX());
            assertEquals(0, spaceship.getPosY());
            assertEquals(1000.0, spaceship.getFuel());

            //WHEN
            int posX = 150;
            int posY = 250;
            spaceship.moveTo(posX, posY);
            fail();

        } catch (ApplicationException e) {
            //THEN
            assertTrue(e.getMessage().contains("moveTo: lack of fuel "));
            assertThrowsExactly(ApplicationException.class, () -> {
                throw new ApplicationException("moveTo: lack of fuel (-43.74038917730877)");
            });
        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    /*
    // testCalculateDistanceTo
    // calculateDistanceTo() is private, for testing set accessModifier to public
    // and uncomment the following two test methods
    @Test
    void testCalculateDistanceTo_shouldNotWork_posXNegative_throwsApplicationException() {
        try {
            //GIVEN
            SpaceShip spaceship = new SpaceShip();
            assertEquals(0, spaceship.getPosX());
            assertEquals(0, spaceship.getPosY());

            //WHEN
            int posX = -1;
            int posY = 0;
            spaceship.calculateDistanceTo(posX, posY);
            fail();

        } catch (ApplicationException e) {
            //THEN
            assertThrowsExactly(ApplicationException.class, () -> {
                throw new ApplicationException("calculateDistanceTo: wrong target-position: -1/0");
            });
        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testCalculateDistanceTo_shouldNotWork_posYNegative_throwsApplicationException() {
        try {
            //GIVEN
            SpaceShip spaceship = new SpaceShip();
            assertEquals(0, spaceship.getPosX());
            assertEquals(0, spaceship.getPosY());

            //WHEN
            int posX = 0;
            int posY = -1;
            spaceship.calculateDistanceTo(posX, posY);
            fail();

        } catch (ApplicationException e) {
            //THEN
            assertThrowsExactly(ApplicationException.class, () -> {
                throw new ApplicationException("calculateDistanceTo: wrong target-position: -1/0");
            });
        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testCalculateDistanceTo_shouldWork_posXposXZero_returnsZero() {
        try {
            //GIVEN
            SpaceShip spaceship = new SpaceShip();
            assertEquals(0, spaceship.getPosX());
            assertEquals(0, spaceship.getPosY());

            //WHEN
            int posX = 0;
            int posY = 0;

            //THEN
            assertEquals(0.0, spaceship.calculateDistanceTo(posX, posY));

        } catch (ApplicationException e) {
            System.out.println("Unexpected Exception: " + e.getMessage());
            fail();
        }
    }

    @Test
    void testCalculateDistanceTo_shouldWork_posX1PosY1_returnsCorrectDistance() {
        try {
            //GIVEN
            SpaceShip spaceship = new SpaceShip();
            assertEquals(0, spaceship.getPosX());
            assertEquals(0, spaceship.getPosY());

            //WHEN
            int posX = 1;
            int posY = 1;
            double expected = Math.sqrt(2);

            //THEN
            assertEquals(expected, spaceship.calculateDistanceTo(posX, posY));

        } catch (ApplicationException e) {
            System.out.println("Unexpected Exception: " + e.getMessage());
            fail();
        }
    }

    @Test
    void testCalculateDistanceTo_shouldWork_posX1PosY2_returnsCorrectDistance() {
        try {
            //GIVEN
            SpaceShip spaceship = new SpaceShip();
            assertEquals(0, spaceship.getPosX());
            assertEquals(0, spaceship.getPosY());

            //WHEN
            int posX = 1;
            int posY = 2;
            double expected = Math.sqrt(5);

            //THEN
            assertEquals(expected, spaceship.calculateDistanceTo(posX, posY));

        } catch (ApplicationException e) {
            System.out.println("Unexpected Exception: " + e.getMessage());
            fail();
        }
    }
    */

    /*
    // testSetHomeBase
    // setHomeBase() is private, for testing set accessModifier to public
    // and uncomment the following two test methods
    @Test
    void testSetHomeBase_shouldWork_isNotNull_noExceptionExpected() {
        try {
            //GIVEN
            SpaceShip spaceship = new SpaceShip();
            SpaceBase spacebase = new SpaceBase("MIR", 0, 0);
            assertNotEquals(spaceship.getHomeBase(), spacebase);

            //WHEN
            spaceship.setHomeBase(spacebase);

            //THEN
            assertEquals(spacebase, spaceship.getHomeBase());

        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testSetHomeBase_shouldNotWork_homebaseNull_throwsApplicationException() {
        try {
            //GIVEN
            SpaceShip spaceship = new SpaceShip();
            assertNotNull(spaceship.getHomeBase());

            //WHEN
            spaceship.setHomeBase(null);
            fail();

        } catch (ApplicationException e) {
            //THEN
            assertThrowsExactly(ApplicationException.class, () -> {
                throw new ApplicationException("homeBase is null");
            });
        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }
    */

    /*
    // testCalculateConsumption
    // calculateConsumption() is private, for testing set accessModifier to public
    // and uncomment the following four test methods
    @Test
    void testCalculateConsumption_shouldWork_distanceNegative_returnsZero() {
        try {
            //GIVEN
            SpaceShip spaceship = new SpaceShip();

            //WHEN
            double distance = -1.0;

            //THEN
            assertEquals(0.0, spaceship.calculateConsumption(distance));

        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testCalculateConsumption_shouldWork_distanceZero_returnsZero() {
        try {
            //GIVEN
            SpaceShip spaceship = new SpaceShip();

            //WHEN
            double distance = 0.0;

            //THEN
            assertEquals(0.0, spaceship.calculateConsumption(distance));

        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testCalculateConsumption_shouldWork_distance1_returnsConsumptionPerAU() {
        try {
            //GIVEN
            SpaceShip spaceship = new SpaceShip();
            double consumptionPerAU = 3.58;

            //WHEN
            double distance = 1.0;

            //THEN
            assertEquals(consumptionPerAU, spaceship.calculateConsumption(distance));

        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testCalculateConsumption_shouldWork_distance3_returnsThreeTimesConsumptionPerAU() {
        try {
           //GIVEN
           SpaceShip spaceship = new SpaceShip();
           double consumptionPerAU = 3.58;

          //WHEN
          double distance = 3.0;

           //THEN
            assertEquals(consumptionPerAU * 3, spaceship.calculateConsumption(distance));

         } catch (Exception ex) {
           System.out.println("Unexpected Exception: " + ex.getMessage());
           fail();
         }
    }
    */

    @Test
    void testIsHomebase_shouldWork_homeBaseIsEqualsArgument_returnTrue() {
        try {
            //GIVEN
            SpaceBase homeBase = new SpaceBase("International Space Station", 0, 0);
            SpaceShip spaceship = new SpaceShip(homeBase, 0, 0, 2000.0);

            //WHEN
            assertEquals(homeBase, spaceship.getHomeBase());

            //THEN
            assertTrue(spaceship.isHomeBase(homeBase));

        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testIsHomebase_shouldWork_homeBaseIsNotEqualsArgument_returnFalse() {
        try {
            //GIVEN
            SpaceBase homeBase = new SpaceBase("International Space Station", 0, 0);
            SpaceBase foreignBase = new SpaceBase("MIR", 1, 1);
            SpaceShip spaceship = new SpaceShip(homeBase, 0, 0, 2000.0);

            //WHEN
            assertNotEquals(foreignBase, spaceship.getHomeBase());

            //THEN
            assertFalse(spaceship.isHomeBase(foreignBase));

        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testIsHomebase_shouldNotWork_homeBaseIsNull_returnFalse() {
        try {
            //GIVEN
            SpaceBase homeBase = new SpaceBase("International Space Station", 0, 0);
            SpaceShip spaceship = new SpaceShip(homeBase, 0, 0, 2000.0);

            //WHEN
            assertEquals(homeBase, spaceship.getHomeBase());

            //THEN
            assertFalse(spaceship.isHomeBase(null));

        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }

    @Test
    void testToString() {
        try {
            //GIVEN
            SpaceBase homeBase = new SpaceBase("International Space Station", 0, 0);
            SpaceShip spaceship = new SpaceShip(homeBase, 10, 10, 2000.0);
            System.out.println(spaceship);
            System.out.println();

            //WHEN
            spaceship.moveTo(0, 0);
            //THEN
            assertEquals(0, spaceship.getPosX());
            assertEquals(0, spaceship.getPosY());
            System.out.println(spaceship);
            System.out.println();

            //WHEN
            homeBase.docking(spaceship);
            //THEN
            assertNotNull(spaceship.getDockingBase());
            assertEquals(homeBase, spaceship.getDockingBase());
            assertEquals(2000.0, spaceship.getFuel());
            assertTrue(spaceship.isHomeBase(spaceship.getDockingBase()));
            System.out.println(spaceship);
            System.out.println();

            /*
            // setHomeBase() is private, for testing set accessModifier to public
            // and uncomment the following two test

            //WHEN
            SpaceBase spacebase = new SpaceBase("Unity Space Station", 10, 10);
            spaceship.setHomeBase(spacebase);
            //THEN
            assertNotNull(spaceship.getHomeBase());
            assertEquals(spacebase, spaceship.getHomeBase());
            assertFalse(spaceship.isHomeBase(spaceship.getDockingBase()));
            System.out.println(spaceship);
            System.out.println();

            //WHEN
            homeBase.moveShipTo(spaceship.getId(), spacebase);
            //THEN
            assertEquals(2000.0, spaceship.getFuel());
            assertTrue(spaceship.isHomeBase(spaceship.getDockingBase()));
            System.out.println(spaceship);
            System.out.println();
            */

        } catch (Exception ex) {
            System.out.println("Unexpected Exception: " + ex.getMessage());
            fail();
        }
    }
}