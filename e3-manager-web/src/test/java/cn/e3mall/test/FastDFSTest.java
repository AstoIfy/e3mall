package cn.e3mall.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

public class FastDFSTest {

	@Test
	public void fn() throws Exception{
		// 1、创建一个配置文件，配置trackerServer的ip地址和端口号。
		// 2、加载配置文件。
		ClientGlobal.init("E:/e3mall/e3-manager-web/src/main/resources/conf/client.conf");
		// 3、创建一个TrackerClient 对象。直接new一个对象
		TrackerClient trackerClient = new TrackerClient();
		// 4、使用TrackerClient获得一个TrackerServer对象。
		TrackerServer trackerServer = trackerClient.getConnection();
		// 5、创建一个StorageClient对象，构造参数两个一个TrackerServer，StorageServer（可以是null）
		StorageClient storageClient = new StorageClient(trackerServer, null);
		// 6、使用StorageClient上传文件。服务端返回文件的路径及文件名。
		String[] upload_file = storageClient.upload_file("D:/1.jpg", "jpg", null);//meta_list代表 元参数，图片的详细信息
		for (String string : upload_file) {
			System.out.println(string);
		}
	}
}
