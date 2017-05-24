import java.io.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;

public class MyBlobTest{
	//Connection
	private static Connection conn;
	private static PreparedStatement pstmtInsertImg;
	private static PreparedStatement pstmtQueryImg;
	private static PreparedStatement pstmtQueryAllImg;
	static{
		try{
			Properties props = new Properties();
			props.load(new FileInputStream("mysql.ini"));
			String driver = props.getProperty("driver");
			String url = props.getProperty("url");
			String user = props.getProperty("user");
			String pass = props.getProperty("pass");
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
			pstmtInsertImg = conn.prepareStatement("insert into img_table" +
				" values (null,?,?)");
			pstmtQueryImg = conn.prepareStatement("select img_data from img_table where "
				+ "img_id = ?");
			pstmtQueryAllImg = conn.prepareStatement("select img_id, img_name "
				+ "from img_table");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	//GUI
	private JFrame jf;
		private JPanel jpLeft;
			private JLabel jlImg;
			private JPanel jp;
				private JTextField jtfFilePath;
				private JButton jbtBroswer;
				private JButton jbtUpload;
		private JList<ImageHolder2> jliImgList;
			private DefaultListModel<ImageHolder2> imgModel;

	private JFileChooser chooser;
	private ExtensionFileFilter2 filter;

	public void initGUI(){
		jf = new JFrame("img selection helper");
			jpLeft = new JPanel(); jf.add(jpLeft); jpLeft.setLayout(new BorderLayout());
				jlImg = new JLabel(); jpLeft.add(new JScrollPane(jlImg));
				jp = new JPanel(); jpLeft.add(jp, BorderLayout.SOUTH);
					jtfFilePath = new JTextField(10); jp.add(jtfFilePath);
						jtfFilePath.setEditable(false);
					jbtBroswer = new JButton("..."); jp.add(jbtBroswer);
					jbtUpload = new JButton("upload"); jp.add(jbtUpload);
			imgModel = new DefaultListModel<>(); jliImgList = new JList<>(imgModel); jf.add(new JScrollPane(jliImgList), BorderLayout.EAST);
				jliImgList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
				jliImgList.setFixedCellWidth(160);
		jf.setSize(620, 400);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}

	public void updateListModel()
		throws Exception
	{
		try(ResultSet rs = pstmtQueryAllImg.executeQuery())
		{
			imgModel.clear();
			while(rs.next()){
				imgModel.addElement(new ImageHolder2(rs.getInt(1), rs.getString(2)));
			}
		}
	}

	public void upload(String fileName){
		String imgName = fileName.substring(fileName.lastIndexOf("\\")+1, fileName.lastIndexOf("."));

		File f = new File(fileName);
		try(InputStream is = new FileInputStream(f))
		{
			pstmtInsertImg.setString(1, imgName);
			pstmtInsertImg.setBinaryStream(2, is, (int)f.length());
			int affect = pstmtInsertImg.executeUpdate();
			if(affect == 1){
				updateListModel();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	public void showImage(int id)
		throws Exception
	{
		pstmtQueryImg.setInt(1, id);
		try(ResultSet rs = pstmtQueryImg.executeQuery()){
			if(rs.next()){
				Blob img = rs.getBlob(1);
				ImageIcon icon = new ImageIcon(img.getBytes(1L, (int)img.length()));
				jlImg.setIcon(icon);
			}
		}
	}


	public void init()
		throws Exception
	{
		chooser = new JFileChooser(".");
		filter = new ExtensionFileFilter2();
		filter.addExts(new String[]{"jpg", "jpeg", "gif", "png"});
		filter.setDesc("Image file(*.jpg, *.jpeg, *.gif, *.png)");
		chooser.addChoosableFileFilter(filter);
		chooser.setAcceptAllFileFilterUsed(false);

		initGUI();
		updateListModel();

		jbtBroswer.addActionListener(e -> {
			int res = chooser.showDialog(jf, "OK");
			if(res == JFileChooser.APPROVE_OPTION){
				jtfFilePath.setText(chooser.getSelectedFile().getPath());
			}
		});

		jbtUpload.addActionListener(e -> {
			if(jtfFilePath.getText().trim().length() > 0){
				upload(jtfFilePath.getText());
				jtfFilePath.setText("");
			}
		});
		jliImgList.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() >= 2){
					ImageHolder2 selected = (ImageHolder2) jliImgList.getSelectedValue();
					try{
						showImage(selected.getId());
					}
					catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}
		});
	}

	public static void main(String[] args)
		throws Exception
	{
		new MyBlobTest().init();
	}
}

class ExtensionFileFilter2 extends FileFilter{
	private String desc = "";
	private ArrayList<String> exts = new ArrayList<>();

	@Override
	public boolean accept(File f){
		if(f.isDirectory())
			return true;

		String name = f.getName().toLowerCase();
		for(String ext : exts){
			if(name.endsWith(ext))
				return true;
		}
		return false;
	}

	@Override
	public String getDescription(){
		return desc;
	}

	public void addExts(String ext){
		if(!ext.startsWith(".")){
			ext = "." + ext;
			exts.add(ext.toLowerCase());
		}
	}

	public void addExts(String[] exts){
		for(String ext : exts)
			addExts(ext);
	}

	public String getDesc(){
		return desc;
	}

	public void setDesc(String desc){
		this.desc = desc;
	}

}

class ImageHolder2{
	private int id;
	private String name;
	public ImageHolder2(){}
	public ImageHolder2(int id, String name){
		this.id = id;
		this.name = name;
	} 

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	@Override
	public String toString(){
		return name;
	}
}