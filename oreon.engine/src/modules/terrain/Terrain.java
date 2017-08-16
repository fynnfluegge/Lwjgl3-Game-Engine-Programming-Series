package modules.terrain;

import core.kernel.Camera;
import core.scene.Node;

public class Terrain extends Node{

	private TerrainConfig configuration;
	private int updateQuadtreeCounter = 0;	
		
	public void init (String config)
	{
		configuration = new TerrainConfig();
		configuration.loadFile(config);
		
		addChild(new TerrainQuadtree(configuration));
	}
	
	public void updateQuadtree(){
		if (Camera.getInstance().isCameraMoved()){
			updateQuadtreeCounter++;
		}
		if (updateQuadtreeCounter == 1){
			
			((TerrainQuadtree) getChildren().get(0)).updateQuadtree();
			
			updateQuadtreeCounter = 0;
		}
	}

	public TerrainConfig getConfiguration() {
		return configuration;
	}

	public void setConfiguration(TerrainConfig configuration) {
		this.configuration = configuration;
	}
	
}
