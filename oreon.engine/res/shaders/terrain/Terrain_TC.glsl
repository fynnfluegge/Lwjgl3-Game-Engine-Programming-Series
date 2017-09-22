#version 430

layout(vertices = 16) out;

const int AB = 2;
const int BC = 3;
const int CD = 0;
const int DA = 1;

uniform int tessellationFactor;
uniform float tessellationSlope;
uniform float tessellationShift;
uniform vec3 cameraPosition;

float lodFactor(float dist) {
	
	float tessellationLevel = max(0.0, tessellationFactor/pow(dist, tessellationSlope) + tessellationShift);
	
	return tessellationLevel;
}

void main(){
	
	if (gl_InvocationID == 0){
	
		vec3 abMid = vec3(gl_in[0].gl_Position + gl_in[3].gl_Position)/2.0;	
		vec3 bcMid = vec3(gl_in[3].gl_Position + gl_in[15].gl_Position)/2.0;
		vec3 cdMid = vec3(gl_in[15].gl_Position + gl_in[12].gl_Position)/2.0;
		vec3 daMid = vec3(gl_in[12].gl_Position + gl_in[0].gl_Position)/2.0;		
		
		float distanceAB = distance(abMid, cameraPosition);
		float distanceBC = distance(bcMid, cameraPosition);
		float distanceCD = distance(cdMid, cameraPosition);
		float distanceDA = distance(daMid, cameraPosition);
		
		gl_TessLevelOuter[AB] = mix(1, gl_MaxTessGenLevel, lodFactor(distanceAB));
		gl_TessLevelOuter[BC] = mix(1, gl_MaxTessGenLevel, lodFactor(distanceBC));
		gl_TessLevelOuter[CD] = mix(1, gl_MaxTessGenLevel, lodFactor(distanceCD));
		gl_TessLevelOuter[DA] = mix(1, gl_MaxTessGenLevel, lodFactor(distanceDA));
		
		gl_TessLevelInner[0] = (gl_TessLevelOuter[BC] + gl_TessLevelOuter[DA])/4;
		gl_TessLevelInner[1] = (gl_TessLevelOuter[AB] + gl_TessLevelOuter[CD])/4;
	}
	
	gl_out[gl_InvocationID].gl_Position = gl_in[gl_InvocationID].gl_Position;
}