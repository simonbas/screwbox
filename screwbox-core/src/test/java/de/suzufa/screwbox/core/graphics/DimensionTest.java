package de.suzufa.screwbox.core.graphics;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

class DimensionTest {

    @Test
    void pixelCount_returnsCountOfPixels() {
        Dimension dimension = Dimension.of(640, 480);

        assertThat(dimension.pixelCount()).isEqualTo(307200);
    }

    @Test
    void testSort_byPixelCount() {
        Dimension mediumRes = Dimension.of(1280, 1024);
        Dimension highRes = Dimension.of(4096.0, 3072.0);
        Dimension lowRes = Dimension.of(640, 480);

        var dimensions = new ArrayList<>(of(mediumRes, highRes, lowRes));

        Collections.sort(dimensions);

        assertThat(dimensions).containsSequence(lowRes, mediumRes, highRes);
    }

}
