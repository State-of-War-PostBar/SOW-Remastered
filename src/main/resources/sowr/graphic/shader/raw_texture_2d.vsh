#version 450 core

layout (location = 0) in vec3 iCoord;
layout (location = 1) in vec2 iTexCoord;

layout (location = 0) out vec2 TexCoord;

void main()
{
	TexCoord = iTexCoord;
	gl_Position = vec4(iCoord, 1.0);
}
