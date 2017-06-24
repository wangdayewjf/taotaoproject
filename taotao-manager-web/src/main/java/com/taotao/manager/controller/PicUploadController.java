package com.taotao.manager.controller;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.pojo.PicUploadResult;

@Controller
@RequestMapping("pic/upload")
public class PicUploadController {

	// json转换所使用的包的工具类，我们使用三个功能：1.把对象转为json；2.把json转为对象；3.直接解析json
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;

	private String[] TYPE = { ".jpg", ".gif", ".png", ".bmp", ".jpeg" };

	// filePostName : "uploadFile",
	// uploadJson : '/rest/pic/upload',
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String upload(MultipartFile uploadFile) throws Exception {
		// 声明返回的结果
		PicUploadResult picUploadResult = new PicUploadResult();
		// 设置默认值，上传失败
		picUploadResult.setError(1);

		// 设置标志位，用来标志校验是否成功
		boolean flag = false;

		// 校验图片后缀
		for (String type : TYPE) {
			// 判断图片是否是规定的后缀
			if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
				// 如果是规定的后缀结尾，设置标志位
				flag = true;

				// 跳出循环
				break;
			}
		}

		// if(flag==false){
		if (!flag) {
			// 如果校验失败，直接返回
			// return picUploadResult;
			String json = MAPPER.writeValueAsString(picUploadResult);
			return json;
		}

		// 重置标志位
		flag = false;

		// 校验图片的内容
		try {
			BufferedImage image = ImageIO.read(uploadFile.getInputStream());
			if (image != null) {
				picUploadResult.setHeight(image.getHeight() + "");
				picUploadResult.setWidth(String.valueOf(image.getWidth()));
				// 到这里还没有报错，表示校验成功
				flag = true;
			}
		} catch (Exception e) {
			// 这里不需要写，因为如果有异常，则表示校验失败
		}

		// 校验成功执行图片上传逻辑
		// if(flag==true){
		if (flag) {
			// 执行上传逻辑
			ClientGlobal.init(System.getProperty("user.dir") + "/src/main/resources/resource/tracker.conf");

			TrackerClient trackerClient = new TrackerClient();

			TrackerServer trackerServer = trackerClient.getConnection();

			StorageServer storageServer = null;

			StorageClient storageClient = new StorageClient(trackerServer, storageServer);

			// 获取文件后缀abc.a.b.c.jpg
			String extName = StringUtils.substringAfterLast(uploadFile.getOriginalFilename(), ".");
			String[] str = storageClient.upload_file(uploadFile.getBytes(), extName, null);

			// 解析上传结果，拼接图片的url
			// String url = "http://192.168.37.161" + "/" + str[0] + "/" +
			// str[1];
			String url = this.IMAGE_SERVER_URL + "/" + str[0] + "/" + str[1];

			// 设置图片的url到结果中
			picUploadResult.setUrl(url);

			// 设置结果中，error为0
			picUploadResult.setError(0);

		}

		// 返回结果
		// return picUploadResult;
		String json = MAPPER.writeValueAsString(picUploadResult);
		return json;
	}

}
