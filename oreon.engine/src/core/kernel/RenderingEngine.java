package core.kernel;

import core.kernel.Window;
import modules.skydome.Skydome;
import modules.terrain.Terrain;
import core.configs.Default;
import core.kernel.Camera;

/**
 * 
 * @author oreon3D
 * The RenderingEngine manages the render calls of all 3D entities
 * with shadow rendering and post processing effects
 *
 */
public class RenderingEngine {
	
	private Window window;
	
	private Skydome skydome;
	private Terrain terrain;
	
	public RenderingEngine()
	{
		window = Window.getInstance();
		skydome = new Skydome();
		terrain = new Terrain();
	}
	
	public void init()
	{
		window.init();
		terrain.init("./res/terrain_settings.txt");
	}

	public void render()
	{	
		Camera.getInstance().update();
		
		Default.clearScreen();
		
		skydome.render();
		terrain.render();
		
		// draw into OpenGL window
		window.render();
	}
	
	public void update(){}
	
	public void shutdown(){}
}
