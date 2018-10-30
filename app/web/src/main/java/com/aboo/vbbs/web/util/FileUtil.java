package com.aboo.vbbs.web.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.aboo.vbbs.base.config.AppSite;
import com.aboo.vbbs.base.util.DateUtil;
import com.aboo.vbbs.base.util.FileUploadEnum;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * https://yiiu.co
 */
@Component
public class FileUtil {

  /**
   * upload file
   *
   * @param file
   * @param fileUploadEnum
   * @return
   * @throws IOException
   */
  public String uploadFile(MultipartFile file, FileUploadEnum fileUploadEnum, String username) throws IOException {
    if (!file.isEmpty()) {
      String type = file.getContentType();
      String suffix = "." + type.split("/")[1];
      String fileName = null;
      BufferedOutputStream stream = null;
      String requestPath = null;

      // upload file
      if (fileUploadEnum == FileUploadEnum.FILE) {
        String today = DateUtil.formatDate(new Date());
        String userUploadPath = username + "/" + today + "/";
        fileName = UUID.randomUUID().toString() + suffix;
        File file_dir = new File(AppSite.me().getUploadPath() + userUploadPath);
        if (!file_dir.exists()) file_dir.mkdirs();
        stream = new BufferedOutputStream(new FileOutputStream(new File(AppSite.me().getUploadPath() + userUploadPath + fileName)));
        requestPath = AppSite.me().getStaticUrl() + userUploadPath;
      }

      // upload avatar (image)
      if (fileUploadEnum == FileUploadEnum.AVATAR) {
        String userAvatarPath = username + "/";
        fileName = "avatar" + suffix;
        File file_dir = new File(AppSite.me().getUploadPath() + userAvatarPath);
        if (!file_dir.exists()) file_dir.mkdirs();
        stream = new BufferedOutputStream(
            new FileOutputStream(new File(AppSite.me().getUploadPath() + userAvatarPath + fileName)));
        requestPath = AppSite.me().getStaticUrl() + userAvatarPath;
      }

      if (stream != null) {
        stream.write(file.getBytes());
        stream.close();
        return requestPath + fileName;
      }
    }
    return null;
  }

  /**
   * search username upload dir's space size
   *
   * @param file
   * @return
   */
  public long getTotalSizeOfFilesInDir(File file) {
    if (file.isFile())
      return file.length();
    File[] children = file.listFiles();
    long total = 0;
    if (children != null)
      for (File child : children)
        total += getTotalSizeOfFilesInDir(child);
    return total;
  }
}
