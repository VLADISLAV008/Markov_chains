package view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
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
        customization(label, hPos, columnSpan);
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
        customization(textField, hPos, 1);
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
        customization(button, hPos, columnSpan);
        table.add(button, columnIndex, rowIndex);
        return button;
    }

    public static GridPane addGridPaneToGridPane(GridPane table, HPos hPos, int columnIndex, int rowIndex, int columnSpan) {
        GridPane gridPane = new GridPane();
        customization(gridPane, hPos, columnSpan);
        table.add(gridPane, columnIndex, rowIndex);
        return gridPane;
    }

    private static void customization(Node node, HPos hPos, int columnSpan)
    {
        GridPane.setHalignment(node, hPos);
        GridPane.setValignment(node, VPos.CENTER);
        GridPane.setMargin(node, new Insets(12, 25, 12, 25));
        GridPane.setColumnSpan(node, columnSpan);
    }
}
