/** 
 * ref
 * Reference class
 * It holds variables and assessors for each reference object 
 * @author CS 149 group 3
 *
 */
public class Reference {
	private int uses;
	private boolean referenced;
	private int refNum;
	private int age;
	
	public Reference(int refNum){
		uses=0;
		setReferenced(false);
		this.setRefNum(refNum);
		age=0;
	}

	public int getUses() {
		return uses;
	}

	public void incUses() {
		uses ++;
	}

	public boolean isReferenced() {
		return referenced;
	}

	public void setReferenced(boolean referenced) {
		this.referenced = referenced;
	}

	public int getAge() {
		return age;
	}

	public void resetAge() {
		age=0;
	}
	
	public void incAge() {
		age++;
	}
	
	public boolean equals(Object obj){
		if (obj == this) { return true; } 
		if (obj == null || obj.getClass() != this.getClass()) { return false; }
		Reference x= (Reference) obj; 
		return getRefNum() == x.getRefNum(); 
	
	}

	public int hashCode() { 
	final int prime = 31; 
	int result = 1;
	result = prime * result + getRefNum(); 
	return result;
	}
	
	public int getRefNum() {
		return refNum;
	}

	public void setRefNum(int refNum) {
		this.refNum = refNum;
	}
	
	public String toString(){
		return  " Num:"+getRefNum()+ " AGE:"+getAge()+ " Uses:"+ getUses()+"  "  ;
		
	}
}
