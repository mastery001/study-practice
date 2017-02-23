package io;

import java.io.File;

public class FilePath {
	
	/**  
	 * @return  
	 * @Description:  获取当前classes下Java类所在的路径
	 */
	public static String getCurrentClassesPath() {
		return getPathBySeparator("");
	}
	
	public static String getCurrentWorkPath() {
		return new File("").getAbsolutePath();
	}
	
	/**  
	 * @return  
	 * @Description:  获取当前classes目录下的路径
	 */
	public static String getCurrentClassesAbsolutePath() {
		return getPathBySeparator("/");
	}
	
	private static String getPathBySeparator(String separator) {
		return FilePath.class.getResource(separator).getPath();
	}
}
