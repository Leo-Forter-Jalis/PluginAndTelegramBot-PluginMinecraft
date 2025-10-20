package com.lfj.plugin.api;

public class MetaData {
    private String name;
    private String author;
    private String botType;
    private int majorVersion; // В будущем буду использовать, чтобы с помощью ByteBuffer проводить проверки конфигов
    private int minorVersion;
    private String mainClass;
    public MetaData(){
    }
    public String getName(){return name;}
    public String getAuthor(){return author;}
    public String getBotType(){return botType;}
    public int getMajorVersion(){return majorVersion;}
    public int getMinorVersion(){return minorVersion;}
    public String getMainClass(){return mainClass;}
    public void setName(String name){this.name = name;}
    public void setAuthor(String author){this.author = author;}
    public void setBotType(String botType){this.botType = botType;}
    public void setMajorVersion(int majorVersion){this.majorVersion = majorVersion;}
    public void setMinorVersion(int minorVersion){this.minorVersion = minorVersion;}
    public void setMainClass(String mainClass){this.mainClass = mainClass;}
}
