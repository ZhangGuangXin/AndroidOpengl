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

    float a = 1080.0;
    float b = 300.0;
    float x = objectCoord.x;
    float y = objectCoord.y;
    float z = objectCoord.z;
    v_normalCoordinates.x = v_normalCoordinates.x + a*sin(b*x);
    v_normalCoordinates.y = v_normalCoordinates.y + a*sin(b*y);
    v_normalCoordinates.z = v_normalCoordinates.z + a*sin(b*z);

    gl_Position = u_Matrix * VertexCoord;
}
