package cz.cuni.mff.d3s.nprg044.twitter.editor.text.parts;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.PatternRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

/**
 * Our scanner that recognizes partitions (text fragments with the marks).
 * It uses predicate rules (patterns).
 */
public class MarkdownTextPartitionScanner extends RuleBasedPartitionScanner {
	public static final String MARKDOWN_H1 = "__markdown_h1";
	public static final String MARKDOWN_H2 = "__markdown_h2";
	public static final String MARKDOWN_BOLD = "__markdown_bold";
	public static final String MARKDOWN_ITALICS = "__markdown_italics";
	
	public static final String[] LEGAL_CONTENT_TYPES = {MARKDOWN_H1, MARKDOWN_H2, MARKDOWN_BOLD, MARKDOWN_ITALICS};
	
	public static final int NUMBER_OF_RULES = 4;
	
	public MarkdownTextPartitionScanner() {
		IToken heading1 = new Token(MARKDOWN_H1);
		IToken heading2 = new Token(MARKDOWN_H2);
		IToken bold = new Token(MARKDOWN_BOLD);
		IToken italics = new Token(MARKDOWN_ITALICS);
		
		// the order of rules is very important !!
		IPredicateRule[] rules = new IPredicateRule[NUMBER_OF_RULES];
		rules[0] = new PatternRule("==", "==", heading2, (char) 0, false);
		rules[1] = new PatternRule("=", "=", heading1, (char) 0, false);		
		rules[2] = new MultiLineRule("*", "*", bold);			
		rules[3] = new MultiLineRule("_", "_", italics);
		
		setPredicateRules(rules);
	}
}
