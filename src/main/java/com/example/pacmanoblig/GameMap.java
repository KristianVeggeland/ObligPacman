package com.example.pacmanoblig;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import com.example.pacmanoblig.Ghosts.Blinky;
import com.example.pacmanoblig.Ghosts.Clyde;
import com.example.pacmanoblig.Ghosts.Inky;
import com.example.pacmanoblig.Ghosts.Pinky;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GameMap {

    private static final int CELL_SIZE = 32;

    private int numRows;
    private int numCols;
    private final int[][] cells;

    private Player player;

    public GameMap(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        numRows = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            numCols = line.length();
            numRows++;
        }
        scanner.close();

        cells = new int[numRows][numCols];

        scanner = new Scanner(file);
        int row = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int col = 0; col < line.length(); col++) {
                char ch = line.charAt(col);
                cells[row][col] = ch;

            }
            row++;
        }
        scanner.close();

    }

    public Group createMap() {
        Group root = new Group();

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                int ch = cells[row][col];
                if (ch == '#') {
                    Rectangle wall = new Rectangle(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    wall.setFill(Color.DARKBLUE);
                    root.getChildren().add(wall);
                }
                if (ch == '-') {
                    Circle dot = new Circle(col * CELL_SIZE + CELL_SIZE / 2, row * CELL_SIZE + CELL_SIZE / 2, 3);
                    dot.setFill(Color.WHITE);
                    root.getChildren().add(dot);
                }
                if (ch == '=') {
                    Circle energy = new Circle(col * CELL_SIZE + CELL_SIZE / 2, row * CELL_SIZE + CELL_SIZE / 2, 6);
                    energy.setFill(Color.WHITE);
                    root.getChildren().add(energy);
                }
                if (ch == '0'){
                    player = new Player(col * CELL_SIZE + CELL_SIZE / 2, row * CELL_SIZE + CELL_SIZE / 2);
                    player.setViewOrder(-1000);
                    root.getChildren().add(player);
                }
                if (ch == '1'){
                    Blinky blinky = new Blinky(col * CELL_SIZE , row * CELL_SIZE);
                    root.getChildren().add(blinky);
                }
                if (ch == '2'){
                    Inky inky = new Inky(col * CELL_SIZE , row * CELL_SIZE);
                    root.getChildren().add(inky);
                }
                if (ch == '3'){
                    Pinky pinky = new Pinky(col * CELL_SIZE , row * CELL_SIZE);
                    root.getChildren().add(pinky);
                }
                if (ch == '4'){
                    Clyde clyde = new Clyde(col * CELL_SIZE , row * CELL_SIZE);
                    root.getChildren().add(clyde);
                }
            }
        }
        return root;
    }

    public Player getPlayer(){
        return player;
    }
}