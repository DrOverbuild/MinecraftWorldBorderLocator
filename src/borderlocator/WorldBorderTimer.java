/*
 * Copyright 2014 Jasper Reddin.
 * All Rights Reserved.
 */

package borderlocator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author jasper
 */
public class WorldBorderTimer extends JFrame implements ActionListener{

	int initialSideLength;
	int finalSideLength;
	int totalTime;
	int elapsedTime;

	JButton pause = new JButton("Pause");
	JButton stop = new JButton("Stop");

	JTextField elapsedTimeField = new JTextField(20);
	JTextField distanceFromOrigin = new JTextField(20);

	WorldBorderLocator home;

	Timer timer;

	public WorldBorderTimer(int initialSideLength, int finalSideLength, int totalTime, WorldBorderLocator home){
		super("World Border Locator");

		this.initialSideLength = initialSideLength;
		this.finalSideLength = finalSideLength;
		this.totalTime = totalTime;
		this.home = home;

		home.setVisible(false);

		// TODO Initialize components here

		JPanel p1 = new JPanel(new BorderLayout());
		p1.add(new JLabel("Elapsed Time: "), BorderLayout.WEST);
		elapsedTimeField.setFont(new Font(Font.MONOSPACED,Font.PLAIN,18));
		elapsedTimeField.setHorizontalAlignment(JTextField.RIGHT);
		elapsedTimeField.setEditable(false);
		p1.add(elapsedTimeField);

		JPanel p2 = new JPanel(new BorderLayout());
		p2.add(new JLabel("Distance from (0,0): "), BorderLayout.WEST);
		distanceFromOrigin.setFont(new Font(Font.MONOSPACED,Font.PLAIN,18));
		distanceFromOrigin.setHorizontalAlignment(JTextField.RIGHT);
		distanceFromOrigin.setEditable(false);
		p2.add(distanceFromOrigin);

		pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(pause.getText().equals("Pause")){
					pause();
				}else{
					resume();
				}
			}
		});
		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		JPanel p3 = new JPanel(new BorderLayout());
		p3.add(pause, BorderLayout.CENTER);
		p3.add(stop,BorderLayout.EAST);

		setLayout(new FlowLayout());
		JPanel main = new JPanel(new GridLayout(3, 1));
		main.setBorder(new EmptyBorder(1, 2, 1, 2));
		main.add(p1);
		main.add(p2);
		main.add(p3);
		add(main);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);

		elapsedTimeField.setText(parseElapsedTime(elapsedTime));
		distanceFromOrigin.setText(getDistance()+" blocks");
		timer = new Timer(1000, this);
		timer.setInitialDelay(1000);
		timer.start();
	}

	@Override
	public void dispose(){
		stop();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		elapsedTime++;
		String time = parseElapsedTime(elapsedTime);
		elapsedTimeField.setText(time);
		distanceFromOrigin.setText(getDistance()+" blocks");
	}

	public static String parseElapsedTime(int elapsedTime) {
		int hours = elapsedTime/3600;
		elapsedTime=elapsedTime%3600;
		int minutes = elapsedTime/60;
		elapsedTime=elapsedTime%60;
		int seconds = elapsedTime;

		String time = "";

		if(hours<10){
			time+="0"+hours+":";
		}else{
			time+=hours+":";
		}

		if(minutes<10){
			time+="0"+minutes+":";
		}else{
			time+=minutes+":";
		}

		if(seconds<10){
			time+="0"+seconds;
		}else{
			time+=seconds+"";
		}

		return time;
	}

	public double getDistance(){
		double i = (double)initialSideLength;
		double f = (double)finalSideLength;
		double t = (double)totalTime;
		double x = (double)elapsedTime;
		double y = (0.5)*(i-((i-f)*(x/t)));
		if(y<(f/2d)){
			y=(f/2d);
		}
		double m = (double)Math.round(y * 1000) / 1000;
		return m;
	}

	public void pause(){
		timer.stop();
		pause.setText("Resume");
	}

	public void resume(){
		timer.start();
		pause.setText("Pause");
	}

	public void stop(){
		timer.stop();
		super.dispose();
		home.setVisible(true);
	}

}
