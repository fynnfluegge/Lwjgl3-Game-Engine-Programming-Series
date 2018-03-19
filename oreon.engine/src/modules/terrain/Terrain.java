package modules.terrain;

import core.kernel.Camera;
import core.math.Vec2f;
import core.scene.Node;

public class Terrain extends Node{

	private TerrainConfig configuration;
		
	public void init (String config)
	{
		configuration = new TerrainConfig();
		configuration.loadFile(config);
		
		addChild(new TerrainQuadtree(configuration));
	}
	
	public void updateQuadtree(){
		if (Camera.getInstance().isCameraMoved()){
			((TerrainQuadtree) getChildren().get(0)).updateQuadtree();
		}
	}
	
	public float getTerrainHeight(float x, float z){
		
		float h = 0;
		
		Vec2f pos = new Vec2f();
		pos.setX(x);
		pos.setY(z);
		pos = pos.add(configuration.getScaleXZ()/2f);
		pos = pos.div(configuration.getScaleXZ());
		Vec2f floor = new Vec2f((int) Math.floor(pos.getX()), (int) Math.floor(pos.getY()));
		pos = pos.sub(floor);
		pos = pos.mul(configuration.getHeightmap().getWidth());
		int x0 = (int) Math.floor(pos.getX());
		int x1 = x0 + 1;
		int z0 = (int) Math.floor(pos.getY());
		int z1 = z0 + 1;
		
		float h0 =  configuration.getHeightmapDataBuffer().get(configuration.getHeightmap().getWidth() * z0 + x0);
		float h1 =  configuration.getHeightmapDataBuffer().get(configuration.getHeightmap().getWidth() * z0 + x1);
		float h2 =  configuration.getHeightmapDataBuffer().get(configuration.getHeightmap().getWidth() * z1 + x0);
		float h3 =  configuration.getHeightmapDataBuffer().get(configuration.getHeightmap().getWidth() * z1 + x1);
		
		float percentU = pos.getX() - x0;
        float percentV = pos.getY() - z0;
        
        float dU, dV;
        if (percentU > percentV)
        {   // bottom triangle
            dU = h1 - h0;
            dV = h3 - h1;
        }
        else
        {   // top triangle
            dU = h3 - h2;
            dV = h2 - h0;
        }
        
        h = h0 + (dU * percentU) + (dV * percentV );
        h *= configuration.getScaleY();
		
		return h;
	}

	public TerrainConfig getConfiguration() {
		return configuration;
	}

	public void setConfiguration(TerrainConfig configuration) {
		this.configuration = configuration;
	}
	
}
