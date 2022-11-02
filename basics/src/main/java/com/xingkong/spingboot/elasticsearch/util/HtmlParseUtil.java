package com.xingkong.spingboot.elasticsearch.util;

import com.xingkong.spingboot.elasticsearch.entity.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * * @className: HtmlParseUtil
 * * @description: 爬取京东的网页
 * * @author: fan xiaoping
 * * @date: 2022/11/1 0001 16:52
 **/
@Component
public class HtmlParseUtil {

    public static void main(String[] args) throws IOException {
        new HtmlParseUtil().parseJD("Java").stream().forEach(System.out :: println);
    }

    public List<Content> parseJD(String keywords) throws IOException {
        String url = "https://search.jd.com/Search?keyword="+keywords;
        //解析网页 (Jsoup返回的document就是浏览器的document对象)
        Document document = Jsoup.parse(new URL(url), 300000);
        //所有你在js中使用的方法这里都可以使用
        Element element = document.getElementById("J_goodsList");
        System.out.println(element.html());
        //获取所有的li元素
        Elements elements = element.getElementsByTag("li");
        List<Content> goodsList = new ArrayList<>();
        //获取元素中的内容,这里的el就是一个li标签
        for (Element el : elements) {
            String price = el.getElementsByClass("p-price").eq(0).text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            String img = el.getElementsByTag("img").eq(0).attr("data-lazy-img");
            Content content = new Content();
            content.setPrice(price);
            content.setImage(img.substring(2));
            content.setTitle(title);
            goodsList.add(content);
        }
        return goodsList;
    }

}
