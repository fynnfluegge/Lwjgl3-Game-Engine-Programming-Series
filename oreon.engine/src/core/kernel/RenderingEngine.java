package core.kernel;

import core.kernel.Window;
import core.kernel.Camera;

public class RenderingEngine {
	
	private Window window;
	
	public RenderingEngine()
	{
		window = Window.getInstance();
	}
	
	public void init()
	{
		window.init();
	}

	public void render()
	{	
		Camera.getInstance().update();
		
		// draw into OpenGL window
		window.render();
	}
	
	public void update(){}
	
	public void shutdown(){}
}
