precision highp float;
//#version 300 es
//#define side 0.5;

varying vec2 v_TextureCoordinates;
uniform sampler2D u_TextureUnit;
uniform sampler2D a_TextureUnit;
uniform vec4 u_Color;
varying vec3 objectCoord;

//uniform float side;
uniform float time;


vec3 MaterialAmbient=vec3(1.0, 0.0, 0.0);
vec3 LightAmbient=vec3(1.0, 1.0, 1.0);


void main()
{
//	float side = 0.3;
//	float dotSize = side * 0.7075;
//	vec2 square = vec2(side, side);
//	vec3 modelColor = vec3(1.0, 0.0, 0.0);
//	vec3 dotColor = vec3(0.0, 1.0, 1.0);

//	vec2 position = mod(gl_FragColor.xy, square) - square*0.5;
//	float length = length(position);
//	float inside = step(length, dotSize);
//	if(inside == 0){
//		gl_FragColor = vec4(dotColor, 1.0);
//	}else{
//	    gl_FragColor = vec4(modelColor, 1.0);
//	}
	//gl_FragColor = vec4(mix(dotColor, modelColor, inside), 1.0);

    vec4 TextureFragile = texture2D(u_TextureUnit, v_TextureCoordinates);
    vec4 TextureWood = texture2D(a_TextureUnit, v_TextureCoordinates+time);

//    vec4 ambient = vec4(LightAmbient, 1.0);

    gl_FragColor = TextureWood*(TextureFragile+0.05);
	//gl_FragColor = mix(TextureWood,TextureFragile,TextureFragile.a);
	//gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates);
}