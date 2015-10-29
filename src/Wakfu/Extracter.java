package Wakfu;

/*
 * Parent Program to extract item info from Wakfu official website encyclopedia
 * @author Raejhan
 */

import java.io.*;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class Extracter {
    //Returns the name of the item/monster on the page
    public static String findTitle(Document theDoc){
        Elements eleList = theDoc.select(".ak-title-container h1");
        return eleList.text();
    }
    //Returns the Item ID of the page
    public static String findLevel(Document theDoc) {
        Elements eleList = theDoc.select(".ak-encyclo-detail-level");
        String text = eleList.text();
        Scanner reader = new Scanner(text);
        reader.next();
        reader.next();
        return reader.next();
    }
    public static String findRarity(Document theDoc){
        Elements eleList = theDoc.select(".ak-object-rarity span");
        return eleList.text();
    }
    public static String findType(Document theDoc){
        Elements eleList = theDoc.select(".ak-encyclo-detail-type span");
        return eleList.text();
    }
    public static String findDescrip(Document theDoc){
        Elements eleList = theDoc.select(".ak-encyclo-detail-right .ak-panel > .ak-panel-content");
        Element ele = eleList.first();
        return ele.text();
    }
    //Returns ArrayList of strings of the Monsters that drop the item
    public static List<String> findDropper(Element table){
        Elements dropNames = table.select(".ak-main-content .ak-linker");
        List<String> dropList = new ArrayList<>(dropNames.size());
        for (Element ele:dropNames){
            if(ele.text().length()>0){
                dropList.add("*[["+ele.text()+"]]");
            }
        }
        return dropList;
    }
    //Returns the div.ak-panel with the drop table
    public static Element dropCheck(Document theDoc){
        Elements eleList = theDoc.select(".ak-panel:has(.ak-panel-title:contains(Dropped))");
        return eleList.first();
    }
}
