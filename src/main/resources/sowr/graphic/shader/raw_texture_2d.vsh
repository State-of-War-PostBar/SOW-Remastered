#version 450 core

layout (location = 0) in vec3 in_coordinates;
layout (location = 1) in vec2 in_texture_coordinates;

layout (location = 0) out vec2 texture_coordinates;

void main()
{
	texture_coordinates = in_texture_coordinates;
	gl_Position = vec4(in_coordinates, 1.0);
}
