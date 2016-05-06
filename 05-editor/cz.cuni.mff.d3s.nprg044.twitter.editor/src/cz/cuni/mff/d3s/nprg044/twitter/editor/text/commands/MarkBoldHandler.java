package cz.cuni.mff.d3s.nprg044.twitter.editor.text.commands;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.internal.e4.compatibility.CompatibilityEditor;

@SuppressWarnings("restriction")
public class MarkBoldHandler {

    @Execute
    public void exec(ESelectionService selectionService, @Named(IServiceConstants.ACTIVE_PART) MPart part) {
        Object partObject = part.getObject();

        if (partObject instanceof CompatibilityEditor) {
            CompatibilityEditor compatibilityEditor = (CompatibilityEditor) partObject;
            if (compatibilityEditor.getEditor() instanceof TextEditor) {
                TextEditor editor = (TextEditor) compatibilityEditor.getEditor();

                // get current selection in the text editor
                Object selection = selectionService.getSelection();

                if (selection instanceof TextSelection) {
                    IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
                    TextSelection textSelection = (TextSelection) selection;
                    try {
                        // put selection into **...** (mark symbol for bold
                        // text)
                        document.replace(textSelection.getOffset(), textSelection.getLength(),
                                "**" + textSelection.getText() + "**");
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
