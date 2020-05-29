package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import utilities.I18N;

public class Manager {
    public static Label addLabelToGridPane(GridPane table, Font font, Boolean bind, String text,
                                           HPos hPos, boolean visibility, int columnIndex, int rowIndex, int columnSpan) {
        Label label = new Label();
        label.setFont(font);
        if (bind) {
            label.textProperty().bind(I18N.createStringBinding(text));
        } else {
            label.setText(text);
        }
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        GridPane.setHalignment(label, hPos);
        GridPane.setValignment(label, VPos.CENTER);
        GridPane.setMargin(label, new Insets(12, 25, 12, 25));
        GridPane.setColumnSpan(label, columnSpan);
        label.setVisible(visibility);
        table.add(label, columnIndex, rowIndex);
        return label;
    }

    public static TextField addTextFieldToGridPane(GridPane table, HPos hPos, boolean real, int columnIndex, int rowIndex) {
        TextField textField = new TextField();
        if (real) {
            realTextField(textField);
        } else {
            decimalTextField(textField);
        }
        GridPane.setHalignment(textField, hPos);
        GridPane.setValignment(textField, VPos.CENTER);
        GridPane.setMargin(textField, new Insets(12, 25, 12, 25));
        table.add(textField, columnIndex, rowIndex);
        return textField;
    }

    private static void realTextField(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private static void decimalTextField(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d+(\\.)?\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d(\\.)]", ""));
            }
        });
    }

    public static Button addButtonToGridPane(GridPane table, Font font, String text, HPos hPos, int columnIndex, int rowIndex, int columnSpan) {
        Button button = new Button();
        button.textProperty().bind(I18N.createStringBinding(text));
        button.setFont(font);
        button.setWrapText(true);
        GridPane.setHalignment(button, hPos);
        GridPane.setValignment(button, VPos.CENTER);
        GridPane.setMargin(button, new Insets(12, 25, 12, 25));
        GridPane.setColumnSpan(button, columnSpan);
        table.add(button, columnIndex, rowIndex);
        return button;
    }
}
