package core.kernel;

public class CommonConfig {

	private static CommonConfig instance = null;
	
	public static CommonConfig getInstance(){
		
		if(instance == null){
			instance = new CommonConfig();
		}
		return instance;
	}
	
	private boolean wireframe;
	
	public boolean isWireframe() {
		return wireframe;
	}

	public void setWireframe(boolean wireframe) {
		this.wireframe = wireframe;
	}
	
}
