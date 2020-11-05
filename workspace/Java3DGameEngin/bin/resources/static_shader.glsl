#type vertex
#version 150

in vec3 position;
in vec2 textureCoords;

out vec3 color;
out vec2 out_textCoords;

uniform mat4 tranformMat;
uniform mat4 projectionMat;
uniform mat4 viewMatrix;

void main(void)	{
	gl_Position = projectionMat * viewMatrix * tranformMat * vec4(position, 1.0);
	out_textCoords = textureCoords;
	color = vec3(position.x+0.5,0.0,position.y+0.5);
}


#type fragment
#version 400 core

in vec2 out_textCoords;

out vec4 out_Color;

uniform sampler2D textureSampler;

void main(void)	{
	out_Color = texture(textureSampler, out_textCoords);
}