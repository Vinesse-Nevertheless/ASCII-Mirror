/*
Alternative Streams heavy version of the project.
*/

package asciimirror;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public class AltStreamsVariant {

    char[][] opposites = {
        {'<', '>'},
        {'>', '<'},
        {'[', ']'},
        {']', '['},
        {'{', '}'},
        {'}', '{'},
        {'(', ')'},
        {')', '('},
        {'/', '\\'},
        {'\\', '/'}
    };
    public static void main(String[] args) {
        new AltStreamsVariant().requestFilePath();
    }

    private void requestFilePath() {
        Scanner in = new Scanner(System.in);

        System.out.println("Input the file path:");
        String filePath = in.nextLine();

        if (!filePath.endsWith(".txt")) {
            printError();
        } else {
            searchFile(filePath);
        }
        in.close();
    }

    private void searchFile(String filePath) {
        Path userFileName = Path.of(filePath).getFileName();
        //points to current file directory automatically
        Path dirPath = Path.of((System.getProperty("user.dir")));

        try (Stream<Path> walker = Files.walk(dirPath)) {
            walker.filter(f -> f.getFileName().equals(userFileName)).findFirst()
                    .ifPresentOrElse(this::printMirror, this::printError);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printError(){
        System.out.println("File not found!");
    }

    private void printMirror(Path filePath) {
        List<String> buffList;

        int maxLen;

        List<String> mirror;
        try {
            //read original
            buffList = Files.readAllLines(filePath);

            //get length of longest String in image
            maxLen = buffList.stream()
                     .max(Comparator.comparingInt(String::length))
                    .orElseThrow().length();

            //create mirror by reversing lines and reversing necessary chars
            mirror = new ArrayList<>(buffList).stream()
                    .map(x -> new StringBuilder(x)
                            .reverse().toString())
                    .map(this::reverseChar)
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //print original and mirror
        for (int i = 0; i < buffList.size(); i++) {
            String spaces = " ".repeat( maxLen - buffList.get(i).length());
            System.out.println(buffList.get(i) + spaces +  " | " + spaces + mirror.get(i) );
        }
    }

    String reverseChar(String revLine) {
        StringBuilder mirLine = new StringBuilder();
        for (int i = 0; i < revLine.length(); i++) {
            char c = getLogicalOpposite(revLine.charAt(i));
            mirLine.append(c);
        }
        return mirLine.toString();
    }

    char getLogicalOpposite(char c) {
        for (int i = 0; i < opposites.length; i++) {
            if (opposites[i][0] == c){
                return opposites[i][1];
            }
        }
        return c;
    }
}
