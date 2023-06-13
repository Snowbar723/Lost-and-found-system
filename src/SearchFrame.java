import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class SearchFrame extends JFrame{

	private JTextField searchP,searchD,name,date,place,category,title;
	private JLabel textN,textD,textP,textC,textT,content,page;
	private JTextArea post;
	private JComboBox categories;
	private JButton search,back,re1,re2,re3,re4,nextP,lastP;
	private JRadioButton Place,Date,Category;
	private ButtonGroup group;
	private ArrayList<String> results;
	private int currentPage,pages;
	private String ID;
	ResultSet re;
	Connection conn;
	Statement state;
	
	public SearchFrame(Connection conn) {
		this.conn = conn;
		setVisible(true);
		setSize(800,470);
		setTitle("�j�M�K��");
		
		currentPage=1;
		createLabel();
		createRadioButton();
		createTextField();
		createTextArea();
		createCombo();
		createButton();
		createLayout();
	}
	
	public void createLabel() {
		textN = new JLabel("�B��̩m�W:");
		textD = new JLabel("�B����:");
		textP = new JLabel("�B��a�I: ");
		textC = new JLabel("�������O:");
		textT = new JLabel("�峹���D:");
		content = new JLabel("�峹���e:");
		page = new JLabel("  ��"+currentPage+"��  ");
		page.setFont(new Font(page.getFont().getName(), page.getFont().getStyle(), 15));
	}
	
	public void createTextArea() {
		post = new JTextArea(14,40);
		post.setEditable(false);
	}
	
	public void createRadioButton() {
		Place = new JRadioButton("�H�a�I�j�M:");
		Date = new JRadioButton("�H����j�M:");
		Category = new JRadioButton("�H���O�j�M:");
		group=new ButtonGroup();
		group.add(Place);
		group.add(Date);
		group.add(Category);
	}
	
	public void createTextField() {
		searchP = new JTextField(15);
		searchD = new JTextField(15);
		name = new JTextField(13);
		date = new JTextField(13);
		place = new JTextField(14);
		category = new JTextField(13);
		title = new JTextField(35);
		name.setEditable(false);
		date.setEditable(false);
		place.setEditable(false);
		category.setEditable(false);
		title.setEditable(false);
	}
	
	private void createCombo() {
		categories = new JComboBox();
		
		categories.addItem("��  ��  ��  ��  �O  :   ");
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
			public void actionPerformed(ActionEvent event) {
				name.setText("");
				date.setText("");
				place.setText("");
				category.setText("");
				title.setText("");
				post.setText("");
				String keyword = "";
				currentPage=1;
				page.setText("  ��"+currentPage+"��  ");
				
				if(Place.isSelected()) {
					keyword = searchP.getText();
					String query = String.format("SELECT Date,Title FROM `Posts` WHERE Place = \"%s\"",keyword);
					try {
						state = conn.createStatement();
						boolean hasResultSet = state.execute(query);
						if(hasResultSet) {
							re = state.getResultSet();
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
							
							pages = (results.size()/4);
							if(results.size()%4!=0) {
								pages++;
							}
							
							int i = currentPage-1;
								for(int j=1;j<5;j++) {
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
						searchD.setText("");
						categories.setSelectedItem("��  ��  ��  ��  �O  :   ");
					}catch (SQLException e) {
						e.printStackTrace();
					}
				}else if(Date.isSelected()) {
					keyword = searchD.getText();
					String query = String.format("SELECT Date,Title FROM `Posts` WHERE Date = \"%s\"",keyword);
					try {
						state = conn.createStatement();
						boolean hasResultSet = state.execute(query);
						if(hasResultSet) {
							re = state.getResultSet();
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
							
							pages = (results.size()/4);
							if(results.size()%4!=0) {
								pages++;
							}
							
							int i = currentPage-1;
								for(int j=1;j<5;j++) {
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
						searchP.setText("");
						categories.setSelectedItem("��  ��  ��  ��  �O  :   ");
					}catch (SQLException e) {
						e.printStackTrace();
					}
				}else if(Category.isSelected()) {
					keyword = categories.getSelectedItem().toString();
					String query = String.format("SELECT Date,Title FROM `Posts` WHERE Category = \"%s\"",keyword);
					try {
						state = conn.createStatement();
						boolean hasResultSet = state.execute(query);
						if(hasResultSet) {
							re = state.getResultSet();
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
							
							pages = (results.size()/4);
							if(results.size()%4!=0) {
								pages++;
							}
							
							int i = currentPage-1;
								for(int j=1;j<5;j++) {
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
						searchP.setText("");
						searchD.setText("");
					}catch (SQLException e) {
						e.printStackTrace();
					}
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
		
		re1 = new JButton();
		re1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String d = re1.getText().substring(0, 10);
				String t = re1.getText().substring(18);
				//System.out.println(date);
				//System.out.print(title);
				String query = String.format("SELECT ID FROM `Posts` WHERE Date=\"%s\" && Title=\"%s\";", d,t);
				try {
					state = conn.createStatement();
					boolean hasResultSet = state.execute(query);
					if(hasResultSet) {
						re = state.getResultSet();
						while(re.next()) {
							setID(re.getInt(1));
						}
					}
					query= String.format("SELECT Name,Date,Place,Category,Title,Content FROM `Posts` WHERE ID=\"%s\";", ID);
					boolean hasResultSet2 = state.execute(query);
					if(hasResultSet2) {
						re = state.getResultSet();
						ResultSetMetaData metaData2 = re.getMetaData();
						int columnCount2 = metaData2.getColumnCount();
						while(re.next()) {
								name.setText(re.getString(1));
								date.setText(re.getString(2));
								place.setText(re.getString(3));
								category.setText(re.getString(4));
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
					state = conn.createStatement();
					boolean hasResultSet = state.execute(query);
					if(hasResultSet) {
						re = state.getResultSet();
						while(re.next()) {
							setID(re.getInt(1));
						}
					}
					query= String.format("SELECT Name,Date,Place,Category,Title,Content FROM `Posts` WHERE ID=\"%s\";", ID);
					boolean hasResultSet2 = state.execute(query);
					if(hasResultSet2) {
						re = state.getResultSet();
						ResultSetMetaData metaData2 = re.getMetaData();
						int columnCount2 = metaData2.getColumnCount();
						while(re.next()) {
								name.setText(re.getString(1));
								date.setText(re.getString(2));
								place.setText(re.getString(3));
								category.setText(re.getString(4));
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
					state = conn.createStatement();
					boolean hasResultSet = state.execute(query);
					if(hasResultSet) {
						re = state.getResultSet();
						while(re.next()) {
							setID(re.getInt(1));
						}
					}
					query= String.format("SELECT Name,Date,Place,Category,Title,Content FROM `Posts` WHERE ID=\"%s\";", ID);
					boolean hasResultSet2 = state.execute(query);
					if(hasResultSet2) {
						re = state.getResultSet();
						ResultSetMetaData metaData2 = re.getMetaData();
						int columnCount2 = metaData2.getColumnCount();
						while(re.next()) {
								name.setText(re.getString(1));
								date.setText(re.getString(2));
								place.setText(re.getString(3));
								category.setText(re.getString(4));
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
					state = conn.createStatement();
					boolean hasResultSet = state.execute(query);
					if(hasResultSet) {
						re = state.getResultSet();
						while(re.next()) {
							setID(re.getInt(1));
						}
					}
					query= String.format("SELECT Name,Date,Place,Category,Title,Content FROM `Posts` WHERE ID=\"%s\";", ID);
					boolean hasResultSet2 = state.execute(query);
					if(hasResultSet2) {
						re = state.getResultSet();
						ResultSetMetaData metaData2 = re.getMetaData();
						int columnCount2 = metaData2.getColumnCount();
						while(re.next()) {
								name.setText(re.getString(1));
								date.setText(re.getString(2));
								place.setText(re.getString(3));
								category.setText(re.getString(4));
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
					for(int j=1;j<5;j++) {
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
					for(int j=1;j<5;j++) {
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
		default:return null;
		}
	}
	
	public void createLayout() {
		JPanel P = new JPanel(new GridLayout(1,2));
		JPanel place = new JPanel();
			place.add(Place);
			place.add(searchP);
		JPanel date = new JPanel();
			date.add(Date);
			date.add(searchD);
		JPanel cate = new JPanel();
			cate.add(Category);
			cate.add(categories);
		JPanel search = new JPanel(new GridLayout(3,1));
			search.add(place);
			search.add(date);
			search.add(cate);
		JPanel search2 = new JPanel();
			search2.add(search);
			search2.add(this.search);
		JPanel result = new JPanel(new GridLayout(4,1));
			result.setPreferredSize(new Dimension(360,250));
			result.add(re1);
			result.add(re2);
			result.add(re3);
			result.add(re4);
		JPanel page = new JPanel();
			page.add(lastP);
			page.add(this.page);
			page.add(nextP);
		JPanel left = new JPanel();
			left.add(search2);
			left.add(result);
			left.add(page);
			left.setBorder(new LineBorder(Color.BLACK));
		JPanel nameNdate = new JPanel();
			nameNdate.add(textN);
			nameNdate.add(name);
			nameNdate.add(textD);
			nameNdate.add(this.date);
		JPanel placeNcate = new JPanel();
			placeNcate.add(textP);
			placeNcate.add(this.place);
			placeNcate.add(textC);
			placeNcate.add(category);
		JPanel content = new JPanel(new GridLayout(1,1));
			content.add(this.content);
		JPanel right = new JPanel();
			right.add(nameNdate);
			right.add(placeNcate);
			right.add(textT);
			right.add(title);
			right.add(content);
			right.add(post);
			right.add(back);
			right.setBorder(new LineBorder(Color.BLACK));
		P.add(left);
		P.add(right);
		this.add(P);
	}
}
