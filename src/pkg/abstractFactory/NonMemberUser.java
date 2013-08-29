/**
 * 
 */
package pkg.abstractFactory;

/**
 * @author redokani
 *
 */
public class NonMemberUser extends User {

	/* (non-Javadoc)
	 * @see pkg.abstractFactory.User#searchItems()
	 */
	@Override
	public void searchItems(String keyword) {
		System.out.println("Hi Anonymous!!!");
		super.searchItems(keyword);

	}

}
