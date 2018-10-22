#version 450 core

layout (location = 0) in vec3 iCoord;
layout (location = 1) in vec4 iColor;

out vec4 color;

void main()
{
	color = iColor;
	gl_Position = vec4(iCoord, 1.0);
}
