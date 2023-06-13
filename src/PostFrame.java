import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import javax.swing.*;

public class PostFrame extends JFrame{
	
	private JTextField text1,text2,text3,text4;
	private JLabel column1,column2,column3,column4,column5,column6,title;
	private JTextArea textArea;
	private JComboBox categories;
	private JButton submitButton,cancelButton;
	Connection conn;
	Statement stat;
	HomePageFrame homef;
	JFrame frame;
	
	public PostFrame(Connection conn) throws SQLException{
		this.conn=conn;
		setVisible(true);
		setSize(800,470);
		setTitle("發布貼文");
		
		
		createLabel();
		createTextField();
		createCombo();
		createTextArea();
		createButton();
		createLayout();
	}
	
	private void createLabel() {
		column1 = new JLabel("拾獲者姓名:");
		column2 = new JLabel("拾獲日期(yyyy-mm-dd):");
		column3 = new JLabel("拾獲地點:");
		column4 = new JLabel("失物類別:");
		column5 = new JLabel("文章標題:");
		column6 = new JLabel("文章內文:");
	}
	
	private void createTextField() {
		text1 = new JTextField(21);
		text2 = new JTextField(21);
		text3 = new JTextField(21);
		text4 = new JTextField(79);
	}
	
	private void createTextArea() {
		textArea = new JTextArea(15,79);
	}
	
	private void createCombo() {
		categories = new JComboBox();
		
		categories.addItem("請選擇類別");
		categories.addItem("錢包");
		categories.addItem("學生證");
		categories.addItem("現金");
		categories.addItem("手機");
		categories.addItem("筆電");
		categories.addItem("文具");
		categories.addItem("雨傘");
		categories.addItem("背包");
		categories.addItem("鑰匙");
		categories.addItem("其他");
	}
	
	private void createButton() {
		cancelButton = new JButton("取消");
		cancelButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				try {
					HomePageFrame homeF = new HomePageFrame(conn);
				}catch(SQLException e) {
					e.printStackTrace();
				}
				
				dispose();
			}
		});
		
		submitButton = new JButton("發布貼文");
		submitButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent event){
				String name=text1.getText();
				//String Name = new String(name, StandardCharsets.UTF_8);
				String date=text2.getText();
				String place=text3.getText();
				String category=categories.getSelectedItem().toString();
				String title=text4.getText();
				String content=textArea.getText();
				frame = new JFrame();
				frame.setSize(800,470);
				
				if(name.equals("")) {
					//NotifyFrame noF = new NotifyFrame("隢撓�憪��!");
					JOptionPane.showMessageDialog(frame, "             請輸入姓名!","通知",JOptionPane.WARNING_MESSAGE);
				}else if(date.equals("")) {
					//NotifyFrame noF = new NotifyFrame("隢撓�����!");
					JOptionPane.showMessageDialog(frame, "             請輸入日期!","通知",JOptionPane.WARNING_MESSAGE);
				}else if(place.equals("")) {
					//NotifyFrame noF = new NotifyFrame("隢撓��暺�!");
					JOptionPane.showMessageDialog(frame, "             請輸入地點!","通知",JOptionPane.WARNING_MESSAGE);
				}else if(category.equals("隢���")) {
					//NotifyFrame noF = new NotifyFrame("隢���!");
					JOptionPane.showMessageDialog(frame, "             請選擇類別!","通知",JOptionPane.WARNING_MESSAGE);
				}else if(title.equals("")) {
					//NotifyFrame noF = new NotifyFrame("隢撓�������!");
					JOptionPane.showMessageDialog(frame, "          請輸入文章標題!","通知",JOptionPane.WARNING_MESSAGE);
				}else if(content.equals("")) {
					//NotifyFrame noF = new NotifyFrame("隢撓�����!");
					JOptionPane.showMessageDialog(frame, "             請輸入內文!","通知",JOptionPane.WARNING_MESSAGE);
				}else{
					String query=String.format("INSERT INTO `Posts`(Name,Date,Place,Category,Title,Content) VALUES (\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\")",name,date,place,category,title,content);
					try {
						stat = conn.createStatement();
						stat.execute(query);
						//NotifyFrame noF = new NotifyFrame("�撣票�����!");
						//noF.setConn(conn);
						JOptionPane.showMessageDialog(frame, "          發布貼文成功!","通知",JOptionPane.INFORMATION_MESSAGE);
						dispose();
						HomePageFrame hf = new HomePageFrame(conn);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
				
			}
		});
	}
	
	private void createLayout() {
		JPanel small_textArea=new JPanel(new GridLayout(2,4));
		small_textArea.add(column1);
		small_textArea.add(text1);
		small_textArea.add(column2);
		small_textArea.add(text2);
		small_textArea.add(column3);
		small_textArea.add(text3);
		small_textArea.add(column4);
		small_textArea.add(categories);
		
		JPanel title_textArea=new JPanel();
		title_textArea.add(column5);
		title_textArea.add(text4);
		
		JPanel contentArea=new JPanel();
		contentArea.add(column6);
		contentArea.add(textArea);
		
		JPanel buttons = new JPanel(new GridLayout(1,2));
		buttons.add(cancelButton);
		buttons.add(submitButton);
		
		JPanel frame=new JPanel();
		frame.add(small_textArea);
		frame.add(title_textArea);
		frame.add(contentArea);
		frame.add(buttons);
		add(frame);
	}
}
