package com.example.quartzdemo.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SysScheduleJob implements Serializable {
    private static final long serialVersionUID = 1L;

	public static final String SYS_SCHEDULE_JOB_KEY = "SYS_SCHEDULE_JOB_KEY";

	public static final String SYS_SCHEDULE_JOB_EXECUTOR = "SYS_SCHEDULE_EXECUTOR";

	/**
	 * 執行頻率的單位
	 */
	public static enum FreqUnit {
		HOUR("sysScheduleJob.hour"), DAY("sysScheduleJob.day"), WEEK("sysScheduleJob.week"), MONTH(
				"sysScheduleJob.month"),MINUTES("sysScheduleJob.minutes");
		private String i18n;

		private FreqUnit(String i18n) {
			this.i18n = i18n;
		}

		public String getI18n() {
			return this.i18n;
		}
	}

	/**
	 * 任務群組
	 */
	public static enum JobGroup {
		BACKGROUND("sysScheduleJob.group.background");
		private String i18n;

		private JobGroup(String i18n) {
			this.i18n = i18n;
		}

		public String getI18n() {
			return this.i18n;
		}

		public String getId() {
			return this.toString();
		}
	}

	/**
	 * 任務id
	 */
	private Integer id;

	/**
	 * 任務名稱
	 */
	private String name;

	/**
	 * 任務分組
	 */
	private String group;

	/**
	 * 記錄關於該 Job 的相關資訊, 存成 Json String 如：一樣是 Parse Log 但可能需要寫到不同的 Table
	 */
	private String jobData;

	/**
	 * 實際運行 Job 的 Class Name 給予全路徑 - Package
	 */
	private String jobClass;

	/**
	 * 首次執行時間
	 */
	private LocalDateTime firstExecTime;

	/**
	 * 任務狀態 0 - 停用 , 1 - 啟用
	 */
	private Boolean enable;

	/**
	 * 任務運行時間表達式
	 */
	private String cronExpression;

	/**
	 * 更新人員 - 中國信託員工編號
	 */
	private String updatedBy;

	/**
	 * 更新時間
	 */
	private LocalDateTime updatedAt;

	/**
	 * 任務描述
	 */
	private String desc;

	/**
	 * 是否為一次性執行
	 */
	private Boolean isOnce;

	/**
	 * 多久執行一次
	 */
	private Integer freqNum;

	/**
	 * 多久執行一次， 每秒、時、天
	 */
	private String freqUnit;

	/**
	 * 下次執行時間(Quartz)
	 */
	private LocalDateTime nextFireTime;

	private LocalDateTime lastFireTime;

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getFirstExecTime() {
		return firstExecTime;
	}

	public void setFirstExecTime(LocalDateTime firstExecTime) {
		this.firstExecTime = firstExecTime;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getJobData() {
		return jobData;
	}

	public void setJobData(String jobData) {
		this.jobData = jobData;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public Boolean getIsOnce() {
		return isOnce;
	}

	public void setIsOnce(Boolean isOnce) {
		this.isOnce = isOnce;
	}

	public Integer getFreqNum() {
		return freqNum;
	}

	public void setFreqNum(Integer freqNum) {
		this.freqNum = freqNum;
	}

	public String getFreqUnit() {
		return freqUnit;
	}

	public void setFreqUnit(String freqUnit) {
		this.freqUnit = freqUnit;
	}


	public LocalDateTime getNextFireTime() {
		return this.nextFireTime;
	}

	public void setNextFireTime(LocalDateTime nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	public LocalDateTime getLastFireTime() {
		return lastFireTime;
	}

	public void setLastFireTime(LocalDateTime lastFireTime) {
		this.lastFireTime = lastFireTime;
	}
}
