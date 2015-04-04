package cz.cuni.mff.d3s.nprg044.twitter.editor.text.colors;

import org.eclipse.swt.graphics.RGB;

/**
 * Container for constants that represent important text colors.
 */
public interface IMarkdownTextColorConstants {
	
	public static final RGB DEFAULT_TEXT = new RGB(0, 0, 0);
	
	public static final RGB H1 = new RGB(128, 0, 0);
	public static final RGB H1_BG = new RGB(250, 250, 200);	
	
	public static final RGB H2 = new RGB(0, 200, 0);
	public static final RGB H2_BG = H1_BG;
	
	public static final RGB BOLD_TEXT = DEFAULT_TEXT;	
	public static final RGB ITALICS_TEXT = DEFAULT_TEXT;

}
