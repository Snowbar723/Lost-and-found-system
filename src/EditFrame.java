import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class EditFrame extends JFrame{
	
	private JTextField type,name,date,place,title;
	private JTextArea post;
	private JLabel text1,textN,textD,textP,textC,textT,content,page;
	private JButton search,back,edit,delete,re1,re2,re3,re4,re5,nextP,lastP;
	private JComboBox categories;
	private ArrayList<String> results;
	private int currentPage,pages;
	private String ID;
	ResultSet re;
	Connection conn;
	Statement stat;
	
	public EditFrame(Connection conn) {
		this.conn = conn;
		setVisible(true);
		setSize(800,470);
		setTitle("�s��K��");

		currentPage=1;
		createTextField();
		createTextArea();
		createLabel();
		createCombo();
		createButton();
		//createTable();
		createLayout();
	}
	
	public void createTextField() {
		type = new JTextField(15);
		name = new JTextField(13);
		date = new JTextField(13);
		place = new JTextField(14);
		title = new JTextField(35);
	}
	
	public void createTextArea() {
		post = new JTextArea(13,40);
	}
	
	public void createLabel() {
		text1 = new JLabel("�п�J�o��ɨϥΤ��m�W:");
		textN = new JLabel("�B��̩m�W:");
		textD = new JLabel("�B����:");
		textP = new JLabel("�B��a�I: ");
		textC = new JLabel("�������O:");
		textT = new JLabel("�峹���D:");
		content = new JLabel("�峹���e:");
		page = new JLabel("  ��"+currentPage+"��  ");
		page.setFont(new Font(page.getFont().getName(), page.getFont().getStyle(), 17));
	}
	
	private void createCombo() {
		categories = new JComboBox();
		
		categories.addItem("��  ��  ��  ��  �O ");
		categories.addItem("���]");
		categories.addItem("�ǥ���");
		categories.addItem("�{��");
		categories.addItem("���");
		categories.addItem("���q");
		categories.addItem("���");
		categories.addItem("�B��");
		categories.addItem("�I�]");
		categories.addItem("�_��");
		categories.addItem("��L");
	}
	
	
	public void createButton() {
		search = new JButton("�j�M");
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				name.setText("");
				date.setText("");
				place.setText("");
				categories.setSelectedItem("�п�����O");
				title.setText("");
				post.setText("");
				String name = type.getText();
				String query = String.format("SELECT Date,Title FROM `Posts` WHERE Name = \"%s\"",name);
				try {
					stat = conn.createStatement();
					boolean hasResultSet = stat.execute(query);
					if(hasResultSet) {
						re = stat.getResultSet();
						ResultSetMetaData metaData = re.getMetaData();
						int columnCount = metaData.getColumnCount();
						int num = 1;
						results = new ArrayList<String>();
						while(re.next()) {
							String text = "";
							for(int i=1;i<=columnCount;i++) {
								text+=String.format("%s        ", re.getString(i));
							}results.add(text);
						}
						
						pages = (results.size()/5);
						if(results.size()%5!=0) {
							pages++;
						}
						
						int i = currentPage-1;
							for(int j=1;j<6;j++) {
								JButton re = FindRe(j);
								re.setFont(new Font(re.getFont().getName(), re.getFont().getStyle(), 15));
								re.setHorizontalAlignment(SwingConstants.LEFT);
								if(((i*5)+j-1)<results.size()){
									re.setText(results.get((i*5)+j-1));
								}else{
									re.setText("");
								}
							}
						}
					} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		back = new JButton("��^�D��");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					HomePageFrame homeF = new HomePageFrame(conn);
					dispose();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		edit = new JButton("��s�K��");
		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String n = name.getText();
				String d = date.getText();
				String p = place.getText();
				String c = categories.getSelectedItem().toString();
				String t = title.getText();
				String content = post.getText();
				String query = String.format("UPDATE `Posts` SET `Name`=\"%s\",`Date`=\"%s\",`Place`=\"%s\",`Category`=\"%s\",`Title`=\"%s\",`Content`=\"%s\" WHERE ID=\"%s\";",n,d,p,c,t,content,ID.toString());
				try {
					stat = conn.createStatement();
					stat.execute(query);
					JOptionPane.showMessageDialog(null, "          ��s�K�妨�\!","�q��",JOptionPane.INFORMATION_MESSAGE);
					name.setText("");
					date.setText("");
					place.setText("");
					categories.setSelectedItem("�п�����O");
					title.setText("");
					post.setText("");
					ID="0";
					
					String name = type.getText();
					String query2 = String.format("SELECT Date,Title FROM `Posts` WHERE Name = \"%s\"",name);
						stat = conn.createStatement();
						boolean hasResultSet = stat.execute(query2);
						if(hasResultSet) {
							re = stat.getResultSet();
							ResultSetMetaData metaData = re.getMetaData();
							int columnCount = metaData.getColumnCount();
							int num = 1;
							results = new ArrayList<String>();
							while(re.next()) {
								String text = "";
								for(int i=1;i<=columnCount;i++) {
									text+=String.format("%s        ", re.getString(i));
								}
								results.add(text);
							}
							
							pages = (results.size()/5);
							
							if(results.size()%5!=0) {
								pages++;
							}
							
							int i = currentPage-1;
							for(int j=1;j<6;j++) {
								JButton re = FindRe(j);
								re.setFont(new Font(re.getFont().getName(), re.getFont().getStyle(), 15));
								re.setHorizontalAlignment(SwingConstants.LEFT);
								if(((i*5)+j-1)<results.size()){
									re.setText(results.get((i*5)+j-1));
								}else{
									re.setText("");
									}
								}
							}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		delete = new JButton("�R���K��");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String query = String.format("DELETE FROM `Posts` WHERE ID=\"%s\";", ID);
				try {
					stat = conn.createStatement();
					stat.execute(query);
					JOptionPane.showMessageDialog(null, "          �R���K�妨�\!","�q��",JOptionPane.INFORMATION_MESSAGE);
					
					name.setText("");
					date.setText("");
					place.setText("");
					categories.setSelectedItem("�п�����O");
					title.setText("");
					post.setText("");
					ID="0";
					
					String name = type.getText();
					String query2 = String.format("SELECT Date,Title FROM `Posts` WHERE Name = \"%s\"",name);
						stat = conn.createStatement();
						boolean hasResultSet = stat.execute(query2);
						if(hasResultSet) {
							re = stat.getResultSet();
							ResultSetMetaData metaData = re.getMetaData();
							int columnCount = metaData.getColumnCount();
							int num = 1;
							results = new ArrayList<String>();
							while(re.next()) {
								String text = "";
								for(int i=1;i<=columnCount;i++) {
									text+=String.format("%s        ", re.getString(i));
								}
								results.add(text);
							}
							
							pages = (results.size()/5);
							
							if(results.size()%5!=0) {
								pages++;
							}
							
							int i = currentPage-1;
							for(int j=1;j<6;j++) {
								JButton re = FindRe(j);
								re.setFont(new Font(re.getFont().getName(), re.getFont().getStyle(), 15));
								re.setHorizontalAlignment(SwingConstants.LEFT);
								if(((i*5)+j-1)<results.size()){
									re.setText(results.get((i*5)+j-1));
								}else{
									re.setText("");
									}
								}
							}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		re1 = new JButton();
		re1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String d = re1.getText().substring(0, 10);
				String t = re1.getText().substring(18);
				//System.out.println(date);
				//System.out.print(title);
				String query = String.format("SELECT ID FROM `Posts` WHERE Date=\"%s\" && Title=\"%s\";", d,t);
				try {
					stat = conn.createStatement();
					boolean hasResultSet = stat.execute(query);
					if(hasResultSet) {
						re = stat.getResultSet();
						while(re.next()) {
							setID(re.getInt(1));
						}
					}
					query= String.format("SELECT Name,Date,Place,Category,Title,Content FROM `Posts` WHERE ID=\"%s\";", ID);
					boolean hasResultSet2 = stat.execute(query);
					if(hasResultSet2) {
						re = stat.getResultSet();
						ResultSetMetaData metaData2 = re.getMetaData();
						int columnCount2 = metaData2.getColumnCount();
						while(re.next()) {
								name.setText(re.getString(1));
								date.setText(re.getString(2));
								place.setText(re.getString(3));
								categories.setSelectedItem(re.getString(4));
								title.setText(re.getString(5));
								post.setText(re.getString(6));
						}
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		re2 = new JButton();
		re2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String d = re2.getText().substring(0, 10);
				String t = re2.getText().substring(18);
				//System.out.println(date);
				//System.out.print(title);
				String query = String.format("SELECT ID FROM `Posts` WHERE Date=\"%s\" && Title=\"%s\";", d,t);
				try {
					stat = conn.createStatement();
					boolean hasResultSet = stat.execute(query);
					if(hasResultSet) {
						re = stat.getResultSet();
						while(re.next()) {
							setID(re.getInt(1));
						}
					}
					query= String.format("SELECT Name,Date,Place,Category,Title,Content FROM `Posts` WHERE ID=\"%s\";", ID);
					boolean hasResultSet2 = stat.execute(query);
					if(hasResultSet2) {
						re = stat.getResultSet();
						ResultSetMetaData metaData2 = re.getMetaData();
						int columnCount2 = metaData2.getColumnCount();
						while(re.next()) {
								name.setText(re.getString(1));
								date.setText(re.getString(2));
								place.setText(re.getString(3));
								categories.setSelectedItem(re.getString(4));
								title.setText(re.getString(5));
								post.setText(re.getString(6));
						}
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		re3 = new JButton();
		re3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String d = re3.getText().substring(0, 10);
				String t = re3.getText().substring(18);
				//System.out.println(date);
				//System.out.print(title);
				String query = String.format("SELECT ID FROM `Posts` WHERE Date=\"%s\" && Title=\"%s\";", d,t);
				try {
					stat = conn.createStatement();
					boolean hasResultSet = stat.execute(query);
					if(hasResultSet) {
						re = stat.getResultSet();
						while(re.next()) {
							setID(re.getInt(1));
						}
					}
					query= String.format("SELECT Name,Date,Place,Category,Title,Content FROM `Posts` WHERE ID=\"%s\";", ID);
					boolean hasResultSet2 = stat.execute(query);
					if(hasResultSet2) {
						re = stat.getResultSet();
						ResultSetMetaData metaData2 = re.getMetaData();
						int columnCount2 = metaData2.getColumnCount();
						while(re.next()) {
								name.setText(re.getString(1));
								date.setText(re.getString(2));
								place.setText(re.getString(3));
								categories.setSelectedItem(re.getString(4));
								title.setText(re.getString(5));
								post.setText(re.getString(6));
						}
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		re4 = new JButton();
		re4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String d = re4.getText().substring(0, 10);
				String t = re4.getText().substring(18);
				//System.out.println(date);
				//System.out.print(title);
				String query = String.format("SELECT ID FROM `Posts` WHERE Date=\"%s\" && Title=\"%s\";", d,t);
				try {
					stat = conn.createStatement();
					boolean hasResultSet = stat.execute(query);
					if(hasResultSet) {
						re = stat.getResultSet();
						while(re.next()) {
							setID(re.getInt(1));
						}
					}
					query= String.format("SELECT Name,Date,Place,Category,Title,Content FROM `Posts` WHERE ID=\"%s\";", ID);
					boolean hasResultSet2 = stat.execute(query);
					if(hasResultSet2) {
						re = stat.getResultSet();
						ResultSetMetaData metaData2 = re.getMetaData();
						int columnCount2 = metaData2.getColumnCount();
						while(re.next()) {
								name.setText(re.getString(1));
								date.setText(re.getString(2));
								place.setText(re.getString(3));
								categories.setSelectedItem(re.getString(4));
								title.setText(re.getString(5));
								post.setText(re.getString(6));
						}
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		re5 = new JButton();
		re5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String d = re5.getText().substring(0, 10);
				String t = re5.getText().substring(18);
				//System.out.println(date);
				//System.out.print(title);
				String query = String.format("SELECT ID FROM `Posts` WHERE Date=\"%s\" && Title=\"%s\";", d,t);
				try {
					stat = conn.createStatement();
					boolean hasResultSet = stat.execute(query);
					if(hasResultSet) {
						re = stat.getResultSet();
						while(re.next()) {
							setID(re.getInt(1));
						}
					}
					query= String.format("SELECT Name,Date,Place,Category,Title,Content FROM `Posts` WHERE ID=\"%s\";", ID);
					boolean hasResultSet2 = stat.execute(query);
					if(hasResultSet2) {
						re = stat.getResultSet();
						ResultSetMetaData metaData2 = re.getMetaData();
						int columnCount2 = metaData2.getColumnCount();
						while(re.next()) {
								name.setText(re.getString(1));
								date.setText(re.getString(2));
								place.setText(re.getString(3));
								categories.setSelectedItem(re.getString(4));
								title.setText(re.getString(5));
								post.setText(re.getString(6));
						}
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		nextP = new JButton("�U�@��");
		nextP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(currentPage<pages) {
					currentPage++;
					page.setText("  ��"+currentPage+"��  ");
					int i = currentPage-1;
					for(int j=1;j<6;j++) {
						JButton re = FindRe(j);
						re.setFont(new Font(re.getFont().getName(), re.getFont().getStyle(), 15));
						re.setHorizontalAlignment(SwingConstants.LEFT);
						if(((i*5)+j-1)<results.size()){
							re.setText(results.get((i*5)+j-1));
						}else{
							re.setText("");
					}
				}
				
					
				}
			}
		});
		
		lastP = new JButton("�e�@��");
		lastP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(currentPage>1) {
					currentPage--;
					page.setText("  ��"+currentPage+"��  ");
					int i = currentPage-1;
					for(int j=1;j<6;j++) {
						JButton re = FindRe(j);
						re.setFont(new Font(re.getFont().getName(), re.getFont().getStyle(), 15));
						re.setHorizontalAlignment(SwingConstants.LEFT);
						if(((i*5)+j-1)<results.size()){
							re.setText(results.get((i*5)+j-1));
						}else{
							re.setText("");
					}
				}
				
					
				}
			}
		});
		
	}
	
		public void setID(int id) {
			ID = Integer.toString(id);
		}
	
		public JButton FindRe(int i) {
			switch(i) {
			case 1:return re1;
			case 2:return re2;
			case 3:return re3;
			case 4:return re4;
			case 5:return re5;
			default:return null;
			}
		}
	
	/*public void createTable() {
		String[] columnNames = {"�峹���D","���"};
		Object[][] items = {{"���W�n�F�j","2022-03-15"},{"�{�b�ڦ��B�E�O","2022-04-01"}};
		result = new JTable(items,columnNames);
	}*/
	
	public void createLayout() {
		JPanel P = new JPanel(new GridLayout(1,2));
		JPanel result = new JPanel(new GridLayout(5,1));
			result.setPreferredSize(new Dimension(360,350));
			result.add(re1);
			result.add(re2);
			result.add(re3);
			result.add(re4);
			result.add(re5);
		JPanel page = new JPanel();
			page.add(lastP);
			page.add(this.page);
			page.add(nextP);
		JPanel left = new JPanel();
			left.add(text1);
			left.add(type);
			left.add(search);
			left.add(result);
			left.add(page);
			left.setBorder(new LineBorder(Color.BLACK));
		JPanel buttons = new JPanel();
			buttons.add(back);
			buttons.add(delete);
			buttons.add(edit);
		JPanel nameNdate = new JPanel();
			nameNdate.add(textN);
			nameNdate.add(name);
			nameNdate.add(textD);
			nameNdate.add(date);
		JPanel placeNcate = new JPanel();
			placeNcate.add(textP);
			placeNcate.add(place);
			placeNcate.add(textC);
			placeNcate.add(categories);
		JPanel content = new JPanel(new GridLayout(1,1));
			content.add(this.content);
		JPanel right = new JPanel();
			right.add(nameNdate);
			right.add(placeNcate);
			right.add(textT);
			right.add(title);
			right.add(content);
			right.add(post);
			right.add(buttons);
			right.setBorder(new LineBorder(Color.BLACK));
		P.add(left);
		P.add(right);
		this.add(P);
	}
}
