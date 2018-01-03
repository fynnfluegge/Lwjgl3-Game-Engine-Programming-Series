#version 430

layout(triangles) in;
layout(triangle_strip, max_vertices = 3) out;

struct Material
{
	sampler2D diffusemap;
	sampler2D normalmap;
	sampler2D heightmap;
	float heightScaling;
	float horizontalScaling;
};

in vec2 mapCoord_GS[];

out vec3 position_FS;
out vec3 tangent_FS;
out vec2 mapCoord_FS;

uniform sampler2D normalmap;
uniform mat4 m_ViewProjection;
uniform vec3 cameraPosition;
uniform Material materials[2];
uniform int tbn_range;

vec3 tangent;

void calcTangent()
{	
	vec3 v0 = gl_in[0].gl_Position.xyz;
	vec3 v1 = gl_in[1].gl_Position.xyz;
	vec3 v2 = gl_in[2].gl_Position.xyz;

	// edges of the face/triangle
    vec3 e1 = v1 - v0;
    vec3 e2 = v2 - v0;
	
	vec2 uv0 = mapCoord_GS[0];
	vec2 uv1 = mapCoord_GS[1];
	vec2 uv2 = mapCoord_GS[2];

    vec2 deltaUV1 = uv1 - uv0;
	vec2 deltaUV2 = uv2 - uv0;
	
	float r = 1.0 / (deltaUV1.x * deltaUV2.y - deltaUV1.y * deltaUV2.x);
	
	tangent = normalize((e1 * deltaUV2.y - e2 * deltaUV1.y)*r);
}

vec3 displacement[3];


void main() {

	for (int i = 0; i < 3; ++i){
		displacement[i] = vec3(0,0,0);
	}

	float dist = (distance(gl_in[0].gl_Position.xyz, cameraPosition) + distance(gl_in[1].gl_Position.xyz, cameraPosition) + distance(gl_in[2].gl_Position.xyz, cameraPosition))/3;
	
	if (dist < tbn_range){
	
		calcTangent();
		
		for(int k=0; k<gl_in.length(); k++){ 
			
			displacement[k] = vec3(0,1,0);
			
			float height = gl_in[k].gl_Position.y;
			
			vec3 normal = normalize(texture(normalmap, mapCoord_GS[k]).rgb);
			
			float[2] materialAlpha = {0,0};
	
			if (normal.y > 0.5){
				materialAlpha[1] = 1;
			}
			else{
				materialAlpha[0] = 1;
			}
			
			float scale = 0;
			for (int i=0; i<2; i++){
				scale += texture(materials[i].heightmap, mapCoord_GS[k]
							* materials[i].horizontalScaling).r 
							* materials[i].heightScaling 
							* materialAlpha[i];
			}
						
			float attenuation = clamp(- distance(gl_in[k].gl_Position.xyz, cameraPosition)/(tbn_range-50) + 1,0.0,1.0);
			scale *= attenuation;

			displacement[k] *= scale;
		}	
	}
	
	for (int i = 0; i < gl_in.length(); ++i)
	{
		vec4 worldPos = gl_in[i].gl_Position + vec4(displacement[i],0);
		gl_Position = m_ViewProjection * worldPos;
		mapCoord_FS = mapCoord_GS[i];
		position_FS = (worldPos).xyz;
		tangent_FS = tangent;
		EmitVertex();
	}
	
	EndPrimitive();
}