package com.example.xworkshopunpackgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));


        Scene scene = new Scene(fxmlLoader.load(), 600, 240);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.setTitle("X4 Workshop Mod Unpacker");
        stage.setScene(scene);
        new JMetro(scene,Style.DARK);
        stage.show();
    }

    public static void main(String[] args) {
        if (args.length >= 2) {
            // Headless mode: args[0] = input file, args[1] = output dir
            try {
                Unpacker unpacker = new Unpacker(new java.io.File(args[0]));
                unpacker.readHeader();
                unpacker.unpack(java.nio.file.Paths.get(args[1]));
                System.out.println("Unpacking completed successfully.");
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                System.exit(1);
            }
            System.exit(0);
        } else {
            launch();
        }
    }
}