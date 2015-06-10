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
    private String author; //Kai Liu, Deqing Zou, Hai Jin
    private String uniqueID;   //SCC2015-4065
    private String paperAbstract;   //
    private String simplifiedAffiliation; //Huazhong University of Science and Technology  China
    private String authorAffiliation;//Kai Liu (Huazhong University of Science and Technology  China)
                                        //Deqing Zou (Huazhong University of Science and Technology China)
                                            //Hai Jin (Huazhong University of Science and Techn China)


    public Paper(String title, String author, String simplifiedAffiliation){
        this.title=title;
        this.author=author;
        this.simplifiedAffiliation=simplifiedAffiliation;
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

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getPaperAbstract() {
        return paperAbstract;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract;
    }

    public String getSimplifiedAffiliation() {
        return simplifiedAffiliation;
    }

    public void setSimplifiedAffiliation(String simplifiedAffiliation) {
        this.simplifiedAffiliation = simplifiedAffiliation;
    }

    public String getAuthorAffiliation() {
        return authorAffiliation;
    }

    public void setAuthorAffiliation(String authorAffiliation) {
        this.authorAffiliation = authorAffiliation;
    }
}
