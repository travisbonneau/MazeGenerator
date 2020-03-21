package maze_generator;

// Name: Travis Bonneau
// Date: 5/19/2016
// Class: Computer Science III
// Lab: MazeGenerator

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;

public class OptionsDialog extends JDialog {
    
    private JTextField rowField = new JTextField("25");
    private JTextField colField = new JTextField("25");
    private JRadioButton mazeRadioButton = new JRadioButton("Maze");
    private JRadioButton antiMazeRadioButton = new JRadioButton("Anti-Maze");
    private JButton okButton = new JButton("OK");
    private JButton cancelButton = new JButton("Cancel");
    
    
    public OptionsDialog(MazeGenerator owner) {
        rowField.setText(""+owner.getRows());
        colField.setText(""+owner.getCols());
        
        JPanel mazeOptionPanel = new JPanel(new GridLayout(2, 1));
        mazeOptionPanel.setBorder(new TitledBorder("Maze Type"));
        mazeOptionPanel.add(mazeRadioButton);
        mazeOptionPanel.add(antiMazeRadioButton);
        
        ButtonGroup mazeTypeGroup = new ButtonGroup();
        mazeTypeGroup.add(mazeRadioButton);
        mazeTypeGroup.add(antiMazeRadioButton);
        if(owner.isAntiMaze())
            antiMazeRadioButton.setSelected(true);
        else
            mazeRadioButton.setSelected(true);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        okButton.setSelected(true);
        okButton.addActionListener((ActionEvent) -> {
            setMaze(owner);
        });
        
        cancelButton.addActionListener((ActionEvent) -> {
            dispose();
        });
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Rows:"),gbc);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(rowField,gbc);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Cols:"),gbc);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(colField,gbc);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(mazeOptionPanel,gbc);
        
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(buttonPanel,gbc);
        
        
        setTitle("Maze Generator");
        validate();
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
        setModal(true);
        setVisible(true);
    }
    
    private void setMaze(MazeGenerator owner){
        String rowString = rowField.getText();
        String colString = colField.getText();
        int newRow = 25;
        int newCol = 25;
        
        if(rowString.matches("[0-9]+") && colString.matches("[0-9]+")){
            newRow = Integer.parseInt(rowString);
            newCol = Integer.parseInt(colString);
        }else{
            JOptionPane.showMessageDialog(this, "Only use numbers in the row and column field!", "Incorrect Input", JOptionPane.WARNING_MESSAGE);
            rowField.setText("");
            colField.setText("");
            return;
        }
        
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int numRows = (int)((screenHeight * 0.7) / owner.getCellSize());
        int numCols = (int)((screenWidth * 0.95) / owner.getCellSize());
        
        if(!(newRow>=2 && newRow<=numRows) || !(newCol>=2 && newCol<=numCols)){
            JOptionPane.showMessageDialog(this, "Use integers in the range of [2, "+numRows+"] for rows and [2, "+numCols+"] for columns.", "Input Out of Bounds", JOptionPane.WARNING_MESSAGE);
            rowField.setText("");
            colField.setText("");
            return;
        }
        
        owner.setAntiMaze(antiMazeRadioButton.isSelected());
        owner.setRowsAndCols(newRow, newCol);
        dispose();
    }
    
}
