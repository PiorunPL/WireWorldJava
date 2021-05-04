package utils;

import logic.StructMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class DBops {
    public static void saveMapToFile(StructMap map, File out) {

    }


    /**
     * Tworzy z pliku w formacie mapowym lub strukturalnym mapę struktur
     * @param in Plik wejściowy w określonym formacie
     * @return Mapa struktur
     */
    public static StructMap getMapFromFile(File in) {
        StructMap map = null;       //tworzona mapa
        String option = null;       //typ pliku wejściowego (map/structure)
        String firstLine[] = null;  //pierwsza linai do tablicy
        int x = 0;                  //rozmiar x tworoznej mapy
        int y = 0;                  //rozmiar y tworzonej mapy

        try {
            firstLine = firstLineToArray(in);
            option = firstLine[0];

            if(firstLine.length == 3){
                x = Integer.parseInt(firstLine[1]);
                y = Integer.parseInt(firstLine[2]);

                if( x>0 && y>0 ) {
                    if (option.matches("MAP.*")) {
                        System.out.println("map");
                        map = new StructMap(x, y);
                        //instrukcje dla mapowego pliku
                    } else if (option.matches("STRUCTURE.*")) {
                        System.out.println("structure");
                        //Tutaj instrukcje dla strukturalnego pliku
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
                else if( x==-1 && y==-1){
                    if (option.matches("MAP.*")) {
                        //instrukcje dla samoobliczjącego się rozmiaru
                    } else if (option.matches("STRUCTURE.*")) {
                        //instrukcje dal samoobliczającego się rozmiaru
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
                else
                    throw new IllegalArgumentException();
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

    /**
     * @author Michał Ziober
     * @param in - plik wejściowy, z którego ma być odczytana pierwsza linia
     * @return Pierwsza linia przekształcona do tablicy (rozdzielnikami są białe znaki)
     * @throws FileNotFoundException
     */
    private static String[] firstLineToArray(File in) throws FileNotFoundException {
        String tab[] = null;
        Scanner scaner = null;
        String line = null;

        try{
            scaner = new Scanner(in);
            line = (scaner.nextLine()).toUpperCase();
            tab = line.split("\\s+");
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
        return tab;
    }
}
