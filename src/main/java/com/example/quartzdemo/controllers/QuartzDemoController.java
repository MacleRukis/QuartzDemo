package com.example.quartzdemo.controllers;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.quartzdemo.services.ScheduleJobService;

@Controller
public class QuartzDemoController {
	
	@Autowired
	private ScheduleJobService scheduleJobSerivce;
	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@RequestMapping("/activeJob/{flag}")
	public String activeJob(@PathVariable("flag") String flag) throws SchedulerException {
		if (flag.equals("true")) {
			scheduleJobSerivce.resumeJob();
		} else if (flag.equals("false")) {
			scheduleJobSerivce.pauseJob();
		}
		return "index";
	}
	
	@RequestMapping("/execute/{job}")
	public String execute(@PathVariable("job") String job) {
		System.out.println(job);
		scheduleJobSerivce.executeJob(job + "Schedule");
		return "index";
	}
	
	@RequestMapping("/pause/{job}")
	public String pause(@PathVariable("job") String job) throws SchedulerException {
		scheduleJobSerivce.pauseJob(job + "Schedule");
		return "index";
	}
	
	@RequestMapping("/resume/{job}")
	public String resume(@PathVariable("job") String job) throws SchedulerException {
		scheduleJobSerivce.resumeJob(job + "Schedule");
		return "index";
	}
}
