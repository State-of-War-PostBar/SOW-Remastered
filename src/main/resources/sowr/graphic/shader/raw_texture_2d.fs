#version 450 core

uniform sampler2D _texture;

layout (location = 0) in vec2 TexCoord;

layout (location = 0) out vec4 FragColor;

void main()
{
	FragColor = texture(_texture, TexCoord);
}
