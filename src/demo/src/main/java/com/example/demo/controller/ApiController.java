package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.FileInfoModel;
import com.example.demo.model.JsonModel;

@RestController
@RequestMapping("/api")
public class ApiController {

	private final static Logger log = LoggerFactory.getLogger("appLogger");
	
	@GetMapping("get")
	public ResponseEntity<String> getText(
			@RequestHeader(name="API-KEY", required=false) String ApiKey
			) {
		log.info("get API"); 
		return ResponseEntity.ok(ApiKey);
	}
	@GetMapping("certificate")
	public ResponseEntity<String> clientCertification(
			@RequestHeader(name="Authorization", required=false) String authorizationHeader
			) {
		log.info("certKey");
		if (authorizationHeader.equals("TETETEST")) {
			return ResponseEntity.ok("OK");
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}
	@GetMapping("getlist")
	public ResponseEntity<List<JsonModel>> getList(
			) {
		List<JsonModel> list = Arrays.asList(
				new JsonModel("image01", 1234),
				new JsonModel("image02", 5678)
		);
		return ResponseEntity.ok(list);
	}
	@GetMapping("notfound")
	public ResponseEntity<String> notFound() {
		return ResponseEntity.notFound().build();
	}
	@GetMapping("error")
	public ResponseEntity<String> isError() {
		return ResponseEntity.badRequest().build();
	}
	@PostMapping("upload")
	public ResponseEntity<JsonModel> uploadFile(
			@RequestPart("filedata") MultipartFile file
			,@RequestPart(name="fileinfo", required=false) FileInfoModel info
			) {
		JsonModel model = new JsonModel();
		model.setFileSize(file.getSize());
		model.setFileName(info.getName());
		return ResponseEntity.ok(model);
		
	}
	@GetMapping("download")
	public ResponseEntity<Resource> downloadFile(
			@RequestParam(name="type", required=false)String type
			) throws Exception {
		String resourcePath = "";
		if (Objects.nonNull(type) && type.toUpperCase().equals("TEXT")) {
			resourcePath = "text/text01.txt";
		} else {
			resourcePath = "images/image00.JPG";
		}
		Resource resource = new ClassPathResource(resourcePath);
		return ResponseEntity.ok()
				.contentType(getContentType(resource.getFile().toPath()))
				.contentLength(resource.contentLength())
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);				
	}
	private MediaType getContentType(Path path) {
		try {
			return MediaType.parseMediaType(Files.probeContentType(path));
		}
		catch(IOException e) {
			return MediaType.APPLICATION_OCTET_STREAM;
		}
	}
}
