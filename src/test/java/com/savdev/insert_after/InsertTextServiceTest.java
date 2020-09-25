package com.savdev.insert_after;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

public class InsertTextServiceTest {

  public static final String ORIGINAL_FILE = "test1.txt";
  public static final String SEARCH_CONTENT_FILE = "text2search.txt";
  public static final String INSERT_CONTENT_FILE = "test1.2Insert.txt";
  public static final String EXPECTED_FINAL_FILE = "test1.cmp.txt";

  public static final String ORIGINAL_FILE_WITHOUT_SEARCH_TEXT = "test.without.search.content.txt";
  public static final String EXPECTED_FINAL_FILE_WIHOUT_SEARCH = "test1.cmp.without.search.txt";

  InsertTextService service = new InsertTextService();

  @Test
  public void testSearchBlockExists() {
    Assert.assertEquals(
      service.insertText(
        testFullPath(ORIGINAL_FILE),
        contentFromTestPath(SEARCH_CONTENT_FILE),
        contentFromTestPath(INSERT_CONTENT_FILE)),
      contentFromTestPath(EXPECTED_FINAL_FILE));
  }

  @Test
  public void testSearchBlockNotExist() {
    Assert.assertEquals(
      service.insertText(
        testFullPath(ORIGINAL_FILE_WITHOUT_SEARCH_TEXT),
        contentFromTestPath(SEARCH_CONTENT_FILE),
        contentFromTestPath(INSERT_CONTENT_FILE)),
      contentFromTestPath(EXPECTED_FINAL_FILE_WIHOUT_SEARCH));
  }

  @Test
  public void testSearchAndInsertBlocksExist() {
    Assert.assertEquals(
      service.insertText(
        testFullPath(ORIGINAL_FILE),
        contentFromTestPath(SEARCH_CONTENT_FILE),
        contentFromTestPath(SEARCH_CONTENT_FILE)),
      contentFromTestPath(ORIGINAL_FILE));
  }

  private String contentFromTestPath(String filePath){
    try {
      String absolutePath = testFullPath(filePath);
      InputStream inputStream = new FileInputStream(absolutePath);
      return IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  private String testFullPath(String filePath) {
    try {
      String withoutFirstSlash = filePath.startsWith(File.separator) ?
        filePath.substring(File.separator.length()) : filePath;

      URL pathResource = this.getClass()
                             .getClassLoader()
                             .getResource(withoutFirstSlash);
      if (pathResource == null) {
        throw new IllegalStateException(
          "Could not find resource for the path in test resources: [" + filePath + "]");
      }
      return new File(pathResource.toURI()).getAbsolutePath();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}
