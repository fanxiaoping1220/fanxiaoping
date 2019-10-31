package com.xingkong.spingboot.controller.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.xingkong.spingboot.service.excel.entity.ComplexDateDO;
import com.xingkong.spingboot.service.excel.entity.ConverterDataDO;
import com.xingkong.spingboot.service.excel.entity.DemoDateDO;
import com.xingkong.spingboot.service.excel.entity.ImageDateDO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: ExcelController
 * @description: excel写入
 * @author: 范小平
 * @date: 2019-10-31 10:48
 * @version: 1.0.0
 */
@RestController
@RequestMapping(value = "/excel")
public class ExcelController {


    /**
     * 写法一 参照{@link DemoDateDO}
     * 导出demoExcel
     */
    @PostMapping(value = "/demoExportExcel")
    void demoExportExcel(HttpServletResponse response) throws IOException {
        setResponse(response);
        EasyExcel.write(response.getOutputStream(), DemoDateDO.class).sheet().doWrite(doList());
    }

    /**
     * 写法二
     * @param response
     * @throws IOException
     */
    @PostMapping(value = "/demoExportExcelTwo")
    void  demoExportExcelTwo(HttpServletResponse response) throws IOException {
        setResponse(response);
        ExcelWriter build = EasyExcel.write(response.getOutputStream(), DemoDateDO.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        build.write(doList(),writeSheet);
        //千万别忘记finish 会帮忙关闭流
        build.finish();
    }

    /**
     * 复杂头导出 参照 {@link ComplexDateDO}
     * @param response
     * @throws IOException
     */
    @PostMapping(value = "/complexHeader")
    void complexHeader(HttpServletResponse response)throws IOException{
        setResponse(response);
        List<DemoDateDO> demoDateList = doList();
        List<ComplexDateDO> list = new ArrayList<>();
        demoDateList.forEach(demoDateDO -> {
            ComplexDateDO complexDateDO = new ComplexDateDO();
            BeanUtils.copyProperties(demoDateDO,complexDateDO);
            list.add(complexDateDO);
        });
        EasyExcel.write(response.getOutputStream(), DemoDateDO.class).sheet().doWrite(list);
    }

    /**
     * 重复多次写入 参照{@link DemoDateDO}
     * 方法一 写到同一个sheet
     * @param response
     * @throws IOException
     */
    @PostMapping(value = "/repeatedWriteOne")
    void repeatedWriteOne(HttpServletResponse response)throws IOException{
        setResponse(response);
        //指定写用哪个class去读
        ExcelWriter write = EasyExcel.write(response.getOutputStream(), DemoDateDO.class).build();
        //如果同一个sheet只要创建一次
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        //去调用写入，这里我调用了5次，实际使用时根据数据库分页的总也数来
        for (int i = 0; i < 5 ; i++) {
            //分页去数据库查询数据，这里可以去数据库查询每一页的数据
            write.write(doList(),writeSheet);
        }
        //千万别忘记finish 会帮忙关闭流
        write.finish();
    }

    /**
     * 重复多次写入 参照{@link DemoDateDO}
     * 方法二 如果写到不同的sheet 同一个对象
     * @param response
     * @throws IOException
     */
    @PostMapping(value = "/repeatedWriteTwo")
    void repeatedWriteTwo(HttpServletResponse response)throws IOException{
        setResponse(response);
        //指定class
        ExcelWriter write = EasyExcel.write(response.getOutputStream(), DemoDateDO.class).build();
        //去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
        for (int i = 0; i < 5 ; i++) {
            //每次都要创建writeSheet 这里注意必须指定sheetNo
            WriteSheet writeSheet = EasyExcel.writerSheet(i).build();
            //分页去数据库查询数据 这里可以去数据库查询每一页的数据
            write.write(doList(),writeSheet);
        }
        write.finish();
    }

    /**
     * 重复多次写入 参照{@link DemoDateDO}
     * 方法三 写到不同的sheet,不同的对象
     * @param response
     * @throws IOException
     */
    @PostMapping(value = "/repeatedWriteThree")
    void repeatedWriteThree(HttpServletResponse response)throws IOException{
        setResponse(response);
        //不指定class
        ExcelWriter writer = EasyExcel.write(response.getOutputStream()).build();
        // 去调用写入,这里我调用了五次，实际使用时根据数据库分页的总的页数来。这里最终会写到5个sheet里面
        for (int i = 0; i < 5; i++) {
            // 每次都要创建writeSheet 这里注意必须指定sheetNo。这里注意DemoData.class 可以每次都变，我这里为了方便 所以用的同一个class 实际上可以一直变
            WriteSheet writeSheet = EasyExcel.writerSheet(i).head(DemoDateDO.class).build();
            // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
            writer.write(doList(),writeSheet);
        }
        // 千万别忘记finish 会帮忙关闭流
        writer.finish();
    }

    /**
     * 自定格式转换
     * 参照 {@link ConverterDataDO}
     * 配合使用注解{@link DateTimeFormat},{@link NumberFormat}
     * 格式转换
     * LocalTime LocalDate LocalDateTime 需要重写{@link Converter}
     * Date 可以直接用 {@link com.alibaba.excel.annotation.format.DateTimeFormat}
     * @param response
     */
    @PostMapping(value = "/converterWrite")
    void converterWrite(HttpServletResponse response) throws IOException {
        setResponse(response);
        List<DemoDateDO> demoDateList = doList();
        List<ConverterDataDO> list = new ArrayList<>();
        demoDateList.forEach(demoDateDO -> {
            ConverterDataDO converterDataDO = new ConverterDataDO();
            converterDataDO.setString(demoDateDO.getTitle());
            converterDataDO.setDate(LocalTime.now());
            converterDataDO.setDoubleDate(demoDateDO.getDoubleDate());
            list.add(converterDataDO);
        });
        EasyExcel.write(response.getOutputStream(), ConverterDataDO.class).sheet().doWrite(list);
    }

    /**
     * 图片导出
     * @param response
     * @throws IOException
     */
    @PostMapping(value = "/imageWrite")
    void imageWrite(HttpServletResponse response)throws IOException{
        setResponse(response);
        List<ImageDateDO> list = new ArrayList<>();
        ImageDateDO imageDateDO = new ImageDateDO();
        list.add(imageDateDO);
        String imagepath = (new File("")).getAbsolutePath()+File.separator+"image"+File.separator+"img.jpg";
        //放入四种类型的图片,实际使用只要选一种即可
        //文件
        imageDateDO.setFile(new File(imagepath));
        //inPutStream
        imageDateDO.setInputStream(new FileInputStream(new File(imagepath)));
        //地址
        imageDateDO.setString(imagepath);
        //byte
        imageDateDO.setByteArray(FileUtils.readFileToByteArray(new File(imagepath)));
        EasyExcel.write(response.getOutputStream(),ImageDateDO.class).sheet().doWrite(list);
    }

    private void setResponse(HttpServletResponse response) throws UnsupportedEncodingException {
        String path = "demo" + ExcelTypeEnum.XLSX.getValue();
        String name = URLEncoder.encode(path, "UTF-8");
        //设置请求头
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + name);
        response.setHeader("fileName", name);
        response.setHeader("Access-Control-Expose-Headers", "fileName,Content-disposition");
    }

    /**
     * 模拟生成数据
     * @return
     */
    private List<DemoDateDO> doList(){
        List<DemoDateDO> list = new ArrayList<>();
        for(int i = 0 ; i <= 10 ; i++){
            DemoDateDO demo = new DemoDateDO();
            demo.setDate(LocalDate.now().plusDays(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            demo.setDoubleDate(15.26*i);
            demo.setTitle("字符串标题:"+i);
            list.add(demo);
        }
        return list;
    }

}