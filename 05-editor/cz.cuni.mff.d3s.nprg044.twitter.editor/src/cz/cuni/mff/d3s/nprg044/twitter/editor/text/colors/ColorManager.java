package cz.cuni.mff.d3s.nprg044.twitter.editor.text.colors;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * Simple cache for colors.
 */
public class ColorManager {
    protected Map<RGB, Color> fColorTable = new HashMap<RGB, Color>(10);

    public void dispose() {
        for (Color c : fColorTable.values()) {
            c.dispose();
        }
    }

    public Color getColor(RGB rgb) {
        Color color = fColorTable.get(rgb);
        if (color == null) {
            // create new Color object for the given RGB value
            color = new Color(Display.getCurrent(), rgb);
            fColorTable.put(rgb, color);
        }
        return color;
    }
}
