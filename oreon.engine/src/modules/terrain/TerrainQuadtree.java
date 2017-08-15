package modules.terrain;

import core.math.Vec2f;
import core.scene.Node;

public class TerrainQuadtree extends Node{

	private static int rootPatches = 8;
	
	public TerrainQuadtree(TerrainConfig terrConfig){
		
		for (int i=0; i<rootPatches; i++){
			for (int j=0; j<rootPatches; j++){
				addChild(new TerrainNode(terrConfig, new Vec2f(1f * i/(float)rootPatches,1f * j/(float)rootPatches), 0, new Vec2f(i,j)));
			}
		}
		
		getTransform().setScaling(terrConfig.getScaleXZ(), terrConfig.getScaleY(), terrConfig.getScaleXZ());
		getTransform().getTranslation().setX(-terrConfig.getScaleXZ()/2f);
		getTransform().getTranslation().setZ(-terrConfig.getScaleXZ()/2f);
		getTransform().getTranslation().setY(0);
	}	
	
	public void updateQuadtree(){
		for (Node node : getChildren()){
			((TerrainNode) node).updateQuadtree();
		}
	}

	public static int getRootPatches() {
		return rootPatches;
	}
}
