precision mediump float;
uniform vec4 vColor;

uniform vec2 vLightPosition;

varying vec3 uPosition;
varying vec2 v_TexCoordinate;

//const vec3 LIGHT_POS = vec3(0.5, 0.5, -0.025);

uniform sampler2D u_Normal;
uniform sampler2D u_Texture;


void main() {
  float lightPower = 0.67;
    vec3 NORMAL = vec3(0.0, 0.0, 1.0);

  vec3 LIGHT_POS = vec3(vLightPosition, 0.75225);
  vec3 vectorToLight = LIGHT_POS - uPosition;

  vec3 texNormal = ((texture2D(u_Normal, v_TexCoordinate).rgb - 0.5)* 2.0)* vec3(1.0, -1.0, 1.0);
  vec3 texColor = texture2D(u_Texture, v_TexCoordinate).rgb;
//  float brigthness = (dot(vectorToLight, texColor) + 1.0) / 2.0;

//  float brigthness = (dot(vectorToLight, texColor) + 1.0) / 2.0;

//    also take into account camera position


//  gl_FragColor = vec4(texColor, 1.0); //vec4(brigthness, brigthness, brigthness, 1.0);//uPosition;
//  gl_FragColor = vec4(brigthness, brigthness, brigthness, 1.0);//uPosition;
//  gl_FragColor = vec4(vectorToLight, 1.0);//uPosition;
//  gl_FragColor = vec4(LIGHT_POS, 1.0);//uPosition;
//  gl_FragColor = vec4( (normalize(uPosition) + 1.0)/2.0, 1.0);//uPosition;

  float fade = sqrt(dot(vectorToLight, vectorToLight));     // distance to the light
//  vec3 b = dot( normalize(vectorToLight), texNormal) * texColor / (fade * fade);
  vec3 b = dot( normalize(vectorToLight), texNormal)  * texColor / (fade * fade) * lightPower;
  gl_FragColor = vec4( b , 1.0);//uPosition;
}
