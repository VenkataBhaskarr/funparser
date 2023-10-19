// Main code
import java.util.*;
import java.util.regex.*;
import java.io.*;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // GET INPUT FROM USER
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the variable names of your choice: ");
        String[] desired = sc.nextLine().split(",");
        // Ask the user for the file path
        System.out.print("Enter your target file path : ");
        String FILE_NAME = sc.nextLine();
        String finalCode = getFinalCode(FILE_NAME, desired);
        // instead of printing this code write the hole code in the another file
        try{
          FileWriter writer = new FileWriter("modified" + FILE_NAME);
          writer.write(finalCode);
          writer.close();
        }catch(IOException e){
          System.out.print(e.getMessage());
        } 
    }

    public static String getFinalCode(String FILE_NAME, String[] desired) throws FileNotFoundException {
        String original = getTextFromFile(FILE_NAME);
        // System.out.print(original);
        List<String> variables = extractVariableNames(original);
       // System.out.println(variables);
        variables = cleanVariableNames(variables);
        Map<String, String> bindings = new HashMap<>();
        int iterator = 0;
        for (String desi : desired) {
            if (iterator >= variables.size()) {
                break;
            }
            bindings.put(variables.get(iterator++), desi);
        }
        String finalCode = getBeautifyCode(replaceVariableNames(original, bindings));
        return finalCode;
    }

    public static List<String> cleanVariableNames(List<String> names) {
        for (int i = 0; i < names.size(); i++) {
            names.set(i, cleanText(names.get(i)));
        }
        return names;
    }

    public static String replaceVariableNames(String input, Map<String, String> replacements) {
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            String variableName = entry.getKey();
            String replacement = entry.getValue();

            // Use regular expression to match standalone variable names
            String regex = "\\b" + Pattern.quote(variableName) + "\\b";

            // Create a pattern with the regex
            Pattern pattern = Pattern.compile(regex);

            // Use Matcher to find and replace
            Matcher matcher = pattern.matcher(input);
            input = matcher.replaceAll(replacement);
        }

        return input;
    }

    public static String getTextFromFile(String FILE_NAME) {
        StringBuilder code = new StringBuilder();
        try {
            File readcode = new File(FILE_NAME);
            if (readcode.exists()) {
                Scanner reader = new Scanner(readcode);
                while (reader.hasNextLine()) {
                    String s = reader.nextLine();
                    s = s.replaceAll("^\\s+", "");
                    if (!s.startsWith("//")) {
                        code.append(s);
                    }
                }
            } else {
                System.out.println("File not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String modifiedString = code.toString().replaceAll("\\s+", " ");
        code.setLength(0); // Clear the StringBuilder
        code.append(modifiedString);
        return code.toString();
    }

    public static List<String> extractVariableNames(String input) {
        List<String> variables = new ArrayList<>();
        String[] firstBreakDown = input.split(";");
        List<String> finalBreakDown = new ArrayList<>();
        for(String s : firstBreakDown){
           String[] secondBreakDown = s.split("\\{");
           for(String sub : secondBreakDown){
             finalBreakDown.add(sub);
           }
        }
        for(String sub : finalBreakDown){
           String cleanSub = cleanText(sub);
           if(cleanSub.startsWith("int")){
             // two cases 
             // one is int a = 20; / int a=20;
             // other is int a = 20,b=10;
             // break at comma
             //  System.out.println(cleanSub);
             String mod = cleanText(Objects.requireNonNull(splitString(cleanSub, "int")));
             // System.out.println(mod);
             String[] vars = mod.split(",");
             for(String var : vars){
               String[] things = var.split("=");
               variables.add(cleanText(things[0]));
             } 
           }
            if(cleanSub.startsWith("float")){
             // two cases 
             // one is int a = 20; / int a=20;
             // other is int a = 20,b=10;
             // break at comma
              // System.out.println(cleanSub);
             String mod = cleanText(Objects.requireNonNull(splitString(cleanSub, "float")));
            // System.out.println(mod);
             String[] vars = mod.split(",");
             for(String var : vars){
               String[] things = var.split("=");
               variables.add(cleanText(things[0]));
             } 
           }
            if(cleanSub.startsWith("String")){
             // two cases 
             // one is int a = 20; / int a=20;
             // other is int a = 20,b=10;
             // break at comma
            //   System.out.println(cleanSub);
             String mod = cleanText(Objects.requireNonNull(splitString(cleanSub, "String")));
            //  System.out.println(mod);
             String[] vars = mod.split(",");
             for(String var : vars){
               String[] things = var.split("=");
               variables.add(cleanText(things[0]));
             } 
           }

            if(cleanSub.startsWith("double")){
             // two cases 
             // one is int a = 20; / int a=20;
             // other is int a = 20,b=10;
             // break at comma
            //   System.out.println(cleanSub);
             String mod = cleanText(Objects.requireNonNull(splitString(cleanSub, "double")));
             // System.out.println(mod);
             String[] vars = mod.split(",");
             for(String var : vars){
               String[] things = var.split("=");
               variables.add(cleanText(things[0]));
             } 
           }

            if(cleanSub.startsWith("boolean")){
             // two cases 
             // one is int a = 20; / int a=20;
             // other is int a = 20,b=10;
             // break at comma
             //  System.out.println(cleanSub);
             String mod = cleanText(Objects.requireNonNull(splitString(cleanSub, "boolean")));
             // System.out.println(mod);
             String[] vars = mod.split(",");
             for(String var : vars){
               String[] things = var.split("=");
               variables.add(cleanText(things[0]));
             } 
           }
            
            if(cleanSub.startsWith("char")){
             // two cases 
             // one is int a = 20; / int a=20;
             // other is int a = 20,b=10;
             // break at comma
             //  System.out.println(cleanSub);
             String mod = cleanText(Objects.requireNonNull(splitString(cleanSub, "char")));
             // System.out.println(mod);
             String[] vars = mod.split(",");
             for(String var : vars){
               String[] things = var.split("=");
               variables.add(cleanText(things[0]));
             } 
           }

           // for datatype support add code here

        }
        return variables;
    }
    public static String splitString(String input, String wordToSplit) {
        int index = input.indexOf(wordToSplit)+wordToSplit.length();
        if (index != -1) {
            return input.substring(index);
        } else {
            return null; // Return null if the word is not found in the string
        }
    }
    public static String getBeautifyCode(String code) {
        StringBuilder beautifiedCode = new StringBuilder();
        int indentationLevel = 0;
        boolean inString = false;

        for (char c : code.toCharArray()) {
            if (c == '{' && !inString) {
                beautifiedCode.append("{\n");
                indentationLevel++;
                appendIndentation(beautifiedCode, indentationLevel);
            } else if (c == '}' && !inString) {
                indentationLevel--;
                appendIndentation(beautifiedCode, indentationLevel - 1);
                beautifiedCode.append("}\n");
                appendIndentation(beautifiedCode, indentationLevel - 1);
            } else if (c == ';' && !inString) {
                beautifiedCode.append(";\n");
                appendIndentation(beautifiedCode, indentationLevel);
            } else if (c == '"') {
                inString = !inString;
                beautifiedCode.append(c);
            } else {
                beautifiedCode.append(c);
            }
        }
        return beautifiedCode.toString();
    }
    private static void appendIndentation(StringBuilder code, int level) {
        for (int i = 0; i < level; i++) {
            code.append(" "); // You can adjust the number of spaces for indentation
        }
    }
    public static String cleanText(String input) {
        return input.trim();
    }

}
