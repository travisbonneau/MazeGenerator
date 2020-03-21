/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze_generator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author TravisBonneau
 */
public class CustomizeDialog extends JDialog {
    
    private MazeGenerator parent;
    private JButton applyButton = new JButton("Apply");
    private JButton cancelButton = new JButton("Cancel");
    private JTextField sizeTextField;
    private JPanel wallColorPanel;
    private JPanel backgroundColorPanel;
    private JPanel pathColorPanel;
    private JPanel solveColorPanel;
    
    
    CustomizeDialog(MazeGenerator parent, int cellSize, Color wallColor, Color backgroundColor, Color pathColor, Color solveColor) {
        this.parent = parent;
        
        JPanel sizePanel = new JPanel();
        JPanel wallPanel = new JPanel();
        JPanel backgroundPanel = new JPanel();
        JPanel pathPanel = new JPanel();
        JPanel solvePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        
        sizeTextField = new JTextField(""+cellSize, 2);
        
        wallColorPanel = new JPanel();
        wallColorPanel.setPreferredSize(new Dimension(20, 20));
        wallColorPanel.setBackground(wallColor);
        
        backgroundColorPanel = new JPanel();
        backgroundColorPanel.setPreferredSize(new Dimension(20, 20));
        backgroundColorPanel.setBackground(backgroundColor);
        
        pathColorPanel = new JPanel();
        pathColorPanel.setPreferredSize(new Dimension(20, 20));
        pathColorPanel.setBackground(pathColor);
       
        solveColorPanel = new JPanel();
        solveColorPanel.setPreferredSize(new Dimension(20, 20));
        solveColorPanel.setBackground(solveColor);
        
        sizePanel.add(new JLabel("Cell Size:"));
        sizePanel.add(sizeTextField);
        
        wallPanel.add(new JLabel("Wall Color:"));
        wallPanel.add(wallColorPanel);
        
        backgroundPanel.add(new JLabel("Background Color:"));
        backgroundPanel.add(backgroundColorPanel);
        
        pathPanel.add(new JLabel("Path Color:"));
        pathPanel.add(pathColorPanel);
        
        solvePanel.add(new JLabel("Solve Color:"));
        solvePanel.add(solveColorPanel);
        
        buttonPanel.add(applyButton);
        buttonPanel.add(cancelButton);
        
        setLayout(new GridLayout(6, 1));
        add(sizePanel);
        add(wallPanel);
        add(backgroundPanel);
        add(pathPanel);
        add(solvePanel);
        add(buttonPanel);
        
        setupButtonListeners();
        pack();
        setLocationRelativeTo(parent);
        setModal(true);
        setResizable(false);
        setVisible(true);
    }
    
    private void applyChanges(){
        String sizeString = sizeTextField.getText();
        int newSize = 20;
        
        if(sizeString.matches("[0-9]+")){
            newSize = Integer.parseInt(sizeString);
            if(newSize < 2 || newSize > 20){
                JOptionPane.showMessageDialog(parent, "Cell size must be within [2-20]", "Incorrect Cell Size", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        else {
            JOptionPane.showMessageDialog(parent, "Enter only Integers between [2-20]", "Incorrect Cell Size", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Color wallColor = wallColorPanel.getBackground();
        Color backgroundColor = backgroundColorPanel.getBackground();
        Color pathColor = pathColorPanel.getBackground();
        Color solveColor = solveColorPanel.getBackground();
        
        parent.setupCell(newSize, wallColor, backgroundColor, pathColor, solveColor);
        dispose();
    }
    
    private void setColor(JPanel panel){
        Color newColor = JColorChooser.showDialog(this, "Choose Color", panel.getBackground());
        if(newColor != null){
            panel.setBackground(newColor);
        }
    }
    
    private void setupButtonListeners(){
        applyButton.addActionListener((ActionEvent) -> {
            applyChanges();
        });
        cancelButton.addActionListener((ActionEvent) -> {
            dispose();
        });
        
        wallColorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setColor(wallColorPanel);
            }
        });
        backgroundColorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setColor(backgroundColorPanel);
            }
        });
        pathColorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setColor(pathColorPanel);
            }
        });
        solveColorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setColor(solveColorPanel);
            }
        });
    }
    
}
