package states;

import java.awt.Graphics;

import gfx.Assets;
import realmcraft.Handler;
import realmcraft.utils.ui.ClickListener;
import realmcraft.utils.ui.UIImageButton;
import realmcraft.utils.ui.UIManager;

public class MenuState extends State {

    private UIManager uiManager;

    public MenuState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(uiManager);

        uiManager.addObject(new UIImageButton(200, 200, 128, 64, Assets.startButton, new ClickListener(){

            @Override
            public void onClick() {
                State.setState(handler.getGame().gameState);

            }
        }));

        uiManager.addObject(new UIImageButton(200, 264, 128, 64, Assets.exitButton, new ClickListener(){

            @Override
            public void onClick() {
                handler.getGame().exit();
            }
        }));
    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
		uiManager.render(g);
	}

}