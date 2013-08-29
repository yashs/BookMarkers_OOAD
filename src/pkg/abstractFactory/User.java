/**
 * 
 */
package pkg.abstractFactory;

import java.util.Iterator;
import java.util.List;

import pkg.database.PersistanceActions;

/**
 * @author redokani
 *
 */
public abstract class User {
	public void searchItems(String keyword){
		List<LibraryItems> searchItems = PersistanceActions.getSearchedItems(keyword);
		displaySearchedItems(searchItems);
	}

	//display this in GUI
	private void displaySearchedItems(List<LibraryItems> searchItems) {
		if(searchItems != null && !searchItems.isEmpty()){
			Iterator<LibraryItems> searchItemsItr = searchItems.iterator();
			while(searchItemsItr.hasNext()){
				LibraryItems item = (LibraryItems) searchItemsItr.next();
				if(item.getType().equalsIgnoreCase("book")){
					System.out.println("\n Book Number: " + item.getItemNumber());
					System.out.println("\nTitle: " + item.getTitle());
					System.out.println("\nAuthor: " + item.getCreator());
					System.out.println("\nType: " + item.getCategory() + " Book");
					System.out.println("\nLocation: " + item.getLocation());
					System.out.println("\nStatus:" + item.getStatus());
				}
				else if(item.getType().equalsIgnoreCase("video")){
					System.out.println("\n Video Number: " + item.getItemNumber());
					System.out.println("\n Title: " + item.getTitle());
					System.out.println("\n Director: " + item.getCreator());
					System.out.println("\n Location: " + item.getLocation());
				}
				System.out.println("");
			}
		}
		else{
			System.out.println("No Items available in library!!!");
		}
		
	}
}
