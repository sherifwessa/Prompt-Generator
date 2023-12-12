package com.example.final_project;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

import java.time.format.TextStyle;

public class GeneratePrompt {
    public GeneratePrompt(){

    }

    @FXML
    private Button generateButton;
    @FXML
    private Button regenerateButton;
    @FXML
    private Button copyButton;

    @FXML
    private TextField topic;
    @FXML
    private TextField existingCode;
    @FXML
    private TextField problemStatement;
    @FXML
    private TextField assumptions;
    @FXML
    private TextField constraints;

    @FXML
    private Label promptLabel;

    @FXML
    private MenuButton languageMenu;

    @FXML
    private void generatePrompt(){
        String topics = topic.getText();
        promptLabel.setText(topics);
    }
}

