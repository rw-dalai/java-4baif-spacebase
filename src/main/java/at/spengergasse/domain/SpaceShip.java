package at.spengergasse.domain;

import at.spengergasse.ApplicationException;

import java.util.Objects;

import static at.spengergasse.foundation.Assert.hasMaxLength;

public class SpaceShip implements Comparable<SpaceShip>{

    private static final double MAX_FUEL = 2000.0;
    private static final double FUEL_CONSUMPTION_PER_AU = 3.58;
    private static Long idCounter = 100L;

    private final Long id;
    private int posX;
    private int posY;
    private double fuel;

    private SpaceBase homeBase;
    private SpaceBase dockingBase;

    private String name;

    public SpaceShip() throws ApplicationException {
        id = idCounter++;
        posX = 0;
        posY = 0;
        fuel = 1000;
        homeBase = new SpaceBase("International Space Station", 0, 0);
        dockingBase = null;
    }

    public SpaceShip(SpaceBase homeBase, int posX, int posY, double fuel) throws ApplicationException {
            id = idCounter++;
            setHomeBase(homeBase);
            setPosX(posX);
            setPosY(posY);
            refuel(fuel);
            dockingBase = null;
    }

    public Long getId() {
        return id;
    }

    public int getPosX() {
        return posX;
    }

    private void setPosX(int posX) throws ApplicationException {
        if (posX >= 0) {
            this.posX = posX;
        } else {
            throw new ApplicationException("setPosX: posX-value is not valid (" + posX + ")");
        }
    }

    public int getPosY() {
        return posY;
    }

    private void setPosY(int posY) throws ApplicationException {
        if (posX >= 0) {
            this.posY = posY;
        } else {
            throw new ApplicationException("setPosY: posY-value is not valid (" + posY + ")");
        }
    }

    public double getFuel() {
        return fuel;
    }

    public void refuel(double fuel) throws ApplicationException {
        if ( fuel >= 0.0 ) {
            if (fuel + this.fuel <= MAX_FUEL) {
                this.fuel = Math.min(fuel + this.fuel, MAX_FUEL);
            } else {
                throw new ApplicationException("refuel: amount " + fuel + " is too much");
            }
        } else {
            throw new ApplicationException("refuel: amount " + fuel + " is to too less");
        }
    }

    public SpaceBase getHomeBase() {
        return homeBase;
    }

    private void setHomeBase(SpaceBase homeBase) throws ApplicationException {
        if (homeBase != null) {
            this.homeBase = homeBase;
        } else {
            throw new ApplicationException("homeBase is null");
        }
    }

    public SpaceBase getDockingBase() {
        return dockingBase;
    }

    public void setDockingBase(SpaceBase dockingBase) {
        this.dockingBase = dockingBase;
        if (isHomeBase(dockingBase)) {
            fuel = MAX_FUEL;
        }
    }

    public void moveTo( int posX, int posY) throws ApplicationException {
        double distance;
        double newFuel;
        if (posX >= 0 && posY >= 0) {
            distance = calculateDistanceTo(posX, posY);
            newFuel  = fuel - calculateConsumption(distance);
            if (newFuel >= 0.0) {
                this.posX = posX;
                this.posY = posY;
                fuel = newFuel;
            } else {
                throw new ApplicationException("moveTo: lack of fuel (" + newFuel + ")");
            }
        } else {
            throw new ApplicationException("moveTo: wrong target-position: " + posX + "/" + posY);
        }
    }

    private double calculateConsumption(double distance) {
        if (distance >= 0.0) {
            return distance * FUEL_CONSUMPTION_PER_AU;
        } else {
            return 0.0;
        }
    }

    private double calculateDistanceTo( int posX, int posY) throws ApplicationException {
        if (posX >= 0 && posY >= 0) {
             return Math.sqrt(Math.pow((posX - this.posX), 2.0) + Math.pow((posY - this.posY), 2.0));
         } else {
            throw new ApplicationException("calculateDistanceTo: wrong target-position: " + posX + "/" + posY);
        }
    }

    public boolean isHomeBase (SpaceBase spaceBase) {
        return this.getHomeBase().equals(spaceBase);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpaceShip spaceShip = (SpaceShip) o;
        return id.equals(spaceShip.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(SpaceShip other) {
        return Long.compare(this.id, other.getId());
    }

    @Override
    public String toString() {
        return "SpaceShip " + "id " + id +
                ", position " + posX +
                "/" + posY +
                ", fuel " + fuel +
                "\nhomebase: " + (homeBase != null ? homeBase.getName() : "none") +
                "\ndockingbase: " + (dockingBase != null ? (dockingBase.equals(homeBase) ? "at home" : dockingBase.getName()) : "none");
    }
}
