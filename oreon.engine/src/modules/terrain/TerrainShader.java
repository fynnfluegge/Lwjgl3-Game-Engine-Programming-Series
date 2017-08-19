package modules.terrain;

import core.kernel.Camera;
import core.math.Vec2f;
import core.scene.GameObject;
import core.shaders.Shader;
import core.utils.ResourceLoader;

public class TerrainShader extends Shader{

private static TerrainShader instance = null;
	
	public static TerrainShader getInstance() 
	{
	    if(instance == null) 
	    {
	    	instance = new TerrainShader();
	    }
	      return instance;
	}
	
	protected TerrainShader()
	{
		super();

		addVertexShader(ResourceLoader.loadShader("shaders/terrain/Terrain_VS.glsl"));
		addTessellationControlShader(ResourceLoader.loadShader("shaders/terrain/Terrain_TC.glsl"));
		addTessellationEvaluationShader(ResourceLoader.loadShader("shaders/terrain/Terrain_TE.glsl"));
		addGeometryShader(ResourceLoader.loadShader("shaders/terrain/Terrain_GS.glsl"));
		addFragmentShader(ResourceLoader.loadShader("shaders/terrain/Terrain_FS.glsl"));
		compileShader();
	
		addUniform("localMatrix");
		addUniform("worldMatrix");
		addUniform("scaleY");
//		
		addUniform("index");
		addUniform("gap");
		addUniform("lod");
		addUniform("location");
		
		for (int i=0; i<8; i++){
			addUniform("lod_morph_area[" + i + "]");
		}

		addUniform("cameraPosition");
		addUniform("m_ViewProjection");
	}
	
	public void updateUniforms(GameObject object)
	{	
		setUniform("cameraPosition", Camera.getInstance().getPosition());
		setUniform("m_ViewProjection", Camera.getInstance().getViewProjectionMatrix());
		
		TerrainConfig terrConfig = ((TerrainNode) object).getConfig();
		int lod = ((TerrainNode) object).getLod();
		Vec2f index = ((TerrainNode) object).getIndex();
		float gap = ((TerrainNode) object).getGap();
		Vec2f location = ((TerrainNode) object).getLocation();
		
		setUniform("localMatrix", object.getLocalTransform().getWorldMatrix());
		setUniform("worldMatrix", object.getWorldTransform().getWorldMatrix());
		
		setUniformf("scaleY", terrConfig.getScaleY());
		setUniformi("lod", lod);
		setUniform("index", index);
		setUniformf("gap", gap);
		setUniform("location", location);
		
		for (int i=0; i<8; i++){
			setUniformi("lod_morph_area[" + i + "]", terrConfig.getLod_morphing_area()[i]);
		}
	}
}
