
#extension GL_OES_EGL_image_external : require
precision mediump float;

varying vec2 v_TextureCoordinates;
varying vec4 normalVector;
varying float isRange;
uniform samplerExternalOES u_TextureUnit;
uniform samplerExternalOES a_TextureUnit;

uniform float textureCoordinateY;

uniform float time;

const float step_w = 0.0015625;
const float step_h = 0.0027778;
const float maxW   = 0.04;

void main()
{
    vec3 lightVector = normalize(vec3(0.0, 4.0, 4.0));
    vec2 textureCoordinate = vec2(v_TextureCoordinates.x, v_TextureCoordinates.y + textureCoordinateY);
//  vec2 textureCoordinate = vec2(v_TextureCoordinates.x, (v_TextureCoordinates.y) + textureCoordinateY);
//
//    float x = textureCoordinate.x - maxW*sin((textureCoordinate.y*80.0 - time*7.50));
//    float x = textureCoordinate.x - maxW*sin((textureCoordinate.y*80.0 ));
//    x = fract(x);

    vec4 TextureFragile = texture2D(u_TextureUnit, vec2(textureCoordinate.x, textureCoordinate.y));
    vec4 TextureWood = texture2D(a_TextureUnit, vec2(textureCoordinate.x, textureCoordinate.y));

//    if(isRange == 1.0){
        gl_FragColor = TextureWood * (max( 0.0, dot(vec3(normalVector.x, normalVector.y, normalVector.z),lightVector)) * 10.0);
//    }else{
//        gl_FragColor = TextureWood;
//    }


//    float side = 80.0;
//        float dotSize = side * 0.25;
//        vec2 square = vec2(side, side);
//
//            vec2 position = mod(gl_FragCoord.xy, square) - square * 0.5;
//            float length = length(position);
//            float inside = step(length, dotSize);
//            vec4 tempColor = vec4(1,0,1,1);
//            vec4 baseColor = vec4(0,1,0,0);
//
//            gl_FragColor = mix(baseColor, tempColor, inside);
}
