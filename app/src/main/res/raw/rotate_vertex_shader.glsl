
attribute vec4 a_Position;
attribute vec2 a_TextureCoordinates;
uniform mat4 u_Matrix;
varying vec2 v_TextureCoordinates;
varying vec3 objectCoord;

void main()
{
	v_TextureCoordinates = a_TextureCoordinates;
	objectCoord = a_Position.xyz;

    gl_Position = u_Matrix * a_Position;
}