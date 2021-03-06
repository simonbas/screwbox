package de.suzufa.screwbox.core.graphics.internal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import de.suzufa.screwbox.core.Percentage;
import de.suzufa.screwbox.core.graphics.Color;
import de.suzufa.screwbox.core.graphics.Font;
import de.suzufa.screwbox.core.graphics.Font.Style;

class AwtMapperTest {

    @Test
    void toAwtColor_mapsAllRedGreenAndBlue() {
        Color color = Color.rgb(20, 40, 60);

        var awtColor = AwtMapper.toAwtColor(color);

        assertThat(awtColor.getRed()).isEqualTo(20);
        assertThat(awtColor.getGreen()).isEqualTo(40);
        assertThat(awtColor.getBlue()).isEqualTo(60);
        assertThat(awtColor.getAlpha()).isEqualTo(255);
    }

    @Test
    void toAwtColor_mapsOpacity() {
        Color color = Color.rgb(20, 40, 60, Percentage.half());

        var awtColor = AwtMapper.toAwtColor(color);

        assertThat(awtColor.getRed()).isEqualTo(20);
        assertThat(awtColor.getGreen()).isEqualTo(40);
        assertThat(awtColor.getBlue()).isEqualTo(60);
        assertThat(awtColor.getAlpha()).isEqualTo(127);
    }

    @Test
    void toAwtFont_normalFont_mapsFont() {
        Font font = new Font("Arial", 12, Style.NORMAL);

        var awtFont = AwtMapper.toAwtFont(font);

        assertThat(awtFont.isBold()).isFalse();
        assertThat(awtFont.isItalic()).isFalse();
        assertThat(awtFont.getSize()).isEqualTo(12);
        assertThat(awtFont.getName()).isEqualTo("Arial");
    }

    @Test
    void toAwtFont_boldFont_mapsFont() {
        Font font = new Font("Arial", 12, Style.BOLD);

        var awtFont = AwtMapper.toAwtFont(font);

        assertThat(awtFont.isBold()).isTrue();
        assertThat(awtFont.isItalic()).isFalse();
    }

    @Test
    void toAwtFont_italicBoldFont_mapsFont() {
        Font font = new Font("Arial", 12, Style.ITALIC_BOLD);

        var awtFont = AwtMapper.toAwtFont(font);

        assertThat(awtFont.isBold()).isTrue();
        assertThat(awtFont.isItalic()).isTrue();
    }

    @Test
    void toAwtFont_italicFont_mapsFont() {
        Font font = new Font("Arial", 12, Style.ITALIC);

        var awtFont = AwtMapper.toAwtFont(font);

        assertThat(awtFont.isBold()).isFalse();
        assertThat(awtFont.isItalic()).isTrue();
    }

}
