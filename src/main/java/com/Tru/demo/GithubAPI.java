package com.Tru.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.RestController;

import entity.GithubKey;
import queries.GitQuery;
import entity.GitTable;

@ComponentScan("entity")
@EnableMongoRepositories("queries")
public class GithubAPI {

	private String apikey;
	private String page;
	private String per_page;
	
	public GithubAPI() {}
	public GithubAPI(String page, String per_page) {
		this.page = page;
		this.per_page = per_page;
	}
	
	public Integer getInfo(String key) {
		StringBuffer response = new StringBuffer();
		String apilink = "https://api.github.com/user";
		
		try {
			URL url = new URL(apilink);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "GoogleChrome");
			conn.addRequestProperty("Content-Type", "application/json");
			conn.addRequestProperty("Authorization", "token "+key);
			
			BufferedReader in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String inputLine;
			while((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			
			JSONObject user = new JSONObject(response.toString());
			Integer output = user.getInt("public_repos") + 2;
			
			return output;
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<GitTable> getRepo(String key) {
		StringBuffer response = new StringBuffer();
		ArrayList<GitTable> output = new ArrayList<>();
		String pagination = "&page="+this.page+"&per_page="+this.per_page;
		
		String apilink = "https://api.github.com/user/repos?visibility=public";
		if(this.page != null & this.per_page != null) {
			apilink += pagination;
		}
		
		try {
			URL url = new URL(apilink);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "GoogleChrome");
			conn.addRequestProperty("Content-Type", "application/json");
			conn.addRequestProperty("Authorization", "token "+key);
			
			BufferedReader in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String inputLine;
			while((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			
			JSONArray repo = new JSONArray(response.toString());
			for(int i=0; i<repo.length();i++) {
				GitTable gt = new GitTable();
				JSONObject obj = repo.getJSONObject(i);
				if(obj.getBoolean("private") == false) {
					gt.setRepo(obj.getString("name"));
					gt.setStar(obj.getInt("stargazers_count"));
					gt.setLanguage(obj.getString("language"));
					gt.setUpdated(obj.getString("updated_at"));
					output.add(gt);
				}
				
			}
			
			return output;
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
