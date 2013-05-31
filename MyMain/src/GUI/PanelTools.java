package GUI;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

import Controller.Controller;

public class PanelTools extends JPanel {
	private JButton buttonFileOpen;
	private JButton buttonFileSave;
	private JButton buttonConnect;
	private JButton buttonAdd;
	private JButton buttonRemove;
	private JButton buttonSearch;
	
	private String controllerClassName;

	public PanelTools(final Controller controller, final FrameMain mainFrame) {
		setBackground(Color.GRAY);

		controllerClassName = controller.getClass().toString();
		
		this.buttonFileOpen = new JButton("Загрузить");
		this.buttonFileOpen.addActionListener(controller.getListner("LOAD"));
		add(buttonFileOpen);
		if(controllerClassName.equals("class Controller.ControllerServer")){
			this.buttonFileSave = new JButton("Сохранить");
			this.buttonFileSave.addActionListener(controller.getListner("SAVE"));
			add(buttonFileSave);
		} else {
			this.buttonConnect = new JButton("Соединиться");
			this.buttonConnect.addActionListener(controller.getListner("CONNECT"));
			add(buttonConnect);
		}
		this.buttonAdd = new JButton("Добавить");
		this.buttonAdd.addActionListener(controller.getListner("ADD"));
		add(buttonAdd);
		this.buttonRemove = new JButton("Удалить");
		this.buttonRemove.addActionListener(controller.getListner("DELETE"));
		add(buttonRemove);
		this.buttonSearch = new JButton("Поиск");
		this.buttonSearch.addActionListener(mainFrame.getListener("swFIND"));
		add(buttonSearch);
	}

	public void setEnabled(boolean flag) {
		if(controllerClassName.equals("class Controller.ControllerServer")){
			this.buttonFileSave.setEnabled(flag);			
		}
		this.buttonAdd.setEnabled(flag);
		this.buttonRemove.setEnabled(flag);
		this.buttonSearch.setEnabled(flag);
	}
}
