package com.Tru.demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import entity.*;
import queries.*;

@RestController
@ComponentScan("entity")
@EnableMongoRepositories("queries")
public class HomeController{
	
	@Autowired
	private CustomerRepo custRepo;
	@Autowired
	private GitQuery gitquery;
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/api/repo/{name}")
	public ArrayList<GitTable> testapi(@PathVariable("name") String name,@RequestParam(value = "page", required = false) String page, @RequestParam(value = "per_page", required = false) String per_page) {
		GithubAPI ga = new GithubAPI(page,per_page);
		String key = this.gitquery.findByName(name).getKey();
		return ga.getRepo(key);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping(value = "/api/{user}")
	public String getUser(@PathVariable(value="user") String user) {
		GithubAPI ga = new GithubAPI();
		String key = this.gitquery.findByName(user).getKey();
		return "{\"total\":"+ga.getInfo(key)+"}";
	}
	
	@RequestMapping("home")
	public ModelAndView home(Customer custom) {
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("obj", custom);
		mv.setViewName("home");
		return mv;
	}
	
	@RequestMapping("Add")
	public void add(HttpServletRequest req, HttpServletResponse res) {
		
		int i = Integer.valueOf(req.getParameter("i")); 
		int k = Integer.valueOf(req.getParameter("k")); 
		
		try {
			PrintWriter out = res.getWriter();
			out.print(i+k);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
