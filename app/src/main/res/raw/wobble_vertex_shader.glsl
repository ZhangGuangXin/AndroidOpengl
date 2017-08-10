#define AMPLITUDE 1.5

#define RIPPLE_AMPLITUDE 6.5
#define FREQUENCY 1.0
#define PI 3.14285714286

attribute vec4 a_Position;
attribute vec2 a_TextureCoordinates;
uniform mat4 u_Matrix;
uniform float time;
varying vec2 v_TextureCoordinates;
varying vec3 objectCoord;

void main()
{
	
	v_TextureCoordinates = a_TextureCoordinates;
	objectCoord = a_Position.xyz;
	vec4 VertexCoord = a_Position;

	VertexCoord.y +=  sin(VertexCoord.x + time) * 2.0;
    gl_Position = u_Matrix * VertexCoord;
}