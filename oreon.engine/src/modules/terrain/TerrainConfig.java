package modules.terrain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import core.utils.Util;

public class TerrainConfig {
	
	private float scaleY;
	private float scaleXZ;
	private int bezier;
	private float sightRangeFactor;
	private float texDetail;
	private int tessellationFactor;
	private float tessellationSlope;
	private float tessellationShift;
	private int detailRange;
	
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
					if(tokens[0].equals("tessellationFactor")){
						setTessellationFactor(Integer.valueOf(tokens[1]));
					}
					if(tokens[0].equals("tessellationSlope")){
						setTessellationSlope(Float.valueOf(tokens[1]));
					}
					if(tokens[0].equals("tessellationShift")){
						setTessellationShift(Float.valueOf(tokens[1]));
					}
					if(tokens[0].equals("lod1_range")){
						setLod1_range( Integer.valueOf(tokens[1]));
					}
					if(tokens[0].equals("lod2_range")){
						setLod2_range( Integer.valueOf(tokens[1]));;
					}
					if(tokens[0].equals("lod3_range")){
						setLod3_range( Integer.valueOf(tokens[1]));
					}
					if(tokens[0].equals("lod4_range")){
						setLod4_range( Integer.valueOf(tokens[1]));
					}
					if(tokens[0].equals("lod5_range")){
						setLod5_range( Integer.valueOf(tokens[1]));
					}
					if(tokens[0].equals("lod6_range")){
						setLod6_range( Integer.valueOf(tokens[1]));
					}
					if(tokens[0].equals("lod7_range")){
						setLod7_range( Integer.valueOf(tokens[1]));
					}
					if(tokens[0].equals("lod8_range")){
						setLod8_range( Integer.valueOf(tokens[1]));
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
		return (int) ((6000/TerrainQuadtree.getRootPatches()) / (Math.pow(2, lod)));
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
	public int getBezier() {
		return bezier;
	}
	public void setBezier(int bezier) {
		this.bezier = bezier;
	}
	public float getSightRangeFactor() {
		return sightRangeFactor;
	}
	public void setSightRangeFactor(float sightRangeFactor) {
		this.sightRangeFactor = sightRangeFactor;
	}
	public float getTexDetail() {
		return texDetail;
	}
	public void setTexDetail(float texDetail) {
		this.texDetail = texDetail;
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
	public int getDetailRange() {
		return detailRange;
	}
	public void setDetailRange(int detailRange) {
		this.detailRange = detailRange;
	}

	public void setLod1_range(int lod1_range) {
		this.lod_range[0] = lod1_range;
		lod_morphing_area[0] = lod1_range-updateMorphingArea(1);
	}

	public void setLod2_range(int lod2_range) {
		this.lod_range[1] = lod2_range;
		lod_morphing_area[1] = lod2_range-updateMorphingArea(2);
	}

	public void setLod3_range(int lod3_range) {
		this.lod_range[2] = lod3_range;
		lod_morphing_area[2] = lod3_range-updateMorphingArea(3);
	}

	public void setLod4_range(int lod4_range) {
		this.lod_range[3] = lod4_range;
		lod_morphing_area[3] = lod4_range-updateMorphingArea(4);
	}

	public void setLod5_range(int lod5_range) {
		this.lod_range[4] = lod5_range;
		lod_morphing_area[4] = lod5_range-updateMorphingArea(5);
	}

	public void setLod6_range(int lod6_range) {
		this.lod_range[5] = lod6_range;
		lod_morphing_area[5] = lod6_range-updateMorphingArea(6);
	}

	public void setLod7_range(int lod7_range) {
		this.lod_range[6] = lod7_range;
		lod_morphing_area[6] = lod7_range-updateMorphingArea(7);
	}

	public void setLod8_range(int lod8_range) {
		this.lod_range[7] = lod8_range;
		lod_morphing_area[7] = lod8_range-updateMorphingArea(8);
	}
	
	public int[] getLod_morphing_area() {
		return lod_morphing_area;
	}

	public int[] getLod_range() {
		return lod_range;
	}

}
