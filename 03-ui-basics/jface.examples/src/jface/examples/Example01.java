package jface.examples;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

/**
 * A simple example showing usage of TableViewer.
 * 
 * Based on the JFace snippet.
 * 
 * @author Michal Malohlava
 * 
 */
public class Example01 {
	
	private static final User[] EMPTY_USER_LIST = new User[] {};
	
	private class TwitterUsersContentProvider implements IStructuredContentProvider {

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
		 */
		public Object[] getElements(Object inputElement) {
			return (User[]) inputElement;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		public void dispose() {
			
		}

		/* (non-Javadoc)
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {			
		}
		
	}
	
	public class TwitterUsersLabelProvider extends LabelProvider implements ITableLabelProvider {
		
		private HashMap<User, Image> imageCache = new HashMap<User, Image>();

		public Image getColumnImage(Object element, int columnIndex) {			
			User user = (User) element;
			switch(columnIndex) {
			case 0:			
				if (!imageCache.containsKey(user)) {
					
					URL imageUrl = user.getProfileImageURL();
					Image image = createImageFromURL(imageUrl);
					imageCache.put(user, image);
				}
				
				return imageCache.get(user);
			default:
				return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			User user = (User) element;
			
			switch (columnIndex) {
			case 0:
				return '@' + user.getScreenName();
			case 1:				
				return user.getStatus().getText();		

			default:
				return null;
			}			
		}
		
		private Image createImageFromURL(URL url) {
			ImageDescriptor imgDescriptor =  ImageDescriptor.createFromURL(url);
			ImageData imgData = imgDescriptor.getImageData().scaledTo(64, 64);
			Display display = Display.getCurrent();			
			Image image = new Image(display, imgData);
			
			return image;			
		}
		
		@Override
		public void dispose() {
			// create images has to be disposed explicitly
			for (Image image : imageCache.values()) {
				image.dispose();
			}
		
			super.dispose();
		}
	}	
	
	private ProgressBar progressBar = null;
	
	public Example01(final Shell shell) {
		// SWT control
		final Text textField = new Text(shell, SWT.SINGLE | SWT.SEARCH | SWT.ICON_SEARCH);
		textField.setText("sofaproject");
		textField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		progressBar = new ProgressBar(shell, SWT.HORIZONTAL);
		progressBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// JFace control
		final TableViewer v = new TableViewer(shell,SWT.BORDER|SWT.FULL_SELECTION);		
		v.setLabelProvider(new TwitterUsersLabelProvider());
		v.setContentProvider(new TwitterUsersContentProvider());
						
		TableColumn column = new TableColumn(v.getTable(),SWT.NONE);
		column.setWidth(200);
		column.setText("Follower name");
		
		column = new TableColumn(v.getTable(),SWT.NONE);
		column.setWidth(500);
		column.setText("Status");
		
		User[] model = EMPTY_USER_LIST; //createModel("sofaproject");
		v.setInput(model);
		v.getTable().setLinesVisible(true);
		v.getTable().setHeaderVisible(true);
		// layout underlying SWT control
		v.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// handle enter in text field
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
					String username = textField.getText();
					
					v.setInput(createModel(username));
				}				
			}
		});		
		
	}
	
	private User[] createModel(String username) {
		Twitter twitter = new TwitterFactory().getInstance();
		
		
		try {
	        ArrayList<User> result = new ArrayList<User>();
	        IDs friendIDs = twitter.getFriendsIDs(username, -1);
	        progressBar.setMinimum(0);
	        progressBar.setMaximum(friendIDs.getIDs().length);
	        
	        int counter = 0;
	        for (long id : friendIDs.getIDs()) {
	        	result.add(twitter.showUser(id));
	        	progressBar.setSelection(++counter);
	        }
//	        result.add(twitter.showUser("sofaproject"));
//	        result.add(twitter.showUser("mmalohlava"));
	        
	        return result.toArray(new User[result.size()]);
	        
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		return EMPTY_USER_LIST;
	}

	public static void main(String[] args) {
		Display display = new Display ();
		
		Shell shell = new Shell(display);
		// set window title
		shell.setText("Get followers");
		shell.setBounds(100,100, 820, 320);
		shell.setLayout(new GridLayout());
		
		new Example01(shell);
		shell.open();		
		
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) 
				display.sleep ();
		}			
		
		display.dispose ();
		
	}

}

