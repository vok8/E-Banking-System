import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
public class ViewOne extends JInternalFrame implements ActionListener 
{
	private JPanel jp_rec=new JPanel();
	private JLabel ac_number_label, name_label, date_label, bal_label;
	private JTextField ac_number_tf, name_tf, date_tf, bal_tf, rec_tf;
	private JButton first_button, back_button, next_button, last_button;

	private int rec_count=0;
	private int rows=0;
	private	int total=0;

	//String Type Array used to Load Records' from File.
	private String records[][]=new String[500][6];

	private FileInputStream fis;
	private DataInputStream dis;

	ViewOne() 
	{
		super("View Account Holders", false, true, false, true);
		setSize(350, 235);

		jp_rec.setLayout(null);

		ac_number_label=new JLabel("Account No.: ");
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
		ac_number_tf.setHorizontalAlignment(JTextField.RIGHT);
		ac_number_tf.setEnabled(false);
		ac_number_tf.setBounds(125, 20, 200, 25);
		name_tf=new JTextField();
		name_tf.setEnabled(false);
		name_tf.setBounds(125, 55, 200, 25);
		date_tf=new JTextField();
		date_tf.setEnabled(false);
		date_tf.setBounds(125, 90, 200, 25);
		bal_tf=new JTextField();
		bal_tf.setHorizontalAlignment(JTextField.RIGHT);
		bal_tf.setEnabled(false);
		bal_tf.setBounds(125, 125, 200, 25);

		//Aligninig The Navigation Buttons
		first_button=new JButton("<<");
		first_button.setBounds(15, 165, 50, 25);
		first_button.addActionListener(this);
		back_button=new JButton("<");
		back_button.setBounds(65, 165, 50, 25);
		back_button.addActionListener(this);
		next_button=new JButton(">");
		next_button.setBounds(225, 165, 50, 25);
		next_button.addActionListener(this);
		last_button=new JButton(">>");
		last_button.setBounds(275, 165, 50, 25);
		last_button.addActionListener(this);
		rec_tf=new JTextField();
		rec_tf.setEnabled(false);
		rec_tf.setHorizontalAlignment(JTextField.CENTER);
		rec_tf.setBounds(115, 165, 109, 25);

		//Adding all the Controls to the Panel
		jp_rec.add(ac_number_label);
		jp_rec.add(ac_number_tf);
		jp_rec.add(name_label);
		jp_rec.add(name_tf);
		jp_rec.add(date_label);
		jp_rec.add(date_tf);
		jp_rec.add(bal_label);
		jp_rec.add(bal_tf);
		jp_rec.add(first_button);
		jp_rec.add(back_button);
		jp_rec.add(next_button);
		jp_rec.add(last_button);
		jp_rec.add(rec_tf);

		//Adding Panel to Window
		getContentPane().add(jp_rec);

		populateArray();
		showRec(0);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) 
	{
		Object obj=ae.getSource();
		if(obj==first_button) 
		{
			rec_count=0;
			showRec(rec_count);
		}
		else if(obj==back_button) 
		{
			rec_count--;
			if(rec_count<0) 
			{
				rec_count=0;
				showRec(rec_count);
				JOptionPane.showMessageDialog(this, "You are on First Record!", "Bank System - Error", JOptionPane.PLAIN_MESSAGE);
			}
			else 
			{
				showRec(rec_count);
			}
		}
		else if(obj==next_button) 
		{
			rec_count++;
			if(rec_count==total) 
			{
				rec_count=total-1;
				showRec(rec_count);
				JOptionPane.showMessageDialog(this, "You are on Last Record!", "Bank System - Error", JOptionPane.PLAIN_MESSAGE);
			}
			else 
			{
				showRec(rec_count);
			}
		}
		else if(obj==last_button) 
		{
			rec_count=total-1;
			showRec(rec_count);
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
				JOptionPane.showMessageDialog(null, "There is/are no Customer(s) in the Bank!","Bank System - Empty File", JOptionPane.PLAIN_MESSAGE);
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

	public void showRec(int rr) 
	{
		ac_number_tf.setText(records[rr][0]);
		name_tf.setText(records[rr][1]);
		date_tf.setText(records[rr][2] + " " + records[rr][3] + ", " + records[rr][4]);
		bal_tf.setText(records[rr][5]);
		if(total==0) 
		{ 
			rec_tf.setText(rr + "/" + total);
			date_tf.setText("");
		}
		else 
		{
			rec_tf.setText((rr+1) + "/" + total);
		}
	}

	void btnEnable() 
	{
		first_button.setEnabled(false);
		back_button.setEnabled(false);
		next_button.setEnabled(false);
		last_button.setEnabled(false);
	}
}	