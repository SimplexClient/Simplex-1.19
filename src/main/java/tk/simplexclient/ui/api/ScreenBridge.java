package tk.simplexclient.ui.api;

import java.util.ArrayList;
import java.util.List;

public abstract class ScreenBridge {
	public int width, height;
	
	public abstract void render(int mouseX, int mouseY, boolean leftClick, boolean rightClick, boolean middleClick);
	public abstract void mouseButtonClick(boolean button, boolean value, double mouseX, double mouseY);
	public boolean isPauseScreen() {return true;}
	public List<GuiComponent> renderComponents(){ return new ArrayList<>(); };
	public boolean canCloseWithEscape() { return true; }
}
