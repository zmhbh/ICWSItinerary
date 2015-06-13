package model;

import java.io.Serializable;

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

public class Paper implements Serializable {
    private int _id;
    private String uniqueID;   //SCC2015-4065
    private String title; //UaaS: Software Update as a Service for the IaaS Cloud
    private String author; //Kai Liu, Deqing Zou, Hai Jin
    private String affiliation; //Huazhong University of Science and Technology  China
    private String authorWithAffiliation;//Kai Liu (Huazhong University of Science and Technology  China)
    //Deqing Zou (Huazhong University of Science and Technology China)
    //Hai Jin (Huazhong University of Science and Techn China)
    private String paperAbstract;   //

    public Paper(int _id, String uniqueID, String title, String author,
                 String affiliation, String authorWithAffiliation, String paperAbstract) {
        this._id = _id;
        this.uniqueID = uniqueID;
        this.title = title;
        this.author = author;
        this.affiliation = affiliation;
        this.authorWithAffiliation = authorWithAffiliation;
        this.paperAbstract = paperAbstract;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
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
        this.author = author;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getAuthorWithAffiliation() {
        return authorWithAffiliation;
    }

    public void setAuthorWithAffiliation(String authorWithAffiliation) {
        this.authorWithAffiliation = authorWithAffiliation;
    }

    public String getPaperAbstract() {
        return paperAbstract;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract;
    }
}
