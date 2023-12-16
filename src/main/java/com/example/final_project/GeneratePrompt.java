package com.example.final_project;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

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
    private ChoiceBox languages;
    @FXML
    private TextField languageVersion;

    @FXML
    private TextArea promptBox;

    private StringBuilder prompt = new StringBuilder();

    @FXML
    private TextField apiKey1;

    @FXML
    private TextField apiKey2;

    @FXML
    private ChoiceBox opChoice;

    @FXML
    private TextArea textQuestionBox;

    @FXML
    private ChoiceBox structureMenu;

    @FXML
    private TextField purposeTextBox;

    @FXML
    private CheckBox humanize;

    @FXML
    private TextField maxWords;

    @FXML
    private TextField relatedTopic;

    @FXML
    private CheckBox opinionCheckBox;

    @FXML
    private TextArea promptBox1;


    private String getContent(String response) {
        int start = response.indexOf("content")+ 11;
        int end = response.indexOf("\"", start);
        return response.substring(start, end);
    }

    private void LLMPrompt(String prompt, int flag)
    {
        String apiKey="";
        if(flag == 0)
        {
            apiKey = apiKey1.getText();
        }
        else
        {
            apiKey = apiKey2.getText();
        }
        String model = "gpt-3.5-turbo";
        String url = "https://api.openai.com/v1/chat/completions";

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");
//            prompt = "Generate prompt based on the following data: Topic: Data Processing, Programming Language: Java,Language Version: 11, I want to: generate a report, Assuming that: the input data is well-formatted, With the following constraints: limited to 1000 records,";

            // The request body
            String body = String.format("{\"model\": \"%s\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}", model, prompt);
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from ChatGPT
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuilder response = new StringBuilder();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
            String res = getContent(response.toString());
            res = res.replace("\n", "");
            if (flag == 0)
            {
                promptBox.setText(res);
            }
            else
            {
                promptBox1.setText(res);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    @FXML
    private void generatePrompt2()
    {
        String finalPrompt = "";
        String textQuestion = textQuestionBox.getText();
        String promptType = opChoice.getValue() != null ? opChoice.getValue().toString() : "";
        String structure = structureMenu.getValue() != null ? structureMenu.getValue().toString() : "";
        String purpose = purposeTextBox.getText();
        boolean isHumanized = humanize.isSelected();
        String maxWordsCounter = maxWords.getText();
        String otherTopic = relatedTopic.getText();
        boolean mentionOpinion = opinionCheckBox.isSelected();

        try {
            if (promptType == "Text") {
                checkEmptyFields(promptType, apiKey2.getText(), textQuestion, structure, purpose);

                if(isHumanized)
                {
                    prompt.append("Generate a clear prompt to generate text, do any assumption and elaborate based on the following data:")
                            .append("Type of prompt: ").append(promptType).append(",")
                            .append("Text: ").append(textQuestion).append(",")
                            .append("Desired Structure: ").append(structure).append(",")
                            .append("Purpose: ").append(purpose).append(",")
                            .append("Write it as if a human wrote it").append(",")
                            .append("Maximum words:").append(maxWordsCounter).append(",")
                            .append("Related it to the topic:").append(otherTopic).append(",");
                    if(mentionOpinion)
                    {
                        prompt.append("Add an opinion as if you were me");
                    }
                }
                else
                {
                    prompt.append("Generate a clear prompt to generate a Question, do any assumption and elaborate based on the following data:")
                            .append("Type of prompt: ").append(promptType).append(",")
                            .append("Text: ").append(textQuestion).append(",")
                            .append("Desired Structure: ").append(structure).append(",")
                            .append("Purpose: ").append(purpose).append(",")
                            .append("Maximum words:").append(maxWordsCounter).append(",")
                            .append("Related it to the topic:").append(otherTopic).append(",");
                    if(mentionOpinion)
                    {
                        prompt.append("Add an opinion as if you were me");
                    }
                }
            } else {
                checkEmptyFields(promptType, apiKey2.getText(), textQuestion, structure, purpose);

                if (isHumanized) {
                    prompt.append("Generate a clear prompt to generate text, do any assumption and elaborate based on the following data:")
                            .append("Type of prompt: ").append(promptType).append(",")
                            .append("Text: ").append(textQuestion).append(",")
                            .append("Desired Structure: ").append(structure).append(",")
                            .append("Purpose: ").append(purpose).append(",")
                            .append("Write it as if a human wrote it").append(",")
                            .append("Maximum words:").append(maxWordsCounter).append(",")
                            .append("Related it to the topic:").append(otherTopic).append(",");
                    if (mentionOpinion) {
                        prompt.append("Add an opinion as if you were me");
                    }
                } else {
                    prompt.append("Generate a clear prompt to generate text, do any assumption and elaborate based on the following data:")
                            .append("Type of prompt: ").append(promptType).append(",")
                            .append("Text: ").append(textQuestion).append(",")
                            .append("Desired Structure: ").append(structure).append(",")
                            .append("Purpose: ").append(purpose).append(",")
                            .append("Maximum words:").append(maxWordsCounter).append(",")
                            .append("Related it to the topic:").append(otherTopic).append(",");
                    if (mentionOpinion) {
                        prompt.append("Add an opinion as if you were me");
                    }
                }
            }

            finalPrompt = prompt.toString();
            prompt = new StringBuilder();
//            System.out.println(finalPrompt);
            LLMPrompt(finalPrompt, 1);
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Empty Field", e.getMessage());
        }
    }

    @FXML
    private void generatePrompt() {
        String finalPrompt = "";
        String topicText = topic.getText();
        String language = languages.getValue() != null ? languages.getValue().toString() : "";
        String languageVar = languageVersion.getText();
        String code = existingCode.getText();
        String prob = problemStatement.getText();
        String assumpts = assumptions.getText();
        String constrs = constraints.getText();

        try {
            if (existingCode.isDisabled()) {
                checkEmptyFields(topicText, language, languageVar, prob);

                prompt.append("Generate a clear prompt, do any assumption and elaborate based on the following data:")
                        .append("Topic: ").append(topicText).append(",")
                        .append("Programming Language: ").append(language).append(",")
                        .append("Language Version: ").append(languageVar).append(",")
                        .append("I want to: ").append(prob).append(",")
                        .append("Assuming that: ").append(assumpts).append(",")
                        .append("With the following constraints: ").append(constrs).append(",");
            } else {
                checkEmptyFields(topicText, language, languageVar, prob, code);

                prompt.append("Generate a clear prompt, do any assumption and elaborate based on the following data:")
                        .append("Given the following code: ").append(code).append(",")
                        .append("Topic: ").append(topicText).append(",")
                        .append("Programming Language: ").append(language).append(",")
                        .append("version: ").append(languageVar).append(",")
                        .append("I want to: ").append(prob).append(",")
                        .append("Assuming that: ").append(assumpts).append(",")
                        .append("With the following constraints: ").append(constrs).append(",");
            }

            finalPrompt = prompt.toString();
            prompt = new StringBuilder();
            LLMPrompt(finalPrompt, 0);

            generateButton.setVisible(false);
            copyButton.setVisible(true);
            regenerateButton.setVisible(true);
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Empty Field", e.getMessage());
        }
    }

    private void checkEmptyFields(String... fields) {
        for (String field : fields) {
            if (field.trim().isEmpty()) {
                throw new IllegalArgumentException("Please fill in all required fields.");
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}

