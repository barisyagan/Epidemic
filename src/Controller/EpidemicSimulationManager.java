package Controller;

import java.util.ArrayList;

import View_gui.UserInterface;
import Model.Country;
import Model.WorldGrid;
import Model.Person;
import Model.HealthStatus;

public class EpidemicSimulationManager {
	boolean isSimulationStarted = false;
	public int currentDay = 0;
	
	private WorldGrid     worldGrid;
	private UserInterface userInterface;
	
	public static double airTrafficPercentage;
	public static int    vaccineLimit;
	
	public static double infectedPercentage;
	public static double superPercentage;
	public static double doctorPercentage;
	
	public static ArrayList<Person> peopleToBeMoved = new ArrayList<Person>();
	
	
	public EpidemicSimulationManager(WorldGrid worldGrid){
		this.worldGrid      = worldGrid;
		this.userInterface = new UserInterface(this);
		setTableContent();
	}
	
	public void startSimulation(){
		if(isSimulationStarted){
			System.out.println("Simulation is already started!");
		}else{
			userInterface.setSize(600,600);
			userInterface.setVisible(true);
			isSimulationStarted = true;
		}
	}
	
	
	public int getCountryCount(){
		return worldGrid.getCountryCount();
	}
	
	public int getTotalPeopleWithStatus(HealthStatus status){

		int count=0;
		int n = (int) Math.sqrt(worldGrid.getCountryCount());
		for(int i=0; i < worldGrid.getCountryCount(); i++){
			Country country = WorldGrid.countries[i / n][i % n];
			switch(status){
			case Healthy:
				count += country.getHealthyPeople();
				break;
			case Infected:
				count += country.getInfectedPeople();
				break;
			case Sick:
				count += country.getSickPeople();
				break;
			case Immune:
				count += country.getImmunePeople();
				break;
			case Dead:
				count += country.getDeadPeople();
				break;
			default:
				break;
			}
		}
		return count;
	}
	
	private void setTableContent(){
		Object[][] tableContent = userInterface.getTableContent();
		
		tableContent[0][1] = getTotalPeopleWithStatus(HealthStatus.Healthy);
		tableContent[0][2] = getTotalPeopleWithStatus(HealthStatus.Infected);
		tableContent[0][3] = getTotalPeopleWithStatus(HealthStatus.Sick);
		tableContent[0][4] = getTotalPeopleWithStatus(HealthStatus.Immune);
		tableContent[0][5] = getTotalPeopleWithStatus(HealthStatus.Dead);
		
		int n = (int) Math.sqrt(worldGrid.getCountryCount());
		for(int i=1; i<tableContent.length; i++){
			Country country = WorldGrid.countries[(i-1) / n][(i-1) % n];
			tableContent[i][1] = country.getHealthyPeople();
			tableContent[i][2] = country.getInfectedPeople();
			tableContent[i][3] = country.getSickPeople();
			tableContent[i][4] = country.getImmunePeople();
			tableContent[i][5] = country.getDeadPeople();
		}
		userInterface.updateTable();
	}
	
	public void performNextDayButton(){
		int n = (int) Math.sqrt(worldGrid.getCountryCount());
		for(int i=0; i<worldGrid.getCountryCount(); i++){
			Country country = WorldGrid.countries[i / n][i % n];
			country.performNextDay();
		}
		
		for(int i = 0; i<peopleToBeMoved.size(); i++){
			Person person = peopleToBeMoved.get(i);
			person.move();
			peopleToBeMoved.remove(i);
		}
		for(int i=0; i<worldGrid.getCountryCount(); i++){
			Country country = WorldGrid.countries[i / n][i % n];
			country.updateNationsHealth();
		}
		currentDay++;
		setTableContent();
	}
	
}
