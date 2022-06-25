#version 130


#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture;
uniform vec2 u_resolution;
uniform int u_direction;

in vec3 worldPosition;



void main() {

    vec4 color = texture2D(u_texture, gl_TexCoord[0].xy);

    if(color.a != 0) {
        gl_FragColor = vec4(0, 0, 0, 0);
        return;
    }
    float radius = 0.001;
    for (float x = -radius; x <= radius; x += radius) {
        for (float y = -radius; y <= radius; y += radius) {
            vec4 currentColor = texture2D(u_texture, gl_TexCoord[0].xy + vec2(x, y));

            if (currentColor.a != 0) {
                gl_FragColor = vec4(1);
                return;
            }
        }
    }
}