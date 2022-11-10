package org.example;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static String path = enterPath();

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        File directory = findDirectory(path);
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(fileTree(stringBuilder, directory.listFiles()));
    }

    public static String enterPath() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> Enter the path to the directory: ");
        return scanner.nextLine();
    }

    public static File findDirectory(String directoryPath) {
        File file = new File(directoryPath);
        if(!file.isDirectory()) {
            throw new IllegalArgumentException("Expected directory. Provided by file");
        }
        return file;
    }

    // Build file tree
    public static String fileTree(StringBuilder stringBuilder, File[] directory) {
        for(File item : directory) {
            if(item.isDirectory()) {
                stringBuilder.append(addSpaces(calculateNestingLevel(item.getAbsolutePath())));
                stringBuilder.append(item.getName());
                stringBuilder.append("\n");
                // If the file is directory, then the method called again (recoursion)
                fileTree(stringBuilder, item.listFiles());
            } else {
                stringBuilder.append(addSpaces(calculateNestingLevel(item.getAbsolutePath())));
                stringBuilder.append(item.getName() + ",\u001B[32m " + item.length() + " bytes\u001B[0m");
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    // Calculate nesting level of file
    // for example: path "D:/IntelliJ Idea/projects/gfl-homework" => nesting level is equals 3
    public static int calculateNestingLevel(String path) {
        int count = 0;
        for(int i = 0; i < path.length(); i++) {
            if(path.charAt(i) == '/' || path.charAt(i) == '\\') {
                count++;
            }
        }
        return count;
    }

    // Add spaces in file tree depending on file nesting level
    // if file's nesting level is equals 2, then the method will be add 2 tabs
    public static String addSpaces(int nestingLevel) {
        nestingLevel -= calculateNestingLevel(path) + 1;
        int counter = 0;
        StringBuilder stringBuilder = new StringBuilder();
        while(counter < nestingLevel) {
            stringBuilder.append("\t");
            counter++;
        }
        return stringBuilder.toString();
    }
}