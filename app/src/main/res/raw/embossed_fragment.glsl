precision highp float;


varying vec2 v_TextureCoordinates;
uniform sampler2D u_TextureUnit;

void main()
{
    float x = 1.0/256.0;
    float y = 1.0/256.0;

    vec3 p00 = texture2D(u_TextureUnit, v_TextureCoordinates).rgb;
    vec3 p01 = texture2D(u_TextureUnit, v_TextureCoordinates + vec2(0.0, y)).rgb;

    vec3 diff = p00 - p01;

    float maxNum = diff.r;
    if(abs(diff.g) > abs(maxNum)){
        maxNum = diff.g;
    }
    if(abs(diff.b) > abs(maxNum)){
        maxNum = diff.b;
    }

    float gray = clamp(maxNum + 0.2, 0.0, 1.0);
    gl_FragColor = vec4(gray, gray, gray, 1.0);

//    vec4 TextureFragile = texture2D(u_TextureUnit, v_TextureCoordinates);
//    gl_FragColor = TextureFragile;
}