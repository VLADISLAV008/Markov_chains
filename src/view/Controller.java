package view;

import calculation.commands.Commands;
import entities.MarkovChain;
import entities.ReaderFromFile;
import exceptions.AppException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utilities.I18N;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Controller {
    @FXML
    public Label fileName;
    public Button buttonLoad;
    public Menu menuFile;
    public Menu menuLanguage;
    public Menu menuHelp;
    public MenuItem exit;
    public MenuItem menuLoadFile;
    public MenuItem help;
    public MenuItem about;
    public RadioMenuItem russian;
    public RadioMenuItem english;
    public Button mainMenu;
    public GridPane mainMenuContent;

    private Stage stage;
    private File file;
    private Commands commands;
    private MarkovChain markovChain;

    private void bind() {
        buttonLoad.textProperty().bind(I18N.createStringBinding("button.load"));
        mainMenu.textProperty().bind(I18N.createStringBinding("button.mainMenu"));
        menuFile.textProperty().bind(I18N.createStringBinding("menu.file"));
        menuLanguage.textProperty().bind(I18N.createStringBinding("menu.language"));
        menuHelp.textProperty().bind(I18N.createStringBinding("menu.help"));
        exit.textProperty().bind(I18N.createStringBinding("exit"));
        menuLoadFile.textProperty().bind(I18N.createStringBinding("button.load"));
        help.textProperty().bind(I18N.createStringBinding("menu.help"));
        about.textProperty().bind(I18N.createStringBinding("about"));
        russian.textProperty().bind(I18N.createStringBinding("russian"));
        english.textProperty().bind(I18N.createStringBinding("english"));
    }

    public void initialize() {
        commands = new Commands(mainMenuContent);
        bind();
    }

    @FXML
    public void loadFile(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        String path = System.getProperty("user.dir");
        fileChooser.setInitialDirectory(new File(path + "/"));
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                markovChain = ReaderFromFile.getMarkovChain(file);
                fileName.setText(I18N.get("uploadedFile") + "\n" + file.getName());
            } catch (FileNotFoundException e) {
                showError(I18N.get("FILE_NOT_LOADED"));
                e.printStackTrace();
            } catch (Exception e) {
                showError(I18N.get("INVALID_FORMAT") + I18N.get("INPUT_FILE_FORMAT"));
                e.printStackTrace();
            }
        }
    }

    public void createMainMenu(ActionEvent actionEvent) {
        deletePreviousTable(mainMenuContent);
        int rowIndex = 0;

        Manager.addLabelToGridPane(mainMenuContent, new Font("System Bold", 35), true,
                "button.mainMenu", HPos.CENTER, true, 0, rowIndex++, 1);

        ArrayList<Map.Entry<String, String>> setCommands = commands.getCommandsInfo();
        for (Map.Entry<String, String> e : setCommands) {
            if ("A1".equals(e.getKey())) {
                Manager.addLabelToGridPane(mainMenuContent, new Font("System Bold", 22), true,
                        "category.A", HPos.LEFT, true, 0, rowIndex++, 1);
            }

            Button button = Manager.addButtonToGridPane(mainMenuContent, new Font(16), e.getValue(), HPos.LEFT, 0, rowIndex++, 1);
            button.setOnAction(event -> {
                try {
                    deletePreviousTable(mainMenuContent);
                    commands.executeCommand(e.getKey(), markovChain);
                } catch (AppException ex) {
                    createMainMenu(actionEvent);
                    showError(ex.getMessage());
                } catch (Exception ex) {
                    Controller.showError(I18N.get("INVALID_INPUT_DATA"));
                }
            });
        }
    }

    private void deletePreviousTable(GridPane table) {
        table.getColumnConstraints().clear();
        table.getRowConstraints().clear();
        table.getChildren().clear();
        table.setGridLinesVisible(false);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(600);
        alert.setTitle(I18N.get("error"));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void helpAction() {
        informationDialog(I18N.get("HELP_MESSAGE") + I18N.get("INPUT_FILE_FORMAT"), I18N.get("menu.help"));
    }

    public void infoProgramme() {
        informationDialog(I18N.get("INFO_PROGRAMME"), I18N.get("about"));
    }

    public void informationDialog(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(850);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void deleteFile(ActionEvent actionEvent) {
        fileName.setText(I18N.get("label.fileName"));
        file = null;
    }

    public void translateToRussian(ActionEvent actionEvent) {
        I18N.setLocale(new Locale("ru", ""));
        translate(actionEvent);
    }

    public void translateToEnglish(ActionEvent actionEvent) {
        I18N.setLocale(Locale.ENGLISH);
        translate(actionEvent);
    }

    private void translate(ActionEvent actionEvent) {
        if (file == null) {
            fileName.setText(I18N.get("label.fileName"));
        } else {
            fileName.setText(I18N.get("uploadedFile") + "\n" + file.getName());
        }
    }
}
