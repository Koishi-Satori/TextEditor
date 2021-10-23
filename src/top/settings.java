package top;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class settings extends Thread{
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
                String temp = new FileOperation().Read(".\\data.set");
                int rue = 30000;
                Pattern p =Pattern.compile("\\d+");
                Matcher m =p.matcher(temp);
                while (m.find()) {
                    rue = Integer.parseInt(temp.substring(m.start(),m.end()));
                }
                EditorThread.AutoSaveTime = rue - 50;
                System.out.print(EditorThread.AutoSaveTime+"\n");
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setUI() {
        JFrame frame = new JFrame("设置自动保存时间");
        frame.setResizable(false);
        frame.setLocationByPlatform(true);
        frame.setSize(250,150);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        JTextField textField = new JTextField("30000");
        JButton button = new JButton("确认");
        JButton defaultButton = new JButton("恢复默认");

        textField.setBounds(10,20,230,40);
        button.setBounds(20,90,80,30);
        defaultButton.setBounds(150,90,80,30);

        button.addActionListener(e->{
            if (Objects.equals(textField.getText(),"")) textField.setText("数值不能为空！");
            else{if (Integer.parseInt(textField.getText())<=100)
                textField.setText("数值过小！");
                else {
                    new FileOperation().Write(".\\data.set","[setting option]\n" +
                            "{\n" +
                            "    AutoSave-time:"+Integer.parseInt(textField.getText())+";\n" +
                            "}");
                    frame.dispose();
                }
            }
        });

        defaultButton.addActionListener(ex ->{
            textField.setText("30000");
                new FileOperation().Write(".\\data.set","[setting option]\n" +
                        "{\n" +
                        "    AutoSave-time:30000;\n" +
                        "}");
                frame.dispose();
        });

        panel.add(textField);
        panel.add(button);
        panel.add(defaultButton);
        panel.setVisible(true);
        frame.add(panel);
        frame.setVisible(true);
        Thread set = new Thread(()->{
            try {
                Thread.sleep(50);
                if (!Objects.equals(textField.getText(),"")) if (Integer.parseInt(textField.getText())<=100)
                    textField.setText("数值过小！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        set.start();
    }
}
