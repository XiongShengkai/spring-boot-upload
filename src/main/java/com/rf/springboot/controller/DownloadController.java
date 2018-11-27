package com.rf.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.rf.springboot.util.TxtUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DownloadController {


  @Value("${uploadController.url}")
  private String url;

  @Value("${file.name}")
  private String fileName;

  @GetMapping("getVersion")
  @ResponseBody
  public String getVersion() {
    Map<String, Object> map = new HashMap<>();
    String ver = TxtUtils.readFile(url + File.separator + "version.txt");
    if (ver == null) {
      map.put("code", -1);
      map.put("data", "暂无版本");
    } else {
      map.put("code", 0);
      map.put("data", Integer.parseInt(ver));
    }

    String versionStr = JSON.toJSONString(map);
    return versionStr;
  }

  @GetMapping("/download")
  public void downloadFileByOutputStream(HttpServletResponse response) {
    File file = new File(url + File.separator + fileName);
    // 1.设置content-disposition响应头控制浏览器以下载的形式打开文件
    response.setHeader("content-disposition", "attachment;filename=" + fileName);
    response.setContentLength((int) file.length());
    // 2.根据文件路径获取要下载的文件输入流
    try {
      InputStream in = new FileInputStream(file);
      int len = 0;
      // 3.创建数据缓冲区
      byte[] buffer = new byte[1024];
      // 4.通过response对象获取OutputStream流
      OutputStream out = response.getOutputStream();
      // 5.将FileInputStream流写入到buffer缓冲区
      while ((len = in.read(buffer)) > 0) {
        // 6.使用OutputStream将缓冲区的数据输出到客户端浏览器
        out.write(buffer, 0, len);
      }
      if (in!=null){
        in.close();
      }
      if (out!=null){
        out.close();
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
