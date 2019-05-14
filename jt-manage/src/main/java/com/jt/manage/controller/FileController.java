package com.jt.manage.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;
import com.jt.manage.service.FileService;

@Controller
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 编辑COntroller方法的步骤
	 *1.明确url地址
	 *2.明确返回值类型   页面String   json(对象)
	 *3.注意请求参数!!!!!!!
	 *4.注意return的写法
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/file")
	public String file(MultipartFile pic) throws IllegalStateException, IOException {
		//1.获取文件上传名称
		String name = 
				pic.getOriginalFilename();
		//2.定义文件上传路径
		File fileDir = new File("E:/jt-upload");
		//3.判断文件夹是否存在
		if(!fileDir.exists()) {
			//创建文件夹
			fileDir.mkdirs();
		}
		pic.transferTo
		(new File("E:/jt-upload/"+name));
		
		return "redirect:/file.jsp";
	}
	
	//实现文件上传
	@RequestMapping("/pic/upload")
	@ResponseBody
	public PicUploadResult fileUpload
	(MultipartFile uploadFile) {
		
		return fileService.upload(uploadFile);
	}
	
	
	
	
	
	
	
	
	
	
	
}
