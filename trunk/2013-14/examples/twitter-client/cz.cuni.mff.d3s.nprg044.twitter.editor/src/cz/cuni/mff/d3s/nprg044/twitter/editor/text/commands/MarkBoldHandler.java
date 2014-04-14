package cz.cuni.mff.d3s.nprg044.twitter.editor.text.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.handlers.HandlerUtil;

public class MarkBoldHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart editorPart = HandlerUtil.getActiveEditor(event);
		
		if (editorPart instanceof TextEditor) {
			// get current selection in the text editor
			ISelection selection = HandlerUtil.getCurrentSelection(event);
			
			if (selection instanceof TextSelection) {
				TextEditor editor = (TextEditor) editorPart;
				IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());				
				TextSelection textSelection = (TextSelection) selection;
				try {
					// put selection into *...* (mark symbol for bold text)
					document.replace(textSelection.getOffset(), textSelection.getLength(), "*" + textSelection.getText()+ "*");
				}
				catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}

}
