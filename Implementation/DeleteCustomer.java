import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
public class DeleteCustomer extends JInternalFrame implements ActionListener 
{
	private JPanel jp_del=new JPanel();
	private JLabel ac_number_label, name_label, date_label, bal_label;
	private JTextField ac_number_tf, name_tf, date_tf, bal_tf;
	private JButton delete_button, cancel_button;

	private int recCount=0;
	private int rows=0;
	private	int total=0;

	//String Type Array, used to Load Records' From File.
	private String records[][]=new String[500][6];

	private FileInputStream fis;
	private DataInputStream dis;

	DeleteCustomer() 
	{
		super("Delete Account Holder", false, true, false, true);
		setSize(350, 235);
		jp_del.setLayout(null);
		ac_number_label=new JLabel("Account No.: ");
		ac_number_label.setForeground(Color.black);
		ac_number_label.setBounds(15, 20, 110, 25);
	    name_label=new JLabel("Cust. Name: ");
		name_label.setForeground(Color.black);
	    name_label.setBounds(15, 55, 110, 25);
		date_label=new JLabel("Last Trans.: ");
		date_label.setForeground(Color.black);
		date_label.setBounds(15, 90, 110, 25);
		bal_label=new JLabel("Account Bal.: ");
		bal_label.setForeground(Color.black);
		bal_label.setBounds(15, 125, 110, 25);

		ac_number_tf=new JTextField();
		ac_number_tf.setHorizontalAlignment(JTextField.RIGHT);
		ac_number_tf.setBounds(125, 20, 200, 25);
		name_tf=new JTextField();
		name_tf.setEnabled(false);
		name_tf.setBounds(125, 55, 200, 25);
		date_tf=new JTextField();
		date_tf.setEnabled(false);
		date_tf.setBounds(125, 90, 200, 25);
		bal_tf=new JTextField ();
		bal_tf.setEnabled(false);
		bal_tf.setHorizontalAlignment(JTextField.RIGHT);
		bal_tf.setBounds(125, 125, 200, 25);

		//Aligning The Buttons
		delete_button=new JButton("Delete");
		delete_button.setBounds(20, 165, 120, 25);
		delete_button.addActionListener(this);
		cancel_button=new JButton("Cancel");
		cancel_button.setBounds(200, 165, 120, 25);
		cancel_button.addActionListener(this);

		//Adding all the Controls to the Panel
		jp_del.add(ac_number_label);
		jp_del.add(ac_number_tf);
		jp_del.add(name_label);
		jp_del.add(name_tf);
		jp_del.add(date_label);
		jp_del.add(date_tf);
		jp_del.add(bal_label);
		jp_del.add(bal_tf);
		jp_del.add(delete_button);
		jp_del.add(cancel_button);

		//Restricting The User Input to only Numerics in Numeric Text-Boxes.
		ac_number_tf.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent ke) 
			{
				char c=ke.getKeyChar();
				if(!((Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE)))) 
				{
					getToolkit().beep();
					ke.consume();
      			}
    		}
  		});

		//Checking the Accunt No., provided By User on Lost Focus of the Text-Box.
		ac_number_tf.addFocusListener(new FocusListener() 
		{
			public void focusGained(FocusEvent e) 
			{ 
			}
			public void focusLost(FocusEvent fe) 
			{
				if(ac_number_tf.getText().equals("")==false) 
				{
					rows=0;
					populateArray(); //Load All Existing Records' in Memory.
					findRec(); //Finding if Account No., already Exists or Not.
				}
			}
		});

		//Adding Panel to Window.
		getContentPane().add(jp_del);

		populateArray(); //Load All Existing Records' in Memory.

		setVisible (true);
	}

	public void actionPerformed(ActionEvent ae) 
	{

		Object obj=ae.getSource();
		if(obj==delete_button) 
		{
			if(ac_number_tf.getText().equals("")) 
			{
				JOptionPane.showMessageDialog(this, "Please! Provide Acc. Num. of Customer.", "Bank System - Empty Field", JOptionPane.PLAIN_MESSAGE);
				ac_number_tf.requestFocus();
			}
			else 
			{
				deletion();
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
		catch(Exception ex) //Catch Any Kind of Exception
		{
			total=rows;
			if(total==0) 
			{
				JOptionPane.showMessageDialog(null, "There is/are no Customer(s) in the Bank!", "Bank System - Empty File", JOptionPane.PLAIN_MESSAGE);
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

	//Function to find the Record, requested by User to Delete
	void findRec() 
	{
		boolean found=false;
		for(int i=0; i<total; i++)
		{
			if(records[i][0].equals(ac_number_tf.getText())) 
			{
				found=true;
				showRec(i);
				break;
			}
		}
		if(found==false) 
		{
			String str=ac_number_tf.getText();
			txtClear();
			JOptionPane.showMessageDialog(this, "Account No. " + str + " doesn't Exist!", "Bank System - Wrong Acc. No.", JOptionPane.PLAIN_MESSAGE);
		}
	}

	void showRec(int rr)
	{

		ac_number_tf.setText(records[rr][0]);
		name_tf.setText(records[rr][1]);
		date_tf.setText(records[rr][2] + ", " + records[rr][3] + ", " + records[rr][4]);
		bal_tf.setText(records[rr][5]);
		recCount=rr;
	}

	//Confirming the Deletion Decision, made by the User
	void deletion() 
	{
		try 
		{
			int reply=JOptionPane.showConfirmDialog(this, "Are you sure, you want to Delete\nAccount No." + ac_number_tf.getText() + ", from the Bank System?", "Bank System - Delete", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			if(reply==JOptionPane.YES_OPTION) 
			{
				delRec();
			}
		} 
		catch(Exception e)
		{
		}
	}

	void delRec() 
	{
		try 
		{
			if(records!=null) 
			{
				for(int i=recCount; i<total; i++)
				{
					for(int r=0; r<6; r++) 
					{
						records[i][r]=records[i+1][r];				
						if(records[i][r]==null)
						{
							break;
						}
					}
				}
				total--;
				deleteFile();
			}
		}
		catch(ArrayIndexOutOfBoundsException ex) //Catch IndexOutOfBoundsException
		{ 
		}
	}

	void deleteFile() 
	{
		try 
		{
			FileOutputStream fos=new FileOutputStream("Bank.dat");
			DataOutputStream dos=new DataOutputStream(fos);
			if(records!=null) 
			{
				for(int i=0; i<total; i++)
				{
					for(int r=0; r<6; r++)
					{
						dos.writeUTF(records[i][r]);
						if(records[i][r]==null)
						{
							break;
						}
					}
				}
				JOptionPane.showMessageDialog(this, "Account has been closed Successfuly.", "Bank System - Record Deleted", JOptionPane.PLAIN_MESSAGE);
				txtClear();
			}
			dos.close();
			fos.close();
		}
		catch(IOException ioe) //Catch IOException
		{
			JOptionPane.showMessageDialog(this, "There is some problem with the Record File!", "Bank System - Error", JOptionPane.PLAIN_MESSAGE);
		}
	}

	void txtClear() 
	{
		ac_number_tf.setText("");
		name_tf.setText("");
		date_tf.setText("");
		bal_tf.setText("");
		ac_number_tf.requestFocus();
	}

	void btnEnable() 
	{
		ac_number_tf.setEnabled(false);
		delete_button.setEnabled(false);
	}
}	