package modules.terrain;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import core.kernel.Camera;
import core.scene.GameObject;
import core.shaders.Shader;
import core.utils.ResourceLoader;

public class TerrainShader extends Shader{
	
	private static TerrainShader instance = null;
	
	public static TerrainShader getInstance(){
		
		if (instance == null){
			
			instance = new TerrainShader();
		}
		return instance;
	}
	
	protected TerrainShader(){
		
		super();
		
		addVertexShader(ResourceLoader.loadShader("shaders/terrain/terrain_VS.glsl"));
		addTessellationControlShader(ResourceLoader.loadShader("shaders/terrain/terrain_TC.glsl"));
		addTessellationEvaluationShader(ResourceLoader.loadShader("shaders/terrain/terrain_TE.glsl"));
		addGeometryShader(ResourceLoader.loadShader("shaders/terrain/terrain_GS.glsl"));
		addFragmentShader(ResourceLoader.loadShader("shaders/terrain/terrain_FS.glsl"));
		
		compileShader();
		
		addUniform("localMatrix");
		addUniform("worldMatrix");
		addUniform("m_ViewProjection");
		
		addUniform("index");
		addUniform("gap");
		addUniform("lod");
		addUniform("scaleY");
		addUniform("location");
		addUniform("cameraPosition");
		
		addUniform("tessellationFactor");
		addUniform("tessellationSlope");
		addUniform("tessellationShift");
		
		addUniform("heightmap");
		addUniform("normalmap");
		addUniform("splatmap");
		
		for (int i=0; i<8; i++){
			addUniform("lod_morph_area[" + i + "]");
		}
		
		addUniform("tbn_range");
		
		for (int i=0; i<3; i++){
			addUniform("materials[" + i + "].diffusemap");
			addUniform("materials[" + i + "].normalmap");
			addUniform("materials[" + i + "].heightmap");
			addUniform("materials[" + i + "].heightScaling");
			addUniform("materials[" + i + "].horizontalScaling");
		}
	}
	
	public void updateUniforms(GameObject object) {
		
		TerrainNode terrainNode = (TerrainNode) object;
		
		setUniformf("scaleY", terrainNode.getConfig().getScaleY());
		setUniformi("lod", terrainNode.getLod());
		setUniform("index", terrainNode.getIndex());
		setUniform("location", terrainNode.getLocation());
		setUniformf("gap", terrainNode.getGap());
		
		for (int i = 0; i<8; i++){
			setUniformi("lod_morph_area[" + i + "]", terrainNode.getConfig().getLod_morphing_area()[i]);
		}
		
		glActiveTexture(GL_TEXTURE0);
		terrainNode.getConfig().getHeightmap().bind();
		setUniformi("heightmap", 0);
		
		glActiveTexture(GL_TEXTURE1);
		terrainNode.getConfig().getNormalmap().bind();
		setUniformi("normalmap", 1);
		
		glActiveTexture(GL_TEXTURE2);
		terrainNode.getConfig().getSplatmap().bind();
		setUniformi("splatmap", 2);
		
		setUniformi("tessellationFactor", terrainNode.getConfig().getTessellationFactor());
		setUniformf("tessellationSlope", terrainNode.getConfig().getTessellationSlope());
		setUniformf("tessellationShift", terrainNode.getConfig().getTessellationShift());
		
		setUniform("cameraPosition", Camera.getInstance().getPosition());
		setUniform("m_ViewProjection", Camera.getInstance().getViewProjectionMatrix());
		setUniform("localMatrix", object.getLocalTransform().getWorldMatrix());
		setUniform("worldMatrix", object.getWorldTransform().getWorldMatrix());
		
		setUniformi("tbn_range", terrainNode.getConfig().getTbn_Range());
		
		int texUnit = 3;
		for (int i=0; i<3; i++){
			
			glActiveTexture(GL_TEXTURE0 + texUnit);
			terrainNode.getConfig().getMaterials().get(i).getDiffusemap().bind();
			setUniformi("materials[" + i + "].diffusemap", texUnit);
			texUnit++;
			
			glActiveTexture(GL_TEXTURE0 + texUnit);
			terrainNode.getConfig().getMaterials().get(i).getDisplacemap().bind();
			setUniformi("materials[" + i + "].heightmap", texUnit);
			texUnit++;
			
			glActiveTexture(GL_TEXTURE0 + texUnit);
			terrainNode.getConfig().getMaterials().get(i).getNormalmap().bind();
			setUniformi("materials[" + i + "].normalmap", texUnit);
			texUnit++;
			
			setUniformf("materials[" + i + "].heightScaling", terrainNode.getConfig().getMaterials().get(i).getDisplaceScale());
			setUniformf("materials[" + i + "].horizontalScaling", terrainNode.getConfig().getMaterials().get(i).getHorizontalScale()); 
		}
	}
}