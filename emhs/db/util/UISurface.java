package emhs.db.util;

import emhs.db.components.UIElement;

import javax.swing.*;
import java.awt.*;

public class UISurface extends UIElement {
    public UISurface() {
        super();
    }

    public final boolean update(final AWTEvent e, final String eventType, Void b) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                update(e, eventType);
            }
        });

        return false;
    }
}
