package Wakfu;

/**
 *
 * @author Raejhan
 */
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class Finder {
    public static int findArmor(String name, String url) throws IOException{
        String html = url;
        Document doc = Jsoup.connect(html).timeout(30*1000).get();
        File space = new File("G:\\Downloads\\Wakfu\\", name+" urls.txt");
        PrintWriter writer = new PrintWriter(space, "UTF-8");
        Elements eleList = doc.select("div.ak-mosaic-item-name a");
        for(Element ele:eleList){
            //take the herf text from the element and add the wakfu starting url
           String text = ele.attributes().toString();
           text = "http://www.wakfu.com"+text.substring(7, text.length()-1);
           //if the link is missing the "armor" in the url, add it
           if(text.substring(43,45).equals("//"))
               text = text.substring(0,44)+"armor"+text.substring(44,text.length());
           writer.println(text);
        }
        writer.close();
        System.out.println(eleList.size()+" Items Found");
        return eleList.size();
    }
}
