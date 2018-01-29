package cn.e3mall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.common.utils.FastDFSClient;

@Controller
public class UploadPictureController {

	@RequestMapping("/pic/upload")
	@ResponseBody
	public Map upload(MultipartFile uploadFile){
		try {
			// 1、接收上传的文件
			// 2、取文件的原始名称
			String filename = uploadFile.getOriginalFilename();
			// 3、截取文件的扩展名
			String extName = filename.substring(filename.lastIndexOf(".") + 1);
			// 4、使用FastDFSClient上传文件
			FastDFSClient client = new FastDFSClient("classpath:conf/client.conf");
			String path = client.uploadFile(uploadFile.getBytes(), extName);
			// 5、服务端返回路径及文件名，需要拼接一个图片服务器的完整路径。
			String url = "http://192.168.25.133/"+path;
			// 6、返回一个Map对象]
			Map result = new HashMap<>();
			result.put("error",0);
			result.put("url", url);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			Map result = new HashMap<>();
			result.put("error",1);
			result.put("message", "图片上传失败!");
			return result;
		}
	}
}
