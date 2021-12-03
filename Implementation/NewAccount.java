import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
public class NewAccount extends JInternalFrame implements ActionListener 
{
	private JPanel jp_info=new JPanel();
	private JLabel ac_number_label, name_label, date_label, deposit_label;
	private JTextField ac_number_tf, name_tf, deposit_tf;
	private JComboBox cbo_month, cbo_day, cbo_year;
	private JButton save_button, cancel_button;

	private int count=0;
	private int rows=0;
	private	int total=0;

	//String Type Array used to Load Records' From File.
	private String records[][]=new String[500][6];

	//String Type Array used to Save Records' into File.
	private String saves[][]=new String[500][6];

	private FileInputStream fis;
	private DataInputStream dis;

	NewAccount() 
	{
		super("Create New Account", false, true, false, true);
		setSize(335, 235);

		jp_info.setBounds(0, 0, 500, 115);
		jp_info.setLayout(null);

		ac_number_label=new JLabel("Account No.: ");
		ac_number_label.setForeground(Color.black);
		ac_number_label.setBounds(15, 20, 110, 25);
	    name_label=new JLabel("Cust. Name: ");
		name_label.setForeground(Color.black);
	    name_label.setBounds(15, 55, 110, 25);
		date_label=new JLabel("Date: ");
		date_label.setForeground(Color.black);
		date_label.setBounds(15, 90, 50, 25);
		deposit_label=new JLabel("Dep. Amount: ");
		deposit_label.setForeground(Color.black);
		deposit_label.setBounds(15, 125, 110, 25);

		ac_number_tf=new JTextField();
		ac_number_tf.setHorizontalAlignment(JTextField.RIGHT);
		ac_number_tf.setBounds(125, 20, 185, 25);
		name_tf=new JTextField();
		name_tf.setBounds(125, 55, 185, 25);
		deposit_tf=new JTextField();
		deposit_tf.setHorizontalAlignment(JTextField.RIGHT);
		deposit_tf.setBounds(125, 125, 185, 25);

		//Restricting The User Input to only Numerics in Numeric Text-Boxes
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
		deposit_tf.addKeyListener(new KeyAdapter() 
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

		String months[]={"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		cbo_month=new JComboBox(months);
		cbo_day=new JComboBox();
		cbo_year=new JComboBox();
		for(int i=1; i<32; i++)
		{
			String days=""+i;
			cbo_day.addItem(days);
		}
		for(int i=2020; i<2050; i++)
		{
			String years=""+i;
			cbo_year.addItem(years);
		}

		//Aligning The Date Option Controls
		cbo_month.setBounds(50, 90, 105, 25);
		cbo_day.setBounds(157, 90, 67, 25);
		cbo_year.setBounds(226, 90, 84, 25);

		//Aligning The Buttons
		save_button=new JButton("Save");
		save_button.setBounds(20, 165, 120, 25);
		save_button.addActionListener(this);
		cancel_button=new JButton("Cancel");
		cancel_button.setBounds(185, 165, 120, 25);
		cancel_button.addActionListener(this);

		//Adding all the Controls to the Panel
		jp_info.add(ac_number_label);
		jp_info.add(ac_number_tf);
		jp_info.add(name_label);
		jp_info.add(name_tf);
		jp_info.add(date_label);
		jp_info.add(cbo_month);
		jp_info.add(cbo_day);
		jp_info.add(cbo_year);
		jp_info.add(deposit_label);
		jp_info.add(deposit_tf);
		jp_info.add(save_button);
		jp_info.add(cancel_button);

		//Adding Panel to Window
		getContentPane().add(jp_info);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) 
	{
		Object obj=ae.getSource();
		if(obj==save_button) 
		{
			if(ac_number_tf.getText().equals("")) 
			{
				JOptionPane.showMessageDialog(this, "Please! Provide Acc. No. of Customer.", "Bank System - Empty Field", JOptionPane.PLAIN_MESSAGE);
				ac_number_tf.requestFocus();
			}
			else if(name_tf.getText().equals("")) 
			{
				JOptionPane.showMessageDialog(this, "Please! Provide Name of Customer.", "Bank System - Empty Field", JOptionPane.PLAIN_MESSAGE);
				name_tf.requestFocus();
			}
			else if(deposit_tf.getText().equals("")) 
			{
				JOptionPane.showMessageDialog(this, "Please! Provide Deposit Amount.", "Bank System - Empty Field", JOptionPane.PLAIN_MESSAGE);
				deposit_tf.requestFocus();
			}
			else 
			{
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
			if(total!=0)
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
		for(int i=0; i<total; i++)
		{
			if(records[i][0].equals(ac_number_tf.getText())) 
			{
				found=true;
				JOptionPane.showMessageDialog(this, "Account No. " + ac_number_tf.getText() + "  already exists.", "Bank System - Wrong Acc. No.", JOptionPane.PLAIN_MESSAGE);
				txtClear();
				break;
			}
		}
		if(found==false) 
		{
			saveArray();
		}
	}

	void saveArray() 
	{
		saves[count][0]=ac_number_tf.getText();
		saves[count][1]=name_tf.getText ();
		saves[count][2]="" + cbo_month.getSelectedItem();
		saves[count][3]="" + cbo_day.getSelectedItem();
		saves[count][4]="" + cbo_year.getSelectedItem();
		saves[count][5]=deposit_tf.getText();
		saveFile ();
		count++;
	}

	void saveFile() 
	{
		try 
		{
			FileOutputStream fos=new FileOutputStream("Bank.dat", true);
			DataOutputStream dos=new DataOutputStream(fos);
			dos.writeUTF(saves[count][0]);
			dos.writeUTF(saves[count][1]);
			dos.writeUTF(saves[count][2]);
			dos.writeUTF(saves[count][3]);
			dos.writeUTF(saves[count][4]);
			dos.writeUTF(saves[count][5]);
			JOptionPane.showMessageDialog(this, "New Account Created Successfully!", "Bank System - Record Saved", JOptionPane.PLAIN_MESSAGE);
			txtClear();
			dos.close();
			fos.close();
		}
		catch(IOException ioe) //Catch IOException
		{
			JOptionPane.showMessageDialog(this, "There is some Problem with the File!", "Bank System - Error", JOptionPane.PLAIN_MESSAGE);
		}
	}

	void txtClear() 
	{
		ac_number_tf.setText("");
		name_tf.setText("");
		deposit_tf.setText("");
		ac_number_tf.requestFocus();
	}
}	