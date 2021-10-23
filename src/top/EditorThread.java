package top;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.io.IOException;
import java.util.LinkedList;

public class EditorThread{
    public static volatile String path;
    public static volatile int def;
    public static volatile String temp;
    public static volatile String text;
    public static volatile String damnback;
    public static volatile LinkedList<String> tempText = new LinkedList<>();
    public static volatile int AutoSaveTime;
    public static void main(String[] args) {
        def = 0;
        new settings().start();
        build();
    }

    public static void build() {
        JFrame frameMain = new JFrame("koishi的文本编辑器~");
        frameMain.setBounds(400,300,700,450);
        frameMain.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        JMenuBar menuBar = new JMenuBar();
        frameMain.setJMenuBar(menuBar);
        JTextArea area = new JTextArea();

        JMenu edit = new JMenu("文件File");
        JMenu op = new JMenu("操作operation");
        JMenu save_as = new JMenu("另存为Save as...");
        JMenu option = new JMenu("设置Options");
        JMenu exit = new JMenu("退出Exit");

        save_as.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                new guard().Save_As(area.getText());
                new FileOperation().create(EditorThread.path,guard.name);
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });

        exit.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                System.exit(0);
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });

        option.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                new settings().setUI();
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

        JMenuItem itemBack = new JMenuItem("上个历史版本History",'H');
        JMenuItem itemClear = new JMenuItem("清空ALL Clear",'A');
        JMenuItem backClear = new JMenuItem("取消清空backClear");
        itemBack.addActionListener(ea->{
            String TempTemp = temp;
            temp = area.getText();
            area.setText(TempTemp);
        });
        itemClear.addActionListener(e -> {
            backClear.setEnabled(true);
            damnback = area.getText();
            area.setText("");
        });

        backClear.addActionListener(eeee ->{
            backClear.setEnabled(false);
            area.setText(damnback);
        });
        backClear.setEnabled(false);

        JMenuItem itemNew = new JMenuItem("创建新文件Create new file",'C');
        itemNew.setMnemonic('C');
        itemNew.addActionListener(ex->{
            new fileCreate().UI();
        });
        JMenuItem itemOpen = new JMenuItem("打开文件Open file",'O');
        itemOpen.setMnemonic('O');
        itemOpen.addActionListener(actionEvent->{
            try {
                path = new filiter().file();
                text = new FileOperation().Read(path);
                def = 1;
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                    | UnsupportedLookAndFeelException | IOException e) {
                e.printStackTrace();
            }
            area.setText(text);
            temp = area.getText();
        });
        JMenuItem itemSave = new JMenuItem("保存Save",'S');
        itemSave.setMnemonic('S');
        itemSave.addActionListener(ee-> new FileOperation().Write(path,area.getText()));

        edit.add(itemNew);
        edit.addSeparator();
        edit.add(itemOpen);
        edit.addSeparator();
        edit.add(itemSave);

        menuBar.add(edit);
        menuBar.add(op);
        menuBar.add(save_as);
        menuBar.add(option);
        menuBar.add(exit);

        op.add(itemBack);
        op.addSeparator();
        op.add(itemClear);
        op.addSeparator();
        op.add(backClear);

        area.setLineWrap(false);
        area.setBounds(0,0,700,450);
        frameMain.add(area);
        JScrollPane scrollPane = new JScrollPane(area);
        frameMain.add(scrollPane);
        panel.setVisible(true);
        frameMain.setVisible(true);
        Thread threadAutoSave = new Thread(()->{
            while(true){
                try {
                    Thread.sleep(50);
                    if (def!=0) {
                        Thread.sleep(29500);
                        new FileOperation().Write(path, area.getText());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.print("auto save failed");
                }
            }
        });
        Thread threadTemp = new Thread(()->{
            try {
                int i = 0;
                String Temp;
                while(true){
                    Thread.sleep(3000);
                    Temp = area.getText();
                    if (!Temp.equals(area.getText())){
                        while (true) {
                            if (i >= 2) {
                                i = 0;
                                tempText.removeFirst();
                                break;
                            }
                            tempText.add(area.getText());
                            i++;
                            System.out.println("record");
                        }
                        tempText.removeFirst();
                        tempText.add(area.getText());
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadAutoSave.start();
        threadTemp.start();
    }

    public void threadU(){

    }
}
  