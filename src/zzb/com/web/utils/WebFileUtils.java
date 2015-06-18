package zzb.com.web.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

public final class WebFileUtils {
	
	private WebFileUtils(){}
	
	/***
	 * 将上传文件保存到对应的文件夹下
	 * @param request
	 * @param item
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static String save2File(HttpServletRequest request, FileItem item)
			throws IOException, FileNotFoundException {
		String saveDirPath = "/fileupload/images";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/HH");
		String suffix = dateFormat.format(new Date()).replace('\\', '/');
		saveDirPath = saveDirPath+"/"+suffix;
		File saveDir = new File(request.getServletContext().getRealPath(saveDirPath));
		if(!saveDir.exists() && !saveDir.isDirectory()){
			saveDir.mkdirs();
		}
		String filefix = item.getName().substring(item.getName().lastIndexOf('.'));
		String filepath = saveDirPath+"/"+UUID.randomUUID().toString()+filefix;
		File file = new File(request.getServletContext().getRealPath(filepath));
		InputStream iStream = item.getInputStream();
		FileOutputStream oStream = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int len = 0;
		while((len = iStream.read(buffer)) != -1){
			oStream.write(buffer, 0, len);
		}
		oStream.flush();
		oStream.close();
		iStream.close();
		return request.getServletContext().getContextPath()+filepath;
	}
}
