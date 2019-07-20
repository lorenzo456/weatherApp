package weatherApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DisplayGui
{
	private JFrame f = new JFrame("WeatherApp");
	private JButton button = new JButton("Get Weather Results");
    JTextArea display = new JTextArea(10,30);
    JTextArea results = new JTextArea(10,30);
    
	private ApiCall api;
	private JPanel topPanel, midPanel, bottomPanel, panel, panel2;
	
	public DisplayGui(ApiCall api) 
	{		
		this.api = api;
		CreateGUI();
	}
	
	private void CreateGUI() 
	{
		f.setSize(600,600);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//f.setResizable(false);
		
		GridLayout frameLayout = new GridLayout(3,1);
		topPanel = new JPanel();
		midPanel = new JPanel();
		bottomPanel = new JPanel();
		
		f.setLayout(frameLayout);
		
		display.setVisible(true);
		display.setEditable(false);
		
		JLabel searchLabel = new JLabel("Search City"); 
		JTextField searchfield = new JTextField("Rotterdam,NL");
		
		JLabel currentCityLabel = new JLabel("Current city");
		JTextField currentCity = new JTextField("Rotterdam,NL");		
		currentCity.setEditable(false);
		
		
        JTextArea searchBar = new JTextArea(5,20);
        searchBar.setVisible(true);
        searchBar.setBackground(Color.blue);
        searchBar.setEditable(false);
        
		topPanel.add(display);
		topPanel.add(button);
		
		midPanel.add(currentCityLabel);
		midPanel.add(currentCity);
		
		bottomPanel.add(searchLabel);
		bottomPanel.add(searchfield);
		bottomPanel.add(searchBar);		

		f.add(topPanel);
		f.add(midPanel);
		f.add(bottomPanel);
		f.pack();
		f.setVisible(true);

		button.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				display.setText(null);
				
				try {
					api.sendGet();
					display.append("City: " + api.GetCurrentCityName() + "\n" + "Weather description: " + api.GetCurrentDescription() + "\n" + api.GetCurrentTemperature() + " Celcius");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	
	
}
