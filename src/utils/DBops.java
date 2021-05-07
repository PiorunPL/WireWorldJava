package utils;

import logic.CellMap;
import logic.Direction;
import logic.StructMap;
import logic.cells.*;
import logic.structures.*;
import logic.structures.UsersStructure;
import logic.structures.UsersStructuresContainer;
import utils.exceptions.IllegalFormatOptionException;
import utils.exceptions.IncorretNumberOfArgumentsException;
import utils.exceptions.TooLessCellsException;
import utils.exceptions.TooManyCellsException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

import static logic.cells.CellState.*;
import static logic.cells.CellState.ELET;

public class DBops {

    //map files keywords
    private final static String structK = "struct";
    private final static String mapK = "map";
    private final static String dimensionsK = "dimensions";
    private final static String structuresK = "structures";
    private final static String boardK = "board";


    public static void main(String[] args){
        CellMap map = getMapFromFile(new File("C:\\Users\\lolol\\OneDrive - Politechnika Warszawska\\Pulpit\\Sem2\\JiMP2\\Wire\\src\\utils\\Test"));
        for(int i=0; i<map.getXSize(); i++){
            for(int j=0; j<map.getYSize(); j++){
                System.out.println(map.getCell(i, j).getState());
            }
            System.out.println();
        }
    }
    public static void saveMapToFile(StructMap map, File out) {

    }

    public static CellMap getMapFromFile(File in) throws NullPointerException{
        CellMap cellMap = null;
        StructMap structMap = null;
        String option = null;
        String firstLine[] = null;
        int x = 0;
        int y = 0;

        try{
            firstLine = firstLineToArray(in);
            option = firstLine[0];
            if(firstLine.length == 3){
                x = Integer.parseInt(firstLine[1]);
                y = Integer.parseInt(firstLine[2]);

                // map format of file
                if (option.equals(mapK)) {
                    cellMap = getMapMapFormat(in, x, y);

                    // structural format of file
                } else if (option.equals(structK)) {

                    // getting user defined structures
                    UsersStructuresContainer container = getUsersStructures(in);

                    // getting map
                    structMap = getMap(in, x, y, container);
                } else {
                    throw new IllegalFormatOptionException();
                }
            }else
                throw new IncorretNumberOfArgumentsException();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(IllegalFormatOptionException e){
            e.getMessage();
        } catch(TooManyCellsException e){
            e.getMessage();
        } catch(TooLessCellsException e){
            e.getMessage();
        } catch(IncorretNumberOfArgumentsException e){
            e.getMessage();
        } catch(NoSuchElementException e){
            System.out.println("Typed to less lines than declared");
        }
        return cellMap;
    }


    private static CellMap getMapMapFormat(File in, int x, int y) throws IOException, TooManyCellsException, TooLessCellsException, NoSuchElementException  {
        CellMap map = new CellMap(x, y);
        String line;
        String lineInTab[];
        Scanner scan = new Scanner(in);
        line = scan.nextLine();
        for(int i=0; i<x; i++){
            lineInTab = (scan.nextLine()).split("\\s+");
            if(lineInTab.length > y)
                throw new TooManyCellsException();
            else if(lineInTab.length < y)
                throw new TooLessCellsException();
            for(int j=0; j<y; j++){
                map.getCell(i, j).changeState(Integer.parseInt(lineInTab[j]));
            }

        }
        return map;
    }

    private CellMap getMapStructFormat(StructMap map) {
        int xsize = map.getXsize();
        int ysize = map.getYsize();
        CellMap cellMap = new CellMap(xsize, ysize);
        for (int i = 0; i < map.size(); i++) {
            Structure struct = map.getStructure(i);
            int temp1 = struct.getX();
            int temp2 = struct.getY();

            for (int j = 0; j < struct.getXsize(); j++) {
                if (struct.getDirection() == Direction.UP || struct.getDirection() == Direction.DOWN)
                    temp1 = temp1Actualization1(struct, j);
                if (struct.getDirection() == Direction.RIGHT || struct.getDirection() == Direction.LEFT)
                    temp2 = temp2Actualization1(struct, j);
                for (int k = 0; k < struct.getYsize(); k++) {
                    if (struct.getDirection() == Direction.RIGHT || struct.getDirection() == Direction.LEFT)
                        temp1 = temp1Actualization2(struct, j);
                    if (struct.getDirection() == Direction.UP || struct.getDirection() == Direction.DOWN)
                        temp2 = temp2Actualization2(struct, j);

                    if (cellMap.getCell(temp1, temp2).getState() == EMPA)
                        cellMap.getCell(temp1, temp2).changeState(struct.getCell(j, k).getState());
                    else if (struct.getCell(j, k).getState() == ELEH) {
                        cellMap.getCell(temp1, temp2).changeState(ELEH);
                    } else if (struct.getCell(j, k).getState() == ELET) {
                        cellMap.getCell(temp1, temp2).changeState(ELET);
                    } else {

                    }
                }
            }
        }
        return cellMap;
    }


    private int temp1Actualization1(Structure struct, int iteration) {
        int temp1;

        if (struct.getDirection() == Direction.UP) {
            temp1 = struct.getX() + iteration;
        } else if (struct.getDirection() == Direction.RIGHT) {
            temp1 = struct.getX();
        } else if (struct.getDirection() == Direction.DOWN) {
            temp1 = struct.getX() - iteration;
        } else {
            temp1 = struct.getX();
        }

        return temp1;
    }

    private int temp2Actualization1(Structure struct, int iteration) {
        int temp2;

        if (struct.getDirection() == Direction.UP) {
            temp2 = struct.getY();
        } else if (struct.getDirection() == Direction.RIGHT) {
            temp2 = struct.getY() - iteration;
        } else if (struct.getDirection() == Direction.DOWN) {
            temp2 = struct.getY();
        } else {
            temp2 = struct.getY() + iteration;
        }

        return temp2;
    }

    private int temp1Actualization2(Structure struct, int iteration) {
        int temp1;

        if (struct.getDirection() == Direction.UP) {
            temp1 = struct.getX();
        } else if (struct.getDirection() == Direction.RIGHT) {
            temp1 = struct.getX() - iteration;
        } else if (struct.getDirection() == Direction.DOWN) {
            temp1 = struct.getX();
        } else {
            temp1 = struct.getX() + iteration;
        }

        return temp1;
    }

    private int temp2Actualization2(Structure struct, int iteration) {
        int temp2;

        if (struct.getDirection() == Direction.UP) {
            temp2 = struct.getY() + iteration;
        } else if (struct.getDirection() == Direction.RIGHT) {
            temp2 = struct.getY();
        } else if (struct.getDirection() == Direction.DOWN) {
            temp2 = struct.getY() - iteration;
        } else {
            temp2 = struct.getY();
        }

        return temp2;
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

