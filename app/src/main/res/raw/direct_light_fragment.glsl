precision highp float;

varying vec2 v_TextureCoordinates;
uniform sampler2D u_TextureUnit;
varying vec3 v_normalCoordinates;

vec4 LightColor=vec4(0.0, 1.0, 1.0 , 1.0);
uniform vec3 lightVector;// = vec3(0.1, 0.2, 0.3);
uniform mat4 u_IT_MVMATRIX;

void main()
{
     vec3 eyeSpaceNormal=normalize(vec3(u_IT_MVMATRIX * vec4(v_normalCoordinates, 0.0)));
     float diffcuse = max(dot(eyeSpaceNormal, lightVector), 0.0);
     vec4 TextureWood = texture2D(u_TextureUnit, v_TextureCoordinates);

     gl_FragColor = TextureWood * LightColor * diffcuse;
}
