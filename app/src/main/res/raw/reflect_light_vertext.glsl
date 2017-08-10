attribute vec4 a_Position;
attribute vec2 a_TextureCoordinates;
attribute vec3 a_normalCoordinates;

uniform mat4 u_Matrix;
varying vec2 v_TextureCoordinates;
varying vec4 objectCoord;
varying vec3 v_normalCoordinates;

void main()
{
	v_TextureCoordinates = a_TextureCoordinates;
	objectCoord = a_Position;
	vec4 VertexCoord = a_Position;

    v_normalCoordinates = a_normalCoordinates;

    gl_Position = u_Matrix * VertexCoord;
}
