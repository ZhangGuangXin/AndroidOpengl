precision highp float;


varying vec2 v_TextureCoordinates;
uniform sampler2D u_TextureUnit;
vec3 lum = vec3(0.2126, 0.7152, 0.0722);
float p00,p10,p20,p01,p21,p02,p12,p22,x,y,px,py,distance;
vec2 FBS = vec2(256.0, 256.0);

float fxaaLuma(vec3 rgb){
    return rgb.y * (0.587/0.299) + rgb.x;
}

void main() {
    float FXAA_SPANA_MAX = 8.0;
    float FXAA_REDUCE_MUL = 1.0/8.0;
    float FXAA_REDUCE_MIN = 1.0/128.0;

    vec3 rgbNW = texture2D(u_TextureUnit, v_TextureCoordinates + (vec2(-1.0, -1.0)/FBS)).xyz;
    vec3 rgbNE = texture2D(u_TextureUnit, v_TextureCoordinates + (vec2(1.0, -1.0)/FBS)).xyz;
    vec3 rgbSW = texture2D(u_TextureUnit, v_TextureCoordinates + (vec2(-1.0, 1.0)/FBS)).xyz;
    vec3 rgbSE = texture2D(u_TextureUnit, v_TextureCoordinates + (vec2(1.0, 1.0)/FBS)).xyz;
    vec3 rgbM = texture2D(u_TextureUnit, v_TextureCoordinates).xyz;

    float lumaNW = fxaaLuma(rgbNW);
    float lumaNE = fxaaLuma(rgbNE);
    float lumaSW = fxaaLuma(rgbSW);
    float lumaSE = fxaaLuma(rgbSE);
    float lumaM = fxaaLuma(rgbM);

    vec2 dir;
    dir.x = ((lumaNW + lumaNE) - (lumaSW + lumaSE));
    dir.y = ((lumaNW + lumaSW) - (lumaNE + lumaSE));

    float dirReduce = max((lumaNW + lumaNE + lumaSE + lumaSW) * (0.25 * FXAA_REDUCE_MUL), FXAA_REDUCE_MIN);
    float rcpDirMin = 1.0 / (min(abs(dir.x), abs(dir.y)) + dirReduce);
    dir = min(vec2(FXAA_SPANA_MAX, FXAA_SPANA_MAX), max(vec2(-FXAA_SPANA_MAX, -FXAA_SPANA_MAX), dir * rcpDirMin)) / FBS;

    vec3 rgbA = 0.5 * (texture2D(u_TextureUnit, v_TextureCoordinates.xy + dir * (1.0 / 3.0 - 0.5)).xyz
        + texture2D(u_TextureUnit, v_TextureCoordinates.xy + dir * (2.0 / 3.0 - 0.5)).xyz);

    vec3 rgbB = rgbA * 0.5 + (1.0 / 4.0) * (texture2D(u_TextureUnit, v_TextureCoordinates.xy + dir * (0.0 / 3.0 - 0.5)).xyz
        + texture2D(u_TextureUnit, v_TextureCoordinates.xy + dir * (3.0 / 3.0 - 0.5)).xyz);

    float lumaB = fxaaLuma(rgbB);
    float lumaMin = min(lumaM, min(min(lumaNW, lumaNE), min(lumaSW, lumaSE)));
    float lumaMax = max(lumaM, max(max(lumaNW, lumaNE), max(lumaSW, lumaSE)));
    if((lumaB < lumaM) || (lumaB > lumaMax)){
          gl_FragColor = vec4(rgbA, 1.0);
    }else {
          gl_FragColor = vec4(rgbB, 1.0);
    }
}
