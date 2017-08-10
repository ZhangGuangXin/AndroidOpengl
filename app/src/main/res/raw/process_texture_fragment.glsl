precision highp float;

varying vec2 v_TextureCoordinates;
uniform sampler2D u_TextureUnit;
varying vec3 objectCoord;

uniform float radianAngle;

void main()
{
//	float RadianAngle = time;//90.0;
	float cosValue = cos(radianAngle); // Calculate Cos of Theta
    float sinValue = sin(radianAngle); // Calculate Sin of Theta
    
    vec3 VertexCoord = objectCoord;
    mat2 rotation = mat2(cosValue, sinValue, -sinValue, cosValue);
    VertexCoord.xy = rotation * VertexCoord.xy;
    
	if (VertexCoord.x > 0.0 && VertexCoord.y > 0.0)
		gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
	else if (VertexCoord.x > 0.0 && VertexCoord.y < 0.0)
		gl_FragColor = vec4(0.0, 01.0, 0.0, 1.0);
	else if (VertexCoord.x < 0.0 && VertexCoord.y > 0.0)
		gl_FragColor = vec4(0.0, 01.0, 1.0, 1.0);
	else if (VertexCoord.x < 0.0 && VertexCoord.y < 0.0)
		 gl_FragColor = vec4(1.0, 0.0, 1.0, 1.0);
}