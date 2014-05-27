package borderlocator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/*
 * Copyright 2014 Jasper Reddin.
 * All Rights Reserved.
 */

/**
 *
 * @author jasper
 */
public class WorldBorderLocator extends JFrame implements ActionListener, KeyListener{
	JTextField initalSideLength = new JTextField(10);
	JTextField finalSideLength = new JTextField(10);
	JTextField totalTime = new JTextField(10);
	JComboBox<String> timeUnits = new JComboBox<>(new String[]{"Seconds","Minutes","Hours"});
	JButton startTimer = new JButton("Start Timer");

	public WorldBorderLocator(){
		super("World Border Locator");

		setLayout(new FlowLayout());

		JPanel main = new JPanel(new GridLayout(4, 1));
		main.setBorder(new EmptyBorder(1,2,1,2));

		// TODO Initialize components here
		JPanel p1 = new JPanel(new BorderLayout());
		p1.add(new JLabel("Initial Side Length: "),BorderLayout.WEST);
		p1.add(initalSideLength,BorderLayout.EAST);
		initalSideLength.addKeyListener(this);
		main.add(p1);

		JPanel p2 = new JPanel(new BorderLayout());
		p2.add(new JLabel("Final Side Length: "),BorderLayout.WEST);
		p2.add(finalSideLength,BorderLayout.EAST);
		finalSideLength.addKeyListener(this);
		main.add(p2);

		JPanel p3 = new JPanel(new BorderLayout());
		p3.add(new JLabel("Total Time: "),BorderLayout.WEST);
		p3.add(totalTime,BorderLayout.CENTER);
		p3.add(timeUnits,BorderLayout.EAST);
		timeUnits.addKeyListener(this);
		main.add(p3);

		startTimer.addActionListener(this);
		getRootPane().setDefaultButton(startTimer);
		main.add(startTimer);

		add(main);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
	}

	@Override
	public void dispose(){
		System.exit(0);
	}

	public void close(){
		super.dispose();
	}

	public static void main(String[] args) {
		new WorldBorderLocator();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int initial = Integer.parseInt(initalSideLength.getText());
		int finalI = Integer.parseInt(finalSideLength.getText());
		int time = Integer.parseInt(totalTime.getText());

		String unit = (String)timeUnits.getSelectedItem();
		switch(unit){
			case "Minutes":
				time*=60;
				break;
			case "Hours":
				time*=3600;
				break;
			default:
				break;
		}

		new WorldBorderTimer(initial, finalI, time, this);

	}

	@Override
	public void keyTyped(KeyEvent e) {
		JTextField field = (JTextField)e.getSource();
		try{
			String s = field.getText()+e.getKeyChar();
			Integer.parseInt(s);
		}catch(NumberFormatException ex){
			e.consume();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
