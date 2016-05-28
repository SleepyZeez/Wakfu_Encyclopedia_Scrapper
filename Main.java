package Wakfu;


import java.io.*;
import java.util.*;

/**
 *
 * @author Raejhan
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String file="", site="";
        Integer type=1;
        Scanner keyboard = new Scanner(System.in);
        /**System.out.print("Armor(1), Weapon(2), or Monster(3)?> ");
        type=keyboard.nextInt();*/
        System.out.print("Enter url> ");
        site=keyboard.next();
        System.out.print("Enter type of item> ");
        file=keyboard.next();
        switch (type) {
            case 1: int size = Finder.findArmor(file, site);
                    int count = 1;
                    //file = "test";
                    File list = new File("G:\\Downloads\\Wakfu\\"+file+" urls.txt");
                    Scanner reader = new Scanner(list);
                    //create txt files of item info and extract images
                    while(reader.hasNext()){
                        ExtractArmor.Create(reader.nextLine(), file);
                        System.out.println(Integer.toString(count)+"/"+Integer.toString(size)+" completed");
                        count++;
                    }
                    break;
            case 2: System.exit(0);
                    break;
            case 3: System.exit(0);
                    break;
        }
    }
}
