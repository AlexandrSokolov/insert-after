package com.savdev.insert_after;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

public class InsertTextService {

  public String insertText(
    final String filePath,
    final String text2Search,
    final String text2Insert) {
    String original = contentFromPath(filePath);
    int indexOfSearchLine = original.indexOf(text2Search);
    if (indexOfSearchLine != -1) {
      StringBuilder sb = new StringBuilder(original);
      sb.insert(indexOfSearchLine + text2Search.length(), "\n");
      sb.insert(indexOfSearchLine + text2Search.length() + 1, text2Insert);
      return sb.toString();
    } else {
      StringBuilder sb = new StringBuilder(original);
      sb.append("\n");
      sb.append(text2Insert);
      return sb.toString();
    }
  }

  private String contentFromPath(String filePath){
    try {
      String absolutePath = new File(filePath).getAbsolutePath();
      InputStream inputStream = new FileInputStream(absolutePath);
      return IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }
}
