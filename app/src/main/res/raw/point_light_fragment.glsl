precision highp float;

varying vec2 v_TextureCoordinates;
uniform sampler2D u_TextureUnit;
varying vec4 objectCoord;
varying vec3 v_normalCoordinates;
uniform vec4 u_light_point;
uniform float textureMove;

vec4 LightColor=vec4(0.0, 1.0, 1.0 , 1.0);
uniform mat4 u_MVMatrix;
uniform mat4 u_IT_MVMATRIX;

void main()
{
    vec4 eyeSpacePosition = u_MVMatrix * objectCoord; //vec4(objectCoord, 1);
    vec3 eyeSpaceNormal=normalize(vec3(u_IT_MVMATRIX * vec4(v_normalCoordinates, 0.0)));  //  -0.5, 0.4, 0.2
    vec3 toPointLight = vec3(u_light_point) - vec3(eyeSpacePosition);
    float distance = length(vec3(toPointLight))/4.0;
    toPointLight = normalize(toPointLight);
    float diffcuse = max(0.0, dot(eyeSpaceNormal, toPointLight));
    vec4 TextureWood = texture2D(u_TextureUnit, v_TextureCoordinates+textureMove);

    gl_FragColor = (TextureWood * LightColor * diffcuse);
}
