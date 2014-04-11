import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class Renamer
{
	private ArrayList<File> filesToBeRenamed = new ArrayList<File>();
	private Integer startingValue;
	
	public Renamer(ArrayList<File> orderedFiles,int startVal) {
		filesToBeRenamed = orderedFiles;
		startingValue = startVal;
	}
	
	public void rename() {
		for (File f : filesToBeRenamed)
		{
			f.renameTo(new File(f.getParent() + File.separator + (Integer)(filesToBeRenamed.indexOf(f)+startingValue) + " - " + f.getName()));
		}
		JOptionPane.showMessageDialog(null, "All Done Sir !!");
	}
}
