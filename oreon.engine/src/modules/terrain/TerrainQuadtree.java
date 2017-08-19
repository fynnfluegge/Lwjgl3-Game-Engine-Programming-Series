package modules.terrain;

import core.buffers.PatchVBO;
import core.math.Vec2f;
import core.scene.Node;

public class TerrainQuadtree extends Node{

	private static int rootNodes = 8;
	
	public TerrainQuadtree(TerrainConfig terrConfig){
		
		PatchVBO buffer = new PatchVBO();
		buffer.allocate(generatePatch(),16);
		
		for (int i=0; i<rootNodes; i++){
			for (int j=0; j<rootNodes; j++){
				addChild(new TerrainNode(buffer, terrConfig, new Vec2f(i/(float)rootNodes,j/(float)rootNodes), 0, new Vec2f(i,j)));
			}
		}
		
		getWorldTransform().setScaling(terrConfig.getScaleXZ(), terrConfig.getScaleY(), terrConfig.getScaleXZ());
		getWorldTransform().getTranslation().setX(-terrConfig.getScaleXZ()/2f);
		getWorldTransform().getTranslation().setZ(-terrConfig.getScaleXZ()/2f);
		getWorldTransform().getTranslation().setY(0);
	}	
	
	public void updateQuadtree(){
		for (Node node : getChildren()){
			((TerrainNode) node).updateQuadtree();
		}
	}
	
	public Vec2f[] generatePatch(){
		
		// 16 vertices for each patch
		Vec2f[] vertices = new Vec2f[16];
		
		int index = 0;
		
		vertices[index++] = new Vec2f(0,0);
		vertices[index++] = new Vec2f(0.333f,0);
		vertices[index++] = new Vec2f(0.666f,0);
		vertices[index++] = new Vec2f(1,0);
		
		vertices[index++] = new Vec2f(0,0.333f);
		vertices[index++] = new Vec2f(0.333f,0.333f);
		vertices[index++] = new Vec2f(0.666f,0.333f);
		vertices[index++] = new Vec2f(1,0.333f);
		
		vertices[index++] = new Vec2f(0,0.666f);
		vertices[index++] = new Vec2f(0.333f,0.666f);
		vertices[index++] = new Vec2f(0.666f,0.666f);
		vertices[index++] = new Vec2f(1,0.666f);
	
		vertices[index++] = new Vec2f(0,1);
		vertices[index++] = new Vec2f(0.333f,1);
		vertices[index++] = new Vec2f(0.666f,1);
		vertices[index++] = new Vec2f(1,1);
		
		return vertices;
	}

	public static int getRootNodes() {
		return rootNodes;
	}
}
