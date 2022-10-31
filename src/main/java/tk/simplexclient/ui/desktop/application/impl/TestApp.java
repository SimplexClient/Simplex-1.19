package tk.simplexclient.ui.desktop.application.impl;

import tk.simplexclient.ui.desktop.application.App;
import tk.simplexclient.ui.desktop.application.EnumAppButtons;
import tk.simplexclient.ui.desktop.application.EnumAppPosition;

public class TestApp extends App {

    public TestApp() {
        super("Test", EnumAppPosition.CENTER, EnumAppButtons.CLOSE_MINIMIZE_MAXIMIZE, 200, 150);
    }
}
