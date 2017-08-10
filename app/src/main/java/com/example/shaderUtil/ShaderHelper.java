package com.example.shaderUtil;

import android.opengl.GLES20;
import android.util.Log;

public class ShaderHelper {
	public static int compileVertexShader(String shaderCode){
		int shaderID = compileShader(GLES20.GL_VERTEX_SHADER, shaderCode);
		return shaderID;
	}
	
	public static int compileFragmentShader(String shaderCode){
		int shaderID = compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode);
		return shaderID;
	}
	
	public static int compileShader(int type, String shaderCode){
		int shaderID = GLES20.glCreateShader(type);
		if(shaderID == 0){
			return 0;
		}
		
		GLES20.glShaderSource(shaderID, shaderCode);
		GLES20.glCompileShader(shaderID);
		
		int[] compileStatus = new int[1];
		GLES20.glGetShaderiv(shaderID, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
		
		String shaderInfo = GLES20.glGetShaderInfoLog(shaderID);

		
		if(compileStatus[0] == 0){
			GLES20.glDeleteShader(shaderID);
			return 0;
		}
		
		
		
		return shaderID;
	}
	
	public static int linkProgram(int vertexShaderID, int fragmentShaderID){
		int programID = GLES20.glCreateProgram();
		if(programID == 0){
			return 0;
		}
		
		GLES20.glAttachShader(programID, vertexShaderID);
		GLES20.glAttachShader(programID, fragmentShaderID);
		
		GLES20.glLinkProgram(programID);
		int[] linkStatus = new int[1];
		GLES20.glGetProgramiv(programID, GLES20.GL_LINK_STATUS, linkStatus, 0);
		if(linkStatus[0] == 0){
			GLES20.glDeleteProgram(programID);
			return 0;
		}
		
		return programID;
	}
	
	public static boolean validateProgram(int program){
		GLES20.glValidateProgram(program);
		int[] validateStatus = new int[1];
		GLES20.glGetProgramiv(program, GLES20.GL_VALIDATE_STATUS, validateStatus, 0);

		return (validateStatus[0] != 0);
	}
	
	 public static int buildProgram(String vertexShaderSource,
		        String fragmentShaderSource) {
		        int program;

		        int vertexShader = compileVertexShader(vertexShaderSource);
		        int fragmentShader = compileFragmentShader(fragmentShaderSource);

		        program = linkProgram(vertexShader, fragmentShader);
		        

		        return program;
		    }
	
	public static void useProgram(int program){
		GLES20.glUseProgram(program);
	}
}
