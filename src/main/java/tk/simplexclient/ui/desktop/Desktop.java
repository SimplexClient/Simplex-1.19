package tk.simplexclient.ui.desktop;

import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import tk.simplexclient.ui.desktop.application.App;
import tk.simplexclient.ui.desktop.application.impl.TestApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Desktop extends Screen {

    @Getter
    private final boolean showBackground;

    public Optional<App> selected = Optional.empty();

    private final List<App> apps = new ArrayList<>();

    private int prevX, prevY;

    public Desktop(boolean showBackground) {
        super(Component.nullToEmpty(""));
        this.showBackground = showBackground;

    }

    @Override
    protected void init() {
        apps.clear();
        apps.add(new TestApp());
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float f) {
        super.renderBackground(poseStack);
        apps.forEach(application -> application.renderApplication(poseStack, mouseX, mouseY));
        selected.ifPresent(application -> {
            application.setPos(mouseX + application.getPos()[0] - prevX, mouseY + application.getPos()[1] - prevY);
            prevX = mouseX;
            prevY = mouseY;
        });
        super.render(poseStack, mouseX, mouseY, f);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0) {
            prevX = (int) mouseX;
            prevY = (int) mouseY;

            selected = apps.stream()
                    .filter(application ->
                            mouseX >= application.getPos()[0]
                                    && mouseX <= application.getPos()[0] + application.getWidth()
                                    && mouseY >= application.getPos()[1]
                                    && mouseY <= application.getPos()[1] + 15
                    ).findFirst();
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean mouseReleased(double d, double e, int i) {
        selected = Optional.empty();
        prevX = 0;
        prevY = 0;
        return super.mouseReleased(d, e, i);
    }
}
