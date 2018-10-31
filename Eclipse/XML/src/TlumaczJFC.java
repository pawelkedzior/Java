import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.FileChooserUI;
import javax.swing.plaf.basic.BasicFileChooserUI;
import javax.swing.plaf.multi.MultiFileChooserUI;

public class TlumaczJFC extends JFileChooser{

	private static final long serialVersionUID = 1L;
	private static JFileChooser wybieracz;
	
	public TlumaczJFC() {
        super();
        FileChooserUI przetlumaczony;
        if(this.getUIClassID().equals(BasicFileChooserUI.class))
        	przetlumaczony=new PodstWybieraczIU(this);
        else
        	przetlumaczony=new PodstWybieraczIU(this);//MultiWybieraczIU();
        ui=przetlumaczony;
    }

    public TlumaczJFC(String currentDirectoryPath) {
        super(currentDirectoryPath);
        FileChooserUI przetlumaczony;
        if(this.getUIClassID().equals(BasicFileChooserUI.class))
        	przetlumaczony=new PodstWybieraczIU(this);
        else
        	przetlumaczony=new PodstWybieraczIU(this);//MultiWybieraczIU();
        ui=przetlumaczony;
    }

    public TlumaczJFC(File currentDirectory) {
        super(currentDirectory);
        FileChooserUI przetlumaczony;
        if(this.getUIClassID().equals(BasicFileChooserUI.class))
        	przetlumaczony=new PodstWybieraczIU(this);
        else
        	przetlumaczony=new PodstWybieraczIU(this);//MultiWybieraczIU();
        ui=przetlumaczony;
    }

    public TlumaczJFC(FileSystemView fsv) {
        super(fsv);
        FileChooserUI przetlumaczony;
        if(this.getUIClassID().equals(BasicFileChooserUI.class))
        	przetlumaczony=new PodstWybieraczIU(this);
        else
        	przetlumaczony=new PodstWybieraczIU(this);//MultiWybieraczIU();
        ui=przetlumaczony;
    }

    public TlumaczJFC(File currentDirectory, FileSystemView fsv) {
        super(currentDirectory, fsv);
        FileChooserUI przetlumaczony;
        if(this.getUIClassID().equals(BasicFileChooserUI.class))
        	przetlumaczony=new PodstWybieraczIU(this);
        else
        	przetlumaczony=new PodstWybieraczIU(this);//MultiWybieraczIU();
        ui=przetlumaczony;
    }

    public TlumaczJFC(String currentDirectoryPath, FileSystemView fsv) {
        super(currentDirectoryPath, fsv);
        FileChooserUI przetlumaczony;
        if(this.getUIClassID().equals(BasicFileChooserUI.class))
        	przetlumaczony=new PodstWybieraczIU(this);
        else
        	przetlumaczony=new PodstWybieraczIU(this);//MultiWybieraczIU();
        ui=przetlumaczony;
    }
	
	public class MultiWybieraczIU extends MultiFileChooserUI{
		
	}
	
	public class PodstWybieraczIU extends BasicFileChooserUI{

		private String newFolderErrorText;
		private String newFolderErrorSeparator;
		private String newFolderParentDoesntExistTitleText;
		private String newFolderParentDoesntExistText;
		private String fileDescriptionText;
		private String directoryDescriptionText;
		private String openDialogTitleText;
		private String saveDialogTitleText;
		private boolean readOnly;		
		private JPanel accessoryPanel = null;
		
		public PodstWybieraczIU(JFileChooser wybieracz) {
			super(wybieracz);
		}
		
		@Override
		public void installUI(JComponent c) {
	        accessoryPanel = new JPanel(new BorderLayout());
	        wybieracz = (JFileChooser) c;

	        createModel();

	        clearIconCache();

	        installDefault(wybieracz);
	        installComponents(wybieracz);
	        installListeners(wybieracz);
	        wybieracz.applyComponentOrientation(wybieracz.getComponentOrientation());
	    }
		
		protected void installDefault(JFileChooser fc) {
			installDefaults(fc);
	        readOnly           = UIManager.getBoolean("FileChooser.readOnly");
	        tlumaczStringi(fc);
	    }
		
		protected void tlumaczStringi(JFileChooser fc) {
			Locale l=new Locale(null);
			newFolderErrorText = "Błąd tworzenia katalogu";
	        newFolderErrorSeparator = UIManager.getString("FileChooser.newFolderErrorSeparator",l);

	        newFolderParentDoesntExistTitleText = "Folder nie istnieje";
	        newFolderParentDoesntExistText = "Folder nadrzędny nie istnieje";

	        fileDescriptionText = "Plik";
	        directoryDescriptionText = "Folder";

	        saveButtonText   = "Zapisz";
	        openButtonText   = "Otwórz";
	        saveDialogTitleText = "Zapisz plik";
	        openDialogTitleText = "Otwórz plik";
	        cancelButtonText = "Anuluj";
	        updateButtonText = "Odśwież";
	        helpButtonText   = "Pomoc";
	        directoryOpenButtonText = "Otwórz katalog";

	        saveButtonMnemonic   = KeyEvent.VK_Z;
	        openButtonMnemonic   = KeyEvent.VK_O;
	        cancelButtonMnemonic = KeyEvent.VK_A;
	        updateButtonMnemonic = KeyEvent.VK_D;
	        helpButtonMnemonic   = KeyEvent.VK_P;
	        directoryOpenButtonMnemonic = KeyEvent.VK_T;

	        saveButtonToolTipText   = "Zapisuje plik o podanej nazwie";
	        openButtonToolTipText   = "Otwiera wybrany plik";
	        cancelButtonToolTipText = "Zamyka to okno";
	        updateButtonToolTipText = "Odświeża widok";
	        helpButtonToolTipText   = "Otwiera okno pomocy";
	        directoryOpenButtonToolTipText = "Otwiera wybrany katalog";
		}
		
		@Override
	    public String getDialogTitle(JFileChooser fc) {
	        String dialogTitle = fc.getDialogTitle();
	        if (dialogTitle != null) {
	            return dialogTitle;
	        } else if (fc.getDialogType() == JFileChooser.OPEN_DIALOG) {
	            return openDialogTitleText;
	        } else if (fc.getDialogType() == JFileChooser.SAVE_DIALOG) {
	            return saveDialogTitleText;
	        } else {
	            return getApproveButtonText(fc);
	        }
	    }
		
		protected class AkcjaNowyFolder extends NewFolderAction{
			private static final long serialVersionUID = 1L;
			protected AkcjaNowyFolder() {
	            super();
	        }
			@Override
			public void actionPerformed(ActionEvent e) {
	            if (readOnly) {
	                return;
	            }
	            JFileChooser fc = getFileChooser();
	            File currentDirectory = fc.getCurrentDirectory();

	            if (!currentDirectory.exists()) {
	                JOptionPane.showMessageDialog(
	                    fc,
	                    newFolderParentDoesntExistText,
	                    newFolderParentDoesntExistTitleText, JOptionPane.WARNING_MESSAGE);
	                return;
	            }

	            File newFolder;
	            try {
	                newFolder = fc.getFileSystemView().createNewFolder(currentDirectory);
	                if (fc.isMultiSelectionEnabled()) {
	                    fc.setSelectedFiles(new File[] { newFolder });
	                } else {
	                    fc.setSelectedFile(newFolder);
	                }
	            } catch (IOException exc) {
	                JOptionPane.showMessageDialog(
	                    fc,
	                    newFolderErrorText + newFolderErrorSeparator + exc,
	                    newFolderErrorText, JOptionPane.ERROR_MESSAGE);
	                return;
	            }

	            fc.rescanCurrentDirectory();
	        }
		}
		
		protected class PodsWidokPlikow extends BasicFileView{
			@Override
			public String getTypeDescription(File f) {
				String type = getFileChooser().getFileSystemView().getSystemTypeDescription(f);
				if (type == null) {
					if (f.isDirectory()) {
						type = directoryDescriptionText;
					} else {
						type = fileDescriptionText;
					}
				}
				return type;
			}
		}
	}
}
