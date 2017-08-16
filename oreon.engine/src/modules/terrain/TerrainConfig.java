package modules.terrain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import core.utils.Util;

public class TerrainConfig {
	
	private float scaleY;
	private float scaleXZ;
	
	private int[] lod_range = new int[8];
	private int[] lod_morphing_area = new int[8];
	
	public void loadFile(String file)
	{
		BufferedReader reader = null;
		
		try{
			if(new File(file).exists()){
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
					if (tokens[0].equals("#lod_ranges")){					
						for (int i = 0; i < 8; i++){
							line = reader.readLine();
							tokens = line.split(" ");
							tokens = Util.removeEmptyStrings(tokens);
							if (tokens[0].equals("lod" + (i+1) + "_range")){
								setLodRange(i, Integer.valueOf(tokens[1]));
							}
						}
					}
				}
				reader.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private int updateMorphingArea(int lod){
		return (int) ((scaleY/TerrainQuadtree.getRootPatches()) / (Math.pow(2, lod)));
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

}
