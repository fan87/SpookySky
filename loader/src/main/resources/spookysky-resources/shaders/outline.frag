#version 130


#ifdef GL_ES
precision mediump float;
#endif

uniform vec2 u_resolution;
in vec3 worldPosition;


void main() {

    gl_FragColor = vec4(1);
}