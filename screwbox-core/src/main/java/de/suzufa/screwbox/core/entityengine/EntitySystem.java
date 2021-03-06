package de.suzufa.screwbox.core.entityengine;

import de.suzufa.screwbox.core.Engine;

public interface EntitySystem {

    void update(Engine engine);

    default UpdatePriority updatePriority() {
        return UpdatePriority.SIMULATION;
    }
}
