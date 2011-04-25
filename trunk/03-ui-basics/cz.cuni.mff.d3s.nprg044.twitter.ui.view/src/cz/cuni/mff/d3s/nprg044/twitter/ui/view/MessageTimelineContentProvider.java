/**
 * 
 */
package cz.cuni.mff.d3s.nprg044.twitter.ui.view;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

/**
 * @author michal
 *
 */
public class MessageTimelineContentProvider implements
		IStructuredContentProvider {
	
	private String username;
	
	private Viewer viewer;
	
	private KeyListener keyListener = new KeyAdapter() {
		public void keyReleased(KeyEvent e) {
			if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
				if (e.widget instanceof Text) {
					String newUsername = ((Text) e.widget).getText();
					if (!newUsername.equals(username)) {
						username = newUsername;
						e.display.asyncExec(new Runnable() {
							
							@Override
							public void run() {
								viewer.refresh();								
							}
						});
					}
				}
			}
		};
	};
	
	

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {	
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = viewer;
		
		if (oldInput instanceof Control) {
			((Control) oldInput).removeKeyListener(keyListener);
		}
		if (newInput instanceof Control) {
			((Control) newInput).addKeyListener(keyListener);
		}
		
		viewer.refresh();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {		
		if (username != null) {
			return new String[] { username };			
		} else {
			return new String[] { "AHOJ" };
		}
	}

}
