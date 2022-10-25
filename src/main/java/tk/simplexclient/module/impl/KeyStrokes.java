package tk.simplexclient.module.impl;

public class KeyStrokes {

    /*
    private KeystrokesMode mode;

    public KeyStrokes() {
        super("keystrokes", 0, 0);
    }

    @Override
    public void render() {
        mode = KeystrokesMode.WASD_JUMP_MOUSE;
        for (Key key : mode.getKeys()) {
            renderer.drawRectangle(getX() + key.getX(), getY() + key.getY(), getX() + key.getX() + key.getWidth(), getY() + key.getY() + key.getHeight(), key.isDown() ? new Color(0, 0, 0, 102) : new Color(255, 255, 255, 120));
        }
        renderKeyStrokes();
    }

    @Override
    public void renderDummy(int width, int height) {
        mode = KeystrokesMode.WASD_JUMP_MOUSE;
        for (Key key : mode.getKeys()) {
            renderer.drawRectangle(getX() + key.getX(), getY() + key.getY(), getX() + key.getX() + key.getWidth(), getY() + key.getY() + key.getHeight(), key.isDown() ? new Color(0, 0, 0, 102) : new Color(255, 255, 255, 120));
        }
        renderKeyStrokes();
    }

    public void renderKeyStrokes() {
        renderer.drawString(AccessMinecraft.getMinecraft().getFPS() + " FPS", getX(), getY(), new Color(255, 255, 255));

        for (Key key : mode.getKeys()) {
            float[] size = renderer.getStringWidth(key.getName());
            int textWidth = (int) size[0];
            renderer.drawString(key.getName(), getX() + key.getX() + key.getWidth() / 2 - textWidth / 2 - 5, getY() + key.getY() + key.getHeight() / 2 - 4, key.isDown() ? new Color(255, 255, 255, 255) : new Color(0, 0, 0, 255));
        }
    }

    public static class Key {
        public static Minecraft mc = Minecraft.getInstance();

        private static final Key W = new Key("W", mc.options.keyUp, 21, 1, 18, 18);
        private static final Key A = new Key("A", mc.options.keyLeft, 1, 21, 18, 18);
        private static final Key S = new Key("S", mc.options.keyDown, 21, 21, 18, 18);
        private static final Key D = new Key("D", mc.options.keyRight, 41, 21, 18, 18);

        private static final Key LMB = new Key("LMB", mc.options.keyAttack, 1, 41, 28, 18);
        private static final Key RMB = new Key("RMB", mc.options.keyUse, 31, 41, 28, 18);

        private static final Key Jump1 = new Key("§m---", mc.options.keyJump, 1, 41, 58, 10);
        private static final Key Jump2 = new Key("§m---", mc.options.keyJump, 1, 61, 58, 10);

        private final String name;
        private final KeyMapping keyBind;
        private final int x, y, w, h;

        private float fade;

        public Key(String name, KeyMapping keyBind, int x, int y, int w, int h) {
            this.name = name;
            this.keyBind = keyBind;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        public boolean isDown() {
            return keyBind.isDown();
        }

        public int getHeight() {
            return h;
        }

        public int getWidth() {
            return w;
        }

        public String getName() {
            return name;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    @Override
    public float getWidth() {
        return 60;
    }

    @Override
    public float getHeight() {
        return 72;
    }

    public static Color outlineColor;

    public enum KeystrokesMode {
        WASD(Key.W, Key.A, Key.S, Key.D),
        WASD_MOUSE(Key.W, Key.A, Key.S, Key.D, Key.LMB, Key.RMB),
        WASD_JUMP(Key.W, Key.A, Key.S, Key.D, Key.Jump1),
        WASD_JUMP_MOUSE(Key.W, Key.A, Key.S, Key.D, Key.LMB, Key.RMB, Key.Jump2);

        private final Key[] keys;
        private int width, height;

        KeystrokesMode(Key... keysIn) {
            this.keys = keysIn;

            for (Key key : keys) {
                this.width = Math.max(this.width, key.getX() + key.getWidth());
                this.height = Math.max(this.height, key.getY() + key.getHeight());
            }
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public Key[] getKeys() {
            return keys;
        }
    }

     */
}
