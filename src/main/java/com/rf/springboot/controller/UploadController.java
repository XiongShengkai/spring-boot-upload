package com.rf.springboot.controller;

import com.rf.springboot.util.TxtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class UploadController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

  @Value("${uploadController.url}")
  private String url;

  @Value("${file.name}")
  private String Name;

  @GetMapping("/upload")
  public String upload() {
    return "upload";
  }

  @PostMapping("/upload")
  @ResponseBody
  public String upload(@RequestParam("file") MultipartFile file, Integer version) {
    if (file.isEmpty()) {
      return "上传失败，请选择文件";
    }
    if (version == null) {
      return "上传失败，请填写版本号";
    }

    String fileName = file.getOriginalFilename();

    if (!fileName.equals(Name)){
     return "请上传rf.apk文件";
    }

    String filePath = url;
    File dest = new File(filePath + fileName);

    try {
      file.transferTo(dest);
      LOGGER.info("上传成功");
      TxtUtils.writeFile(version, url + File.separator + "version.txt");

      return "上传成功";
    } catch (IOException e) {
      LOGGER.error(e.toString(), e);
    }
    return "上传失败！";
  }

}
