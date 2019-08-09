package lang.classloader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MyClassLoader extends ClassLoader{
	
	public static void main(String[] args) throws Exception {
		String srcPath = args[0] +" " +  args[1];
		String destDir = args[2];
		FileInputStream fips = new FileInputStream(srcPath);
		String destFileName = srcPath.substring(srcPath.lastIndexOf("\\") + 1);
		String destPath = destDir + "\\" + destFileName;
		FileOutputStream fos = new FileOutputStream(destPath);
		cypher(fips,fos);
		fips.close();
		fos.close();
	}
	
	private static void cypher(InputStream ips , OutputStream ops) throws IOException {
		int b = -1;
		while((b = ips.read()) != -1) {
//			ops.write(b ^ 0xff);
			ops.write(b);
		}
	}

	private String classDir;
	
	@SuppressWarnings("deprecation")
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String classFileName = name + ".class";
		if(classDir != null) {
			classFileName = classDir + "/" + classFileName;
		}
		try {
			FileInputStream fips = new FileInputStream(classFileName);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			cypher(fips,bos);
			fips.close();
			byte[] bytes = bos.toByteArray();
			return defineClass(bytes,0,bytes.length);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.findClass(name);
	}
	
	public MyClassLoader(String classDir) {
		this.classDir = classDir;
	}
}
