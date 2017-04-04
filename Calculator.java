//////////
//	Calculator.java
//	Tanner Crocker
//	A binary calculator
//////////

import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class Calculator
{
	public static void main (String[] args)
	{
		JFrame window = new JFrame("Binary Calculator");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.setMinimumSize(new Dimension(300, 400));
		
		window.getContentPane().add(new CalcPanel());
		
		window.setVisible(true);
	}
}