package www.knowledgeshare.com.knowledgeshare.bean;

public class SearchHistoryEntity {

	private String content;

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public SearchHistoryEntity(String content) {
		this.content = content;
	}

	public SearchHistoryEntity() {
		super();
	}
	
}
