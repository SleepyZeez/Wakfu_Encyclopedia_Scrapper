package Wakfu;

/**
 *
 * @author Raejhan
 */
import java.io.*;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

public class ExtractWeapon extends Extracter {
    public static void Create(String url) throws IOException {
        String html = url;
        Document doc = Jsoup.connect(html).get();
        File space = new File("G:\\Downloads\\Wakfu\\Weapon", findTitle(doc)+".txt");
        PrintWriter writer = new PrintWriter(space, "UTF-8"); 
        //writer.println("{{ItemWrap");
        //writer.println("|name="+findTitle(doc));
        //writer.println("|id="+findID(html));
        //writer.println("|level="+findLevel(doc));
        //writer.println("|rarity="+findRarity(doc));
        //writer.println("|type="+findType(doc));
        /**writer.println("|description="+findDescrip(doc));
        writer.print("|equipbonus=");
        List<String> stats= findStats(doc);
        for(int i=0;i<stats.size();i++){
            writer.println(stats.get(i));
        }
        writer.println("|set="+findSet(doc));
        Element dropTable = dropCheck(doc);
        if(dropTable != null){
            writer.print("|dropby=");
            List<String> dropList=findDropper(dropTable);
            for (String dropper : dropList) {
                writer.println(dropper);
            }
        }
        writer.println("}}");*/
        writer.close();
        Scanner reader = new Scanner(space);
        while(reader.hasNext()){
            System.out.println(reader.nextLine());
        }
    }
    public static String findID(String site){
        Scanner reader = new Scanner(site);
        int i1 = site.indexOf("/weapons/")+9;
        int i2 = site.indexOf("-", i1);
        return site.substring(i1,i2);
    }
}
