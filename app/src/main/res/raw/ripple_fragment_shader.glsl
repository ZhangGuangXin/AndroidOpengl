precision highp float;

varying vec2 v_TextureCoordinates;
uniform sampler2D u_TextureUnit;
uniform sampler2D a_TextureUnit;

uniform float textureCoordinateY;

void main()
{
    vec2 textureCoordinate = vec2(v_TextureCoordinates.x, v_TextureCoordinates.y + textureCoordinateY);
    vec4 TextureFragile = texture2D(u_TextureUnit, textureCoordinate);
    vec4 TextureWood = texture2D(a_TextureUnit, textureCoordinate);

    gl_FragColor = TextureWood * TextureFragile;
}