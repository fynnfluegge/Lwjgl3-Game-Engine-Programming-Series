package modules.skydome;

import core.scene.GameObject;
import core.shaders.Shader;
import core.utils.ResourceLoader;

public class AtmosphereShader extends Shader{

	private static AtmosphereShader instance = null;
	
	public static AtmosphereShader getInstance() 
	{
		if(instance == null) 
		{
			instance = new AtmosphereShader();
		}
		return instance;
	}
		
	protected AtmosphereShader()
	{
		super();

		addVertexShader(ResourceLoader.loadShader("shaders/sky/atmosphere_VS.glsl"));
		addFragmentShader(ResourceLoader.loadShader("shaders/sky/atmosphere_FS.glsl"));
		compileShader();
			
		addUniform("modelViewProjectionMatrix");
		addUniform("worldMatrix");
	}
		
	public void updateUniforms(GameObject object)
	{
		setUniform("modelViewProjectionMatrix", object.getTransform().getModelViewProjectionMatrix());
		setUniform("worldMatrix", object.getTransform().getWorldMatrix());
	}
}
