package top;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

class fileCreate {
    public static volatile String name;
    public void UI() {
        AtomicReference<String> d = new AtomicReference<>("");
        AtomicInteger def = new AtomicInteger();
        Thread threadIf = new Thread(()->{
            while (true) {
                try {
                    Thread.sleep(10);
                    if (d.equals(new AtomicReference<>(""))) def.set(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadIf.start();
        JFrame frame = new JFrame("新建文件..");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setSize(300,210);
        JPanel panel = new JPanel();
        JTextField textField = new JTextField("在此清空并输入文件名");
        JButton buttonSelect = new JButton("选择文件创建路径");
        buttonSelect.addActionListener(eee->{
            try {
                EditorThread.path = new filiter().dictionary();
                d.set(EditorThread.path);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                    | UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }
        });
        JButton confirm = new JButton("确认");
        confirm.addActionListener(ex->{
            if(!def.equals(new AtomicInteger(1))){
                if (!textField.getText().equals("")) {
                    EditorThread.def = 1;
                    name = textField.getText();
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(EditorThread.path).append("\\").append(textField.getText());
                    EditorThread.path = String.valueOf(stringBuffer);
                    new FileOperation().create(EditorThread.path,name);
                } else {
                    textField.setText("文件名不能为空!");
                }
                frame.dispose();
            } else {
                textField.setText("请选择文件夹！");
            }
        });
        JLabel label = new JLabel();
        textField.setBounds(20,20,200,40);
        label.setBounds(10,70,280,40);
        buttonSelect.setBounds(100,110,100,30);
        confirm.setBounds(100,160,100,30);
        panel.add(textField);
        panel.add(buttonSelect);
        panel.add(confirm);
        panel.setVisible(true);
        frame.add(panel);
        frame.setVisible(true);
        Thread set = new Thread(()->{
            try {
                Thread.sleep(50);
                label.setText("路径:"+EditorThread.path);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        set.start();
    }
}
