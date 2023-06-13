import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class HomePageFrame extends JFrame{
	
	private JLabel title,finder,searcher;
	private JButton post,edit,search;
	Connection conn;
	Statement stat;
	
	public HomePageFrame(Connection conn) throws SQLException{
		this.conn = conn;
		setVisible(true);
		setSize(800,470);
		setTitle("失物招領系統");
		
		createLabel();
		createButton();
		createLayout();
	}
	
	public void createLabel() {
		title = new JLabel("失物招領系統",JLabel.CENTER);
		title.setFont(new Font(title.getFont().getName(), title.getFont().getStyle(), 25));
		title.setOpaque(true);
		title.setBackground(Color.LIGHT_GRAY);
		finder = new JLabel("拾獲者",JLabel.CENTER);
		finder.setFont(new Font(finder.getFont().getName(), finder.getFont().getStyle(), 20));
		finder.setBorder(new LineBorder(Color.BLACK));
		searcher = new JLabel("尋找者",JLabel.CENTER);
		searcher.setFont(new Font(searcher.getFont().getName(), searcher.getFont().getStyle(), 20));
		searcher.setBorder(new LineBorder(Color.BLACK));
	}
	
	public void createButton() {
		post = new JButton("發布貼文");
		post.setFont(new Font(searcher.getFont().getName(), searcher.getFont().getStyle(), 20));
			post.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event){
					try {
						PostFrame postF = new PostFrame(conn);
						dispose();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
		
		edit = new JButton("編輯貼文");
		edit.setFont(new Font(searcher.getFont().getName(), searcher.getFont().getStyle(), 20));
			edit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event){
					EditFrame editF = new EditFrame(conn);
					dispose();
				}
			});
		search = new JButton("搜尋貼文");
		search.setFont(new Font(searcher.getFont().getName(), searcher.getFont().getStyle(), 20));
			search.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event){
					SearchFrame searchF = new SearchFrame(conn);
					dispose();
				}
			});
	}
	
	public void createLayout() {
		JPanel buttonF = new JPanel(new GridLayout(1,2));
			buttonF.add(post);
			buttonF.add(edit);
		JPanel buttons = new JPanel(new GridLayout(1,2));
			buttons.add(buttonF);
			buttons.add(search);
		JPanel user = new JPanel(new GridLayout(1,2));
			user.add(finder);
			user.add(searcher);
		JPanel frame = new JPanel(new GridLayout(3,1));
			frame.add(title);
			frame.add(user);
			frame.add(buttons);
		add(frame);
	}

}
