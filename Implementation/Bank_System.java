import java.awt.*;
import java.awt.event.*;
import java.awt.PrintJob.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.metal.*;
import java.util.*;
import java.io.*;
import java.text.*;
public class Bank_System extends JFrame implements ActionListener, ItemListener 
{
	//Main Window
	private JDesktopPane desktop=new JDesktopPane();

	//Main Menu-Bar.
	private JMenuBar bar;

	//'File' Menu
	private JMenu file;
	private JMenuItem add_new, print_record, quit_mi;

	//'Edit' Menu
	private JMenu edit;
	private	JMenuItem  deposit, withdraw, delete_record, search, search_by_name;

	//'View' Menu
	private JMenu view;
	private	JMenuItem one_by_one, all_customers;

	//'Option' Menu
	private JMenu option;
	private	JMenuItem change, style, theme;

	//'Window' Menu
	private JMenu window;
	private JMenuItem close, close_all;

	//'Help' Menu
	private JMenu help;
	private	JMenuItem content, help_menu_item, about;

	//'Pop-Up' Menu
	private JPopupMenu pop_up_menu=new JPopupMenu();
	private JMenuItem open, report, dep, with, del, find, all;

	//Tool-Bar
	private	JToolBar tool_bar;
	private	JButton new_button, dep_button, with_button, record_button, delete_button, search_button, help_button, key_button;

	//Status-Bar
	private JPanel status_bar=new JPanel();

	//Labels for showing "BankSystem" Name and "Group" Roll_Numbers.
	private JLabel welcome;
	private JLabel roll_numbers;

	//Making the Menu, for different styles.
	private String strings[]={"1. Metal", "2. Motif", "3. Windows"};
	private UIManager.LookAndFeelInfo looks[]=UIManager.getInstalledLookAndFeels();
	private ButtonGroup group=new ButtonGroup();
	private JRadioButtonMenuItem radio[]=new JRadioButtonMenuItem[strings.length];

	//Getting the Current System Date.
	private java.util.Date curr_date=new java.util.Date();
	private SimpleDateFormat sdf=new SimpleDateFormat("dd MMMM yyyy",Locale.getDefault());
	private String d=sdf.format(curr_date);

	//Variables, which will be used to read the System Records' File & Store it in an Array.
	private int count=0;
	private int rows=0;
	private	int total=0;

	//String Type Array, for storing the Records, got from the File.
	private String records[][]=new String[500][6];

	//Variables for Reading the System Records' File.
	private FileInputStream fis;
	private DataInputStream dis;

	//Constructor
	public Bank_System() 
	{
		super("Bank [Pvt] Ltd.");
		UIManager.addPropertyChangeListener(new UISwitchListener ((JComponent)getRootPane()));

		bar=new JMenuBar(); //Menu-Bar

		//Setting the Main Window
		setIconImage(getToolkit().getImage ("Images/Bank.gif"));
		setSize(700,550);
		setJMenuBar(bar);

		//Closing Code of Main Window
		addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent we) 
			{
				quitApp();
			}
		});

		//Setting the Location of Application on Screen.
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-getWidth())/2,(Toolkit.getDefaultToolkit().getScreenSize().height-getHeight())/2);

		//Creating the Menu-Bar Items.
		file=new JMenu("File");
		file.setMnemonic((int)'F');
		edit=new JMenu("Edit");
		edit.setMnemonic((int)'E');
		view=new JMenu("View");
		view.setMnemonic((int)'V');
		option=new JMenu("Options");
		option.setMnemonic((int)'O');
		window=new JMenu("Window");
		window.setMnemonic((int)'W');
		help=new JMenu("Help");
		help.setMnemonic((int)'H');

		//Creating Menu-Items of 'File' Menu
		add_new=new JMenuItem("Open New Account", new ImageIcon("Images/Open.gif"));
		add_new.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,Event.CTRL_MASK));
		add_new.setMnemonic((int)'N');
		add_new.addActionListener(this);
		print_record=new JMenuItem("Print Customer Balance", new ImageIcon("Images/New.gif"));
		print_record.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,Event.CTRL_MASK));
		print_record.setMnemonic((int)'R');
		print_record.addActionListener(this);
		quit_mi=new JMenuItem("Quit", new ImageIcon("Images/export.gif"));
		quit_mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,Event.CTRL_MASK));
		quit_mi.setMnemonic((int)'Q');	
		quit_mi.addActionListener(this);

		//Creating Menu-Items of 'Edit' Menu
		deposit=new JMenuItem("Deposit Money");
		deposit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,Event.CTRL_MASK));
		deposit.setMnemonic((int)'T');
		deposit.addActionListener(this);
		withdraw=new JMenuItem("Withdraw Money");
		withdraw.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,Event.CTRL_MASK));
		withdraw.setMnemonic((int)'W');	
		withdraw.addActionListener(this);
		delete_record=new JMenuItem("Delete Customer",new ImageIcon("Images/Delete.gif"));
		delete_record.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,Event.CTRL_MASK));
		delete_record.setMnemonic((int)'D');
		delete_record.addActionListener(this);
		search=new JMenuItem("Search By Account No.", new ImageIcon("Images/find.gif"));
		search.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,Event.CTRL_MASK));
		search.setMnemonic((int)'S');	
		search.addActionListener(this);
		search_by_name=new JMenuItem("Search By Name");
		search_by_name.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,Event.CTRL_MASK));
		search_by_name.setMnemonic((int)'M');
		search_by_name.addActionListener(this);

		//Creating Menu-Items of 'View' Menu
		one_by_one=new JMenuItem("View Accounts, One By One");
		one_by_one.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,Event.CTRL_MASK));
		one_by_one.setMnemonic((int)'O');	
		one_by_one.addActionListener(this);
		all_customers=new JMenuItem("View All Accounts",new ImageIcon("Images/refresh.gif"));
		all_customers.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,Event.CTRL_MASK));
		all_customers.setMnemonic((int)'A');
		all_customers.addActionListener(this);

		//Creating Menu-Items of 'Option' Menu
		change=new JMenuItem("Change Background Color");
		change.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,Event.CTRL_MASK));
		change.setMnemonic((int)'B');
		change.addActionListener(this);
		style=new JMenu("Change Layout Style");
		style.setMnemonic((int)'L');
		for(int i=0; i<radio.length; i++) 
		{
			radio[i]=new JRadioButtonMenuItem(strings[i]);
			radio[i].addItemListener(this);			
			group.add(radio[i]); //Grouping them			
			style.add(radio[i]);					
		}
		MetalTheme[] themes={new DefaultMetalTheme(), new GreenTheme(), new AquaTheme(), new SandTheme(), new SolidTheme(), new MilkyTheme(), new GrayTheme()};
		theme=new MetalThemeMenu("Apply Theme",themes);
		theme.setMnemonic((int)'M');

		//Creating Menu-Items of 'Window' Menu
		close=new JMenuItem("Close Active Window");
		close.setMnemonic((int)'C');
		close.addActionListener(this);
		close_all=new JMenuItem("Close All Windows");
		close_all.setMnemonic((int)'A');
		close_all.addActionListener(this);

		//Creating Menu-Items of 'Help' Menu
		content=new JMenuItem("Help!",new ImageIcon("Images/paste.gif"));
		content.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,Event.CTRL_MASK));
		content.setMnemonic((int)'H');
		content.addActionListener(this);
		help_menu_item=new JMenuItem("Help on Shortcuts!");
		help_menu_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K,Event.CTRL_MASK));
		help_menu_item.setMnemonic((int)'K');
		help_menu_item.addActionListener(this);
		about=new JMenuItem("About System",new ImageIcon("Images/Save.gif"));
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,Event.CTRL_MASK));
		about.setMnemonic((int)'C');
		about.addActionListener(this);


		//Adding Menu-Items' to respective Menus.
		file.add(add_new);
		file.addSeparator();
		file.add(print_record);
		file.addSeparator();
		file.add(quit_mi);
		edit.add(deposit);
		edit.add(withdraw);
		edit.addSeparator();
		edit.add(delete_record);
		edit.addSeparator();
		edit.add(search);
		edit.add(search_by_name);
		view.add(one_by_one);
		view.addSeparator();
		view.add(all_customers);
		option.add(change);
		option.addSeparator();
		option.add(style);
		option.addSeparator();
		option.add(theme);
		window.add(close);
		window.add(close_all);
		help.add(content);
		help.addSeparator();
		//help.add(help_menu_item);
		//help.addSeparator();
		help.add(about);

		//Adding Menu's to Menu-Bar
		bar.add(file);
		bar.add(edit);
		bar.add(view);
		bar.add(option);
		bar.add(window);
		bar.add(help);

		//Menu-Items for Pop-Up Menu
		open=new JMenuItem("Open New Account",new ImageIcon("Images/Open.gif"));
		open.addActionListener(this);
		report=new JMenuItem("Print Customer Report",new ImageIcon("Images/New.gif"));
		report.addActionListener(this);
		dep=new JMenuItem("Deposit Money");
		dep.addActionListener(this);
		with=new JMenuItem("Withdraw Money");
		with.addActionListener(this);
		del=new JMenuItem("Delete Customer",new ImageIcon("Images/Delete.gif"));
		del.addActionListener(this);
		find=new JMenuItem("Search Customer",new ImageIcon("Images/find.gif"));
		find.addActionListener(this);
		all=new JMenuItem("View All Customers",new ImageIcon("Images/refresh.gif"));
		all.addActionListener(this);

		//Adding Menu-Items to Pop-Up Menu
		pop_up_menu.add(open);
		pop_up_menu.add(report);
		pop_up_menu.add(dep);
		pop_up_menu.add(with);
		pop_up_menu.add(del);
		pop_up_menu.add(find);
		pop_up_menu.add(all);

		addMouseListener(new MouseAdapter() 
		{
			public void mousePressed(MouseEvent me)
			{ 
				checkMouseTrigger(me); 
			}
			public void mouseReleased(MouseEvent me) 
			{ 
				checkMouseTrigger(me); 
			}
			private void checkMouseTrigger(MouseEvent me) 
			{
				if(me.isPopupTrigger())
				{
					pop_up_menu.show(me.getComponent(),me.getX(),me.getY());
				}
			}
		});
		
		//Creating Buttons of the Tool-Bar
		new_button=new JButton(new ImageIcon("Images/NotePad.gif"));
		new_button.setToolTipText("Create New Account");
		new_button.addActionListener(this);
		dep_button=new JButton(new ImageIcon("Images/ImationDisk.gif"));
		dep_button.setToolTipText("Deposit Money");
		dep_button.addActionListener(this);
		with_button=new JButton(new ImageIcon("Images/SuperDisk.gif"));
		with_button.setToolTipText("Withdraw Money");
		with_button.addActionListener(this);
		record_button=new JButton(new ImageIcon("Images/Paproll.gif"));
		record_button.setToolTipText("Print Customer Balance");
		record_button.addActionListener(this);
		delete_button=new JButton(new ImageIcon("Images/Toaster.gif"));
		delete_button.setToolTipText("Delete Customer");
		delete_button.addActionListener(this);
		search_button=new JButton(new ImageIcon("Images/Search.gif"));
		search_button.setToolTipText("Search Customer");
		search_button.addActionListener(this);
		help_button=new JButton(new ImageIcon("Images/Help.gif"));
		help_button.setToolTipText("Help on Bank System");
		help_button.addActionListener(this);
		key_button=new JButton(new ImageIcon("Images/Keys.gif"));
		key_button.setToolTipText("Shortcut Keys of System");
		key_button.addActionListener(this);

		//Creating the Tool-Bar
		tool_bar=new JToolBar();
		tool_bar.add(new_button);
		tool_bar.addSeparator();
		tool_bar.add(dep_button);
		tool_bar.add(with_button);
		tool_bar.addSeparator();
		tool_bar.add(record_button);
		tool_bar.addSeparator();
		tool_bar.add(delete_button);
		tool_bar.addSeparator();
		tool_bar.add(search_button);
		tool_bar.addSeparator();
		tool_bar.add(help_button);
		// tool_bar.add(key_button);

		//Creating the Status-Bar
		roll_numbers=new JLabel(" " + "Bank [Pvt] Ltd.",Label.LEFT);
		roll_numbers.setForeground(Color.black);
		roll_numbers.setToolTipText("Program's Title");
		welcome=new JLabel("Welcome, Today is " + d + " ",JLabel.RIGHT);
		welcome.setForeground(Color.black);
		welcome.setToolTipText("Welcoming the User & System Current Date");
		status_bar.setLayout(new BorderLayout());
		status_bar.add(roll_numbers,BorderLayout.WEST);
		status_bar.add(welcome,BorderLayout.EAST);

		//For Making the Dragging Speed of Internal Frames Faster.
		desktop.putClientProperty("JDesktopPane.dragMode","outline");

		//Setting the Contents of Programs.
		getContentPane().add(tool_bar,BorderLayout.NORTH);
		getContentPane().add(desktop,BorderLayout.CENTER);
		getContentPane().add(status_bar,BorderLayout.SOUTH);

		setVisible (true);
	}

	public void actionPerformed(ActionEvent ae)
	{
		Object obj=ae.getSource();
		if(obj==add_new || obj==open || obj==new_button)
		{
			if(openChildWindow("Create New Account")==false) 
			{
				NewAccount new_acc=new NewAccount();
				desktop.add(new_acc);
				new_acc.show();
			}
		}
		else if(obj==print_record || obj==record_button || obj==report) 
		{
			getAccountNo();
		}
		else if(obj==quit_mi) 
		{
			quitApp();
		}
		else if(obj==deposit || obj==dep || obj==dep_button) 
		{
			if(openChildWindow("Deposit Money")==false) 
			{
				DepositMoney dep_mon=new DepositMoney();
				desktop.add(dep_mon);
				dep_mon.show();
			}
		}
		else if(obj==withdraw || obj==with || obj==with_button) 
		{
			if(openChildWindow ("Withdraw Money")==false) 
			{
				WithdrawMoney with_mon=new WithdrawMoney();
				desktop.add(with_mon);
				with_mon.show();
			}
		}
		else if(obj==delete_record || obj==del || obj==delete_button) 
		{
			if(openChildWindow("Delete Account Holder")==false) 
			{
				DeleteCustomer del_cus=new DeleteCustomer();
				desktop.add(del_cus);
				del_cus.show();
			}
		}
		else if(obj==search || obj==find || obj==search_button) 
		{
			if(openChildWindow("Search Customer [By No.]")==false) 
			{
				FindAccount find_account=new FindAccount ();
				desktop.add(find_account);
				find_account.show();
			}
		}
		else if(obj==search_by_name) 
		{
			if(openChildWindow("Search Customer [By Name]")==false) 
			{
				FindName find_name=new FindName();
				desktop.add(find_name);
				find_name.show();
			}
		}
		else if(obj==one_by_one) 
		{
			if(openChildWindow("View Account Holders")==false) 
			{
				ViewOne view_obo=new ViewOne();
				desktop.add(view_obo);
				view_obo.show();
			}
		}
		else if(obj==all_customers || obj==all) 
		{
			if(openChildWindow("View All Account Holders")==false) 
			{
				ViewCustomer view_cus=new ViewCustomer();
				desktop.add(view_cus);
				view_cus.show();
			}
		}
		else if(obj==change) 
		{
			Color color=new Color(153,153,204);
			color=JColorChooser.showDialog(this,"Choose Background Color",color);
			if(color!=null) 
			{
				desktop.setBackground(color);
				desktop.repaint();
			}
		}
		else if(obj==close) 
		{
			try 
			{
				desktop.getSelectedFrame().setClosed(true);
			}
			catch(Exception CloseExc)
			{ 
			}
		}
		else if(obj==close_all) 
		{

			JInternalFrame Frames[]=desktop.getAllFrames(); //Getting all Open Frames.
			for(int i=0; i<Frames.length; i++) 
			{
				try 
				{
	 				Frames[i].setClosed(true); //Close the frame.
				} 
				catch(Exception CloseExc) //Catch "Can't Close" Exception
				{ 
				}
			}

		}
		else if(obj==content || obj==help_button) 
		{
			if(openChildWindow ("System Help")==false) 
			{
				BankHelp help_bank=new BankHelp("System Help","Help/Bank.htm");
				desktop.add(help_bank);
				help_bank.show();
			}
		}
		else if(obj==help_menu_item || obj==key_button) 
		{
			if(openChildWindow("System Keys")==false) 
			{
				BankHelp help_key=new BankHelp("System Keys","Help/Keys.htm");
				desktop.add(help_key);
				help_key.show();
			}
		}
		else if(obj==about) 
		{

			String message="Bank [Pvt] Ltd.\n\n" + "Created & Designed By: \n" + "Vinay (126), Vasu (123), & Umang (120)\n\n";
			JOptionPane.showMessageDialog(this,message,"About System",JOptionPane.PLAIN_MESSAGE);
		}
	}

	public void itemStateChanged(ItemEvent e) 
	{
		for(int i=0; i<radio.length; i++)
		{
			if(radio[i].isSelected()) 
			{
				changeLookAndFeel(i);
			}
		}
	}	

	private void quitApp() 
	{
		try 
		{
			int reply=JOptionPane.showConfirmDialog(this,"Do you want to exit?\nFrom System? (Please Confirm)","System - Exit",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
			if(reply==JOptionPane.YES_OPTION) 
			{
				setVisible (false);
				dispose();
				System.out.println("Thank you for using the System!\n");
				System.exit(0);
			}
			else if(reply==JOptionPane.NO_OPTION) 
			{
				setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		} 
		catch(Exception e) //Catch any Exception in Closing the System
		{
		}
	}

	public void changeLookAndFeel(int val) 
	{
		try 
		{
			UIManager.setLookAndFeel(looks[val].getClassName());
			SwingUtilities.updateComponentTreeUI(this);
		}
		catch(Exception e) //Catch Every Exception, while changing the 'Style'
		{ 
		}
	}

	private boolean openChildWindow(String title) 
	{
		JInternalFrame[] childs=desktop.getAllFrames();
		for(int i=0; i<childs.length; i++) 
		{
			if(childs[i].getTitle().equalsIgnoreCase(title)) 
			{
				childs[i].show();
				return true;
			}
		}
		return false;
	}

	void getAccountNo() 
	{
		String printing;
		rows=0;
		if(populateArray()==true) 
		{
			try 
			{
				printing=JOptionPane.showInputDialog(this, "Enter Account No. to Print Customer Balance: \n" + "(Tip: Account No. Contains only Digits)", "System - Print_Record", JOptionPane.PLAIN_MESSAGE);
				if(printing.equals("")) 
				{
					JOptionPane.showMessageDialog(this, "Please provide Account No. to Print.", "System - Empty Field", JOptionPane.PLAIN_MESSAGE);
					getAccountNo();
				}
				else 
				{
					findRec(printing);
				}
			}
			catch(Exception e) 
			{ 
			}
		}
	}

	boolean populateArray() 
	{
		boolean b=false;
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
				JOptionPane.showMessageDialog(null, "There is/are no Customer(s) in the Bank!", "System - Empty File", JOptionPane.PLAIN_MESSAGE);
				b=false;
			}
			else 
			{
				b=true;
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
		return b;
	}

	void findRec(String rec) 
	{
		boolean found=false;
		for(int i=0; i<total; i++)
		{
			if(records[i][0].equals(rec)) 
			{
				found=true;
				print_rec(makeRecordPrint(i));
				break;
			}
		}
		if(found==false) 
		{
			JOptionPane.showMessageDialog(this, "Account No. " + rec + " doesn't Exist.", "System - Wrong Account No.", JOptionPane.PLAIN_MESSAGE);
			getAccountNo();
		}
	}

	String makeRecordPrint(int rec) 
	{
		String data0="               Bank [Pvt] Limited.               \n";
		String data1="               Customer Balance Report.              \n\n";	//Page Header.
		String data2="  Account No.:       " + records[rec][0] + "\n";
		String data3="  Customer Name:     " + records[rec][1] + "\n";
		String data4="  Last Transaction:  " + records[rec][2] + " " + records[rec][3] + ", " + records[rec][4] + "\n";
		String data5="  Current Balance:   " + records[rec][5] + "\n\n";
		String sep=" -----------------------------------------------------------\n";

		String final_data=data0+sep+data1+data2+sep+data3+sep+data4+sep+data5;
		return final_data;
	}

	void print_rec(String rec) 
	{
		StringReader sr=new StringReader(rec);
		LineNumberReader lnr=new LineNumberReader(sr);
		Font typeface=new Font("Times New Roman", Font.PLAIN, 12);
		Properties p=new Properties();
		PrintJob pJob=getToolkit().getPrintJob(this, "Print Customer Balance Report", p);
		if(pJob!=null) 
		{
			Graphics gr=pJob.getGraphics();
			if(gr!=null) 
			{
				FontMetrics fm=gr.getFontMetrics(typeface);
				int margin=20;
				int pageHeight=pJob.getPageDimension().height-margin;
    			int fontHeight=fm.getHeight();
	    		int fontDescent=fm.getDescent();
    			int curHeight=margin;
				String nextLine;
				gr.setFont(typeface);
				try 
				{
					do 
					{
						nextLine=lnr.readLine();
						if(nextLine!=null) 
						{         
							if((curHeight+fontHeight)>pageHeight) //New Page
							{
								gr.dispose();
								gr=pJob.getGraphics();
								curHeight=margin;
							}							
							curHeight+=fontHeight;
							if(gr!=null) 
							{
								gr.setFont(typeface);
								gr.drawString(nextLine, margin, curHeight-fontDescent);
							}
						}
					}
					while(nextLine!=null);					
				}
				catch(EOFException eof) //Catch EOF Exception
				{
				}
				catch(Throwable t) //Catch Throwable Exception
				{ 
				}
			}
			gr.dispose();
		}
	}
}