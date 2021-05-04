package utils;

import logic.Direction;
import logic.StructMap;
import logic.cells.*;
import logic.structures.*;
import logic.structures.UsersStructure;
import logic.structures.UsersStructuresContainer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBops {

    //map files keywords
    private final static String structK = "struct";
    private final static String mapK = "map";
    private final static String dimensionsK = "dimensions";
    private final static String structuresK = "structures";
    private final static String boardK = "board";


    public static void saveMapToFile(StructMap map, File out) {

    }

    public static StructMap getMapFromFile(File in) throws NullPointerException {
        StructMap map = null;
        int x, y;

        try {
            Scanner scanner = new Scanner(in);
            String tmp;

            // checking file format (struct/map)
            if ((tmp = scanner.nextLine()).equals(structK)) {

                // getting dimensions of file, if provided
                int[] dimensions = getDimensions(in);
                x = dimensions[0];
                y = dimensions[1];

                // getting user defined structures
                UsersStructuresContainer structs = getUsersStructures(in);

                // getting map
                map = getMap(in, x, y, structs);
            } else if (tmp.equals(mapK)) {

            } else {
                System.out.printf(
                        "Wrong file format.\n" +
                                "In line 1 should be %s or %s instead of %s\n",
                        structK, mapK, tmp);
                return null;
            }


        } catch (FileNotFoundException e) {
            System.out.printf(
                    "File %s doesn't exist.\n",
                    in.getAbsolutePath());
            return null;
        }

        return map;
    }

    //returns string containing name of type of file (struct/map)
    //TODO: change to private
    public static String getType(File in) throws FileNotFoundException {
        return new Scanner(in).nextLine();
    }

    //TODO: zmienić na private
    public static int[] getDimensions(File in) throws FileNotFoundException {
        Scanner scanner = new Scanner(in);
        int[] dimensions = new int[2];
        int lines = 0;

        while (!scanner.nextLine().equals(dimensionsK + "<")) lines++;
        if (lines != 1) return null;

        String tmp;
        if ((tmp = scanner.nextLine()).equals(">")) {
            dimensions[0] = -1;
            dimensions[1] = -1;
        }

        dimensions[0] = Integer.parseInt(tmp);
        dimensions[1] = Integer.parseInt(scanner.nextLine());

        if (!scanner.nextLine().equals(">")) return null;

        return dimensions;
    }

    //TODO: zmienić na private
    public static UsersStructuresContainer getUsersStructures(File in) throws FileNotFoundException {
        Scanner scanner = new Scanner(in);
        int lines = 0;

        UsersStructuresContainer usersStructures = new UsersStructuresContainer();
        while (!scanner.nextLine().equals(structuresK + "<")) lines++;
        if (lines != 5) return null;

        String name;
        int x, y;
        Cell[][] struct;

        Pattern nameRegex = Pattern.compile("^:.+:$");
        Matcher matcher;
        String line;
        String[] state;
        int i;

        Scanner counter = new Scanner(in);
        while (!counter.nextLine().equals(structuresK + "<")) ;
        counter.nextLine();

        // iterating on user's structures
        line = scanner.nextLine();
        while (!(line.equals(">"))) {
            matcher = nameRegex.matcher((name = line));
            if (!matcher.find()) {
                System.out.println(name);
                return null;
            }

            i = 0;
            x = 0;
            y = 0;

            // counting structure's rows
            matcher = nameRegex.matcher(line = counter.nextLine());
            while (!matcher.find() && !line.equals(">")) {
                if (x == 0) y = line.split(" ").length;
                x++;
                matcher = nameRegex.matcher(line = counter.nextLine());
            }

            struct = new Cell[x][y];

            // iterating on lines in user's structure
            matcher = nameRegex.matcher((line = scanner.nextLine()));
            while (!matcher.find() && !line.equals(">")) {
                state = line.split(" ");
                for (int j = 0; j < y; j++) {
                    struct[i][j] = new Cell(Integer.parseInt(state[j]));
                }
                matcher = nameRegex.matcher((line = scanner.nextLine()));
                i++;
            }

            // adding structure to container for structures
            usersStructures.add(new UsersStructure(name.replaceAll(":", ""), x, y, struct));
        }
        counter.close();
        scanner.close();
        return usersStructures;
    }


    //TODO: zmienić na private
    public static StructMap getMap(File in, int x, int y, UsersStructuresContainer structs)
            throws FileNotFoundException {
        StructMap map = new StructMap(x, y);
        Scanner scanner = new Scanner(in);
        String line;
        String[] lineArr;

        while (!scanner.nextLine().equals(boardK + "<"));

        while (!(line = scanner.nextLine()).equals(">")) {
            lineArr = line.split(" ");
            map.addStruct(
                    lineArr[0],
                    Integer.parseInt(lineArr[1]),
                    Integer.parseInt(lineArr[2]),
                    Direction.setDirection(lineArr[3]),
                    lineArr.length > 4 ? Integer.parseInt(lineArr[4]) : -1
            );
        }
        return map;
    }
}

