attribute vec4 vPosition;
attribute vec2 vTexture; // Per-vertex tex

varying vec3 uPosition;
varying vec2 v_TexCoordinate;

void main() {
    uPosition = vPosition.xyz;
    gl_Position = vPosition;
    v_TexCoordinate = vTexture;
}
