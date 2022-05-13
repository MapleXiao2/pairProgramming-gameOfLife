package com.maple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class Frame extends JFrame {
    private JButton setMapBtn = new JButton("设置地图大小");
    private JButton startGameBtn = new JButton("开始游戏");
    private JLabel durationPromtLabel = new JLabel("动画间隔设置(默认200ms)",SwingConstants.CENTER);
    private JTextField durationTextField = new JTextField();
    /**
     * 游戏是否开始的标志
     */
    private boolean isStart = false;

    /**
     * 游戏结束的标志
     */
    private boolean stop = false;

    private Cell[][] cellMatrix;
    private JPanel buttonPanel = new JPanel(new GridLayout(2, 2));
    private JPanel gridPanel = new JPanel();

    private JTextField[][] textMatrix;


    //动画间隔
    private int duration = Constants.DEFAULT_DURATION;

    public Frame() {
        setTitle("生命游戏");
        setMapBtn.addActionListener(new OpenFileActioner());
        startGameBtn.addActionListener(new StartGameActioner());

        buttonPanel.add(setMapBtn);
        buttonPanel.add(startGameBtn);
        buttonPanel.add(durationPromtLabel);
        buttonPanel.add(durationTextField);
        buttonPanel.setBackground(Color.WHITE);

        getContentPane().add("North", buttonPanel);

        this.setSize(900, 900);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private class OpenFileActioner implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
                new AttributesChooser().showDialog(null,"设置地图大小");
        }
    }

    private class AttributesChooser extends JPanel
    {
        private JTextField length;
        private JTextField width;
        private JButton okButton;
        private boolean ok;
        private JDialog dialog;

        public AttributesChooser()
        {
            setLayout(new BorderLayout());

            // construct a panel with username and password fields

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2, 2));
            panel.add(new JLabel("length:"));
            panel.add(length = new JTextField(""));
            panel.add(new JLabel("width:"));
            panel.add(width = new JTextField(""));
            add(panel, BorderLayout.CENTER);

            // create Ok and Cancel buttons that terminate the dialog

            okButton = new JButton("Ok");
            okButton.addActionListener(event -> {
                ok = true;
                Constants.LENGTH = Integer.valueOf(length.getText());
                Constants.WIDTH = Integer.valueOf(width.getText());
                dialog.setVisible(false);
            });

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(event -> dialog.setVisible(false));

            // add buttons to southern border

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);
            add(buttonPanel, BorderLayout.SOUTH);
        }

        /**
         * Show the chooser panel in a dialog.
         * @param parent a component in the owner frame or null
         * @param title the dialog window title
         */
        public boolean showDialog(Component parent, String title)
        {
            ok = false;

            // locate the owner frame

            java.awt.Frame owner = null;
            if (parent instanceof java.awt.Frame)
                owner = (java.awt.Frame) parent;
            else
                owner = (java.awt.Frame) SwingUtilities.getAncestorOfClass(java.awt.Frame.class, parent);

            // if first time, or if owner has changed, make new dialog

            if (dialog == null || dialog.getOwner() != owner)
            {
                dialog = new JDialog(owner, true);
                dialog.add(this);
                dialog.getRootPane().setDefaultButton(okButton);
                dialog.pack();
            }

            // set title and show dialog

            dialog.setTitle(title);
            dialog.setSize(200,140);
            dialog.setVisible(true);
            return ok;
        }
    }





    private class StartGameActioner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            cellMatrix = Matrix.initial();
            initGridLayout();
            if (!isStart) {
                //获取时间
                try {
                    duration = Integer.parseInt(durationTextField.getText().trim());
                } catch (NumberFormatException e1) {
                    duration = Constants.DEFAULT_DURATION;
                }
                new Thread(new GameControlTask()).start();
                isStart = true;
                stop = false;
                startGameBtn.setText("暂停游戏");
            } else {
                stop = true;
                isStart = false;
                showMatrix(cellMatrix);
                startGameBtn.setText("开始游戏");
            }
        }
    }

    private void showMatrix(Cell[][] cells) {
        int[][] a = new int[Constants.WIDTH][Constants.LENGTH];
        for (int i = 0; i < Constants.WIDTH; i++) {
            for (int j = 0; j < Constants.LENGTH; j++) {
                a[i][j] = cells[i][j].isLive() ? 1 : 0;
            }
        }
        for (int y = 0; y < a.length; y++) {
            for (int x = 0; x < a[0].length; x++) {
                if (a[y][x] == 1) {
                    textMatrix[y][x].setBackground(Color.BLACK);
                } else {
                    textMatrix[y][x].setBackground(Color.WHITE);
                }
            }
        }
    }

    private void initGridLayout() {
        int rows = Constants.WIDTH;
        int cols = Constants.LENGTH;
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(rows, cols));
        textMatrix = new JTextField[rows][cols];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                JTextField text = new JTextField();
                textMatrix[y][x] = text;
                gridPanel.add(text);
            }
        }
        add("Center", gridPanel);
    }
    private class GameControlTask implements Runnable {
        @Override
        public void run() {
            while (!stop) {
                Matrix.updateCells(cellMatrix);
                showMatrix(cellMatrix);
                try {
                    TimeUnit.MILLISECONDS.sleep(duration);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
