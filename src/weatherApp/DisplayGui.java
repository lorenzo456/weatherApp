package weatherApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.json.JSONArray;
import org.json.JSONObject;

public class DisplayGui
{
	private JFrame f = new JFrame("WeatherApp");
	private JButton button = new JButton("Get Weather Results");
    JTextArea display = new JTextArea(10,30);
    JTextArea results = new JTextArea(10,30);
    private JList<String> countryList;

    
	private ApiCall api;
	private JPanel topPanel, midPanel, bottomPanel;
	
	private City[] cityArray = new City[209579];
    DefaultListModel<String> listModel = new DefaultListModel<>();

	public DisplayGui(ApiCall api) 
	{		
		this.api = api;
		CreateGUI();
		GetCitiesFromFile();
	}
	
	private void CreateGUI() 
	{
		f.setSize(600,600);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		
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
		
		
        JTextArea searchBar = new JTextArea(10,20);
        searchBar.setVisible(true);
        searchBar.setBackground(Color.blue);
        searchBar.setEditable(false);
        searchBar.setMaximumSize(new Dimension(10,25));
        searchBar.setLineWrap(true);
        searchBar.setWrapStyleWord(true);
 
        //create the list
        countryList = new JList<>(listModel);
        //add(countryList);

        JScrollPane scrollpane = new JScrollPane(countryList);

		topPanel.add(display);
		topPanel.add(button);
		
		midPanel.add(currentCityLabel);
		midPanel.add(currentCity);
		
		bottomPanel.add(searchLabel, BorderLayout.WEST);
		bottomPanel.add(searchfield, BorderLayout.WEST);
		bottomPanel.add(scrollpane, BorderLayout.EAST);		

		f.add(topPanel);
		f.add(midPanel);
		f.add(bottomPanel);
		f.pack();
		f.setVisible(true);

		DocumentListener documentListener = new DocumentListener() {
		      public void changedUpdate(DocumentEvent documentEvent) {
		        printIt(documentEvent);
		      }
		      public void insertUpdate(DocumentEvent documentEvent) {
		        printIt(documentEvent);
		      }
		      public void removeUpdate(DocumentEvent documentEvent) {
		        printIt(documentEvent);
		      }
		      private void printIt(DocumentEvent documentEvent) {
		        DocumentEvent.EventType type = documentEvent.getType();
		        String typeString = null;
		        if (type.equals(DocumentEvent.EventType.CHANGE)) {
		          typeString = "Change";
		        }  else if (type.equals(DocumentEvent.EventType.INSERT)) {
		          typeString = "Insert";
		        }  else if (type.equals(DocumentEvent.EventType.REMOVE)) {
		          typeString = "Remove";
		        }
		        System.out.print("Type : " + typeString);
		        Document source = documentEvent.getDocument();
		        int length = source.getLength();
		        System.out.println("Length: " + length);
		        try {
					System.out.println("Length: " + source.getText(0, source.getLength()));
					if(length > 3) {
				          searchBar.setText(PrintCityArray(source.getText(0, source.getLength())));
					}
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		      }
		    };
		    searchfield.getDocument().addDocumentListener(documentListener);
		/*
		searchfield.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(searchfield.getText().length() > 3) 
				{
					searchBar.setText(PrintCityArray(searchfield.getText()));
				}
			}
			
		});
		*/
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
		
	countryList.addMouseListener(new MouseAdapter(){
	          @Override
	          public void mouseClicked(MouseEvent e) {
	              //System.out.println((String)countryList.getModel().getElementAt(countryList.locationToIndex(e.getPoint())).trim());
	              api.SetCurrentCityName((String)countryList.getModel().getElementAt(countryList.locationToIndex(e.getPoint())).trim());
	              currentCity.setText((String)countryList.getModel().getElementAt(countryList.locationToIndex(e.getPoint())).trim());
	          }
	    });
	}
	
	
	public void GetCitiesFromFile() 
	{
		InputStream is = null; 
		try {
			is = new FileInputStream("CityList.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		BufferedReader buf = new BufferedReader(new InputStreamReader(is)); 
		String line = null;
		try {
			line = buf.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} StringBuilder sb = new StringBuilder();
		while(line != null)
		{ 
			sb.append(line).append("\n"); try {
				line = buf.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String data = sb.toString(); 
		JSONObject jobj = new JSONObject(data);
		JSONArray cityName = jobj.getJSONArray("Cities");

    	
		for (int i = 0; i < cityName.length(); i++) {
		    String temp = cityName.get(i).toString();
		    
			JSONObject name = new JSONObject(temp);
			String tempName = name.getString("name");
			String tempCountry = name.getString("country");
			int tempID = name.getInt("id");
			City tempCity = new City(tempName, tempID, tempCountry);
			cityArray[i] = tempCity;
		}
	    System.out.println("DONE");
	}
	
	public String PrintCityArray(String tempName) 
	{
		String cityNames = "";
		listModel.clear();
		for(City temp : cityArray ) 
		{
			if(temp.GetName().contains(tempName)) 
			{
				//System.out.print(temp.printDetails());
				cityNames += temp.printDetails();
		        listModel.addElement(temp.printDetails());

			}
		}
		
		return cityNames;
	}
	
	
}
