package fia.ues.edu.siam.entity;

public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String user_from;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

	public String getUser_from() {
		return user_from;
	}

	public void setUser_from(String user_from) {
		this.user_from = user_from;
	}
    
    
    
    
    
}
