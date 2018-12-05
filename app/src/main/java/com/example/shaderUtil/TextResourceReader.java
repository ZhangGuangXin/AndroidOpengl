package com.example.shaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;

public class TextResourceReader {
	public static String readTextFileFromResource(Context context, int resourceID){
		StringBuilder body = new StringBuilder();
		
		try {
			InputStream inputStream = context.getResources().openRawResource(resourceID);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String nextLine = null;
			
			while( (nextLine = bufferedReader.readLine()) != null ){
				body.append(nextLine);
				body.append("\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
		}
		return body.toString();
	}
}
