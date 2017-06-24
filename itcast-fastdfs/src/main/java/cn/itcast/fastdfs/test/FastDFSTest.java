package cn.itcast.fastdfs.test;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FastDFSTest {

	public static void main(String[] args) throws Exception {
		// 1. 初始化，加载Tracker服务的信息
		// 添加一个配置文件，tracker.conf 内容为：tracker_server=192.168.37.161:22122
		// C:\20161031\workspace\itcast-fastdfs
		System.out.println(System.getProperty("user.dir"));
		ClientGlobal.init(System.getProperty("user.dir") + "/src/main/resources/tracker.conf");

		// 2.创建TrackerClient，直接new
		TrackerClient trackerClient = new TrackerClient();

		// 3.使用TrackerClient，获取TrackerServer
		TrackerServer trackerServer = trackerClient.getConnection();

		// 4.声明StorageServer，直接声明为null
		StorageServer storageServer = null;

		// 5.创建StorageClient，用来上传图片，创建需要两个参数TrackerServer,StorageServer
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);

		// 6.使用StorageClient上传图片，返回file_id，其实是一个字符串数组
		String[] str = storageClient.upload_file("C:/Users/tree/Desktop/图片/英雄01/37_Web_0.jpg", "jpg", null);

		// 7.解析结果，打印
		for (String s : str) {
			System.out.println(s);
		}

	}

}
