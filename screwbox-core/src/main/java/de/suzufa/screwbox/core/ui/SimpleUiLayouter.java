package de.suzufa.screwbox.core.ui;

import de.suzufa.screwbox.core.graphics.Dimension;
import de.suzufa.screwbox.core.graphics.Offset;
import de.suzufa.screwbox.core.graphics.Window;
import de.suzufa.screwbox.core.graphics.WindowBounds;

public class SimpleUiLayouter implements UiLayouter {

    @Override
    public WindowBounds calculateBounds(final UiMenuItem item, final UiMenu menu, final Window window) {
        final int itemIndex = menu.itemIndex(item);
        final int heightOfItem = 50;
        final int heightOfMenu = menu.itemCount() * heightOfItem;
        final int y = window.center().y() - heightOfMenu / 2;

        final var offset = Offset.at(0, y + itemIndex * heightOfItem);
        final var dimension = Dimension.of(window.size().width(), heightOfItem);
        return new WindowBounds(offset, dimension);
    }

}
