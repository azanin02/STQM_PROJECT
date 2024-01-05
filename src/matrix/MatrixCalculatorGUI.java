package matrix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class MatrixCalculatorGUI {
	 private JFrame frame;
	    private JTextField[][] matrixFieldsA, matrixFieldsB;
	    private JTextArea resultTextArea;
	    private int rowsA, colsA, rowsB, colsB;

	    public MatrixCalculatorGUI() {
	        rowsA = Integer.parseInt(JOptionPane.showInputDialog("Enter number of rows for Matrix A:"));
	        colsA = Integer.parseInt(JOptionPane.showInputDialog("Enter number of columns for Matrix A:"));
	        rowsB = Integer.parseInt(JOptionPane.showInputDialog("Enter number of rows for Matrix B:"));
	        colsB = Integer.parseInt(JOptionPane.showInputDialog("Enter number of columns for Matrix B:"));

	        frame = new JFrame("Matrix Calculator");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLayout(new BorderLayout(10, 10));

	        matrixFieldsA = new JTextField[rowsA][colsA];
	        matrixFieldsB = new JTextField[rowsB][colsB];
	        JPanel matrixPanelA = createMatrixPanel(matrixFieldsA, "Matrix A", rowsA, colsA);
	        JPanel matrixPanelB = createMatrixPanel(matrixFieldsB, "Matrix B", rowsB, colsB);

	        JPanel operationPanel = createOperationPanel();

	        resultTextArea = new JTextArea(10, 30);
	        resultTextArea.setEditable(false);
	        JScrollPane scrollPane = new JScrollPane(resultTextArea);

	        frame.add(matrixPanelA, BorderLayout.WEST);
	        frame.add(matrixPanelB, BorderLayout.EAST);
	        frame.add(operationPanel, BorderLayout.NORTH);
	        frame.add(scrollPane, BorderLayout.CENTER);

	        frame.pack();
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	    }

	    private JPanel createMatrixPanel(JTextField[][] matrixFields, String title, int rows, int cols) {
	        JPanel panel = new JPanel(new BorderLayout());
	        JPanel matrix = new JPanel(new GridLayout(rows, cols, 5, 5));
	        for (int i = 0; i < rows; i++) {
	            for (int j = 0; j < cols; j++) {
	                matrixFields[i][j] = new JTextField(2);
	                matrix.add(matrixFields[i][j]);
	            }
	        }
	        panel.add(new JLabel(title), BorderLayout.NORTH);
	        panel.add(matrix, BorderLayout.CENTER);

	        JPanel controlPanel = new JPanel(new FlowLayout());
	        JButton clearButton = new JButton("Clear");
	        JButton fillRandomButton = new JButton("Random");
	        clearButton.addActionListener(e -> clearMatrix(matrixFields));
	        fillRandomButton.addActionListener(e -> fillRandom(matrixFields));
	        controlPanel.add(clearButton);
	        controlPanel.add(fillRandomButton);
	        panel.add(controlPanel, BorderLayout.SOUTH);

	        return panel;
	    }

	    private JPanel createOperationPanel() {
	        JPanel operationPanel = new JPanel(new FlowLayout());
	        JButton addButton = new JButton("A + B");
	        JButton subtractButton = new JButton("A - B");
	        JButton multiplyButton = new JButton("A * B");
	        addButton.addActionListener(this::addMatrices);
	        subtractButton.addActionListener(this::subtractMatrices);
	        multiplyButton.addActionListener(this::multiplyMatrices);
	        operationPanel.add(addButton);
	        operationPanel.add(subtractButton);
	        operationPanel.add(multiplyButton);

	        return operationPanel;
	    }

	    private void clearMatrix(JTextField[][] matrixFields) {
	        for (JTextField[] row : matrixFields) {
	            for (JTextField field : row) {
	                field.setText("");
	            }
	        }
	        resultTextArea.setText("");
	    }

	    private void fillRandom(JTextField[][] matrixFields) {
	        Random rand = new Random();
	        for (int i = 0; i < matrixFields.length; i++) {
	            for (int j = 0; j < matrixFields[i].length; j++) {
	                int value = rand.nextInt(10);
	                matrixFields[i][j].setText(Integer.toString(value));
	            }
	        }
	    }
	    
    private void addMatrices(ActionEvent e) {
        if (rowsA != rowsB || colsA != colsB) {
            JOptionPane.showMessageDialog(frame, "Matrices must have the same dimensions to add.");
            return;
        }

        StringBuilder steps = new StringBuilder("Addition of Matrices:\n");
        int[][] result = new int[rowsA][colsA];
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsA; j++) {
                int valueA = Integer.parseInt(matrixFieldsA[i][j].getText());
                int valueB = Integer.parseInt(matrixFieldsB[i][j].getText());
                result[i][j] = valueA + valueB;
                steps.append("Position (").append(i).append(",").append(j).append("): ")
                     .append(valueA).append(" + ").append(valueB).append(" = ").append(result[i][j]).append("\n");
            }
        }
        steps.append("\nResultant Matrix:\n");
        displayResult(result, steps.toString());
    }

    private void subtractMatrices(ActionEvent e) {
        if (rowsA != rowsB || colsA != colsB) {
            JOptionPane.showMessageDialog(frame, "Matrices must have the same dimensions to subtract.");
            return;
        }

        StringBuilder steps = new StringBuilder("Subtraction of Matrices:\n");
        int[][] result = new int[rowsA][colsA];
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsA; j++) {
                int valueA = Integer.parseInt(matrixFieldsA[i][j].getText());
                int valueB = Integer.parseInt(matrixFieldsB[i][j].getText());
                result[i][j] = valueA - valueB;
                steps.append("Position (").append(i).append(",").append(j).append("): ")
                     .append(valueA).append(" - ").append(valueB).append(" = ").append(result[i][j]).append("\n");
            }
        }
        steps.append("\nResultant Matrix:\n");
        displayResult(result, steps.toString());
    }

    private void multiplyMatrices(ActionEvent e) {
        if (colsA != rowsB) {
            JOptionPane.showMessageDialog(frame, "Number of columns in Matrix A must equal number of rows in Matrix B.");
            return;
        }

        StringBuilder steps = new StringBuilder("Multiplication of Matrices:\n");
        int[][] result = new int[rowsA][colsB];
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    int valueA = Integer.parseInt(matrixFieldsA[i][k].getText());
                    int valueB = Integer.parseInt(matrixFieldsB[k][j].getText());
                    result[i][j] += valueA * valueB;
                    steps.append("Position (").append(i).append(",").append(j).append(") += ")
                         .append(valueA).append(" * ").append(valueB).append(";\n");
                }
                steps.append("Position (").append(i).append(",").append(j).append(") result: ")
                     .append(result[i][j]).append("\n");
            }
        }
        steps.append("\nResultant Matrix:\n");
        displayResult(result, steps.toString());
    }

    private void displayResult(int[][] matrix, String steps) {
        StringBuilder sb = new StringBuilder(steps);
        for (int[] row : matrix) {
            for (int value : row) {
                sb.append(value).append("\t");
            }
            sb.append("\n");
        }
        resultTextArea.setText(sb.toString());
    }

    // ... (rest of your methods)

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MatrixCalculatorGUI::new);
    }
}

