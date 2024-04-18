package at.spengergasse.domain;

import at.spengergasse.ApplicationException;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

// static import
import static at.spengergasse.foundation.Assert.*;
import static java.lang.String.format;
import static java.lang.StringTemplate.STR;


public class SpaceBase {

    private String name;
    private int posX;
    private int posY;

    private List<SpaceShip> shipList;

    public SpaceBase() {
        name = "International Space Station";
        posX = 0;
        posY = 0;
        shipList = new LinkedList<>();
    }

    public SpaceBase(String name, int posX, int posY) throws ApplicationException {
        setName(name);
        setPosX(posX);
        setPosY(posY);
        shipList = new LinkedList<>();
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



// ORIGINAL VERSION `setName`
//
//    public void setName(String name) throws ApplicationException {
//        if (name != null) {
//            if (!name.isBlank())
//                if (name.length() < 256)
//                    this.name = name;
//        } else {
//            throw new ApplicationException("setName: null-value for name");
//        }
//    }


// ALTERNATIVE VERSION `setName`
//
    public void setName(String name) throws ApplicationException {
       this.name = hasMaxLength(name, 255, "name");
    }

    public String getName() {
        return name;
    }


// ORIGINAL VERSION `docking`
//
//    public boolean docking(SpaceShip spaceship) throws ApplicationException {
//        if (spaceship != null) {
//            if (!shipList.contains(spaceship)) {
//                spaceship.setDockingBase(this);
//                return shipList.add(spaceship);
//            } else {
//                throw new ApplicationException("docking: spaceship id " + spaceship.getId() + " already docked at this spacebase '" + spaceship.getDockingBase().getName() + "'");
//            }
//        } else {
//            throw new ApplicationException("docking: value of spaceship is null");
//        }
//    }


// ALTERNATIVE VERSION `docking`
//
    public boolean docking(SpaceShip spaceship) throws ApplicationException {
        isNotNull(spaceship, "spaceship");

        isTrue(!shipList.contains(spaceship), () ->
            STR."spaceship id \{spaceship.getId()} already docked at " +
                STR."this spacebase \{spaceship.getDockingBase().getName()}");

        spaceship.setDockingBase(this);
        return shipList.add(spaceship);

    }

    public SpaceShip moveShipTo(Long id, SpaceBase spaceBase) {
        if (id != null && spaceBase != null) {
            SpaceShip spaceshipToMove = shipWithId(id);
            if (spaceshipToMove != null) {
                try {
                    spaceshipToMove.moveTo(spaceBase.getPosX(), spaceBase.getPosY());
                } catch (ApplicationException e) {
                     return null;
                }
                try {
                    spaceBase.docking(spaceshipToMove);
                } catch (ApplicationException e) {
                     return null;
                }
                shipList.remove(spaceshipToMove);
                return spaceshipToMove;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private SpaceShip shipWithId(Long id) {
        if (id == null) {
            return null;
        }
        for (SpaceShip spaceship : shipList) {
            if (spaceship.getId().equals(id)) {
                return spaceship;
            }
        }
        return null;
    }

    public List<SpaceShip> arrangeShips() {
        shipList.sort(null);
        return new LinkedList<>(shipList);
    }

    public List<SpaceShip> arrangeShipsByFuel() {
        shipList.sort(Comparator.comparingDouble(SpaceShip::getFuel));
        return new LinkedList<>(shipList);
    }

    public Long buildShip() throws ApplicationException {
        SpaceShip spaceship = new SpaceShip(this, this.getPosX(), this.getPosY(), 2000.0);
        this.docking(spaceship);
        return spaceship.getId();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SpaceBase ");
        sb.append("'").append(name).append("'");
        sb.append(", position ").append(posX);
        sb.append("/").append(posY);
        if (!shipList.isEmpty()) {
            sb.append(", ").append(shipList.size()).append(" ").append(shipList.size() > 1 ? "ships" : "ship").append(" in docks\n");
            for (SpaceShip spaceship : shipList) {
                sb.append(spaceship).append("\n");
            }
        } else {
            sb.append(", no ships");
        }
        return sb.toString();
    }
}
