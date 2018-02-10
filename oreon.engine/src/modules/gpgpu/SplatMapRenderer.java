package modules.gpgpu;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glFinish;
import static org.lwjgl.opengl.GL15.GL_WRITE_ONLY;
import static org.lwjgl.opengl.GL30.GL_RGBA16F;
import static org.lwjgl.opengl.GL42.glBindImageTexture;
import static org.lwjgl.opengl.GL42.glTexStorage2D;
import static org.lwjgl.opengl.GL43.glDispatchCompute;

import core.texturing.Texture2D;

public class SplatMapRenderer {
	
	private Texture2D splatmap;
	private SplatMapShader computeShader;
	private int N;
	
	public SplatMapRenderer(int N) {
		
		this.N = N;
		computeShader = SplatMapShader.getInstance();
		splatmap = new Texture2D();
		splatmap.generate();
		splatmap.bind();
		splatmap.bilinearFilter();
		glTexStorage2D(GL_TEXTURE_2D, (int) (Math.log(N)/Math.log(2)), GL_RGBA16F, N, N);
	}
	
	public void render(Texture2D normalmap){
		
		computeShader.bind();
		computeShader.updateUniforms(normalmap, N);
		glBindImageTexture(0, splatmap.getId(), 0, false, 0, GL_WRITE_ONLY, GL_RGBA16F);
		glDispatchCompute(N/16,N/16,1);
		glFinish();
		splatmap.bind();
		splatmap.bilinearFilter();
	}
	
	public Texture2D getSplatmap() {
		return splatmap;
	}
	public void setSplatmap(Texture2D splatmap) {
		this.splatmap = splatmap;
	}

}
