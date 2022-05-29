package sberwork_001;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * A program that works with city data (Sort, Search).
 */

public class Main {

	/**
	 * Our main method. Application entry point.
	 **/
	
	public static void main(String[] args) throws FileNotFoundException {
		List<City> cities = parse();
		
		//print unsorted list to console
        //print(cities);
		
        //print sorted list by name in alphabetical descending order to console
        //print(sortListNameCity(cities));
		
		//print sorted list by federal district and city name within each federal district in alphabetical descending order to console
		//sortListDistrictAndNameCity(cities);
        //print(cities);
        
		//Converting the list to an array and searching it for the city with the largest number of citizens
		//searchCityMaxPopulation(cities);
		
		//Displaying the number of cities in the regions.
		//quantityOfCitiesByRegion(cities);
		
		//Displaying the number of cities in the regions (lambda-expressions).
		quantityOfCitiesByRegionLambda(cities);
	}
	
	/**
	 * Loading city data into an array
	 * 
	 * @return cities - array with city data
	 */
	
	public static List<City> parse() {
    	String encoding = System.getProperty("console.encoding", "utf-8");
        List<City> cities = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File("city_ru.csv"), encoding);
            while (scanner.hasNextLine()) {
                cities.add(parse(scanner.nextLine()));
            }
            scanner.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return cities;
    }

    /**
     * Parsing a line with city data
     *
     * @param line - line with data
     * @return {@link City}
     */

    private static City parse(String line) {
        Scanner scanner = new Scanner(line);
        String[] values = scanner.nextLine().split(";", 6);
        if (values[5].isEmpty()) {
            values[5] = null;
        }
        scanner.close();

        return new City(values[1], values[2], values[3], Integer.parseInt(values[4]), values[5]);

    }
    
    /**
	 * Print method. Prints the City object.
	 **/
    
    public static void print(List<City> cities) {
        cities.forEach(System.out::println);
    }
    
    /**
	 * Sort method. Sort the list of cities by name in alphabetical descending order, case insensitive.
	 * 
	 * @return sortCitiesName - sorted list
	 **/
    
    private static List<City> sortListNameCity(List<City> cities) {
        List<City> sortCitiesName = cities.stream()
                .sorted(Comparator.comparing(City::getName))
                .collect(Collectors.toList());
        return sortCitiesName;
    }
    
    /**
	 * Sort method. Sort the list of cities by federal district and city name within each federal district in alphabetical descending order, case sensitive.
	 **/
    
    private static void sortListDistrictAndNameCity(List<City> cities) {
    	cities.sort(Comparator.comparing(City::getDistrict).thenComparing(City::getName));
    }
    
    /**
     * Search method. Converts the incoming list to an array and then looks for the city with the largest population.
     */
    
    private static void searchCityMaxPopulation(List<City> cities) {
        City[] array = cities.toArray(new City[0]);
        int max = 0;
        int index = 0;
        String city = null;
        for (int i = 0; i < array.length; i++) {
            if (array[i].getPopulation() > max) {
                max = array[i].getPopulation();
                index = i;
                city = array[i].getName();
            }
        }
        System.out.println(MessageFormat.format("[{0}] = {1} = {2}", index, city, max));

    }
    
    /**
	 * Sort method. Sort the list of cities by region name and city name within each region in alphabetical descending order, case sensitive.
	 **/
    
    private static void sortListRegion(List<City> cities) {
    	cities.sort(Comparator.comparing(City::getRegion).thenComparing(City::getName));
    }
    
    /**
     * Search method. Number of cities by regions.
     */
    
    private static void quantityOfCitiesByRegion(List<City> cities) {
    	cities.stream()
                .collect(Collectors.groupingBy(
                        City::getRegion, Collectors.counting()))
                .forEach((sity, nubmer) -> System.out.println(sity + " - " + nubmer));

    }
    
    /**
     * Search method. Number of cities by regions (lambda-expressions).
     * 
     * @param cities - array with city
     */
    
    private static void quantityOfCitiesByRegionLambda(List<City> cities) {
        Map<String, Integer> regions = new HashMap<>();
        cities.forEach(city -> regions.merge(city.getRegion(), 1, Integer::sum));
        regions.forEach((sity, nubmer) -> System.out.println(MessageFormat.format(" {0} = {1}", sity, nubmer)));
    }
}
