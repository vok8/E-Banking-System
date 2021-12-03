import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
public class FindAccountName extends JInternalFrame implements ActionListener 
{
	private JPanel jp_find=new JPanel();
	private JLabel ac_number_label, name_label, date_label, bal_label;
	private JTextField ac_number_tf, name_tf, date_tf, bal_tf;
	private JButton find_button, cancel_button;

	private int count=0;
	private int rows=0;
	private	int total=0;

	//String Type Array, used to Load Records' From File.
	private String records[][]=new String[500][6];

	private FileInputStream fis;
	private DataInputStream dis;

	FindAccountName() 
	{
		super("Search Account Holder", false, true, false, true);
		setSize(350, 235);

		jp_find.setLayout(null);

		ac_number_label=new JLabel("Account No. :");
		ac_number_label.setForeground(Color.black);
		ac_number_label.setBounds(15, 20, 110, 25);
	    name_label=new JLabel("Cust. Name: ");
		name_label.setForeground(Color.black);
	    name_label.setBounds(15, 55, 110, 25);
		date_label=new JLabel("Last Trans.: ");
		date_label.setForeground(Color.black);
		date_label.setBounds(15, 90, 110, 25);
		bal_label=new JLabel("Balance: ");
		bal_label.setForeground(Color.black);
		bal_label.setBounds(15, 125, 110, 25);

		ac_number_tf=new JTextField();
		ac_number_tf.setEnabled(false);
		ac_number_tf.setHorizontalAlignment(JTextField.RIGHT);
		ac_number_tf.setBounds(125, 20, 200, 25);
		name_tf=new JTextField();
		name_tf.setBounds(125, 55, 200, 25);
		date_tf=new JTextField();
		date_tf.setEnabled(false);
		date_tf.setBounds(125, 90, 200, 25);
		bal_tf=new JTextField();
		bal_tf.setHorizontalAlignment(JTextField.RIGHT);
		bal_tf.setEnabled(false);
		bal_tf.setBounds(125, 125, 200, 25);

		//Aligning The Buttons
		find_button=new JButton("Search");
		find_button.setBounds(20, 165, 120, 25);
		find_button.addActionListener(this);
		cancel_button=new JButton("Cancel");
		cancel_button.setBounds(200, 165, 120, 25);
		cancel_button.addActionListener(this);

		//Adding all the Controls to the Panel.
		jp_find.add(ac_number_label);
		jp_find.add(ac_number_tf);
		jp_find.add(name_label);
		jp_find.add(name_tf);
		jp_find.add(date_label);
		jp_find.add(date_tf);
		jp_find.add(bal_label);
		jp_find.add(bal_tf);
		jp_find.add(find_button);
		jp_find.add(cancel_button);

		//Adding Panel to Window
		getContentPane().add(jp_find);

		populateArray();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) 
	{
		Object obj=ae.getSource();
		if(obj==find_button) 
		{
			if(name_tf.getText().equals("")) 
			{
				JOptionPane.showMessageDialog(this, "Please! Provide Name of Customer to Search.", "Bank System - Empty Field", JOptionPane.PLAIN_MESSAGE);
				name_tf.requestFocus();
			}
			else 
			{
				rows=0;
				populateArray();
				findRec();
			}
		}
		if(obj==cancel_button) 
		{
			txtClear();
			setVisible(false);
			dispose();
		}
	}

	void populateArray() 
	{
		try 
		{
			fis=new FileInputStream("Bank.dat");
			dis=new DataInputStream(fis);
			while(true) 
			{
				for(int i=0; i<6; i++)
				{
					records[rows][i]=dis.readUTF();
				}
				rows++;
			}
		}
		catch(Exception ex) 
		{
			total=rows;
			if(total==0) 
			{
				JOptionPane.showMessageDialog (null, "There is/are no Customer(s) in the Bank!", "Bank System - Empty File", JOptionPane.PLAIN_MESSAGE);
				btnEnable();
			}
			else 
			{
				try 
				{
					dis.close();
					fis.close();
				}
				catch(Exception exp)
				{
				}
			}
		}
	}

	void findRec() 
	{
		boolean found=false;
		for(int i=0; i<6; i++)
		{
			if(records[i][1].equalsIgnoreCase(name_tf.getText())) 
			{
				found=true;
				showRec(i);
				break;
			}
		}
		if(found==false) 
		{
			JOptionPane.showMessageDialog (this, "Customer Name " + name_tf.getText() + " doesn't Exist.", "Bank System - Wrong Name", JOptionPane.PLAIN_MESSAGE);
			txtClear();
		}
	}

	public void showRec(int rr) 
	{
		ac_number_tf.setText(records[rr][0]);
		name_tf.setText(records[rr][1]);
		date_tf.setText(records[rr][2] + " " + records[rr][3] + ", " + records[rr][4]);
		bal_tf.setText (records[rr][5]);
	}

	void txtClear() 
	{
		ac_number_tf.setText("");
		name_tf.setText("");
		date_tf.setText("");
		bal_tf.setText("");
		name_tf.requestFocus();
	}

	void btnEnable() 
	{
		name_tf.setEnabled(false);
		find_button.setEnabled(false);
	}
}	