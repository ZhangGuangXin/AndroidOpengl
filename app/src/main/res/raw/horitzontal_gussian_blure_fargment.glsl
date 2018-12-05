precision highp float;

varying vec2 v_TextureCoordinates;
uniform sampler2D u_TextureUnit;
float step = 10.0;
uniform float[11] weightArray;

void main()
{
    float rowLength = step + 1.0;
    vec4 sum = texture2D(u_TextureUnit, v_TextureCoordinates);
    float unit = 0.01;
    for(float i=1.0; i<rowLength; i++){
        sum += texture2D(u_TextureUnit, v_TextureCoordinates + vec2(unit * i, 0));
        sum += texture2D(u_TextureUnit, v_TextureCoordinates - vec2(unit * i, 0));
    }
    gl_FragColor = sum / rowLength;

//    float[rowLength * rowLength] colorMat;
//    vec4 sum;
//    int index = 0;
//    for(int row = 0; row < 3; row++){
//        for(int col = 0; col <3; col++){
//            sum += texture2D(u_TextureUnit, v_TextureCoordinates+vec2(row-step, col-step)) * colorMat[index]; //
//            index++;
//        }
//    }
//    gl_FragColor = sum;

//    gl_FragColor = vec4(sum[0]/49.0, sum[1]/49.0, sum[2]/49.0, 0.0);

//        vec4 TextureFragile = texture2D(u_TextureUnit, v_TextureCoordinates);
//        gl_FragColor = TextureFragile;
}