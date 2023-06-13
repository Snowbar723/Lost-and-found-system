import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class NotifyFrame extends JFrame{
	
	private JLabel text;
	private JButton confirm;
	Connection conn;
	
	public NotifyFrame(String text) {
		setSize(250,150);
		setLocation(260, 150);
		setTitle("通知");
		setVisible(true);
		
		createLabel(text);
		createButton(text);
		createLayout();
	}
	
	public void setConn(Connection c) {
		conn=c;
	}
	
	public void createLabel(String t) {
		this.text = new JLabel(String.format("%s",t),JLabel.CENTER);
		text.setFont(new Font(text.getFont().getName(), text.getFont().getStyle(), 20));
	}
	
	public void createButton(String t) {
		confirm = new JButton("確認");
		confirm.setFont(new Font(text.getFont().getName(), text.getFont().getStyle(), 20));
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(t.equals("發布貼文成功!")) {
					try {
						HomePageFrame hf = new HomePageFrame(conn);
					}catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
				dispose();
			}
		});
	}
	
	public void postPage(PostFrame p) {
		
	}
	
	public void createLayout() {
		JPanel p = new JPanel(new GridLayout(2,1));
		p.add(text);
		p.add(confirm);
		add(p);
	}
}
