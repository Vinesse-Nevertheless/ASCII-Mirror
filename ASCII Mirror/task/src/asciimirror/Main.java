package asciimirror;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public class Main {

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
        new Main().requestFilePath();
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
                    .ifPresentOrElse(this::printFile, this::printError);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printError(){
        System.out.println("File not found!");
    }


    private void printFile(Path filePath) {

        List<String> originalImage = getOriginalImage(filePath);
        int maxLenImageStr =  getLongestString(originalImage);
        List<String> mirrorImage = getMirrorImage(originalImage);

        for (int i = 0; i < originalImage.size(); i++) {
            String spaces = " ".repeat( maxLenImageStr - originalImage.get(i).length());
            System.out.println(originalImage.get(i) + spaces +  " | "
                    + spaces + mirrorImage.get(i));
        }
    }

    List<String> getOriginalImage(Path filePath){
        List<String> buffList;

        try {
            buffList = Files.readAllLines(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return buffList;
    }

    int getLongestString(List<String> buffList){
       return buffList.stream().max(Comparator.comparingInt(String::length))
                .orElseThrow().length();
    }

    List<String> getMirrorImage(List<String> buffList){
        List<String> mirror = new ArrayList<>();

        for (int i = 0; i < buffList.size(); i++) {
            String line = buffList.get(i);
            String rev = reverseLine(line);
            mirror.add(reverseChar(rev));
        }

        return mirror;
    }


    String reverseLine(String line){
        StringBuilder revLine = new StringBuilder(line);
        return revLine.reverse().toString();
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