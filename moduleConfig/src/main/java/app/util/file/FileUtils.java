package app.util.file;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.zip.GZIPOutputStream;

import app.util.LogUtils;


public class FileUtils {

  /**文档类型*/
  public static final int TYPE_DOC = 0;
  /**apk类型*/
  public static final int TYPE_APK = 1;
  /**压缩包类型*/
  public static final int TYPE_ZIP = 2;

  public static String getAssetFileString(Context context, String fileName){
    StringBuilder stringBuilder = new StringBuilder();

    AssetManager assets = context.getAssets();
    InputStream is = null;
    try {
      is = assets.open(fileName);
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
      String line;
      while ((line = bufferedReader.readLine()) != null){
        stringBuilder.append(line);
      }
      bufferedReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      if (is!=null){
        try {
          is.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return stringBuilder.toString();
  }

  public static boolean isFileExists(String filename) {
    File folder1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);
    return folder1.exists();
  }

  public static boolean deleteFile(String filename) {
    File folder1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filename);
    return folder1.delete();
  }

  public static void delFile(File file) {
    if (null == file){
      return;
    }
    if (file.exists()) {
      file.delete();
    }
  }

  public static boolean copyFile(String src, String dst) {
    InputStream inputStream = null;
    OutputStream outputStream = null;
    try {
      inputStream = new FileInputStream(src);
      outputStream = new FileOutputStream(dst);
      File file = new File(src);
      byte[] data = new byte[(int) file.length()];
      inputStream.read(data);
      outputStream.write(data);
      outputStream.flush();
      return true;
    } catch (FileNotFoundException e) {
      LogUtils.e("fileUtils","copy file not found:" + e);
    } catch (IOException e) {
      LogUtils.e("fileUtils","copy file IOException:" + e);
    } finally {
      if (null != inputStream) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (null != outputStream) {
        try {
          outputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return false;
  }

  public static boolean createDirs(String dir) {
    File destDir = new File(dir);
    if (destDir.exists()) {
      return true;
    }
    return destDir.mkdirs();
  }

  /**
   * Gzip压缩
   *
   * @param fileName
   * @param gzipFileName
   * @return
   */
  public static boolean packGZip(String fileName, String gzipFileName) {
    OutputStream outputStream = null;
    InputStream inputStream = null;
    try {
      File file = new File(fileName);
      outputStream = new FileOutputStream(gzipFileName);
      inputStream = new FileInputStream(file);
      GZIPOutputStream gzip = new GZIPOutputStream(outputStream);
      byte[] buffer = new byte[(int) file.length()];
      inputStream.read(buffer);
      gzip.write(buffer);
      gzip.close();
      return true;
    } catch (IOException e) {
      LogUtils.e("fileUtils","gzip error:" + e);
      return false;
    } finally {
      if (outputStream != null) {
        try {
          outputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (null != inputStream) {
        try {
          inputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }



  public static int getFileType(String path) {
    path = path.toLowerCase();
    if (path.endsWith(".doc") || path.endsWith(".docx") || path.endsWith(".xls") || path.endsWith(".xlsx")
            || path.endsWith(".ppt") || path.endsWith(".pptx")) {
      return TYPE_DOC;
    }else if (path.endsWith(".apk")) {
      return TYPE_APK;
    }else if (path.endsWith(".zip") || path.endsWith(".rar") || path.endsWith(".tar") || path.endsWith(".gz")) {
      return TYPE_ZIP;
    }else{
      return -1;
    }
  }


  /**通过文件名获取文件图标*/
  public static int getFileIconByPath(String path){
    path = path.toLowerCase();
//    int iconId = R.mipmap.unknow_file_icon;
    if (path.endsWith(".txt")){
//      iconId = R.mipmap.type_txt;
    }else if(path.endsWith(".doc") || path.endsWith(".docx")){
//      iconId = R.mipmap.type_doc;
    }else if(path.endsWith(".xls") || path.endsWith(".xlsx")){
//      iconId = R.mipmap.type_xls;
    }else if(path.endsWith(".ppt") || path.endsWith(".pptx")){
//      iconId = R.mipmap.type_ppt;
    }else if(path.endsWith(".xml")){
//      iconId = R.mipmap.type_xml;
    }else if(path.endsWith(".htm") || path.endsWith(".html")){
//      iconId = R.mipmap.type_html;
    }
//    return iconId;
    return 0;
  }

  /**是否是图片文件*/
  public static boolean isPicFile(String path){
    path = path.toLowerCase();
    if (path.endsWith(".jpg") || path.endsWith(".jpeg") || path.endsWith(".png")){
      return true;
    }
    return false;
  }


  /** 判断SD卡是否挂载 */
  public static boolean isSDCardAvailable() {
    if (Environment.MEDIA_MOUNTED.equals(Environment
            .getExternalStorageState())) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 从文件的全名得到文件的拓展名
   *
   * @param filename
   * @return
   */
  public static String getExtFromFilename(String filename) {
    int dotPosition = filename.lastIndexOf('.');
    if (dotPosition != -1) {
      return filename.substring(dotPosition + 1, filename.length());
    }
    return "";
  }
  /**
   * 读取文件的修改时间
   *
   * @param f
   * @return
   */
  public static String getModifiedTime(File f) {
    Calendar cal = Calendar.getInstance();
    long time = f.lastModified();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    cal.setTimeInMillis(time);
    // System.out.println("修改时间[2] " + formatter.format(cal.getTime()));
    // 输出：修改时间[2] 2009-08-17 10:32:38
    return formatter.format(cal.getTime());
  }


//  public interface FileCharSet{
//     String UTF_8 = "UTF-8";
//     String UTF_16LE = "UTF-16LE";
//     String UTF_16BE= "UTF-16BE";
//     String GBK = "GBK";
//  }
//
//  //获取文件的编码格式
//  public static String getCharset(String fileName) {
//    BufferedInputStream bis = null;
//    String charset = FileCharSet.GBK;
//    byte[] first3Bytes = new byte[3];
//    try {
//      boolean checked = false;
//      bis = new BufferedInputStream(new FileInputStream(fileName));
//      bis.mark(0);
//      int read = bis.read(first3Bytes, 0, 3);
//      if (read == -1)
//        return charset;
//      if (first3Bytes[0] == (byte) 0xEF
//              && first3Bytes[1] == (byte) 0xBB
//              && first3Bytes[2] == (byte) 0xBF) {
//        charset = FileCharSet.UTF_8;
//        checked = true;
//      }
//            /*
//             * 不支持 UTF16LE 和 UTF16BE
//            else if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
//                charset = Charset.UTF16LE;
//                checked = true;
//            } else if (first3Bytes[0] == (byte) 0xFE
//                    && first3Bytes[1] == (byte) 0xFF) {
//                charset = Charset.UTF16BE;
//                checked = true;
//            } else */
//
//      bis.mark(0);
//      if (!checked) {
//        while ((read = bis.read()) != -1) {
//          if (read >= 0xF0)
//            break;
//          if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
//            break;
//          if (0xC0 <= read && read <= 0xDF) {
//            read = bis.read();
//            if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
//              // (0x80 - 0xBF),也可能在GB编码内
//              continue;
//            else
//              break;
//          } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
//            read = bis.read();
//            if (0x80 <= read && read <= 0xBF) {
//              read = bis.read();
//              if (0x80 <= read && read <= 0xBF) {
//                charset = FileCharSet.UTF_8;
//                break;
//              } else
//                break;
//            } else
//              break;
//          }
//        }
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//    } finally {
//      if (bis!=null){
//        try {
//          bis.close();
//        } catch (IOException e) {
//          e.printStackTrace();
//        }
//      }
//    }
//    return charset;
//  }
}
