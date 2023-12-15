package com.example.final_project;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.TextStyle;

public class GeneratePrompt {

    @FXML
    private Button generateButton;
    @FXML
    private Button regenerateButton;
    @FXML
    private Button copyButton;

    @FXML
    private TextField topic;
    @FXML
    private TextArea existingCode;
    @FXML
    private TextArea problemStatement;
    @FXML
    private TextArea assumptions;
    @FXML
    private TextArea constraints;

    @FXML
    private Label promptLabel;

    @FXML
    private ChoiceBox existingCodeChoice;

    @FXML
    private void generatePrompt(){
        String topics = topic.getText();
        promptLabel.setText(topics);
    }

    @FXML
    private void setVisible() {
        String choiceValue = existingCodeChoice.getValue().toString();
        if ("No".equals(choiceValue)) {
            existingCode.setDisable(true);
        } else if ("Yes".equals(choiceValue)) {
            existingCode.setDisable(false);
        }
    }
}

