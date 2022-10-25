package tk.simplexclient.module;

import lombok.Getter;
import tk.simplexclient.renderer.Renderable;

public class HUDModule extends Module{
    @Getter
    private int x, y;

    public HUDModule(String name, int x, int y) {
        super(name);
        this.renderable = new Renderable(x, y, this);
        this.setPosition(new int[] {x, y});
    }

    public Renderable renderable;

    public Renderable getRenderable(){
        return renderable;
    }

    public void render(){
        if(this.isEnabled()){
            renderable.reset();
            getRenderable().renderWithXY(x, y);
        }
    }

    public void setPosition(int[] position) {
        this.x = position[0];
        this.y = position[1];

        this.renderable.x = position[0];
        this.renderable.y = position[1];
    }
}
