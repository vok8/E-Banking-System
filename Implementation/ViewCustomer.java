import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.table.DefaultTableModel;
public class ViewCustomer extends JInternalFrame 
{
	private JPanel show_jp=new JPanel();

	private DefaultTableModel dtm_customer;
	private JTable customer_table;
	private JScrollPane jsp_table;

	private int row=0;
	private int total=0;

	//String Type Array used to Load Records' from File.
	private String row_data[][];

	private FileInputStream fis;
	private DataInputStream dis;

	ViewCustomer() 
	{
		super("View All Account Holders", false, true, false, true);
		setSize(475, 280);

		show_jp.setLayout(null);

		populateArray();

		customer_table=makeTable();
		jsp_table=new JScrollPane(customer_table);
		jsp_table.setBounds(20, 20, 425, 200);

		//Adding the Table to Panel
		show_jp.add(jsp_table);

		//Adding Panel to Window
		getContentPane().add(show_jp);

		setVisible (true);
	}

	void populateArray() 
	{
		String rows[][]=new String[500][6];
		try 
		{
			fis=new FileInputStream("Bank.dat");
			dis=new DataInputStream(fis);
			while(true) 
			{
				for(int i=0; i<6; i++)
				{
					rows[row][i]=dis.readUTF();
				}
				row++;
			}
		}
		catch(Exception ex) 
		{
			total=row;
			row_data=new String[total][4];
			if(total==0) 
			{
				JOptionPane.showMessageDialog (null, "There is/are no Customer(s) in the Bank!", "Bank System - Empty File", JOptionPane.PLAIN_MESSAGE);
			}
			else 
			{
				for(int i=0; i<total; i++)
				{
					row_data[i][0]=rows[i][0];
					row_data[i][1]=rows[i][1];
					row_data[i][2]=rows[i][2] + " " + rows[i][3] + ", " + rows[i][4];
					row_data[i][3]=rows[i][5];
				}
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

	//Function to Create the Table and Add Data to Show
	private JTable makeTable() 
	{
		String cols[]={"Account No.", "Customer Name", "Opening Date", "Bank Balance"};
		dtm_customer=new DefaultTableModel(row_data, cols);
		customer_table=new JTable(dtm_customer) 
		{
			public boolean isCellEditable(int iRow, int iCol) 
			{
				return false;
			}
		};
		(customer_table.getColumnModel().getColumn(0)).setPreferredWidth(180);
		(customer_table.getColumnModel().getColumn(1)).setPreferredWidth(275);
		(customer_table.getColumnModel().getColumn(2)).setPreferredWidth(275);
		(customer_table.getColumnModel().getColumn(3)).setPreferredWidth(200);
		customer_table.setRowHeight(20);
		customer_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return customer_table;
	}
}