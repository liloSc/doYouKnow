package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Do you know...?");
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack(); //Fit the preferred Size and Layouts it subcomponents
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}