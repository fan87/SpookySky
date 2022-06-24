#version 130


#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture;
uniform vec2 u_resolution;
uniform int u_direction;

in vec3 worldPosition;



void main() {

    gl_FragColor = vec4(texture2D(u_texture, gl_TexCoord[0].xy));
}