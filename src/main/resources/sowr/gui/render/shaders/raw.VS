#version 150 core

in vec3 vertices;

out vec4 color;

void main() {
	gl_Position = vec4(vertices, 1.0);
	color = vec4(1.0, 1.0, 1.0, 1.0);
}
