package com.aboo.vbbs.web.util.identicon;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.aboo.vbbs.base.config.AppSite;
import com.aboo.vbbs.base.util.HashUtil;
import com.aboo.vbbs.base.util.StrUtil;
import com.aboo.vbbs.base.util.security.MD5Helper;
import com.aboo.vbbs.web.util.identicon.generator.IBaseGenartor;
import com.aboo.vbbs.web.util.identicon.generator.MyGenerator;
import com.google.common.base.Preconditions;

/**
 * Author: Bryant Hang
 * Date: 15/1/10
 * Time: 下午2:42
 */
@Component
public class Identicon {

  private IBaseGenartor genartor;

  public Identicon() {
    this.genartor = new MyGenerator();
  }

  public BufferedImage create(String hash, int size) {
    Preconditions.checkArgument(size > 0 && !StringUtils.isEmpty(hash));

    boolean[][] array = genartor.getBooleanValueArray(hash);

//        int ratio = DoubleMath.roundToInt(size / 5.0, RoundingMode.HALF_UP);
    int ratio = size / 6;

    BufferedImage identicon = new BufferedImage(ratio * 6, ratio * 6, BufferedImage.TYPE_INT_ARGB);
    Graphics graphics = identicon.getGraphics();

    graphics.setColor(genartor.getBackgroundColor()); // 背景色
    graphics.fillRect(0, 0, identicon.getWidth(), identicon.getHeight());

    graphics.setColor(genartor.getForegroundColor()); // 图案前景色
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (array[i][j]) {
          graphics.fillRect(j * ratio + 35, i * ratio + 35, ratio, ratio);
        }
      }
    }

    return identicon;
  }

  public static String imgToBase64String(RenderedImage img, String formatName) {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try {
      ImageIO.write(img, formatName, os);
      return Base64.getEncoder().encodeToString(os.toByteArray());
    } catch (final IOException ioe) {
      throw new UncheckedIOException(ioe);
    }
  }

  public String generator() {
    Identicon identicon = new Identicon();
    String md5 = MD5Helper.getMD5String(StrUtil.randomString(6));
    BufferedImage image = identicon.create(md5, 300);
    return "data:image/png;base64," + imgToBase64String(image, "png");
  }

  public String generator(String username) {
    Identicon identicon = new Identicon();
    String md5 = HashUtil.md5(StrUtil.randomString(6));
    BufferedImage image = identicon.create(md5, 420);
    return saveFile(username, image);
  }

  public String saveFile(String username, BufferedImage image) {
    String fileName = "avatar.png";
    String userAvatarPath = username + "/";
    try {
      File file = new File(AppSite.me().getUploadPath() + userAvatarPath);
      if (!file.exists()) file.mkdirs();
      File file1 = new File(AppSite.me().getUploadPath() + userAvatarPath + fileName);
      if (!file1.exists()) file1.createNewFile();
      ImageIO.write(image, "PNG", file1);
      return AppSite.me().getStaticUrl() + userAvatarPath + fileName;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static void main(String[] args) {
    System.out.println(new Identicon().generator());
  }
}
