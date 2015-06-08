package model;
import java.util.ArrayList;
/**
 * Created by zmhbh on 6/7/15.
 */
public class Section {

    private String sectionName;
    private int sectionId;
    private ArrayList <Paper> paperArrayList;
    private boolean followedFlag;

    public Section(String sectionName, int sectionId){
        this.sectionName=sectionName;
        this.sectionId=sectionId;
        paperArrayList= new ArrayList<Paper>();
        this.followedFlag=false;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public boolean isFollowed() {
        return followedFlag;
    }

    public void setFollowedFlag(boolean followedFlag) {
        this.followedFlag = followedFlag;
    }

    public void addPaper(Paper paper){
        paperArrayList.add(paper);
    }

}
