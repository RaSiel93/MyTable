package GUI;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import Controller.Controller;

public class PanelMenu extends JMenuBar {
	private JMenuItem loadFile;
	private JMenuItem saveFile;
	private JMenuItem saveAsFile;
	private JMenuItem closeFile;
	private JMenuItem exitItem;
	private JMenuItem addData;
	private JMenuItem removeData;
	private JMenuItem findData;
	private JMenuItem about;
	
	private String controllerClassName;

	public PanelMenu(Controller controller, FrameMain mainFrame) {
		controllerClassName = controller.getClass().toString();

		JMenu fileMenu = new JMenu("Файл");
		this.loadFile = new JMenuItem("Загрузить");
		this.loadFile.addActionListener(controller.getListner("LOAD"));
		this.loadFile.setAccelerator(KeyStroke
				.getKeyStroke('O', CTRL_DOWN_MASK));
		fileMenu.add(this.loadFile);
		
		if (controllerClassName.equals("class Controller.ControllerServer")) {
			this.saveFile = new JMenuItem("Сохранить");
			this.saveFile.setAccelerator(KeyStroke.getKeyStroke('S',
					CTRL_DOWN_MASK));
			this.saveFile.addActionListener(controller.getListner("SAVE"));
			fileMenu.add(this.saveFile);
			this.saveAsFile = new JMenuItem("Сохранить как...");
			this.saveAsFile.setAccelerator(KeyStroke.getKeyStroke('E',
					CTRL_DOWN_MASK));
			this.saveAsFile.addActionListener(controller.getListner("SAVE_AS"));
			fileMenu.add(this.saveAsFile);
		}
		this.closeFile = new JMenuItem("Закрыть");
		this.closeFile.setAccelerator(KeyStroke.getKeyStroke('Q',
				CTRL_DOWN_MASK));
		this.closeFile.addActionListener(controller.getListner("CLOSE"));
		this.exitItem = new JMenuItem("Выход");
		this.exitItem.addActionListener(controller.getListner("EXIT"));
		
		fileMenu.add(this.closeFile);
		fileMenu.add(this.exitItem);
		add(fileMenu);
		// -----------------------------------
		JMenu toolsMenu = new JMenu("Изменить");
		this.addData = new JMenuItem("Добавить");
		this.addData.addActionListener(controller.getListner("ADD"));
		this.addData
				.setAccelerator(KeyStroke.getKeyStroke('W', CTRL_DOWN_MASK));
		this.addData.setEnabled(false);
		this.removeData = new JMenuItem("Удалить");
		this.removeData.addActionListener(controller.getListner("DELETE"));
		this.removeData.setAccelerator(KeyStroke.getKeyStroke('D',
				CTRL_DOWN_MASK));
		this.removeData.setEnabled(false);
		toolsMenu.add(this.addData);
		toolsMenu.add(this.removeData);
		add(toolsMenu);
		// -----------------------------------
		JMenu findMenu = new JMenu("Поиск");
		this.findData = new JMenuItem("Поиск");
		this.findData.addActionListener(mainFrame.getListener("swFIND"));
		this.findData.setAccelerator(KeyStroke
				.getKeyStroke('F', CTRL_DOWN_MASK));
		findMenu.add(this.findData);
		add(findMenu);
		// -----------------------------------
		JMenu aboutMenu = new JMenu("О программе");
		this.about = new JMenuItem("Об авторе");
		this.about.addActionListener(controller.getListner("ABOUT"));
		aboutMenu.add(this.about);
		add(aboutMenu);
	}

	public void setEnabled(boolean flag) {
		if(controllerClassName.equals("class Controller.ControllerServer")){
				this.saveFile.setEnabled(flag);
				this.saveAsFile.setEnabled(flag);
		}
		this.closeFile.setEnabled(flag);
		this.addData.setEnabled(flag);
		this.removeData.setEnabled(flag);
		this.findData.setEnabled(flag);
	}
}
