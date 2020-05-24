package view;

import exceptions.AppException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utilities.I18N;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private Stage stage;
    File file;

    public void bind() {
        buttonLoad.textProperty().bind(I18N.createStringBinding("button.load"));
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

    @FXML
    public void loadFile(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        String path = System.getProperty("user.dir");
        fileChooser.setInitialDirectory(new File(path + "/"));
        file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                fileName.setText(I18N.get("uploadedFile") + "\n" + file.getName());
            } catch (Exception e) {
                showError(new AppException(I18N.get("INVALID_FORMAT") + I18N.get("INPUT_FILE_FORMAT"), e));
                e.printStackTrace();
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void showError(AppException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(600);
        alert.setTitle(I18N.get("error"));
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
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
        if (file == null) {
            fileName.setText(I18N.get("label.fileName"));
        } else {
            fileName.setText(I18N.get("uploadedFile") + "\n" + file.getName());
        }
    }

    public void translateToEnglish(ActionEvent actionEvent) {
        I18N.setLocale(Locale.ENGLISH);
        if (file == null) {
            fileName.setText(I18N.get("label.fileName"));
        } else {
            fileName.setText(I18N.get("uploadedFile") + "\n" + file.getName());
        }
    }
}
