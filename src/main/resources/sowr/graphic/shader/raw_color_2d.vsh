#version 450 core

layout (location = 0) in vec3 in_coordinates;
layout (location = 1) in vec4 in_color;

layout (location = 0) out vec4 color;

void main()
{
	color = in_color;
	gl_Position = vec4(in_coordinates, 1.0);
}
