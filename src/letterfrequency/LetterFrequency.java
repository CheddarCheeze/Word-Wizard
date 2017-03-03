package letterfrequency;

import MapUtil.MapUtil;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author macandcheese
 */
public class LetterFrequency {

    public static void main(String[] args) {
        //letterStats();
        
        // Wait 20 seconds for dictionary to be loaded
        // Message will display when ready
        fileStats("wordlist.txt");
    }
    
    // Create hashmap and fill with count of all ascii characters
    public static void clearAndCount(HashMap<Character,Integer> map, String word) {
        
        // Load all alphabetic characters with a start of 0
        for(int i = 32; i < 127; i++) {
            map.put((char)i, 0);
        }
        
        // For each letter, increment for that letter
        for(int i = 0; i < word.length(); i++) {
            map.put(Character.toUpperCase(word.charAt(i)), map.get(Character.toUpperCase(word.charAt(i))) + 1) ;
        }
        
        map = MapUtil.sortByValue(map);
    }
    
    // Load dictionary into memory and create hash map for each word
    public static void fileStats(String filename) {
        List<HashMap<Character,Integer>> map = new ArrayList<HashMap<Character,Integer>>();
        List<String> words = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
               // process each word
               //System.out.println(line);
               HashMap<Character,Integer> word = new HashMap<Character,Integer>();
               clearAndCount(word,line);
               map.add(word);
               words.add(line);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        wordUnscrambler(map,words);
    }
    
    public static void wordUnscrambler(List<HashMap<Character,Integer>> dict, List<String> words) {
        System.out.print("Word: ");
        Scanner s = new Scanner(System.in);
        
        while(s.hasNext()) {
            // Frequency of letters for unscrambled/scrambled word
            HashMap<Character, Integer> countMap = new HashMap<>();

            // Fill hashmap with count of ascii
            clearAndCount(countMap,s.next());

            System.out.println("Results: ");
            // Loop through dictionary and print word once word is found
            for(int i = 0; i < dict.size(); i++) {
                // process each word and check if it contains all the same symbols
                if(dict.get(i).entrySet().equals(countMap.entrySet())) {
                    System.out.println(words.get(i));
                }
            }
            
            System.out.print("Word: ");
        }
    }
    
    public static void letterStats() {
        // Frequency of letters
        HashMap<Character, Integer> countMap = new HashMap<>();
        
        // Checks for patterns of 2 and 3 - will only print if it finds 2 ore more occurances
        HashMap<String, Integer> doublePattern = new HashMap<>();
        HashMap<String, Integer> triplePattern = new HashMap<>();
        String temp = "";
        
        Scanner s = new Scanner(System.in);
        String ciphertext = s.next();
        
        // Load all alphabetic characters with a start of 0
        for(char ch = 'A'; ch <= 'Z'; ++ch) {
            countMap.put(ch, 0);
        }
        
        // For each letter, increment for that letter
        for(int i = 0; i < ciphertext.length(); i++) {
            countMap.put(Character.toUpperCase(ciphertext.charAt(i)), countMap.get(Character.toUpperCase(ciphertext.charAt(i))) + 1) ;
        }
        
        // For each adjacent characters, keep track and increment any repeating patterns -- doublePattern
        for(int i = 0; i < ciphertext.length()-1; i++) {
            temp = String.valueOf(Character.toUpperCase(ciphertext.charAt(i))) + String.valueOf(Character.toUpperCase(ciphertext.charAt(i+1)));
            if(doublePattern.containsKey(temp)) {
                doublePattern.put(temp, doublePattern.get(temp) + 1) ;
            }
            else {
                doublePattern.put(temp, 1) ;
            }
        }
        
        // For each adjacent characters, keep track and increment any repeating patterns -- triplePattern
        for(int i = 1; i < ciphertext.length()-1; i++) {
            temp = String.valueOf(Character.toUpperCase(ciphertext.charAt(i-1))) + String.valueOf(Character.toUpperCase(ciphertext.charAt(i))) + String.valueOf(Character.toUpperCase(ciphertext.charAt(i+1)));
            if(triplePattern.containsKey(temp)) {
                triplePattern.put(temp, triplePattern.get(temp) + 1) ;
            }
            else {
                triplePattern.put(temp, 1) ;
            }
        }
        
//        for (Character key : countMap.keySet()) {
//            System.out.println(key + " " + countMap.get(key));
//        }

        countMap = MapUtil.sortByValue(countMap);

        // Print single character frequency
        for (HashMap.Entry<Character, Integer> entry : countMap.entrySet()) {
            String key = entry.getKey().toString();
            Integer value = entry.getValue();
            System.out.println("key, " + key + " value " + value);
        }
        
        doublePattern = MapUtil.sortByValue(doublePattern);
        System.out.println("\n\nDouble Patterns:\n");
        
        // Print double patterns if they occurred more than once
        for (HashMap.Entry<String, Integer> entry : doublePattern.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if(value > 1) {
                System.out.println("key, " + key + " value " + value);
            }
            else if(key.charAt(0) == key.charAt(1)) {
                System.out.println("key, " + key + " value " + value +" --double");
            }
        }
        
        triplePattern = MapUtil.sortByValue(triplePattern);
        System.out.println("\n\nTriple Patterns:\n");
        
        // Print double patterns if they occurred more than once
        for (HashMap.Entry<String, Integer> entry : triplePattern.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if(value > 1) {
                System.out.println("key, " + key + " value " + value);
            }
        }
    }
    
}
