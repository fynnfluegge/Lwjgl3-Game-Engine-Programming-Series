#version 430 core

layout (local_size_x = 16, local_size_y = 16) in;

layout (binding = 0, rgba16f) uniform writeonly image2D splatmap;

uniform sampler2D normalmap;
uniform int N;

void main(void)
{
	ivec2 x = ivec2(gl_GlobalInvocationID.xy);
	vec2 texCoord = gl_GlobalInvocationID.xy/float(N);
	
	vec3 normal = normalize(texture(normalmap, texCoord).rgb);
	
	float slopeFactor = normal.z;
	
	float alpha0 = 
	float alpha1 = 
	
	imageStore(splatmap, x, vec4(normal.x,1,0,1));
}