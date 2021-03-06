package de.suzufa.screwbox.playground.debo.menues;

import java.util.List;

import de.suzufa.screwbox.core.Engine;
import de.suzufa.screwbox.core.graphics.Dimension;
import de.suzufa.screwbox.core.ui.ScrollingUiLayouter;
import de.suzufa.screwbox.core.ui.UiMenu;
import de.suzufa.screwbox.core.ui.UiMenuItem;
import de.suzufa.screwbox.core.ui.UiSubMenu;

public class OptionsMenu extends UiSubMenu {

    public OptionsMenu(UiMenu caller) {
        super(caller);

        add(new UiMenuItem("toggle Fullscreen") {

            @Override
            public void onActivate(Engine engine) {
                engine.graphics().configuration().toggleFullscreen();
            }
        });

        add(new UiMenuItem("change resolution") {

            @Override
            public void onActivate(Engine engine) {
                List<Dimension> resolutions = engine.graphics().supportedResolutions();
                Dimension resolution = engine.graphics().configuration().resolution();
                engine.ui().setLayouter(new ScrollingUiLayouter());
                engine.ui().openMenu(new ResolutionOptionMenu(new OptionsMenu(caller), resolutions, resolution));

            }
        });

        add(new UiMenuItem("back") {

            @Override
            public void onActivate(Engine engine) {
                onExit(engine);

            }
        });
    }

}
