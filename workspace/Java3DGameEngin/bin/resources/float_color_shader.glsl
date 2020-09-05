#type vertex
#version 150

in vec3 position;

uniform mat4 tranformMat;
uniform mat4 projectionMat;
uniform mat4 viewMatrix;

void main(void)	{
	gl_Position = projectionMat * viewMatrix * tranformMat * vec4(position, 1.0);
}


#type fragment
#version 330 core

out vec4 out_Color;

uniform vec4 u_color;

void main(void)	{
	out_Color = u_color;
}