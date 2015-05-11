
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

/**
 *
 * @author Trung Pham
 */
public class A000_CreateClassHelper {

    private static final String TEMPLATE_FILE_LOCATION = "Template.txt";

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String name = null;
        while (true) {
            System.out.println("Please input a valid file name: ");
            name = scanner.next();
            if (isJavaClassName(name)) {
                break;
            }
        }
        File root = new File("");
        File file = new File(root.getAbsolutePath()+ "/src/" + name + ".java");
        if(file.exists()){
        	throw new IllegalArgumentException("This File exist!");
        }
        Scanner in = new Scanner(new FileReader(new File(TEMPLATE_FILE_LOCATION)));
        PrintWriter out = new PrintWriter(new FileOutputStream(file));
        while (in.hasNextLine()) {
            String line = in.nextLine();
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '$') {
                    out.print(name);
                } else {
                    out.print(line.charAt(i));
                }
            }
            out.println();
        }
        out.close();

    }
    private static final Set<String> javaKeywords = new HashSet<String>(Arrays.asList(
            "abstract", "assert", "boolean", "break", "byte",
            "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extends", "false", "final", "finally",
            "float", "for", "goto", "if", "implements",
            "import", "instanceof", "int", "interface", "long",
            "native", "new", "null", "package", "private",
            "protected", "public", "return", "short", "static",
            "strictfp", "super", "switch", "synchronized", "this",
            "throw", "throws", "transient", "true", "try",
            "void", "volatile", "while"));
    private static final Pattern JAVA_CLASS_NAME_PART_PATTERN =
            Pattern.compile("[A-Za-z_$]+[a-zA-Z0-9_$]*");

    public static boolean isJavaClassName(String text) {
        for (String part : text.split("\\.")) {
            if (javaKeywords.contains(part)
                    || !JAVA_CLASS_NAME_PART_PATTERN.matcher(part).matches()) {
                return false;
            }
        }
        return text.length() > 0;
    }
}
