package cz.cuni.mff.d3s.nprg044.twitter.editor.text.outline;

import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

/**
 * The content outline page. It shows a tree of headings.
 */
public class MarkdownTextOutlinePage extends ContentOutlinePage {
    private IDocument input;

    private class SimpleLabelProvider extends LabelProvider {
        @Override
        public String getText(Object element) {
            if (element instanceof ITypedRegion) {
                ITypedRegion region = (ITypedRegion) element;

                try {
                    return input.get(region.getOffset(), region.getLength());
                } catch (BadLocationException e) {
                }
            }
            return super.getText(element);
        }
    }

    @Override
    public void createControl(Composite parent) {
        super.createControl(parent);

        // get tree viewer of this page
        TreeViewer treeViewer = getTreeViewer();

        treeViewer.setContentProvider(new MarkdownSyntaxContentProvider());
        treeViewer.setLabelProvider(new SimpleLabelProvider());

        treeViewer.setInput(this.input);

        // we must look for selections in the tree viewer so that we can show
        // properties
        // of the selected node
        getSite().setSelectionProvider(treeViewer);
    }

    public void setInput(IDocument element) {
        this.input = element;
        if (getTreeViewer() != null) {
            getTreeViewer().setInput(element);
        }
    }

    public void select(ITextSelection textSelection) {
        Viewer viewer = getTreeViewer();

        if (viewer != null) {
            ISelection s = viewer.getSelection();
            if (s instanceof IStructuredSelection) {
                IStructuredSelection ss = (IStructuredSelection) s;
                List<?> elements = ss.toList();

                try {
                    ITypedRegion region = input.getPartition(textSelection.getOffset());

                    if (!elements.contains(region)) {
                        s = (region == null ? StructuredSelection.EMPTY : new StructuredSelection(region));
                        viewer.setSelection(s, true);
                    }
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
