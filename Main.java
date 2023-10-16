// Main code
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class Main{
  public static void main(String[] args){
    String FILE_NAME = "Test.java";
    List<String> variables = getVariableNames(getTextFromFile(FILE_NAME));
    for(String var : variables){
      System.out.println(cleanText(var));
    }
  }
  public static String getTextFromFile(String filename){
    StringBuilder code = new StringBuilder();
    try{
    File readcode = new File(filename);
    Scanner reader = new Scanner(readcode);
    while(reader.hasNextLine()){
      String s = reader.nextLine();
      s = s.replaceAll("^\\s+" ,"");
      //System.out.println(s);
      if(s.startsWith("//")){
        continue;
      }
      code.append(s);
    }
    }catch(Exception e){
      System.out.print(e);
    }
    String modifiedString = code.toString().replaceAll("\\s+", " ");
    code.setLength(0); // Clear the StringBuilder
    code.append(modifiedString);
    return code.toString();
  }
  public static List<String> getVariableNames(String input){
    String[] statements = extractText(input.toString()).split(";");

    List<String> variables = new ArrayList<>();
    for(String statement : statements){
      // System.out.println(statement);
      statement = extractSubstringFromKeywords(cleanText(statement));
     // System.out.println(statement);
      // some modification should be done here
      
      if(statement.startsWith("int") || statement.startsWith("String") || statement.startsWith("float")){
         String[] blocks = statement.split(",");
         String[] firstThing = blocks[0].split(" ");
        // System.out.print(Arrays.toString(firstThing));
         variables.add(cleanText(firstThing[1]));
         for(int i=1;i<blocks.length;i++){
           String block = cleanText(blocks[i]);
           String[] names = block.split("=");
           variables.add(names[0]);
         }
      }
    }
    return variables;
  }
  public static String extractSubstringFromKeywords(String input) {
        List<String> keywords = new ArrayList<>();
        keywords.add("String");
        keywords.add("int");
        keywords.add("float");
        int startIndex = -1;

        for (String keyword : keywords) {
            int keywordIndex = input.indexOf(keyword);
            if (keywordIndex >= 0 && (startIndex == -1 || keywordIndex < startIndex)) {
                startIndex = keywordIndex;
            }
        }

        if (startIndex >= 0) {
            return input.substring(startIndex);
        }

        return ""; // No keywords found in the input string
  }
  public static String cleanText(String input){
    return input.trim();
  }
  public static String extractText(String input){
     Pattern pattern = Pattern.compile("\\{([^}]+)\\}");

        Matcher matcher = pattern.matcher(input);

        // Find and print all matches.
        StringBuilder extractedText = new StringBuilder();
        while (matcher.find()) {
            extractedText.append(matcher.group(1));
            // System.out.println("Extracted text: " + extractedText);
        }
        return extractedText.toString();
  } 
}
