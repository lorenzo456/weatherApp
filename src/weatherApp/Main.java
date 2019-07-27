package weatherApp;

public class Main {

		public static void main(String[] args) 
	    {
			
	    	ApiCall api = null;	    	
			try {
				api = new ApiCall();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(api != null) 
			{
			    DisplayGui display = new DisplayGui(api);			
			}

	    }
		
	
}
