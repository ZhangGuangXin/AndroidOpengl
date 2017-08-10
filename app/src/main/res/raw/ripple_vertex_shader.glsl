#define AMPLITUDE 1.2

#define RIPPLE_AMPLITUDE 6.5
#define FREQUENCY 1.0
#define PI 3.14285714286

attribute vec4 a_Position;
attribute vec2 a_TextureCoordinates;
uniform mat4 u_Matrix;
uniform float time;
varying vec2 v_TextureCoordinates;

void main()
{
	v_TextureCoordinates = a_TextureCoordinates;

	vec4 VertexCoord = a_Position;
	float distance = length(VertexCoord);
    VertexCoord.y = sin( 2.0 * PI * distance * FREQUENCY + time)* RIPPLE_AMPLITUDE;
	
    gl_Position = u_Matrix * VertexCoord;
}