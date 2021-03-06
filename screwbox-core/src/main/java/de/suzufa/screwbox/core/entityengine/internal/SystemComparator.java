package de.suzufa.screwbox.core.entityengine.internal;

import java.util.Comparator;

import de.suzufa.screwbox.core.entityengine.EntitySystem;

public class SystemComparator implements Comparator<EntitySystem> {

    @Override
    public int compare(EntitySystem o1, EntitySystem o2) {
        return o1.updatePriority().compareTo(o2.updatePriority());
    }

}
