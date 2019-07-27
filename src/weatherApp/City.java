package weatherApp;

public class City 
{
	private String Name;
	private int ID;
	private String Country;
	
	public City(String Name, int ID, String Country) 
	{
		this.Name = Name;
		this.ID = ID;
		this.Country = Country;		
	}
	
	public String GetName() 
	{
		return Name;
	}
	
	public String GetCountry() 
	{
		return Country;
	}
	
	public int GetID() 
	{
		return ID;
	}
	
	public String printDetails() 
	{
		return Name + "," + Country +"\n";  
	}
}
