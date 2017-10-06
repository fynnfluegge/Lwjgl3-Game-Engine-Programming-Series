#version 430

layout(quads, fractional_odd_spacing, cw) in;

in vec2 mapCoord_TE[];

out vec2 mapCoord_GS;

uniform sampler2D heightmap;

void main(){

    float u = gl_TessCoord.x;
    float v = gl_TessCoord.y;
	
	// world position
	vec4 position =
	((1 - u) * (1 - v) * gl_in[12].gl_Position +
	u * (1 - v) * gl_in[0].gl_Position +
	u * v * gl_in[3].gl_Position +
	(1 - u) * v * gl_in[15].gl_Position);
	
	vec2 mapCoord =
	((1 - u) * (1 - v) * mapCoord_TE[12] +
	u * (1 - v) * mapCoord_TE[0] +
	u * v * mapCoord_TE[3] +
	(1 - u) * v * mapCoord_TE[15]);
	
	float height = texture(heightmap, mapCoord).r;
	height *= 600;
					
	position.y = height;
	
	mapCoord_GS = mapCoord;
	
	gl_Position = position;
}