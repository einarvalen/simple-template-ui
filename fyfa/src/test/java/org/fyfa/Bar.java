package org.fyfa;

import java.util.Date;

public class Bar {
	private int iI;
	private long iL;
	private float iF;
	private double iD;
	private boolean iB;
	private Date date;
	private Date myDateTime;
	private String iS;

	public Bar() {}

	public Bar( int I, long L, float F, double D, boolean B, Date date, Date datetime, String S ) {
		this.iI = I;
		this.iL = L;
		this.iF = F;
		this.iD = D;
		this.iB = B;
		this.date = date;
		this.myDateTime = datetime;
		this.iS = S;
	}

	public int getI() {
		return iI;
	}

	public void setI( int i ) {
		iI = i;
	}

	public long getL() {
		return iL;
	}

	public void setL( long l ) {
		iL = l;
	}

	public float getF() {
		return iF;
	}

	public void setF( float f ) {
		iF = f;
	}

	public double getD() {
		return iD;
	}

	public void setD( double d ) {
		iD = d;
	}

	public Date getDate() {
		return date;
	}

	public void setDate( Date date ) {
		this.date = date;
	}

	public String getS() {
		return iS;
	}

	public void setS( String s ) {
		iS = s;
	}

	public boolean isB() {
		return iB;
	}

	public void setB( boolean b ) {
		iB = b;
	}

	public Date getMyDatetime() {
		return myDateTime;
	}

	public void setMyDatetime(Date datetime) {
		this.myDateTime = datetime;
	}
}
