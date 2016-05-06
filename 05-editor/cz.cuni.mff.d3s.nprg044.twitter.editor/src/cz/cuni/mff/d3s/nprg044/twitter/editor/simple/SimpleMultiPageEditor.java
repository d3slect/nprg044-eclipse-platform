package cz.cuni.mff.d3s.nprg044.twitter.editor.simple;

import java.util.StringTokenizer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.MultiPageEditorPart;

/**
 * The superclass "MultiPageEditorPart" provides the basic infrastructure, such
 * as the method "createControlPart" that calls "createPages", and it takes care
 * of the editor input.
 */
public class SimpleMultiPageEditor extends MultiPageEditorPart {

    private TextEditor textEditor;
    private ListViewer listViewer;

    public SimpleMultiPageEditor() {
    }

    // this method creates visual representation of all pages from various SWT
    // widgets and controls.
    @Override
    protected void createPages() {
        createPage0();
        createPage1();
    }

    // this page contains a standard text editor
    private void createPage0() {
        try {
            textEditor = new TextEditor();

            // add new page to the multi-page editor
            int index = addPage(textEditor, getEditorInput());

            // set the page label
            setPageText(index, "Editor");
        } catch (PartInitException e) {
            e.printStackTrace();
        }
    }

    // this page shows a list of lines
    private void createPage1() {
        // there will be just the list viewer in the page so layout does not
        // really matter
        Composite composite = new Composite(getContainer(), SWT.NONE);
        FillLayout layout = new FillLayout();
        composite.setLayout(layout);

        listViewer = new ListViewer(composite);

        int index = addPage(composite);
        setPageText(index, "Preview");
    }

    @Override
    public void doSave(IProgressMonitor monitor) {
        // tell editor to save its content
        textEditor.doSave(monitor);
    }

    @Override
    public void doSaveAs() {
        textEditor.doSaveAs();
    }

    @Override
    public boolean isSaveAsAllowed() {
        return true;
    }

    // notification that some page (tab) has been activated (displayed)
    @Override
    protected void pageChange(int newPageIndex) {
        super.pageChange(newPageIndex);

        if (newPageIndex == 1) {
            // our list has been activated -> fill it with current data

            // get the whole content of the text editor
            String editorText = textEditor.getDocumentProvider().getDocument(getEditorInput()).get();

            StringTokenizer tokenizer = new StringTokenizer(editorText, "\t\n\r\f");

            // remove old content
            listViewer.getList().removeAll();

            int i = 0;
            while (tokenizer.hasMoreTokens()) {
                listViewer.add(i + ": " + tokenizer.nextToken());
                i++;
            }
        }
    }
}
