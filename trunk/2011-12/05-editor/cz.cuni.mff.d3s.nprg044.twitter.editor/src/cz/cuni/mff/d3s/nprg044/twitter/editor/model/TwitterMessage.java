package cz.cuni.mff.d3s.nprg044.twitter.editor.model;


public class TwitterMessage {
	
	private String text;
		
	private String receiverUsername;
	
	private String imagePath;
	
	public TwitterMessage() {		
	}
	
	
	public TwitterMessage(String[] lines) {
		parse(lines);		
	}
	
	protected void parse(String[] lines) {
		for (String line : lines) {
			if (line.startsWith("Message:")) {
				setText(line.replace("Message:", ""));
			} else  if (line.startsWith("Image:")) {
				setImagePath(line.replace("Image:", ""));
			}
		}
	}
	
	
	public boolean isDirect() {
		return getReceiverUsername() != null;
	}
	
	public boolean hasImage() {
		return getImagePath() != null;
	}
	
	public boolean hasText() {
		return getText() != null;
	}	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getReceiverUsername() {
		return receiverUsername;
	}

	public void setReceiverUsername(String receiverUsername) {
		this.receiverUsername = receiverUsername;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Message:");
		sb.append(getText());
		if (hasImage()) {
			sb.append("\nImage:");
			sb.append(getImagePath());			
		}
		
		return sb.toString();
	}

}
