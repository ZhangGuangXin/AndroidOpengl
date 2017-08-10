precision highp float;

varying vec2 v_TextureCoordinates;
uniform sampler2D u_TextureUnit;

void main()
{
    vec4 TextureFragile = texture2D(u_TextureUnit, v_TextureCoordinates);

    gl_FragColor = TextureFragile;
}