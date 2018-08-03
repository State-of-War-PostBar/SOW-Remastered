#version 150 core

in vec3 vertices;
in vec2 texCoords;

out vec2 passTexCoords;

void main() {
	gl_Position = vec4(vertices, 1.0);
	passTexCoords = texCoords;
}
