import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class Renamer
{
	private ArrayList<File> filesToBeRenamed = new ArrayList<File>();
	
	public Renamer(ArrayList<File> orderedFiles) {
		filesToBeRenamed = orderedFiles;
	}
	
	public void rename() {
		for (File f : filesToBeRenamed)
		{
			//System.out.println(f.getParent());
			f.renameTo(new File(f.getParent() + File.separator + filesToBeRenamed.indexOf(f) + " - " + f.getName()));
		}
		JOptionPane.showMessageDialog(null, "All Done Sir !!");
	}
}
