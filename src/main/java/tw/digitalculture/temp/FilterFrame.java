/*
 * The MIT License
 *
 * Copyright 2017 Jonathan Chang, Chun-yien <ccy@musicapoetica.org>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
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
