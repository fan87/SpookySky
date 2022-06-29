#version 130


#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture;
uniform vec2 u_resolution;
uniform float u_radius;
uniform int u_direction;
uniform vec4 u_color;

in vec3 worldPosition;



void main() {

    vec4 color = texture2D(u_texture, gl_TexCoord[0].xy);

    if(color.a != 0) {
        gl_FragColor = vec4(0, 0, 0, 0);
        return;
    }
    if (gl_TexCoord[0].x < u_radius || gl_TexCoord[0].x > 1.0 - u_radius || gl_TexCoord[0].y < u_radius || gl_TexCoord[0].y > 1.0 - u_radius) {
        gl_FragColor = color;
        return;
    }
    for (float x = -u_radius; x <= u_radius; x += u_radius) {
        for (float y = -u_radius; y <= u_radius; y += u_radius) {
            vec4 currentColor = texture2D(u_texture, gl_TexCoord[0].xy + vec2(x, y));

            if (currentColor.a != 0) {
                gl_FragColor = u_color;
                return;
            }
        }
    }
}