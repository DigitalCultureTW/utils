/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.digitalculture.temp;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import tw.digitalculture.utils.Constants;
import static tw.digitalculture.utils.Constants.*;

public class FilterFrame extends JFrame {

    public static void main(String[] args) {
        FilterFrame ff = new FilterFrame();
        ff.setVisible(true);
    }

    JPanel panel = new javax.swing.JPanel();
    JPanel jPanel1 = new javax.swing.JPanel();
    JLabel lblMinimal = new javax.swing.JLabel();
    JTextField txtMinimal = new javax.swing.JTextField();
    JPanel jPanel2 = new javax.swing.JPanel();
    JLabel lblFiletype = new javax.swing.JLabel();
    JTextField txtFiletype = new javax.swing.JTextField();
    JPanel jPanel3 = new javax.swing.JPanel();
    JButton okButton = new javax.swing.JButton();

    public FilterFrame() {
        super(APP_TITLE);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel.setLayout(new java.awt.GridLayout(3, 1, 1, 3));
        jPanel1.setLayout(new java.awt.GridLayout(1, 2));
        jPanel2.setLayout(new java.awt.GridLayout(1, 2));
        lblMinimal.setText("請設定檔案大小下限(Mb, 0為不設限):");
        txtMinimal.setText("0");
        lblFiletype.setText("請設定選取檔案類型，以分號隔開(如jpg;png):");

        panel.add(jPanel1);
        panel.add(jPanel2);
        panel.add(jPanel3);

        jPanel1.add(lblMinimal);
        jPanel1.add(txtMinimal);
        jPanel2.add(lblFiletype);
        jPanel2.add(txtFiletype);
        jPanel3.add(okButton);

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setVisible(false);
                dispose();
            }
        });
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panel, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
        );
        Constants.setFont(this.getComponents());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setResizable(false);
        setAlwaysOnTop(true);
        pack();
        this.setLocation(dim.width / 2 - this.getSize().width / 2,
                dim.height / 2 - this.getSize().height / 2);
    }

}
