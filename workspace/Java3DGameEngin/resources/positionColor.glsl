#type vertex
#version 400 core

in vec3 position;

out vec3 color;

uniform mat4 tranformMat;
uniform mat4 projectionMat;
uniform mat4 viewMatrix;

void main(void)	{
	gl_Position = projectionMat * viewMatrix * tranformMat * vec4(position, 1.0);
	color = vec3(position.x + 0.5, 1, position.y + 0.5) ;
}

#type fragment
#version 400 core

in vec3 color;

out vec4 out_Color;

void main(void)	{
	out_Color = vec4(color, 1.0);
}