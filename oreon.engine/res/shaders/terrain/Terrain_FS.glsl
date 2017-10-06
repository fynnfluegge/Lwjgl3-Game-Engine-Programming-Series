#version 330

layout(location = 0) out vec4 outputColor;

in vec2 mapCoord_FS;

uniform sampler2D normalmap;

const vec3 direction = vec3(0.1,-1,0.1);
const float intensity = 1.2;

float diffuse(vec3 direction, vec3 normal, float intensity)
{
	return max(0.01, dot(normal, -direction) * intensity);
}

void main()
{
	vec3 normal = texture(normalmap, mapCoord_FS).rgb;

	float diffuse = diffuse(direction, normal, intensity);

	outputColor = vec4(0.1,1.0,0.1,1.0) * diffuse;
}