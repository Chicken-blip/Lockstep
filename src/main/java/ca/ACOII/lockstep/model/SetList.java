package ca.ACOII.lockstep.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SetList {
    private List<LootSet> sets;

    public SetList() {
        this.sets = new ArrayList<>();
    }

    public List<LootSet> getSets() {
        return sets;
    }

    public void setSets(List<LootSet> sets) {
        this.sets = sets;
    }

    public boolean hasSet(String set) {
        for (LootSet n : sets) {
            if (Objects.equals(n.getId(), set)) return true;
        }
        return false;
    }
}
