import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


public class MainFrame extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
	private ArrayList<File> files = new ArrayList<>();
	private ArrayList<File> newOrderFiles = new ArrayList<>();
	private DefaultListModel<String> oldListModel = new DefaultListModel<String>();
	private DefaultListModel<String> newListModel = new DefaultListModel<String>();
	private JList<String> oldList;
	private JList<String> newList; 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try { 
				    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
				    e.printStackTrace();
				}
				try
				{
					MainFrame frame = new MainFrame();
					Dimension frameSize = new Dimension(500, 300);
					frame.setBounds(frame.ss.width / 2 - frameSize.width / 2, frame.ss.height / 2
							- frameSize.height / 2, frameSize.width, frameSize.height);
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame()
	{
		setTitle("Order It");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		JButton btnOpenDirectory = new JButton("Open Directory");
		btnOpenDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fch = new JFileChooser();
				fch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fch.setDialogTitle("Select Directory");
				fch.setCurrentDirectory(new java.io.File("."));
				FileNameExtensionFilter dirType = new FileNameExtensionFilter(
						"Folder", "Dir");
				fch.setAcceptAllFileFilterUsed(false);
				fch.addChoosableFileFilter(dirType);
				if(fch.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
					String path = fch.getSelectedFile().getAbsolutePath();
					File dir = new File(path);
					if(dir.isDirectory()) {
						
						clearLists();
						
						for (File f : dir.listFiles())
						{
							files.add(f);
							oldListModel.addElement(f.getName());
						}
					}
					
				}
			}
		});
		btnOpenDirectory.setIcon(new ImageIcon(MainFrame.class.getResource("/com/sun/java/swing/plaf/windows/icons/Directory.gif")));
		toolBar.add(btnOpenDirectory);
		
		JButton btnTurnItBack = new JButton("");
		btnTurnItBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				files.add(newOrderFiles.get(newList.getSelectedIndex()));
				oldListModel.addElement(newList.getSelectedValue());
				
				newOrderFiles.remove(newList.getSelectedIndex());
				newListModel.removeElementAt(newList.getSelectedIndex());
			}
		});
		btnTurnItBack.setIcon(new ImageIcon(MainFrame.class.getResource("/res/arrow_left_ico.png")));
		toolBar.add(btnTurnItBack);
		
		JButton btnPutAfter = new JButton("");
		btnPutAfter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newOrderFiles.add(files.get(oldList.getSelectedIndex()));
				newListModel.addElement(oldList.getSelectedValue());
				
				files.remove(oldList.getSelectedIndex());
				oldListModel.removeElementAt(oldList.getSelectedIndex());
			}
		});
		btnPutAfter.setIcon(new ImageIcon(MainFrame.class.getResource("/res/arrow_right_ico.png")));
		toolBar.add(btnPutAfter);
		
		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!newOrderFiles.isEmpty()){
				Renamer renamer = new Renamer(newOrderFiles);
				renamer.rename();
				clearLists();
				}else{
					JOptionPane.showMessageDialog(null, "Renaming list is empty !!");
				}
			}
		});
		btnApply.setIcon(new ImageIcon(MainFrame.class.getResource("/res/tick.png")));
		toolBar.add(btnApply);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		oldList = new JList<String>(oldListModel);
		scrollPane.setViewportView(oldList);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);
		
		newList = new JList<String>(newListModel);
		scrollPane_1.setViewportView(newList);
	}
	
	private void clearLists() {
		oldListModel.clear();
		files.clear();
		newListModel.clear();
		newOrderFiles.clear();
	}

}