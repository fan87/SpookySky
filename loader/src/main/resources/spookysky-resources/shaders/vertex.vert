#version 130

out vec3 worldPosition;

void main(void) {

    gl_TexCoord[0] = gl_MultiTexCoord0;
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
    worldPosition = gl_Vertex.xyz;
}