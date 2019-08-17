package queries;

import org.springframework.data.mongodb.repository.MongoRepository;

import entity.GithubKey;

public interface GitQuery extends MongoRepository<GithubKey, String> {

	public GithubKey findByName(String name);
}
