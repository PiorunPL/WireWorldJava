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
import java.io.IOException;

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
        StructMap map = null;       // map which is being created
        String option = null;       // input file type (map/structure)
        String firstLine[] = null;  // first line of array
        int x = 0;                  // x dimension of an array
        int y = 0;                  // y dimension of an array

        try {
            firstLine = firstLineToArray(in);
            option = firstLine[0];

            if(firstLine.length == 3){
                x = Integer.parseInt(firstLine[1]);
                y = Integer.parseInt(firstLine[2]);

                // map format of file
                if (option.equals(mapK)) {
                    System.out.println("map");
                    map = new StructMap(x, y);
                    //...
                // structural format of file
                } else if (option.equals(structK)) {

                    // getting user defined structures
                    UsersStructuresContainer container = getUsersStructures(in);

                    // getting map
                    map = getMap(in, x, y, container);
                } else {
                    throw new IllegalArgumentException();
                }
            }else
                throw new IllegalArgumentException();
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        } catch (NegativeArraySizeException e){
            e.printStackTrace();
        }
        return map;
    }

    //TODO: zmienić na private
    public static UsersStructuresContainer getUsersStructures(File in) throws FileNotFoundException {
        Scanner scanner = new Scanner(in);

        UsersStructuresContainer usersStructures = new UsersStructuresContainer();
        while (!scanner.nextLine().equals(structuresK + "<"));

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
    public static StructMap getMap(File in, int x, int y, UsersStructuresContainer container)
            throws FileNotFoundException {
        StructMap map = new StructMap(x, y);
        Scanner scanner = new Scanner(in);
        String line;
        String[] lineArr;

        map.addUserStructures(container);

        while (!scanner.nextLine().equals(boardK + "<"));

        while (!(line = scanner.nextLine()).equals(">")) {
            lineArr = line.split(" ");
            if (lineArr.length == 4 || lineArr.length == 5) {
                map.addStruct(
                        lineArr[0],
                        Integer.parseInt(lineArr[1]),
                        Integer.parseInt(lineArr[2]),
                        Direction.setDirection(lineArr[3]),
                        lineArr.length == 5 ? Integer.parseInt(lineArr[4]) : -1
                );
            }
        }
        return map;
    }

    /**
     * @author Michał Ziober
     * @param in plik wejściowy, z którego ma być odczytana pierwsza linia
     * @return Pierwsza linia przekształcona do tablicy (rozdzielnikami są białe znaki)
     * @throws FileNotFoundException
     */
    private static String[] firstLineToArray(File in) throws FileNotFoundException {
        String tab[] = null;
        Scanner scaner = null;
        String line = null;

        try{
            scaner = new Scanner(in);
            line = (scaner.nextLine());
            tab = line.split("\\s+");
            scaner.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
        return tab;
    }
}

