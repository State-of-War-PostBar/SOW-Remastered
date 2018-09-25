#version 150 core

in vec4 color;
in vec2 passTexCoords;

out vec4 fragColor;

uniform sampler2D sampler;

void main() {
	fragColor = texture(sampler, passTexCoords);
}
