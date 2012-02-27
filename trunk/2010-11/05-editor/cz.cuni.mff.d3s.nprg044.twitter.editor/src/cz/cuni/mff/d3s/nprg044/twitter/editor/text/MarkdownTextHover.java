package cz.cuni.mff.d3s.nprg044.twitter.editor.text;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextHoverExtension2;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.swt.graphics.Point;

public class MarkdownTextHover implements ITextHover, ITextHoverExtension2 {

	@Override
	public Object getHoverInfo2(ITextViewer textViewer, IRegion hoverRegion) {
		try {
			return textViewer.getDocument().getPartition(hoverRegion.getOffset()).toString();
		} catch (BadLocationException e) {
			return "No info because of " + e.getMessage();			
		}
	}

	@Override
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {		
		return getHoverInfo2(textViewer, hoverRegion).toString();
	}

	@Override
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		Point selection= textViewer.getSelectedRange();
        if (selection.x <= offset && offset < selection.x + selection.y)
                return new Region(selection.x, selection.y);
        return new Region(offset, 0);
	}

}
