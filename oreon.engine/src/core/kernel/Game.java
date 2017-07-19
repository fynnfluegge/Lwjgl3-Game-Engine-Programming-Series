package core.kernel;

public class Game {
	
	protected CoreEngine engine;
	
	public Game(){
		engine = new CoreEngine();
	}
	
	public void launch(){
		engine.start();
	}
	
	public void init(){
		engine.init();
	}
	
	public CoreEngine getEngine() {
		return engine;
	}
	public void setEngine(CoreEngine engine) {
		this.engine = engine;
	}
}
