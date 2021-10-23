package top;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class filiter {
    public String file() throws ClassNotFoundException,InstantiationException
            ,IllegalAccessException, UnsupportedLookAndFeelException {
        String path = null;
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "非字节码/格式文件","txt","ini","json","MD","MF","css","js",
                "java","c","cpp","py","bat","cmd");
        jFileChooser.setFileFilter(filter);
        jFileChooser.setDialogTitle("请选择文件路径");
        if (JFileChooser.APPROVE_OPTION == jFileChooser.showOpenDialog(null))
            path = jFileChooser.getSelectedFile().getAbsolutePath();
        return path;
    }

    public String dictionary() throws ClassNotFoundException,InstantiationException,
            IllegalAccessException,UnsupportedLookAndFeelException {
        String path = null;
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("文件夹","directories");
        jFileChooser.setFileFilter(filter);
        jFileChooser.setDialogTitle("请选择一个文件夹");
        if (JFileChooser.APPROVE_OPTION == jFileChooser.showOpenDialog(null))
            path = jFileChooser.getSelectedFile().getAbsolutePath();
        return path;
    }
}
