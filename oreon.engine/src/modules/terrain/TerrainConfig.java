package modules.terrain;

import java.io.BufferedReader;
import java.io.FileReader;

import core.texturing.Texture2D;
import core.utils.Util;
import modules.gpgpu.NormalMapRenderer;

public class TerrainConfig {
	
	private float scaleY;
	private float scaleXZ;
	
	private Texture2D heightmap;
	private Texture2D normalmap;
	
	private int tessellationFactor;
	private float tessellationSlope;
	private float tessellationShift;
	
	private int[] lod_range = new int[8];
	private int[] lod_morphing_area = new int[8];
	
	public void loadFile(String file)
	{
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			String line;
			
			while((line = reader.readLine()) != null){
				
				String[] tokens = line.split(" ");
				tokens = Util.removeEmptyStrings(tokens);
				
				if(tokens.length == 0)
					continue;
				
				if(tokens[0].equals("scaleY")){
					setScaleY(Float.valueOf(tokens[1]));
				}
				if(tokens[0].equals("scaleXZ")){
					setScaleXZ(Float.valueOf(tokens[1]));
				}
				if(tokens[0].equals("heightmap")){
					setHeightmap(new Texture2D(tokens[1]));
					getHeightmap().bind();
					getHeightmap().bilinearFilter();
					
					NormalMapRenderer normalRenderer = new NormalMapRenderer(getHeightmap().getWidth());
					normalRenderer.setStrength(8);
					normalRenderer.render(getHeightmap());
					setNormalmap(normalRenderer.getNormalmap());
				}
				if (tokens[0].equals("tessellationFactor")){
					setTessellationFactor(Integer.valueOf(tokens[1]));
				}
				if (tokens[0].equals("tessellationSlope")){
					setTessellationSlope(Float.valueOf(tokens[1]));
				}
				if (tokens[0].equals("tessellationShift")){
					setTessellationShift(Float.valueOf(tokens[1]));
				}
				if (tokens[0].equals("#lod_ranges")){		 			
					for (int i = 0; i < 8; i++){
						line = reader.readLine();
						tokens = line.split(" ");
						tokens = Util.removeEmptyStrings(tokens);
						if (tokens[0].equals("lod" + (i+1) + "_range")){
							if (Integer.valueOf(tokens[1]) == 0){
								lod_range[i] = 0;
								lod_morphing_area[i] = 0;
							}
							else {
								setLodRange(i, Integer.valueOf(tokens[1]));
							}
						}
					}
				}
			}
			reader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private int updateMorphingArea(int lod){
		return (int) ((scaleXZ/TerrainQuadtree.getRootNodes()) / (Math.pow(2, lod)));
	}
	
	public float getScaleY() {
		return scaleY;
	}
	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
	}
	public float getScaleXZ() {
		return scaleXZ;
	}
	public void setScaleXZ(float scaleXZ) {
		this.scaleXZ = scaleXZ;
	}
	
	public void setLodRange(int index, int lod_range) {
		this.lod_range[index] = lod_range;
		lod_morphing_area[index] = lod_range - updateMorphingArea(index+1);
	}
		
	public int[] getLod_morphing_area() {
		return lod_morphing_area;
	}

	public int[] getLod_range() {
		return lod_range;
	}

	public int getTessellationFactor() {
		return tessellationFactor;
	}

	public void setTessellationFactor(int tessellationFactor) {
		this.tessellationFactor = tessellationFactor;
	}

	public float getTessellationSlope() {
		return tessellationSlope;
	}

	public void setTessellationSlope(float tessellationSlope) {
		this.tessellationSlope = tessellationSlope;
	}

	public float getTessellationShift() {
		return tessellationShift;
	}

	public void setTessellationShift(float tessellationShift) {
		this.tessellationShift = tessellationShift;
	}

	public Texture2D getHeightmap() {
		return heightmap;
	}

	public void setHeightmap(Texture2D heightmap) {
		this.heightmap = heightmap;
	}

	public Texture2D getNormalmap() {
		return normalmap;
	}

	public void setNormalmap(Texture2D normalmap) {
		this.normalmap = normalmap;
	}

}
