package com.rf.springboot.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import org.springframework.beans.factory.annotation.Value;

public class TxtUtils {

  @Value("${uploadController.url}")
  private String url;

  /**
   * 写入TXT文件
   */
  public static void  writeFile(Integer version,String pathname){
    try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
      /* 写入Txt文件 */
      File writename = new File(pathname); // 相对路径，如果没有则要建立一个新的output。txt文件
      writename.createNewFile(); // 创建新文件
      BufferedWriter out = new BufferedWriter(new FileWriter(writename));
      out.write(version.toString()); // \r\n即为换行
      out.flush(); // 把缓存区内容压入文件
      out.close(); // 最后记得关闭文件

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public static String  readFile(String pathname){
    try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
      File filename = new File(pathname); // 要读取以上路径的input。txt文件
      InputStreamReader reader = new InputStreamReader(
          new FileInputStream(filename)); // 建立一个输入流对象reader
      BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
      String line = "";
      line = br.readLine();
      while (line != null) {
        // 一次读入一行数据
        if( br.readLine()!=null){
          line =line+br.readLine();
        }else {
          break;
        }
      }
      return line;

    } catch (Exception e) {
      e.printStackTrace();
    }
    return  "";
  }
}
