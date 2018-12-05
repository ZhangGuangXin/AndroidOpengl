precision highp float;


varying vec2 v_TextureCoordinates;
uniform sampler2D u_TextureUnit;

void main()
{
    vec4 TextureFragile = texture2D(u_TextureUnit, v_TextureCoordinates);
    float textureBlue = 1.0;//TextureFragile.g-0.001;
//    if(textureBlue < 0.0){
//        textureBlue = 1.0;
//    }
    TextureFragile = vec4(1.0, textureBlue, TextureFragile.b, 0);
    gl_FragColor = TextureFragile;
}