package Model;

import java.util.Random;

public class WorldGrid {
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	private int countryCount;
	public static Country[][] countries;
	
	
	public WorldGrid(int peopleCount, int n){

		countryCount = n * n;
		countries = new Country[n][n];
		
		int remainingPerson = peopleCount;
		 int avg = peopleCount / countryCount;
		 int minimum = avg-avg/4;
		 int maximum = avg+avg/4;
		 Random rand = new Random();
		
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				if(remainingPerson < maximum){
					maximum = remainingPerson;
					minimum = 0;
				}
				int randomPeopleCount = minimum + rand.nextInt((maximum - minimum) + 1);
				remainingPerson -= randomPeopleCount;
				if(i==(n-1) && j==(n-1)){
					randomPeopleCount += remainingPerson;
				}
				Country country = new Country(randomPeopleCount, i, j);
				countries[i][j] = country;
			}
		}
	 
		setNeighbourCountries();
	}
	
	private void setNeighbourCountries(){
		int n = (int) Math.sqrt(countryCount);
		
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				Country country = countries[i][j];
				// Special conditions for edge countries
				int northX, eastY, southX, westY;
				int xCoor = i, yCoor = j;
				if(xCoor == 0)     northX = n - 1; else northX = xCoor - 1;
				if(yCoor == n - 1) eastY  = 0;     else eastY  = yCoor + 1;
				if(xCoor == n - 1) southX = 0;     else southX = xCoor + 1;
				if(yCoor == 0)     westY  = n - 1; else westY  = yCoor - 1;
				
				Country[] neighbourCountries = country.getNeighbourCountries();
				neighbourCountries[NORTH] = countries[northX][yCoor];
				neighbourCountries[EAST]  = countries[xCoor][eastY];
				neighbourCountries[SOUTH] = countries[southX][yCoor];
				neighbourCountries[WEST]  = countries[xCoor][westY];
			}
		}
	}
	
	public int getCountryCount(){
		return this.countryCount;
	}
	
	public static Country getRandomCountry(){
		Random rand = new Random();
		return countries[rand.nextInt(countries.length)][rand.nextInt(countries.length)];
	}
}
