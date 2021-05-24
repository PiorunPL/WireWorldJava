package utils;

import logic.CellMap;
import logic.Direction;
import logic.StructMap;
import logic.cells.*;
import logic.structures.*;
import logic.structures.UsersStructure;
import logic.structures.UsersStructuresContainer;
import utils.exceptions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;

import static logic.cells.CellState.*;

public class DBops {

    //map files keywords
    private final static String structK = "struct";
    private final static String mapK = "map";
    private final static String dimensionsK = "dimensions";
    private final static String structuresK = "structures";
    private final static String boardK = "board";


    public static void main(String[] args) {
        //CellMap map = getMapFromFile(new File("C:\\Users\\lolol\\OneDrive - Politechnika Warszawska\\Pulpit\\Sem2\\JiMP2\\Wire\\src\\utils\\Test"));
        CellMap map = getMapFromFile(new File("test/testStructFormatFile"));
        //Wyswietlanie mapy
        for (int i = 0; i < map.getXSize(); i++) {
            for (int j = 0; j < map.getYSize(); j++) {
                System.out.print(map.getCell(i, j).getState() + " ");
            }
            System.out.println();
        }
    }


    public static void saveMapToFile(StructMap map, File out) {

    }

    public static CellMap getMapFromFile(File in) throws NullPointerException {
        CellMap cellMap = null;
        StructMap structMap;
        String option;
        String[] firstLine;
        int x = 0;
        int y = 0;

        try {
            firstLine = firstLineToArray(in);
            option = firstLine[0];
            if (firstLine.length == 3) {
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
                    cellMap = getMapStructFormat(structMap);
                } else {
                    throw new IllegalFormatOptionException();
                }
            } else
                throw new IncorretNumberOfArgumentsException();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalFormatOptionException e) {
            e.getMessage();
        } catch (TooManyCellsException e) {
            e.getMessage();
        } catch (TooLessCellsException e) {
            e.getMessage();
        } catch (IncorretNumberOfArgumentsException e) {
            e.getMessage();
        } catch (NoSuchElementException e) {
            System.out.println("Typed to less lines than declared");
        } catch (IllegalStructurePlacement e) {
            e.getMessage();
        }
        //testowanie czy struktury są dobrze wczytane
        /*for(int i=0; i<structMap.size(); i++){
            Structure struct = structMap.getStructure(i);
            for(int j=0; j<struct.getXsize(); j++){
                for(int k=0; k<struct.getYsize(); k++){
                    System.out.print(struct.getCell(j, k).getState()+" ");
                }
                System.out.println();
            }
            System.out.println();
        }*/
        return cellMap;
    }


    private static CellMap getMapMapFormat(File in, int x, int y) throws IOException, TooManyCellsException, TooLessCellsException, NoSuchElementException {
        CellMap map = new CellMap(x, y);
        String line;
        String[] lineInTab;
        Scanner scan = new Scanner(in);
        line = scan.nextLine();
        for (int i = 0; i < x; i++) {
            lineInTab = (scan.nextLine()).split("\\s+");
            if (lineInTab.length > y)
                throw new TooManyCellsException();
            else if (lineInTab.length < y)
                throw new TooLessCellsException();
            for (int j = 0; j < y; j++) {
                map.getCell(i, j).changeState(Integer.parseInt(lineInTab[j]));
            }

        }
        return map;
    }

    //DONE Trzeba dodać sprawdzanie, czy dane pole można nadpisać (tzn. czy znajdują się tam jedynie pola EMPA, inaczej w przypadku elektronu, on musi nadpisywać kabel)
    //DONE Zmienić - ELektron teraz może być stawiany wszędzie
    //DONE Sprawdzić, czy struktury mieszczą się na planszy
    //TODO stworzyć testy dla metod konwertujących strukturę na cellMapę

    /**
     * @param structMap
     * @return Zwraca Mapę komórek (CellMap), stworzoną z przekształcenia Mapy struktur (StructMap)
     * @throws IllegalStructurePlacement
     * @author Jakub Maciejewski
     */
    //TODO przetestować
    private static CellMap getMapStructFormat(StructMap structMap) throws IllegalStructurePlacement {
        int xsize = structMap.getXsize();
        int ysize = structMap.getYsize();
        CellMap cellMap = new CellMap(xsize, ysize);

        for (int i = 0; i < structMap.size(); i++) {
            Structure struct = structMap.getStructure(i);
            if (checkIfStructureFit(cellMap, struct) && checkIfSpaceForStructureIsClear(cellMap, struct)) {
                putStructToCellMap(cellMap, struct);
            } else {
                throw new IllegalStructurePlacement();
            }


        }
        return cellMap;
    }

    //TODO Przetestować
    private static void setMapCell(CellMap cellMap, Structure struct, int temp1, int temp2, int j, int k) {
        if (cellMap.getCell(temp1, temp2).getState() == EMPA) {
            cellMap.setCell(temp1, temp2, struct.getCell(j, k));
        } else if (cellMap.getCell(temp1, temp2).getState() == WIRE && (struct.getCell(j, k).getState() == ELEH || struct.getCell(j, k).getState() == ELET)) {
            cellMap.setCell(temp1, temp2, struct.getCell(j, k));
        }
    }

    //TODO prztestować
    public static void putStructToCellMap(CellMap cellMap, Structure struct) {
        int temp1;
        int temp2;

        if (struct.getDirection() == Direction.UP) {
            for (int j = 0; j < struct.getXSize(); j++) {
                temp1 = struct.getX() + j;
                for (int k = 0; k < struct.getYSize(); k++) {
                    temp2 = struct.getY() + k;

                    setMapCell(cellMap, struct, temp1, temp2, j, k);

                }
            }
        } else if (struct.getDirection() == Direction.RIGHT) {
            for (int j = 0; j < struct.getXSize(); j++) {
                temp2 = struct.getY() - j;
                for (int k = 0; k < struct.getYSize(); k++) {
                    temp1 = struct.getX() + k;

                    setMapCell(cellMap, struct, temp1, temp2, j, k);
                }
            }
        } else if (struct.getDirection() == Direction.DOWN) {
            for (int j = 0; j < struct.getXSize(); j++) {
                temp1 = struct.getX() - j;
                for (int k = 0; k < struct.getYSize(); k++) {
                    temp2 = struct.getY() - k;

                    setMapCell(cellMap, struct, temp1, temp2, j, k);
                }
            }
        } else {
            for (int j = 0; j < struct.getXSize(); j++) {
                temp2 = struct.getY() + j;
                for (int k = 0; k < struct.getYSize(); k++) {
                    temp1 = struct.getX() - k;

                    setMapCell(cellMap, struct, temp1, temp2, j, k);
                }
            }
        }
    }

    //DONE Przetestować
    private static boolean checkIfStructureFit(CellMap cellMap, Structure struct) {
        int tempX = struct.getX() + 1;
        int tempY = struct.getY() + 1;

        int tempXSize = struct.getXSize() - 1;
        int tempYSize = struct.getYSize() - 1;

        if (struct.getDirection() == Direction.UP) {
            if (tempX + tempXSize > cellMap.getXSize())
                return false;
            if (tempY + tempYSize > cellMap.getYSize())
                return false;
        } else if (struct.getDirection() == Direction.RIGHT) {
            if (tempX + tempYSize > cellMap.getXSize())
                return false;
            if (tempY - tempXSize <= 0)
                return false;
        } else if (struct.getDirection() == Direction.DOWN) {
            if (tempX - tempXSize <= 0)
                return false;
            if (tempY - tempYSize <= 0)
                return false;
        } else {
            if (tempX - tempYSize <= 0)
                return false;
            if (tempY + tempXSize > cellMap.getYSize())
                return false;
        }
        return true;
    }

    //TODO przetestować
    private static boolean checkIfSpaceForStructureIsClear(CellMap cellMap, Structure struct) {
        int temp1;
        int temp2;

        if (struct.getDirection() == Direction.UP) {
            for (int j = 0; j < struct.getXSize(); j++) {
                temp1 = struct.getX() + j;
                for (int k = 0; k < struct.getYSize(); k++) {
                    temp2 = struct.getY() + k;

                    if (!canBePlaced(cellMap.getCell(temp1, temp2).getState(), struct.getCell(j, k).getState())) {
                        return false;
                    }

                }
            }
        } else if (struct.getDirection() == Direction.RIGHT) {
            for (int j = 0; j < struct.getXSize(); j++) {
                temp2 = struct.getY() - j;
                for (int k = 0; k < struct.getYSize(); k++) {
                    temp1 = struct.getX() + k;

                    if (!canBePlaced(cellMap.getCell(temp1, temp2).getState(), struct.getCell(j, k).getState())) {
                        return false;
                    }
                }
            }
        } else if (struct.getDirection() == Direction.DOWN) {
            for (int j = 0; j < struct.getXSize(); j++) {
                temp1 = struct.getX() - j;
                for (int k = 0; k < struct.getYSize(); k++) {
                    temp2 = struct.getY() - k;

                    if (!canBePlaced(cellMap.getCell(temp1, temp2).getState(), struct.getCell(j, k).getState())) {
                        return false;
                    }
                }
            }
        } else {
            for (int j = 0; j < struct.getXSize(); j++) {
                temp2 = struct.getY() + j;
                for (int k = 0; k < struct.getYSize(); k++) {
                    temp1 = struct.getX() - k;

                    if (!canBePlaced(cellMap.getCell(temp1, temp2).getState(), struct.getCell(j, k).getState())) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    //DONE przetestować
    private static boolean canBePlaced(CellState cellMapState, CellState cellStructState) {
        if (cellMapState == EMPN || cellMapState == ELET || cellMapState == ELEH) {
            return false;
        } else if (cellMapState == WIRE) {
            if (!(cellStructState == ELET || cellStructState == ELEH)) {
                return false;
            }
        }

        return true;
    }


    //TODO: zmienić na private
    public static UsersStructuresContainer getUsersStructures(File in) throws FileNotFoundException {
        Scanner scanner = new Scanner(in);

        UsersStructuresContainer usersStructures = new UsersStructuresContainer();
        while (!scanner.nextLine().equals(structuresK + "<")) ;

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

        while (!scanner.nextLine().equals(boardK + "<")) ;

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
     * @param in plik wejściowy, z którego ma być odczytana pierwsza linia
     * @return Pierwsza linia przekształcona do tablicy (rozdzielnikami są białe znaki)
     * @throws FileNotFoundException
     * @author Michał Ziober
     */
    private static String[] firstLineToArray(File in) throws FileNotFoundException {
        String[] tab = null;
        Scanner scaner = null;
        String line = null;

        try {
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

