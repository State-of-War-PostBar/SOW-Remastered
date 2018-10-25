#version 450 core

uniform sampler2D _texture;

layout (location = 0) in vec2 texture_coordinates;

layout (location = 0) out vec4 fragment_color;

void main()
{
	fragment_color = texture(_texture, texture_coordinates);
}
