

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;


public class show_student_frame {

	private static JFrame frame;
	private static JTable table;
	private static JPanel contentPane;
	static String tableName ; 

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					show_student_frame window = new show_student_frame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public show_student_frame() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame("All Students");
		frame.setBounds(108, 220, 1160, 500);
		JFrame f = new JFrame("show");
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);
		
		String data[][]=null;
		String column[]=null;
		try
		{
			Connection con=Login.db();
			PreparedStatement ps=con.prepareStatement("select student_register_number,student_name,student_contact,student_mail,class_name,department_name from student s,class c,student_department sd where sd.department_id=c.department_id and c.class_id=s.class_id",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			ResultSet rs=ps.executeQuery();
			ResultSetMetaData rsmd=rs.getMetaData();
			int cols=rsmd.getColumnCount();
			column=new String[cols];
			for(int i=1;i<=cols;i++)
			{
				column[i-1]=rsmd.getColumnName(i);
			}
			
			rs.last();
			
			int rows=rs.getRow();
			rs.beforeFirst();

			data=new String[rows][cols];
			int count=0;
			while(rs.next())
			{
				for(int i=1;i<=cols;i++)
				{
					data[count][i-1]=rs.getString(i);
				}
				count++;
			}
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
			
		}
		
		table = new JTable(data,column);
		table.editingCanceled(null);
		JScrollPane sp=new JScrollPane(table);
		contentPane.add(sp, BorderLayout.CENTER);
	}
}













