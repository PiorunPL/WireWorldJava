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
import java.io.FileWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;
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

    private static UsersStructuresContainer container = null;
    private static StructMap structMap = null;

    /**
     * Zapisuje mapę do pliku w formacie strukturalnym
     *
     * @param map StructMap do zapisu
     * @param out Plik w którym będzie zapisywane
     * @throws IOException
     * @author Michał Ziober
     */
    public static void saveMapToFile(StructMap map, File out, Vector<Cell> electronHeadsVector, Vector<Cell> electronTailsVector) throws IOException {
        int x = map.getXsize();
        int y = map.getYsize();
        out.createNewFile();

        FileWriter fw = new FileWriter(out);
        fw.write("struct " + x + " " + y + "\n");

        //Zapisywanie struktur użytkownika
        if (map.getUserStructures() != null) {
            putUsersStructures(out, fw, map.getUserStructures());
        }

        fw.write("board<");
        putStructuresOnBoard(out, map, fw);
        putElectronHeadsOnBoard(fw, electronHeadsVector);
        putElectronTailsOnBoard(fw, electronTailsVector);
        fw.write(">");

        fw.close();
    }

    private static void putElectronHeadsOnBoard(FileWriter fw, Vector<Cell> electronHeadsVector) throws IOException {
        for(int i = 0; i < electronHeadsVector.size(); i++)
        {
            Cell cell = electronHeadsVector.get(i);
            fw.write("electronHead " + cell.getxMap() + " " + cell.getyMap() + "\n");
        }
    }

    private static void putElectronTailsOnBoard(FileWriter fw, Vector<Cell> electronTailsVector) throws IOException {
        for(int i = 0; i < electronTailsVector.size(); i++)
        {
            Cell cell = electronTailsVector.get(i);
            fw.write("electronTail " + cell.getxMap() + " " + cell.getyMap() + "\n");
        }
    }

    /**
     * Metoda wykorzystywana do zapisywania pliku w formacie strukturalnym; zapisuje struktury zdefiniowane przez użytkownika
     *
     * @param out Plik do zapisu
     * @param fw  FileWriter zapisujący
     * @throws IOException
     * @author Michał Ziober
     */
    private static void putUsersStructures(File out, FileWriter fw, UsersStructuresContainer container) throws IOException {
        fw.write("structures<\n");
        UsersStructure us;
        for (int i = 0; i < container.size(); i++) {
            us = container.get(i);
            fw.write(":" + us.getName() + ":\n");
            for (int j = 0; j < us.getXSize(); j++) {
                for (int k = 0; k < us.getYSize(); k++) {
                    int st = -1;
                    if (us.getCell(j, k).getState().equals(WIRE))
                        st = 1;
                    else if (us.getCell(j, k).getState().equals(EMPA))
                        st = 4;
                    else if (us.getCell(j, k).getState().equals(EMPN))
                        st = 5;
                    fw.write(st + " ");
                }
                fw.write("\n");
            }
        }
        fw.write(">\n");
    }

    /**
     * Metoda wykorzystywana do zapisywania pliku w formacie strukturalnym; zapisuje struktury w zakładce board
     *
     * @param out Plik do zapisu
     * @param fw  FileWriter zapisujący
     * @throws IOException
     * @author Michał Ziober
     */
    private static void putStructuresOnBoard(File out, StructMap structMap, FileWriter fw) throws IOException {
        Structure str = null;
        try {
            for (int i = 0; i < structMap.size(); i++) {
                fw.write("\n");
                str = structMap.getStructure(i);
                if (str instanceof Wire)
                    fw.write(str.getName() + " " + str.getX() + " " + str.getY() + " " + str.getDirection() + " " + str.getXSize());
                else
                    fw.write(str.getName() + " " + str.getX() + " " + str.getY() + " " + str.getDirection());
            }
            fw.write("\n");
        } catch (Exception e) {
            System.out.println("Pusta structMapa");
        }
    }

    /**
     * Zapisuje mapę w formacie mapy komórek do pliku
     *
     * @param cellMap Mapa do zapisania
     * @param out     Plik w którym mapa będzie zapisywana
     * @author Michał Ziober
     */
    public static void saveMapToFile(CellMap cellMap, File out) throws IOException {
        int x = cellMap.getXSize();
        int y = cellMap.getYSize();
        out.createNewFile();

        FileWriter fw = new FileWriter(out);
        fw.write("map " + x + " " + y + "\n");
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                int state = 0;
                CellState cellState = cellMap.getCell(i, j).getState();
                if (WIRE.equals(cellState)) {
                    state = 1;
                } else if (ELET.equals(cellState)) {
                    state = 2;
                } else if (ELEH.equals(cellState)) {
                    state = 3;
                } else if (EMPA.equals(cellState)) {
                    state = 4;
                } else if (EMPN.equals(cellState)) {
                    state = 5;
                }
                fw.write(state + " ");
            }
            fw.write("\n");
        }
        fw.close();
    }


    public static StructMap getMapFromFile(File in) throws NullPointerException {
        CellMap cellMap = null;
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
                    System.out.println(cellMap.getXSize() + " " + cellMap.getYSize());
                    // structural format of file
                } else if (option.equals(structK)) {
                    // getting user defined structures
                    container = getUsersStructures(in);
                    // getting map
                    structMap = getMap(in, x, y, container);


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
            ExceptionsDialogs.warningDialog("Warning", "Typed to less lines than declared");
        } catch (IllegalArgumentException e) {
            ExceptionsDialogs.warningDialog("Warning", "Incorrect value in input file");
        } catch (NegativeArraySizeException e) {
            ExceptionsDialogs.warningDialog("Warning", "Typed illegal size. Only positive or [-1 -1] values are allowed");
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
        return structMap;
    }


    /**
     * Funkcja konwertuje plik wejściowy w mapowym formacie na CellMap
     *
     * @param in Plik wejściowy w formacie mapy
     * @param x  Ilość wierszy mapy
     * @param y  Ilośc kolumn mapy
     * @return Przekonwertowany plik na CellMap
     * @throws IOException
     * @throws TooManyCellsException
     * @throws TooLessCellsException
     * @throws NoSuchElementException
     * @author Michał Ziober
     */
    private static CellMap getMapMapFormat(File in, int x, int y) throws IOException, TooManyCellsException, TooLessCellsException, NoSuchElementException, NegativeArraySizeException {
        if (x == -1 && y == -1) {
            //Wydupca wireworlda, poprawić
            //instrukcje dla samoobliczającej się cellmapy- wystarczy zmienić x i y
            System.out.println("samoobliczajacy się rozmiar");
            int numberOfRows = 0;
            Scanner counter = new Scanner(in);
            String tmp = counter.nextLine();
            String[] countElem;
            if (counter.hasNextLine()) {
                countElem = (counter.nextLine()).split("\\s+");
                y = countElem.length;
                numberOfRows++;
            }
            while (counter.hasNextLine()) {
                tmp = counter.nextLine();
                numberOfRows++;
            }
            x = numberOfRows;
        } else if (x <= 0 || y <= 0) {
            throw new NegativeArraySizeException();
        }

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
    //DONE stworzyć testy dla metod konwertujących strukturę na cellMapę

    /**
     * @param structMap1
     * @return Zwraca Mapę komórek (CellMap), stworzoną z przekształcenia Mapy struktur (StructMap)
     * @throws IllegalStructurePlacement
     * @author Jakub Maciejewski
     */
    //DONE przetestować
    public static CellMap getMapStructFormat(StructMap structMap1) throws IllegalStructurePlacement {
        int xsize = structMap1.getXsize();
        int ysize = structMap1.getYsize();
        CellMap cellMap = new CellMap(xsize, ysize);

        if (structMap == null)
            structMap = structMap1;

        for (int i = 0; i < structMap1.size(); i++) {
            Structure struct = structMap1.getStructure(i);
            if (checkIfStructureFit(cellMap, struct) && checkIfSpaceForStructureIsClear(cellMap, struct)) {
                putStructToCellMap(cellMap, struct);
            } else {
                throw new IllegalStructurePlacement();
            }
        }

        for(int i = 0; i < electronHead.size(); i++)
        {
            Cell cell = electronHead.get(i);
            cellMap.getCell(cell.getxMap(), cell.getyMap()).changeState(ELEH);
        }
        electronHead = null;

        for(int i = 0; i < electronTail.size(); i++)
        {
            Cell cell = electronTail.get(i);
            cellMap.getCell(cell.getxMap(), cell.getyMap()).changeState(ELET);
        }
        electronTail = null;

        return cellMap;
    }

    public static void getMapStructFormat(Structure struct, CellMap cellMap) throws IllegalStructurePlacement {

        boolean bool1 = checkIfStructureFit(cellMap, struct);
        if (!bool1)
            System.err.println("FIT");

        boolean bool2 = checkIfSpaceForStructureIsClear(cellMap, struct);
        if (!bool2)
            System.err.println("SPACE");

        if (bool1 && bool2) {
            putStructToCellMap(cellMap, struct);
        } else {
            throw new IllegalStructurePlacement();
        }
    }


    //DONE prztestować
    private static void putStructToCellMap(CellMap cellMap, Structure struct) {

        CellMap cellMap1 = struct.structureAfterDirection();

        int x, y;

        x = struct.getXSizeAfterRotation();
        y = struct.getYSizeAfterRotation();

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                cellMap.setCell(struct.getXAfterRotation() + i, struct.getYAfterRotation() + j, cellMap1.getCell(i, j));
                cellMap.getCell(struct.getXAfterRotation() + i, struct.getYAfterRotation() + j).setxMap(struct.getXAfterRotation() + i);
                cellMap.getCell(struct.getXAfterRotation() + i, struct.getYAfterRotation() + j).setyMap(struct.getYAfterRotation() + j);
                cellMap.getCell(struct.getXAfterRotation() + i, struct.getYAfterRotation() + j).setStruct(struct);
            }
        }
    }

    //DONE Przetestować
    private static boolean checkIfStructureFit(CellMap cellMap, Structure struct) {
        int tempX = struct.getX();
        int tempY = struct.getY();

        int tempXSize = struct.getXSize() - 1;
        int tempYSize = struct.getYSize() - 1;

        if (struct.getDirection() == Direction.UP) {
            if (tempX + tempXSize > cellMap.getXSize() - 1)
                return false;
            if (tempY + tempYSize > cellMap.getYSize() - 1)
                return false;
        } else if (struct.getDirection() == Direction.RIGHT) {
            if (tempX + tempYSize > cellMap.getXSize() - 1)
                return false;
            if (tempY - tempXSize < 0)
                return false;
        } else if (struct.getDirection() == Direction.DOWN) {
            if (tempX - tempXSize < 0)
                return false;
            if (tempY - tempYSize < 0)
                return false;
        } else {
            if (tempX - tempYSize < 0)
                return false;
            if (tempY + tempXSize > cellMap.getYSize() - 1)
                return false;
        }
        return true;
    }

    //DONE przetestować
    private static boolean checkIfSpaceForStructureIsClear(CellMap cellMap, Structure struct) {

        CellMap cellMap1 = struct.structureAfterDirection();

        int x, y;

        x = struct.getXSizeAfterRotation();
        y = struct.getYSizeAfterRotation();

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (!canBePlaced(cellMap.getCell(struct.getXAfterRotation() + i, struct.getYAfterRotation() + j).getState(), cellMap1.getCell(i, j).getState())) {
                    return false;
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

    private static UsersStructuresContainer getUsersStructures(File in) throws FileNotFoundException {
        Scanner scanner = new Scanner(in);

        UsersStructuresContainer usersStructures = new UsersStructuresContainer();

        String currentLine;
        while (!(currentLine = scanner.nextLine()).equals(structuresK + "<") && scanner.hasNextLine()) ;
        if (!currentLine.equals(structuresK + "<")) return null;

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

    private static StructMap getMap(File in, int x, int y, UsersStructuresContainer container)
            throws FileNotFoundException {
        StructMap map = new StructMap(x, y);
        Scanner scanner = new Scanner(in);
        String line;
        String[] lineArr;
        electronHead = new Vector<Cell>();
        electronTail = new Vector<Cell>();
        map.addUserStructures(container);

        while (!scanner.nextLine().equals(boardK + "<")) ;

        while (!(line = scanner.nextLine()).equals(">")) {
            lineArr = line.split(" ");
            if(lineArr[0].equals("electronHead"))
            {
                Cell cell = new Cell(4);
                cell.setxMap(Integer.parseInt(lineArr[1]));
                cell.setyMap(Integer.parseInt(lineArr[2]));
                electronHead.add(cell);
            }
            else if(lineArr[0].equals("electronTail")) {
                Cell cell = new Cell(4);
                cell.setxMap(Integer.parseInt(lineArr[1]));
                cell.setyMap(Integer.parseInt(lineArr[2]));
                electronTail.add(cell);
            }
            else if (lineArr.length == 4 || lineArr.length == 5) {
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

    private static Vector<Cell> electronHead;
    private static Vector<Cell> electronTail;

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

