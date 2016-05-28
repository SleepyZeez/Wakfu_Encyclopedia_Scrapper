package Wakfu;

/**
 * Program to extract item info for Armors
 * @author Raejhan
 */
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import javax.imageio.ImageIO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class ExtractArmor extends Extracter {
    public static void Create(String url, String folder) throws IOException {
        String html = url;
        Document doc = null;
        try{
            doc = Jsoup.connect(html).timeout(10*1000).get();
            File dir = new File("G:\\Downloads\\Wakfu\\"+folder);
            dir.mkdir();
            File dir2 = new File("G:\\Downloads\\Wakfu\\"+folder+" pics");
            dir2.mkdir();
            File space = new File("G:\\Downloads\\Wakfu\\"+folder, findTitle(doc)+".txt");
            PrintWriter writer = new PrintWriter(space, "UTF-8");
            writer.println("{{ItemWrap");
            writer.println("|name="+findTitle(doc));
            writer.println("|id="+findID(html));
            writer.println("|level="+findLevel(doc));
            writer.println("|rarity="+findRarity(doc));
            writer.println("|type="+findType(doc));
            writer.println("|description="+findDescrip(doc));
            writer.print("|equipbonus=");
            List<String> stats=findStats(doc);
            List<String> category = new ArrayList<>();
            for (String stat : stats) {
                writer.println(stat);
                if(addCategory(stat)!=null){
                    category.add(addCategory(stat));
                }
            }
            if(findSet(doc).length()>0)
                writer.println("|set="+findSet(doc));
            Element dropTable = dropCheck(doc);
            if(dropTable != null){
                writer.print("|dropby=");
                List<String> dropList=findDropper(dropTable);
                for (String dropper : dropList) {
                    writer.println(dropper);
                }
            }
            writer.println("}}");
            for(String text:category) {
                writer.println(text);
            }
            writer.close();
        } catch (org.jsoup.HttpStatusException e){
            System.out.println(url+"\n"+"does not exist");
        } catch (java.lang.NullPointerException w){
            
        }
        //Using the grabbed image create a png and save to folder
        File file = new File("G:\\Downloads\\Wakfu\\"+folder+" pics", findTitle(doc)+".png");
        try{
            ImageIO.write(findImage(doc),"png",file);
        } catch(IOException e){
            System.out.println("Write error for "+ file.getPath()+": "+e.getMessage());
        }
        /** for debugging:
        System.out.println(file.getPath());
        Scanner reader = new Scanner(space);
        while(reader.hasNext()){
            System.out.println(reader.nextLine());
        }*/
    }
    private static String findID(String site){
        //Scanner reader = new Scanner(site);
        int i1 = site.indexOf("/armor/")+7;
        int i2 = site.indexOf("-", i1);
        return site.substring(i1,i2);
    }
    private static List<String> findStats(Document theDoc){
        Elements eleList = theDoc.select(".ak-encyclo-detail-right .ak-list-element .ak-main .ak-main-content .ak-content .ak-title");
        List<String> stats = new ArrayList<>(eleList.size());
        for(Element stat : eleList) {
            stats.add("*"+stat.text());
        }
        return stats;
    }
    private static String findSet(Document theDoc){
        Elements eleList = theDoc.select(".ak-panel-title:contains(is part of the)");
        eleList = eleList.select("a");
        if(eleList.size()>0){
        Element ele = eleList.get(0);
        return ele.text();
        }else{return "";}
    }
    //returns item's pic
    private static BufferedImage findImage(Document theDoc) throws MalformedURLException{
        Elements eleList = theDoc.select(".ak-encyclo-detail-illu img");
        String text = eleList.first().attributes().toString();
        text = text.substring(6,text.indexOf(".png")+4);
        URL url = new URL(text);
        BufferedImage image= null;
        try{
            image = ImageIO.read(url);
        } catch (IOException e){ 
            System.out.println("cant find image");
        }
    return image;
    }
    //returns code for adding page to category
    private static String addCategory(String txt) throws FileNotFoundException, UnsupportedEncodingException{
        Scanner reader = new Scanner(txt);
        while(reader.hasNext()){
            String line = reader.nextLine();
            if(line.contains("HP"))
                return "[[Category:Max HP Equipment]]";
            if(line.contains("AP"))
                return "[[Category:AP Equipment]]";
            if(line.contains("MP"))
                return "[[Category:MP Equipment]]";
            if(line.contains("WP"))
                return "[[Category:WP Equipment]]";
            if(line.contains("damage for 1 random element"))
                return "[[Category:Damage for 1 Element Equipment]]";
            if(line.contains("damage for 2 random element"))
                return "[[Category:Damage for 2 Elements Equipment]]";
            if(line.contains("damage for 3 random element"))
                return "[[Category:Damage for 3 Elements Equipment]]";
            if(line.contains("Damage Water"))
                return "[[Category:Water Damage Equipment]]";
            if(line.contains("Damage Earth"))
                return "[[Category:Earth Damage Equipment]]";
            if(line.contains("Damage Air"))
                return "[[Category:Air Damage Equipment]]";
            if(line.contains("Damage Earth"))
                return "[[Category:Earth Damage Equipment]]";
            if(line.contains("Damage")&&line.indexOf("Damage")==line.length()-6&&line.length()<11)
                return "[[Category:All Damage Equipment]]";
            if(line.contains("Resist. to 1 random element"))
                return "[[Category:Resistance to 1 Element Equipment]]";
            if(line.contains("Resist. to 2 random element"))
                return "[[Category:Resistance to 2 Elements Equipment]]";
            if(line.contains("Resist. to 3 random element"))
                return "[[Category:Resistance to 3 Elements Equipment]]";
            if(line.contains("Resist.")&&line.indexOf("t.")==line.length()-2)
                return "[[Category:All Resistance Equipment]]";
            if(line.contains("Resist. Water"))
                return "[[Category:Water Resistance Equipment]]";
            if(line.contains("Resist. Earth"))
                return "[[Category:Earth Resistance Equipment]]";
            if(line.contains("Resist. Air"))
                return "[[Category:Air Resistance Equipment]]";
            if(line.contains("Resist. Fire"))
                return "[[Category:Fire Resistance Equipment]]";
            if(line.contains("Initiative"))
                return "[[Category:Initiative Equipment]]";
            if(line.contains("Range"))
                return "[[Category:Range Equipment]]";
            if(line.contains("Lock"))
                return "[[Category:Lock Equipment]]";
            if(line.contains("Dodge"))
                return "[[Category:Dodge Equipment]]";
            if(line.contains("Block"))
                return "[[Category:Block Equipment]]";
            if(line.contains("Critical Hits"))
                return "[[Category:Critical Hits Equipment]]";
            if(line.contains("Critical Hit Damage"))
                return "[[Category:Critical Hit Damage Equipment]]";
            if(line.contains("berserker damage"))
                return "[[Category:Berseker Damage Equipment]]";
            if(line.contains("Close Combat Damage"))
                return "[[Category:Close Combat Damage Equipment]]";
            if(line.contains("distance damage"))
                return "[[Category:Long Distance Damage Equipment]]";
            if(line.contains("single-target damage"))
                return "[[Category:Single Target Damage Equipment]]";
            if(line.contains("area damage"))
                return "[[Category:Area of Effect Damage Equipment]]";
            if(line.contains("Backstab Damage"))
                return "[[Category:Backstab Damage Equipment]]";
            if(line.contains("Resist. to attacks from behind"))
                return "[[Category:Backstab Resistance Equipment]]";
            if(line.contains("Heals"))
                return "[[Category:Heals Equipment]]";
            if(line.contains("Wisdom"))
                return "[[Category:Wisdom Equipment]]";
            if(line.contains("Prospecting"))
                return "[[Category:Prospecting Equipment]]";
            if(line.contains("Control"))
                return "[[Category:Control Equipment]]";
            if(line.contains("Kit Skill"))
                return "[[Category:Kit Skill Equipment]]";
        }
        return null;
    }
}
