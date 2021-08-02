package nithra.word.game.onepiconeword;

public class Item_noti {
	private int Id;
	private int isclose;
	private int ismark;
	private String title;
	private String message;
	private String msgType;
	private String bm;
	private String urll;
	private String ntype;

	public Item_noti() {
	}

	public Item_noti(int Id, String title, String message, String msgType, int isclose, String bm, String urll, String ntype, int ismark) {

		this.Id = Id;
		this.title = title;
		this.message = message;
		this.msgType = msgType;
		this.isclose = isclose;
		this.bm = bm;
		this.urll = urll;
		this.ntype = ntype;
		this.ismark = ismark;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public String getmessage() {
		return message;
	}

	public void setmessage(String message) {
		this.message = message;
	}
	public String getmsgType() {
		return msgType;
	}

	public void setmsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getbm() {
		return bm;
	}

	public void setbm(String bm) {
		this.bm = bm;
	}
	public String geturll() {
		return urll;
	}

	public void seturll(String urll) {
		this.urll = urll;
	}
	public String getntype() {
		return ntype;
	}

	public void setntype(String ntype) {
		this.ntype = ntype;
	}


	public int getId() {
		return Id;
	}

	public void setId(int year) {
		this.Id = year;
	}

	public int getisclose() {
		return isclose;
	}

	public void setisclose(int isclose) {
		this.isclose = isclose;
	}

	public int getismark() {
		return ismark;
	}

	public void setismark(int ismark) {
		this.ismark = ismark;
	}



}
