package pkg.swing.gui;

import javax.swing.JOptionPane;

class LoginDemo
{
	static LandingPage frame = null;
	public static void main(String arg[])
	{	
		start();
	}
	public static void start() {
		try
		{
			if(frame == null)
				frame =new LandingPage();
			frame.start();
			frame.setSize(800,275);
			frame.setVisible(true);
		}
		catch(Exception e)
		{JOptionPane.showMessageDialog(null, e.getMessage());}

	}


}