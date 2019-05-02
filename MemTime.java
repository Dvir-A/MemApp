package mmn14_2;

import java.io.Serializable;

public class MemTime implements Serializable{
	private int _day;
	private int _month;
	private int _year;
	
	public MemTime(int day,int month,int year) {
		this.set_day(day);
		this.set_month(month);
		this.set_year(year);
	}
	@Override
	public boolean equals(Object obj) {
		if(obj==null) {
			return false;
		}else if(!(obj instanceof MemTime)) {
			return false;
		}else if(this == obj) {
			return true;
		}
		return ((this.hashCode()-((MemTime)obj).hashCode())==0); 
	}
	
	
	@Override
	public int hashCode() {
		return (int) (get_year()+((get_month()+(get_day()*100))*Math.pow(10, 4)));
	}
	
	@Override
	public String toString() {
		return (this._day + "-"+this.get_month()+"-"+this.get_year());
	}
	
	public int get_day() {
		return _day;
	}

	public void set_day(int day) {
		this._day = (day>0) ? day : 1;
	}

	public int get_month() {
		return _month;
	}

	public void set_month(int month) {
		this._month = (month>0 && month<=12) ? month : 1;
	}

	public int get_year() {
		return _year;
	}

	public void set_year(int year) {
		this._year = (year < 1990) ? 1990 : year;
	}
}
