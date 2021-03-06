package lgq.action;


import org.hibernate.Query;
import org.hibernate.Session;

import lgq.model.Paper;

import com.opensymphony.xwork2.ActionSupport;

public class UpdatePaperAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private int articleId;
	private String title;
	private String keyWord;
	private String type1;
	private String type2;
	private int authorNum;
	private String author1;
	private String author2;
	private String author3;
	private String author4;
	private double score;
	private int year;
	private int month;
	private String name;
	private String sci;
	private String ei;
	private String istp;
	private String effect;
	private int phase;
	private int volume;
	private int beginPage;
	private int endPage;
	private int num;
	private String location;
	private String otherInfo;
	
	public int getPhase() {
		return phase;
	}
	public void setPhase(int phase) {
		this.phase = phase;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public int getBeginPage() {
		return beginPage;
	}
	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getAuthorNum() {
		return authorNum;
	}
	public void setAuthorNum(int authorNum) {
		this.authorNum = authorNum;
	}
	public String getAuthor1() {
		return author1;
	}
	public void setAuthor1(String author1) {
		this.author1 = author1;
	}
	public String getAuthor2() {
		return author2;
	}
	public void setAuthor2(String author2) {
		this.author2 = author2;
	}
	public String getAuthor3() {
		return author3;
	}
	public void setAuthor3(String author3) {
		this.author3 = author3;
	}
	public String getAuthor4() {
		return author4;
	}
	public void setAuthor4(String author4) {
		this.author4 = author4;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSci() {
		return sci;
	}
	public void setSci(String sci) {
		this.sci = sci;
	}
	public String getEi() {
		return ei;
	}
	public void setEi(String ei) {
		this.ei = ei;
	}
	public String getIstp() {
		return istp;
	}
	public void setIstp(String istp) {
		this.istp = istp;
	}
	public String getEffect() {
		return effect;
	}
	public void setEffect(String effect) {
		this.effect = effect;
	}
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getType2() {
		return type2;
	}
	public void setType2(String type2) {
		this.type2 = type2;
	}
	public String execute()throws Exception{
		Session session=null;
		Paper paper=null;
		try{
			session=HibernateSessionFactory.getSession();
			session.beginTransaction();
			String hql="from Paper p where p.articleId=\'"+articleId+"\'";
			Query query=session.createQuery(hql);
			paper=(Paper) query.list().get(0);
			session.getTransaction().commit();
		}catch(Exception e)
		{
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		otherInfo="";
		score=0;
		switch(type1)
		{
		case "期刊论文":{
			switch(type2)
			{
			case "EI索引论文":score+=10;break;
			case "非SCI/EI索引国外期刊":score+=8;break;
			case "非SCI/EI索引国内一级期刊":score+=8;break;
			case "非SCI/EI索引国内核心期刊":score+=2;break;
			case "非SCI/EI索引国内一般期刊":score+=1;break;
			}
			switch(effect)
			{
			case "level1":score+=80;break;
			case "level2":score+=50;break;
			case "level3":score+=20;break;
			case "levle4":score+=10;break;
			case "level5":score+=0;break;
			}
			otherInfo=phase+"{"+volume+"},"+beginPage+"-"+endPage;
		}break;
		case "会议论文":{
			switch(type2)
			{
			case "国际CCF A类会议":score+=30;break;
			case "国际CCF B类会议":score+=15;break;
			case "国际CCF C类会议":score+=5;break;
			case "国际其它类会议":score+=2;break;
			case "国内会议":score+=1;break;
			}
			switch(effect)
			{
			case "levle1":score+=80;break;
			case "level2":score+=50;break;
			case "level3":score+=20;break;
			case "levle4":score+=10;break;
			case "level5":score+=0;break;
			}
			otherInfo=num+","+location;
		}break;
		}
		String author="";
		switch(authorNum)
		{
		case 1:author=author1;break;
		case 2:author=author1+","+author2;break;
		case 3:author=author1+","+author2+","+author3;break;
		case 4:author=author1+","+author2+","+author3+","+author4;break;
		}
		Session session1=null;
		try{
			session1=HibernateSessionFactory.getSession();
			session1.beginTransaction();
			paper=(Paper)session1.load(Paper.class,new Integer(articleId));
			paper.setAuthor(author);
			paper.setScore(score);
			paper.setOtherInfo(otherInfo);
			paper.setAuthorNum(authorNum);
			paper.setEffect(effect);
			paper.setEi(ei);
			paper.setSci(sci);
			paper.setIstp(istp);
			paper.setKeyWord(keyWord);
			paper.setMonth(month);
			paper.setYear(year);
			paper.setName(name);
			paper.setTitle(title);
			paper.setType1(type1);
			paper.setType2(type2);
			session1.getTransaction().commit();
		}catch(Exception e)
		{
			e.printStackTrace();
			session1.getTransaction().rollback();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return SUCCESS;
	}
}
