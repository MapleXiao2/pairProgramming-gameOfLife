package com.maple;


import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MyFrame {
    public MyFrame() {
        JFrame jframe = new JFrame();
        jframe.setSize(425, 460);

        JPanel jpanel = new JPanel();
        jpanel.setBounds(0, 0, 400, 400);

        int count = 0;
        Cell[][] cell = Matrix.initial();

        jframe.setTitle("这是第" + count + "次演化");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setLocationRelativeTo(null);
        jframe.add(jpanel);
        jframe.setVisible(true);
        Graphics g = jpanel.getGraphics();

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                g.drawRect(i * 25, j * 25, 25, 25);
            }
        }

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (cell[i][j].isLive()) {
                    g.fillRect(i * 25, j * 25, 25, 25);
                } else {
                    g.drawRect(i * 25, j * 25, 25, 25);
                }
            }
        }

        long d1 = System.currentTimeMillis();
        while (true) {
            long d2 = System.currentTimeMillis();
            if (d2 - d1 > 2000) {
                d1 = d2;
                jframe.repaint();
                Matrix.updateCells(cell);
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        g.drawRect(i * 25, j * 25, 25, 25);
                    }
                }
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        if (cell[i][j].isLive()) {
                            g.fillRect(i * 25, j * 25, 25, 25);
                        } else {
                            g.drawRect(i * 25, j * 25, 25, 25);
                        }
                    }
                }
                count++;
                jframe.setTitle("这是第" + count + "次演化");
                if (count > 1000) break;
            }
        }
        JOptionPane.showMessageDialog(jframe, "在第" + count + "次演化达到平衡", "提示", JOptionPane.PLAIN_MESSAGE);
    }
}

