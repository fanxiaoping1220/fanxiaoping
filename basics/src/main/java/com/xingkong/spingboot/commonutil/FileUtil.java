package com.xingkong.spingboot.commonutil;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @className: FileUtil
 * @description:
 * @author: 范小平
 * @date: 2019-04-22 17:57
 * @version: 1.0.0
 */
public class FileUtil {

    /**
     * 第一步： 把 支付宝生成的账单 下载到本地目录
     * @param path 支付宝资源url
     * @param filePath 生成的zip 包目录
     * @throws MalformedURLException
     */
    public static void downloadNet(String path, String filePath)
            throws MalformedURLException {
        // 下载网络文件
        int bytesum = 0;
        int byteread = 0;
        URL url = new URL(path);
        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(filePath);
            byte[] buffer = new byte[1204];
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                fs.write(buffer, 0, byteread);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压文件
     * @param zipPath 要解压的目标文件
     * @param descDir 指定解压目录
     * @return 解压结果：成功，失败
     */
    @SuppressWarnings("rawtypes")
    public static List<String> unzip(String zipPath, String descDir) {
        List<String> pathList = new ArrayList<>();
        File zipFile = new File(zipPath);
//        boolean flag = false;
        File pathFile = new File(descDir);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
        ZipFile zip;
        try {
            //防止中文目录，乱码
            zip = new ZipFile(zipFile, Charset.forName("gbk"));
            for(Enumeration entries = zip.entries(); entries.hasMoreElements();){
                ZipEntry entry = (ZipEntry)entries.nextElement();
                String zipEntryName = entry.getName();
                InputStream in = zip.getInputStream(entry);
                //指定解压后的文件夹+当前zip文件的名称
                String outPath = (descDir+zipEntryName).replace("/", File.separator);
                //判断路径是否存在,不存在则创建文件路径
                File file = new File(outPath.substring(0, outPath.lastIndexOf(File.separator)));
                if(!file.exists()){
                    file.mkdirs();
                }
                //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                if(new File(outPath).isDirectory()){
                    continue;
                }
                //保存文件路径信息（可利用md5.zip名称的唯一性，来判断是否已经解压）
                System.err.println("当前zip解压之后的路径为：" + outPath);
                pathList.add(outPath);
                OutputStream out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[2048];
                int len;
                while((len=in.read(buf1))>0){
                    out.write(buf1,0,len);
                }
                in.close();
                out.close();
            }
//            flag = true;
            //必须关闭，要不然这个zip文件一直被占用着，要删删不掉，改名也不可以，移动也不行，整多了，系统还崩了。
            zip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathList;
    }

    /**
     * 读取Excel 格式为 .xlsx .xls
     * @param path
     * @return
     * @throws InvalidFormatException
     * @throws IOException
     */
    public static List<String[]> readerExcel(String path) throws InvalidFormatException, IOException {
        FileInputStream file = new FileInputStream(path);
        Workbook workbook;
        if(path.contains(".xlsx")){
            OPCPackage opcPackage = OPCPackage.open(file);
            workbook = new XSSFWorkbook(opcPackage);
        }else {
            workbook = new HSSFWorkbook(file);
        }
        List<String[]> list = new ArrayList<>();
        Sheet sheetAt = workbook.getSheetAt(0);
        for(int i = sheetAt.getFirstRowNum()+1;i<= sheetAt.getLastRowNum(); i++){
            Row row = sheetAt.getRow(i);
            String[] cells = new String[row.getLastCellNum()];
            for(int j = row.getFirstCellNum();j < row.getLastCellNum(); j++){
                if(row.getCell(j) == null){
                    cells[j] = "";
                    continue;
                }
                if(CellType.STRING.equals(row.getCell(j).getCellType())){
                    cells[j] = row.getCell(j).getStringCellValue();
                    continue;
                }
                if(CellType.NUMERIC.equals(row.getCell(j).getCellType())){
                    //对时间进行特殊处理
                    double numericCellValue = row.getCell(j).getNumericCellValue();
                    if(String.valueOf(numericCellValue).contains(".")){
                        String[] split = String.valueOf(numericCellValue).split("\\.");
                        if(split[1].length() > 2 && !split[1].contains("E")){
                            cells[j] = HSSFDateUtil.getJavaDate(numericCellValue).toString();
                        }else {
                            cells[j] = String.valueOf(numericCellValue);
                        }
                    }else {
                        cells[j] = String.valueOf(numericCellValue);
                    }
                }
            }
            list.add(cells);
        }
        workbook.close();
        return list;
    }
}