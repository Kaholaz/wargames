package org.ntnu.vsbugge.wargames;

import org.ntnu.vsbugge.wargames.units.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Army {
    private final String name;
    private List<Unit> units;

    public Army(String name) {
        this(name, new ArrayList<>());
    }

    public Army(String name, List<Unit> units) {
        this.name = name;
        this.units = new ArrayList<>();
        addAll(units);
    }

    public void add(Unit unit) {
        this.units.add(unit);
    }

    public void addAll(List<Unit> units) {
        this.units.addAll(List.copyOf(units));
    }

    public void remove(Unit unit) {
        units.remove(unit);
    }

    public boolean hasUnits() {
        return units.size() != 0;
    }

    public Unit getRandomUnit() throws IllegalStateException {
        if (!hasUnits()) {
            throw new IllegalStateException("The army has no units");
        }

        Random random = new Random();
        int index = random.nextInt(units.size());

        return units.get(index);
    }

    public String getName() {
        return name;
    }

    public List<Unit> getAllUnits() {
        return units;
    }

    @Override
    public String toString() {
        return "Army{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Army army = (Army) o;
        return name.equals(army.name) && units.equals(army.units);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, units);
    }
}
