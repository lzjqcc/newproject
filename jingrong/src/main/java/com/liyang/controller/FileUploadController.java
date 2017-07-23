package com.liyang.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.ListBucketsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.liyang.util.FailReturnObject;
import com.liyang.util.SuccessReturnObject;

@Controller
public class FileUploadController {

	@Value("${spring.oss.endpoint}")
	private  String endpoint;

	@Value("${spring.oss.accessKeyId}")
	private String accessKeyId;

	@Value("${spring.oss.accessKeySecret}")
	private String accessKeySecret;

	@Value("${spring.oss.bucketName}")
	private String bucketName;

	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	@ResponseBody
	public SuccessReturnObject handleUploadProcess(@RequestParam("file") MultipartFile file, Model model) throws Exception {
		String originalFilename = file.getOriginalFilename();
		String suffix=originalFilename.substring(originalFilename.lastIndexOf("."));
		UUID randomUUID = UUID.randomUUID();
		String newFileName = randomUUID.toString()+suffix;
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		try {
			ossClient.putObject(new PutObjectRequest(bucketName, newFileName, file.getInputStream()));
		}catch (OSSException oe) {
			throw new FailReturnObject(1500, oe.getErrorCode()+":"+oe.getErrorMessage());
		} finally {
			ossClient.shutdown();
		}
		return new SuccessReturnObject(new File(originalFilename,newFileName));
	}
	private class File{
		private String originalFileName;
		private String newFileName;
		public String getOriginalFileName() {
			return originalFileName;
		}
		public void setOriginalFileName(String originalFileName) {
			this.originalFileName = originalFileName;
		}
		public String getNewFileName() {
			return newFileName;
		}
		public void setNewFileName(String newFileName) {
			this.newFileName = newFileName;
		}
		public File(String originalFileName, String newFileName) {
			super();
			this.originalFileName = originalFileName;
			this.newFileName = newFileName;
		}
	}
}
