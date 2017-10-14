precision highp float;

varying vec2 v_TextureCoordinates;
uniform sampler2D u_TextureUnit;
float twirlRadius = 100.0;
uniform float angle;
float imageWidth = 256.0;
float imageHeight = 256.0;
vec2 center = vec2(125.0,125.0);
float radiusFacotr = 3.0;

vec4 twirl(sampler2D  tex, vec2 uv, float angle){
    vec2 texSize = vec2(imageWidth, imageHeight);
    vec2 tc = (uv * texSize) - center;
    float distance = sqrt(tc.x*tc.x + tc.y*tc.y);
    if(distance < twirlRadius + angle * radiusFacotr){
       float percent = (twirlRadius - distance) / twirlRadius;
       float theta = percent * percent * angle;
       float sinus = sin(theta);
       float cosine = cos(theta);
       tc = vec2(dot(tc, vec2(cosine, -sinus)), dot(tc, vec2(sinus, cosine)));
    }
    return texture2D(tex, (tc+center)/256.0);
}

void main()
{
//     if(gl_FragCoord.x > 100.0){
        gl_FragColor = twirl(u_TextureUnit, v_TextureCoordinates, angle);
//     }else{
//        gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates);
//     }



//    vec4 TextureFragile = texture2D(u_TextureUnit, v_TextureCoordinates);
//    gl_FragColor = TextureFragile;
}