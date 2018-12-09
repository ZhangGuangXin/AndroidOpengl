precision highp float;

varying vec2 v_TextureCoordinates;
uniform sampler2D u_TextureUnit;
varying vec4 objectCoord;
varying vec3 v_normalCoordinates;
uniform vec4 u_light_point;
uniform float textureMove;
uniform samplerCube skyCube;

vec4 LightColor=vec4(0.0, 1.0, 1.0 , 1.0);
uniform mat4 u_MVMatrix;
uniform mat4 u_IT_MVMATRIX;
uniform mat4 matrixForSky;

vec3 getReflectVector(vec3 toPointLight, vec3 cubeNormal){ //得到反射向量
    vec3 cubePointForCamera = vec3(toPointLight.x, toPointLight.y, toPointLight.z) * 1.0;
    vec3 cuberReflect = reflect(normalize(cubePointForCamera), cubeNormal);
//    vec3 cuberRefract = -refract(cubePointForCamera, cubeNormal, 0.9);
    vec4 tempVector = vec4(cuberReflect, 0.0);
    tempVector = matrixForSky * tempVector;
    cuberReflect = vec3(tempVector.x, tempVector.y, tempVector.z);
    return cuberReflect;
}

vec3 getRefractVector(vec3 toPointLight, vec3 cubeNormal){ //得到折射向量
    vec3 cubePointForCamera = vec3(toPointLight.x, toPointLight.y, toPointLight.z) * 1.0;
    vec3 cuberRefract = -refract(cubePointForCamera, cubeNormal, 0.9);
    vec4 tempVector = vec4(cuberRefract, 0.0);
    tempVector = matrixForSky * tempVector;
    cuberRefract = vec3(tempVector.x, tempVector.y, tempVector.z);
    return cuberRefract;
}


void main()
{
    vec4 eyeSpacePosition = u_MVMatrix * objectCoord;
    vec3 eyeSpaceNormal = normalize(vec3(u_IT_MVMATRIX * vec4(v_normalCoordinates, 0.0)));
    vec3 viewer = -normalize(vec3(eyeSpacePosition)); //vec3(1,1,1);
    vec3 toPointLight = vec3(u_light_point) - vec3(eyeSpacePosition);

    float distance = length(vec3(toPointLight));
    toPointLight = normalize(toPointLight);
    vec3 reflect = reflect(-toPointLight, eyeSpaceNormal); //vec3(1,1,1);//

    vec3 cubeNormal = eyeSpaceNormal;
    if(cubeNormal.z < 0.0){
        cubeNormal.z = -cubeNormal.z;
    }
   vec3 cuberRefract = getRefractVector(toPointLight, cubeNormal);//折射
//    vec3 cuberReflect = getReflectVector(toPointLight, cubeNormal);//反射
    vec4 skyColor = textureCube(skyCube, cuberRefract);

    float intensity = pow(max(dot(reflect, viewer), 0.0), 1.0);
    vec4 TextureWood = texture2D(u_TextureUnit, v_TextureCoordinates+textureMove);
      float diffcuse = max(0.0, dot(eyeSpaceNormal, toPointLight));
    gl_FragColor = (TextureWood * LightColor * intensity * 0.1) + (skyColor * 0.9);
}

