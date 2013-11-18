package com.tixa.industry.model;

import java.io.Serializable;

import android.content.Intent;

public class FunItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2471326544407668195L;
	private String text ;
	private  String name;
	private int icon ;
	private Intent intent ;
	private boolean enabled ;
	public ClickLisener listener ;
	private int newCount ;
	private int number ;
	private int rImage ; 
	private boolean isMenu; 
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	private boolean isDivide;
	private String flag ;
	//对应AppConfig中的appTYpe
	private int type;
	private Object object;
	private boolean checked; 
	private int whichToShow;
	private String[] selectArray;
	
	public String[] getSelectArray() {
		return selectArray;
	}
	public void setSelectArray(String[] selectArray) {
		this.selectArray = selectArray;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public boolean isDivide() {
		return isDivide;
	}
	public void setDivide(boolean isDivide) {
		this.isDivide = isDivide;
	}
	public int getNewCount() {
		return newCount;
	}
	public void setNewCount(int newCount) {
		this.newCount = newCount;
	}
	private FunItem fi ;
	public FunItem(String text ,int icon ,ClickLisener listener ,boolean enabled){
		this.text = text ;
		this.icon = icon ;
		this.listener = listener ;
		this.enabled = enabled ;
		fi = this ;
	}
	public FunItem(String text,int icon, ClickLisener listener ,int rImage){
		this.text = text ;
		this.icon = icon ;
		this.listener = listener ;
		this.rImage = rImage ;
		fi = this ;
	}
	public FunItem(){
	}
	public FunItem(String name,String text){
		this.text = text ;
		this.name=name;
	}
	public FunItem(String name,String text,int icon){
		this.icon = icon ;
		this.text = text ;
		this.name=name;
	}
	public FunItem(String name,String text,ClickLisener listener,boolean enabled){
		this.name=name;
		this.text=text;
		this.listener=listener;
		this.enabled=enabled;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public FunItem(String text,ClickLisener clickLisener){
		this.text = text ;
		this.listener = clickLisener ;
	}
	public FunItem(String text ,ClickLisener listener,String flag){
		this.text = text ;
		this.listener = listener ;
		this.flag = flag;
	}
	public FunItem(boolean isDivide){
		this.isDivide = isDivide;
	};
	public ClickLisener getListener() {
		return listener;
	}
	public void setListener(ClickLisener listener) {
		this.listener = listener;
	}
	public interface ClickLisener{
		public abstract void onclick();
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public Intent getIntent() {
		return intent;
	}
	public void setIntent(Intent intent) {
		this.intent = intent;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public void clear(){
		fi = null;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public int getWhichToShow() {
		return whichToShow;
	}
	public void setWhichToShow(int whichToShow) {
		this.whichToShow = whichToShow;
	}
	public int getrImage() {
		return rImage;
	}
	public void setrImage(int rImage) {
		this.rImage = rImage;
	}
	public boolean isMenu() {
		return isMenu;
	}
	public void setMenu(boolean isMenu) {
		this.isMenu = isMenu;
	}
	
	
}
