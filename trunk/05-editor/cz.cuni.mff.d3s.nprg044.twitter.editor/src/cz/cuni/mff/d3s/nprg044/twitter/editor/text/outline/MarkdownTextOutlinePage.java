package cz.cuni.mff.d3s.nprg044.twitter.editor.text.outline;

import java.util.ArrayList;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import cz.cuni.mff.d3s.nprg044.twitter.editor.text.parts.MarkdownTextPartitionScanner;

public class MarkdownTextOutlinePage extends ContentOutlinePage {
	private Object input;
	
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		TreeViewer treeViewer = getTreeViewer();
		treeViewer.setContentProvider(new ITreeContentProvider() {
			
			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {								
			}
			
			@Override
			public void dispose() {
			}
			
			@Override
			public Object[] getElements(Object inputElement) {
				if (inputElement instanceof IDocument) {
					IDocument doc = (IDocument) inputElement;
					ArrayList<String> result = new ArrayList<String>();
					try {
						ITypedRegion[] regions = doc.computePartitioning(0, doc.getLength());
						for(ITypedRegion region : regions) {
							if (region.getType().equals(MarkdownTextPartitionScanner.MARKDOWN_H1)) {
								result.add(doc.get(region.getOffset(), region.getLength()));
							}
						}
					} catch (BadLocationException e) {						
						e.printStackTrace();
					}
					return result.toArray();
				}
				return null;
			}

			@Override
			public Object[] getChildren(Object parentElement) {
				return null;
			}

			@Override
			public Object getParent(Object element) {
				return null;
			}

			@Override
			public boolean hasChildren(Object element) {
				return false;
			}
		});
		
		treeViewer.setInput(this.input);
	}
	
	public void setInput(Object element) {
		this.input = element;		
	}

}
