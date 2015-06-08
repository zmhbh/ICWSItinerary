package model;

/**
 * Created by zmhbh on 6/7/15.
 */

/*
Example:
UaaS: Software Update as a Service for the IaaS Cloud(#8365) (SCC2015-4065)
    Kai Liu (Huazhong University of Science and Technology  China)
    Deqing Zou (Huazhong University of Science and Technology China)
    Hai Jin (Huazhong University of Science and Techn China)
*/

public class Paper {
    private String title; //UaaS: Software Update as a Service for the IaaS Cloud
    private String author; //Kai Liu (Huazhong University of Science and Technology  China)
    //Deqing Zou (Huazhong University of Science and Technology China)
    //Hai Jin (Huazhong University of Science and Techn China)
    private String accessionNumber;   //#8365
    private String presentationTime;  //06/28 Sunday, 14:50-15:50;
    private String presentationPlace;  //3.02/3.03
    private String paperAbstract;
    private String track;              //SCC2015-4065
    private int sectionId;              // parallel section

    public Paper(String title, String author, String accessionNumber
            , String presentationTime, String presentationPlace
            , String paperAbstract, String track, int sectionId) {
        this.title = title;
        this.author = author;
        this.accessionNumber = accessionNumber;
        this.presentationTime = presentationTime;
        this.presentationPlace = presentationPlace;
        this.paperAbstract = paperAbstract;
        this.track = track;
        this.sectionId = sectionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        author = author;
    }

    public String getAccessionNumber() {
        return accessionNumber;
    }

    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public String getPresentationTime() {
        return presentationTime;
    }

    public void setPresentationTime(String presentationTime) {
        this.presentationTime = presentationTime;
    }

    public String getPresentationPlace() {
        return presentationPlace;
    }

    public void setPresentationPlace(String presentationPlace) {
        this.presentationPlace = presentationPlace;
    }

    public String getPaperAbstract() {
        return paperAbstract;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }
}
