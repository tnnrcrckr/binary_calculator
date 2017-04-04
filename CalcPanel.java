//////////
//	CalcPanel.java
//	Tanner Crocker
//////////
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class CalcPanel extends JPanel
{	
	private JButton zero, one, add, subtract, mult, divide, bitAnd, bitOr, clear, clearAll, equals;
	private JPanel buttonRow1, buttonRow2, labelPanel;
	private JLabel numberInput, numberShown;
	private String input = " ", display = " ";
	private boolean operatorsEnabled = false, shownHasChanged = false;
	private int operatorType = 0;	// 0:No Operator, 1:+, 2:-, 3:*, 4:/, 5:&, 6:|
	
	private int operand1, temp;
	
	final Color 	BLUE = new Color(0, 125, 200), 		RED = new Color(225, 35, 25), 
						GREEN = new Color(15, 225, 15), 		YELLOW = new Color (225, 225, 0),
						WHITE = new Color(245, 245, 245), 	BKG = new Color(200, 200, 200),
						ORANGE = new Color(250, 120, 0), 	PURPLE = new Color(180, 0, 180);
	
	public CalcPanel()
	{
		setBackground(Color.black);
		setPreferredSize(new Dimension(200, 400));
		
		
		numberShown = new JLabel(display);
		numberInput = new JLabel(input);
		
		zero = new JButton("0");
		one = new JButton("1");
		
		add = new JButton("+");
			add.setBorder(BorderFactory.createLineBorder(BLUE, 3));
		subtract = new JButton("-");
			subtract.setBorder(BorderFactory.createLineBorder(RED, 3));
		
		mult = new JButton("*");
			mult.setBorder(BorderFactory.createLineBorder(GREEN, 3));
		divide = new JButton("" + (char)(247));
			divide.setBorder(BorderFactory.createLineBorder(YELLOW, 3));
		
		bitAnd = new JButton("&");
			bitAnd.setBorder(BorderFactory.createLineBorder(ORANGE, 3));
		bitOr = new JButton("|");
			bitOr.setBorder(BorderFactory.createLineBorder(PURPLE, 3));
		
		clear = new JButton("C");
		clearAll = new JButton("CE");
		
		equals = new JButton("=");
		
		ButtonListener listener = new ButtonListener();
		zero.addActionListener(listener);
		one.addActionListener(listener);
		add.addActionListener(listener);
		subtract.addActionListener(listener);
		mult.addActionListener(listener);
		divide.addActionListener(listener);
		bitAnd.addActionListener(listener);
		bitOr.addActionListener(listener);
		clear.addActionListener(listener);
		clearAll.addActionListener(listener);
		equals.addActionListener(listener);
		
		buttonRow1 = new JPanel();
		buttonRow1.setMinimumSize(new Dimension(200, 400));
		buttonRow1.setBackground(BKG);
		buttonRow1.setLayout(new GridLayout(5, 2));
		buttonRow1.add(zero);
		buttonRow1.add(one);
		buttonRow1.add(add);
		buttonRow1.add(subtract);
		buttonRow1.add(mult);
		buttonRow1.add(divide);
		buttonRow1.add(bitAnd);
		buttonRow1.add(bitOr);
		buttonRow1.add(clear);
		buttonRow1.add(clearAll);
		
		buttonRow2 = new JPanel();
		buttonRow2.setMinimumSize(new Dimension(200, 400));
		buttonRow2.setBackground(BKG);
		buttonRow2.setLayout(new GridLayout(1, 1));
		buttonRow2.add(equals);
		
		labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(0, 1));
		labelPanel.setMinimumSize(new Dimension(200, 50));
		labelPanel.setBackground(WHITE);
		labelPanel.add(numberShown);
		labelPanel.add(numberInput);
		
		numberShown.setHorizontalAlignment(SwingConstants.LEFT);
		numberShown.setVerticalAlignment(SwingConstants.CENTER);
		numberInput.setHorizontalAlignment(SwingConstants.CENTER);
		numberInput.setVerticalAlignment(SwingConstants.CENTER);
		
		
		setBackground(WHITE);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(labelPanel);
		add(Box.createVerticalGlue());
		add(buttonRow1);
		add(buttonRow2);
	}
	
	public void paintComponent(Graphics page)
	{
		super.paintComponent(page);
		if(numberShown.getText().endsWith(" + ") || numberShown.getText().endsWith(" - ") ||
			numberShown.getText().endsWith(" * ") || numberShown.getText().endsWith(" " + (char)(247) + " ") ||
			numberShown.getText().endsWith(" & ") ||numberShown.getText().endsWith(" | ") )
		{
			numberShown.setText(numberShown.getText().substring(0, (numberShown.getText().length()-3)));
		}
		switch (operatorType) // 0:No Operator, 1:+, 2:-, 3:*, 4:/, 5:&, 6:|
		{
			case 0:
				labelPanel.setBackground(WHITE);
				setBackground(WHITE);
				break;
			case 1:
				labelPanel.setBackground(BLUE);
				setBackground(BLUE);
				numberShown.setText(numberShown.getText() + " + ");
				break;
			case 2:
				labelPanel.setBackground(RED);
				setBackground(RED);
				numberShown.setText(numberShown.getText() + " - ");
				break;
			case 3:
				labelPanel.setBackground(GREEN);
				setBackground(GREEN);
				numberShown.setText(numberShown.getText() + " * ");
				break;
			case 4:
				labelPanel.setBackground(YELLOW);
				setBackground(YELLOW);
				numberShown.setText(numberShown.getText() + " " + (char)(247) + " ");
				break;
			case 5:
				labelPanel.setBackground(ORANGE);
				setBackground(ORANGE);
				numberShown.setText(numberShown.getText() + " & ");
				break;
			case 6:
				labelPanel.setBackground(PURPLE);
				setBackground(PURPLE);
				numberShown.setText(numberShown.getText() + " | ");
				break;
			default: break;
		}
		
	}
	
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (event.getSource() == zero)
			{
				if(input == " ")
					input = "";
				input += "0";
				numberInput.setText(input);
				operatorsEnabled = true;
			}
			////////////////////////////////////////////////////////////
			if (event.getSource() == one)
			{
				if(input == " ")
					input = "";
				input += "1";
				numberInput.setText(input);
				operatorsEnabled = true;
			}
			////////////////////////////////////////////////////////////
			if (event.getSource() == clear)
			{
				clear();
			}
			////////////////////////////////////////////////////////////
			if (event.getSource() == clearAll)
			{
				clearAll();
			}
			////////////////////////////////////////////////////////////
			if (event.getSource() == equals)
			{
				if(numberInput.getText().length() > 0)
				{
					equalSign();
				}
			}
			////////////////////////////////////////////////////////////
			if (operatorsEnabled && (event.getSource() == add || event.getSource() == subtract || event.getSource() == mult ||
										event.getSource() == divide || event.getSource() == bitAnd || 
										event.getSource() == bitOr || event.getSource() == equals) )
			{
				if (event.getSource() == add)
					addSign();
				if (event.getSource() == subtract)
					subSign();
				if (event.getSource() == mult)
					multSign();
				if (event.getSource() == divide)
					divSign();
				if (event.getSource() == bitAnd)
					bAnd();
				if (event.getSource() == bitOr)
					bOr();
				
				operatorsEnabled = false;
				repaint();
			}
			else
			{	
				if(numberShown.getText().length() > 2 && (event.getSource() != zero || event.getSource() != one) )
				{
					// 0:No Operator, 1:+, 2:-, 3:*, 4:/, 5:&, 6:|
					if (event.getSource() == add)
					{
						operatorType = 1;
						System.out.println("operatorType updated: " + operatorType);
						repaint();
					}
					if (event.getSource() == subtract)
					{
						operatorType = 2;
						System.out.println("operatorType updated: " + operatorType);
						repaint();
					}
					if (event.getSource() == mult)
					{
						operatorType = 3;
						System.out.println("operatorType updated: " + operatorType);
						repaint();
					}
					if (event.getSource() == divide)
					{
						operatorType = 4;
						System.out.println("operatorType updated: " + operatorType);
						repaint();
					}
					if (event.getSource() == bitAnd)
					{
						operatorType = 5;
						System.out.println("operatorType updated: " + operatorType);
						repaint();
					}
					if (event.getSource() == bitOr)
					{
						operatorType = 6;
						System.out.println("operatorType updated: " + operatorType);
						repaint();
					}
				}
			}
		}
	}
	
	private void addSign()
	{
		temp = BinaryDecimal.BinaryToDecimal(input);
		operand1 += temp;
		input = " ";
		display = ("" + BinaryDecimal.DecimalToBinary(operand1));
		numberShown.setText(display);
		numberInput.setText(input);
		System.out.println("ADD by " + temp +"\t= " + operand1);
		operatorsEnabled = false;
		shownHasChanged = true;
		operatorType = 1;
		repaint();
	}
	////////////////////////////////////////////////////////////
	private void subSign()
	{
		temp = BinaryDecimal.BinaryToDecimal(input);
		operand1 -= temp;
		input = " ";
		display = ("" + BinaryDecimal.DecimalToBinary(operand1));
		numberShown.setText(display);
		numberInput.setText(input);
		System.out.println("SUB by " + temp +"\t= " + operand1);
		operatorsEnabled = false;
		shownHasChanged = true;
		operatorType = 2;
		repaint();
	}
	////////////////////////////////////////////////////////////
	private void multSign()
	{
		temp = BinaryDecimal.BinaryToDecimal(input);
		operand1 *= temp;
		input = " ";
		display = ("" + BinaryDecimal.DecimalToBinary(operand1));
		numberShown.setText(display);
		numberInput.setText(input);
		System.out.println("MUL by " + temp +"\t= " + operand1);
		operatorsEnabled = false;
		shownHasChanged = true;
		operatorType = 3;
		repaint();
	}
	////////////////////////////////////////////////////////////
	private void divSign()
	{
		temp = BinaryDecimal.BinaryToDecimal(numberInput.getText());
		if (temp != 0)
		{	
			temp = BinaryDecimal.BinaryToDecimal(input);
			operand1 /= temp;
			input = " ";
			display = ("" + BinaryDecimal.DecimalToBinary(operand1));
			numberShown.setText(display);
			numberInput.setText(input);
			System.out.println("DIV by " + temp +"\t= " + operand1);
			operatorsEnabled = false;
			shownHasChanged = true;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Division by zero is not permitted.");
			input = " ";
			numberInput.setText(input);
		}
		
		operatorType = 4;
		repaint();
	}
	////////////////////////////////////////////////////////////
	private void bAnd()
	{
		//Prevents comparison with zero when used without a number in (Str) display
		if(display != " " && display != "")
		{
			System.out.println("-----Begin bAnd()-----" + "\n\tinLength " + input.length() + "\tdisLength " + display.length() +
							"\n\tIn: " + input + "\tDis: " + display);
			
			//Makes lengths match
			if (input.length() > display.length() && input.length() != display.length())
			{
				for (int numOfZeros = (input.length() - display.length()); numOfZeros > 0; numOfZeros--)
				{
					display = ("0" + display);
				}
				System.out.println("Resized:" + "\n\tinLength " + input.length() + "\tdisLength " + display.length() +
							"\n\tIn: " + input + "\tDis: " + display);
			} else
			{
				if(input.length() != display.length())
				{
					for (int numOfZeros = (display.length() - input.length()); numOfZeros > 0; numOfZeros--)
					{
						input = ("0" + input);
					}
					System.out.println("Resized:" + "\n\tinLength " + input.length() + "\tdisLength " + display.length() +
							"\n\tIn: " + input + "\tDis: " + display);
				}
			}
			
			System.out.println("Using:" + "\n\tinLength " + input.length() + "\tdisLength " + display.length() +
							"\n\tIn: " + input + "\tDis: " + display);
			String temp = "";
			System.out.println("Length of comparison: " + display.length());
			//Identify result and add it to (Str) temp
			for (int numLength = 0; numLength < display.length(); numLength++)
			{
				if(display.charAt(numLength) == '1' && input.charAt(numLength) == '1')
				{
					temp += "1";
					System.out.println("One. @ " + numLength);
				} else
				{
					temp += "0";
					System.out.println("Zero @ " + numLength);
				}
			}
			System.out.println("result: " + temp);
			
			System.out.println("-----End bAnd()-----");
			input = " ";
			display = temp;
			numberInput.setText(input);
			numberShown.setText(display);
			operatorsEnabled = false;
			shownHasChanged = true;
		} else
		{
			display = input;
			input = " ";
			numberInput.setText(input);
			numberShown.setText(display);
		}
		operatorType = 5;
		repaint();
	}
	////////////////////////////////////////////////////////////
	private void bOr()
	{
		//Prevents comparison with zero when used without a number in (Str) display
		if(display != " " || display != "")
		{
			System.out.println("-----Begin bOr()-----" + "\n\tinLength " + input.length() + "\tdisLength " + display.length() +
							"\n\tIn: " + input + "\tDis: " + display);
			//Makes lengths match
			if (input.length() > display.length() && input.length() != display.length())
			{
				for (int numOfZeros = (input.length() - display.length()); numOfZeros > 0; numOfZeros--)
				{
					display = ("0" + display);
				}
				System.out.println("Resized:" + "\n\tinLength " + input.length() + "\tdisLength " + display.length() +
							"\n\tIn: " + input + "\tDis: " + display);
			} else
			{
				if(input.length() != display.length())
				{
					for (int numOfZeros = (display.length() - input.length()); numOfZeros > 0; numOfZeros--)
					{
						input = ("0" + input);
					}
					System.out.println("Resized:" + "\n\tinLength " + input.length() + "\tdisLength " + display.length() +
							"\n\tIn: " + input + "\tDis: " + display);
				}
			}
			
			System.out.println("Using:" + "\n\tinLength " + input.length() + "\tdisLength " + display.length() +
							"\n\tIn: " + input + "\tDis: " + display);
			String temp = "";
			System.out.println("Length of comparison: " + display.length());
			//Identify result and add it to (Str) temp
			for (int numLength = 0; numLength < display.length(); numLength++)
			{
				if(display.charAt(numLength) == '1' || input.charAt(numLength) == '1')
				{
					temp += "1";
					System.out.println("One. @ " + numLength);
				} else
				{
					temp += "0";
					System.out.println("Zero @ " + numLength);
				}
			}
			System.out.println("result: " + temp);
			
			System.out.println("-----End bOr()-----");
			input = " ";
			display = temp;
			numberInput.setText(input);
			numberShown.setText(display);
			operatorsEnabled = false;
			shownHasChanged = true;
		} else
		{
			display = input;
			input = " ";
			numberInput.setText(input);
			numberShown.setText(display);
		}
		operatorType = 6;
		repaint();
	}
	////////////////////////////////////////////////////////////
	private void clear()
	{
		input = " ";
		temp = 0;
		numberInput.setText(input);
		System.out.println("\tnumberInput cleared; operatorType reset");
		operatorsEnabled = false;
		operatorType = 0;
		repaint();
	}
	////////////////////////////////////////////////////////////
	private void clearAll()
	{
		input = " ";
		display = " ";
		operand1 = 0;
		temp = 0;
		numberInput.setText(input);
		numberShown.setText(display);
		System.out.println("\nCalculator Reset\n");
		operatorsEnabled = false;
		shownHasChanged = false;
		operatorType = 0;
		repaint();
	}
	////////////////////////////////////////////////////////////
	private void equalSign()
	{
		switch (operatorType) // 0:No Operator, 1:+, 2:-, 3:*, 4:/, 5:&, 6:|
		{
			case 0: 
				temp = BinaryDecimal.BinaryToDecimal(input);
				operand1 = temp;
				input = " ";
				display = ("" + BinaryDecimal.DecimalToBinary(operand1));
				numberShown.setText(display);
				numberInput.setText(input);
				System.out.println("Input moved to Display: " + BinaryDecimal.BinaryToDecimal(numberShown.getText()));
				operatorsEnabled = true;
				break;
			case 1:
				addSign();
				operatorsEnabled = false;
				break;
			case 2:
				subSign();
				operatorsEnabled = false;
				break;
			case 3:
				multSign();
				operatorsEnabled = false;
				break;
			case 4:
				divSign();
				operatorsEnabled = false;
				break;
			case 5:
				bAnd();
				operatorsEnabled = false;
				break;
			case 6:
				bOr();
				operatorsEnabled = false;
				break;
			default: break;
		}
		
		shownHasChanged = true;
		operatorType = 0;
		repaint();
	}
}