package de.suzufa.screwbox.playground.debo.systems;

import de.suzufa.screwbox.core.Engine;
import de.suzufa.screwbox.core.entityengine.Archetype;
import de.suzufa.screwbox.core.entityengine.Entity;
import de.suzufa.screwbox.core.entityengine.EntitySystem;
import de.suzufa.screwbox.core.keyboard.Key;
import de.suzufa.screwbox.playground.debo.components.ScreenshotComponent;
import de.suzufa.screwbox.playground.debo.scenes.PauseScene;

public class PauseSystem implements EntitySystem {

    private static final Archetype SCREENSHOT = Archetype.of(ScreenshotComponent.class);

    @Override
    public void update(Engine engine) {
        if (engine.keyboard().justPressed(Key.P)
                || engine.keyboard().justPressed(Key.ESCAPE)
                || !engine.graphics().window().hasFocus()) {

            Entity screenshotEntity = engine.entityEngine().forcedFetch(SCREENSHOT);
            screenshotEntity.get(ScreenshotComponent.class).screenshot = engine.graphics().window().takeScreenshot();
            engine.audio().stopAllAudio();
            engine.scenes().switchTo(PauseScene.class);
        }
    }

}
