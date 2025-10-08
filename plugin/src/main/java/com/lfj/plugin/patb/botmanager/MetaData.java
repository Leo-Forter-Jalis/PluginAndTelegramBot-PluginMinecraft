package com.lfj.plugin.patb.botmanager;

public class MetaData {
    private String name;
    private String author;
    private String version;
    private String mainClass;
    public MetaData(){
    }
    public String getName(){return name;}
    public String getAuthor(){return author;}
    public String getVersion(){return version;}
    public String getMainClass(){return mainClass;}
    public void setName(String name){this.name = name;}
    public void setAuthor(String author){this.author = author;}
    public void setVersion(String version){this.version = version;}
    public void setMainClass(String mainClass){this.mainClass = mainClass;}
}
