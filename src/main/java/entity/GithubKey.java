package entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;

@Service
public class GithubKey {
	
	@Id
	private ObjectId _id;
	private String name;
	private String key;
	
	public GithubKey () {}
	
	public GithubKey(ObjectId _id, String key, String name) {
		this._id = _id;
		this.key = key;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return _id.toHexString();
	}

	public void setId(ObjectId id) {
		this._id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	@Override
	public String toString() {
		return "the name is: " + this.name + " and key is: " + this.key;
	}
}
