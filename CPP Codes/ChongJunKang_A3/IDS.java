import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IDS 
{
	public static boolean isInteger(String s) 
	{
		String regex = "([\\d]+)";
		
		Pattern p = Pattern.compile(regex);
		
		if (s == null) 
		{ 
            return false; 
        } 
		
		Matcher m = p.matcher(s);
		return m.matches();
    }
	
	public static boolean isDecimal(String s) 
	{
		String regex = "([\\d]+)([.]?)([\\d]*)";
		
		Pattern p = Pattern.compile(regex);
		
		if (s == null) 
		{ 
            return false; 
        } 
		
		Matcher m = p.matcher(s);
		return m.matches();
    }
	
	public static void main(String[] args) throws FileNotFoundException 
	{
		File eventsTxt = new File("Events.txt");
		File statsTxt = new File("Stats.txt");
		System.out.println("Enter Number Of Days: ");
		Scanner myObj = new Scanner(System.in);
		String Days = myObj.nextLine();
		int Days = Integer.valueOf(userName);
		Scanner readEvents = new Scanner(eventsTxt);
		Scanner readStats = new Scanner(statsTxt);
		boolean check1 = false, check2 = false, check3 = false, check4 = false, check5 = false, check6 = false, 
				check7 = false, check8 = false;
		String eventsLine, statsLine, events_eventName, events_type, events_minimum, events_maximum, events_weight,
				stats_eventName, stats_mean, stats_SD, events_num = null, stats_num = null;
		String[] eventsArray = new String[5];
		String[] statsArray = new String[3];
		Vector<String> eventsArrayVec = new Vector<String>();
		Vector<String> statsArrayVec = new Vector<String>();
		int counter = 1;
		
		while(readEvents.hasNextLine() && readStats.hasNextLine())
		{
			if(counter == 1)
			{
				events_num = readEvents.nextLine();
				stats_num = readStats.nextLine();
				counter++;
				
				if(events_num.equals(stats_num))
				{
					check1 = true;
				}
				else
				{
					check1 = false;
					System.out.println("Event number in Events.txt and Stats.txt do not match (line " + counter + ").");
				}
			}
			
			eventsLine = readEvents.nextLine();
			eventsArray = eventsLine.split(":");
			statsLine = readStats.nextLine();
			statsArray = statsLine.split(":");
			events_eventName = eventsArray[0];
			events_type = eventsArray[1];
			events_minimum = eventsArray[2];
			events_maximum = eventsArray[3];
			events_weight = eventsArray[4];
			stats_eventName = statsArray[0];
			stats_mean = statsArray[1];
			stats_SD = statsArray[2];
			eventsArrayVec.add(eventsLine);
			statsArrayVec.add(statsLine);
			
			if(!events_eventName.isEmpty() && !stats_eventName.isEmpty())
			{
				if(events_eventName.equals(stats_eventName))
				{
					check2 = true;
				}
				else
				{
					check2 = false;
					System.out.println("Event names in Events.txt and Stats.txt do not match (line " + counter + ").");
				}
			}
			else
			{
				check2 = false;
				System.out.println("Event names in Events.txt and Stats.txt cannot be empty (line " + counter + ").");
			}
			
			if(events_type.equals("D") || events_type.equals("C"))
			{
				if(events_type.equals("D"))
				{
					if(!events_minimum.isEmpty())
					{
						//double minimum = Double.valueOf(events_minimum);
						
						if(isInteger(events_minimum) == true)
				        {
							check3 = true;
				        }
						else
						{
							check3 = false;
							System.out.println("Minimum for event type \"D\" in Events.txt can only accept integer values (line " + counter + ").");
						}
					}
					
					if(!events_maximum.isEmpty())
					{
						//double maximum = Double.valueOf(events_maximum);
						
						if(isInteger(events_maximum) == true)
				        {
							check4 = true;
				        }
						else
						{
							check4 = false;
							System.out.println("Maximum for event type \"D\" in Events.txt can only accept integer values (line " + counter + ").");
						}
					}
				}
				
				if(events_type.equals("C"))
				{
					if(!events_minimum.isEmpty())
					{
						//double minimum = Double.valueOf(events_minimum);
						
						if(isDecimal(events_minimum) == true)
				        {
							check3 = true;
				        }
						else
						{
							check3 = false;
							System.out.println("Minimum for event type \"C\" in Events.txt can only accept both decimal and integer values (line " + counter + ").");
						}
					}
					
					if(!events_maximum.isEmpty())
					{
						//double maximum = Double.valueOf(events_maximum);
						
						if(isDecimal(events_maximum) == true)
				        {
							check4 = true;
				        }
						else
						{
							check4 = false;
							System.out.println("Maximum for event type \"C\" in Events.txt can only accept both decimal and integer values (line " + counter + ").");
						}
					}
				}
			}
			else
			{
				check3 = false;
				System.out.println("Event type in Events.txt can only accept \"C\" or \"D\" (line " + counter + ").");
			}
			
			if(!events_weight.isEmpty())
			{
				if(isInteger(events_weight))
				{
					check5 = true;
				}
				else
				{
					check5 = false;
					System.out.println("Weight in Events.txt can only accept integer values (line " + counter + ").");
				}
			}
			else
			{
				check5 = false;
				System.out.println("Weight in Events.txt cannot be empty (line " + counter + ").");
			}
			
			if(!stats_mean.isEmpty())
			{
				if(isDecimal(stats_mean))
				{
					check6 = true;
				}
				else
				{
					check6 = false;
					System.out.println("Mean in Stats.txt can only accept both decimal and integer values (line " + counter + ").");
				}
			}
			else
			{
				check6 = false;
				System.out.println("Mean in Stats.txt cannot be empty (line " + counter + ").");
			}
			
			if(!stats_SD.isEmpty())
			{
				if(isDecimal(stats_SD))
				{
					check7 = true;
				}
				else
				{
					check7 = false;
					System.out.println("Standard deviation in Stats.txt can only accept both decimal and integer values (line " + counter + ").");
				}
			}
			else
			{
				check7 = false;
				System.out.println("Standard deviation in Stats.txt cannot be empty (line " + counter + ").");
			}
			
			counter++;
		}
		
		if(events_num.equals(String.valueOf(eventsArrayVec.size())) && stats_num.equals(String.valueOf(statsArrayVec.size())))
		{
			check8 = true;
		}
		else
		{
			check8 = false;
			System.out.println("Event number and number of events in Events.txt or Stats.txt do not match.");
		}
		
		/*if(stats_num.equals(String.valueOf(statsArrayVec.size())))
		{
			check9 = true;
		}
		else
		{
			check9 = false;
			System.out.println("Event number and number of events in Stats.txt do not match.");
		}*/
		
		if(check1 == false || check2 == false || check3 == false || check4 == false || check5 == false || check6 == false || check7 == false || 
				check8 == false)
		{
			System.exit(0);
		}
	}
}
