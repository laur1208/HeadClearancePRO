package ps.room.headclearancefree;

import android.graphics.Bitmap;

public class FastenerSizes {
    private String fastenerType;
    private int id;
    private String fastenerName;
    private Bitmap image;
    private String mainSize;
    private double aMinSize;
    private double aSize;
    private double aMaxSize;
    private double bSize;
    private double cSize;
    private double dSize;

    FastenerSizes(String fastenerType, int id, String fastenerName, Bitmap image, String mainSize, double aMinSize, double aSize, double aMaxSize, double bSize, double cSize, double dSize){
        this.setFastenerType(fastenerType);
        this.setId(id);
        this.setFastenerName(fastenerName);
        this.setImage(image);
        this.setMainSize(mainSize);
        this.setaMinSize(aMinSize);
        this.setaSize(aSize);
        this.setaMaxSize(aMaxSize);
        this.setbSize(bSize);
        this.setcSize(cSize);
        this.setdSize(dSize);
    }

    public String getFastenerType() {
        return fastenerType;
    }

    public void setFastenerType(String fastenerType) {
        this.fastenerType = fastenerType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFastenerName() {
        return fastenerName;
    }

    public void setFastenerName(String fastenerName) {
        this.fastenerName = fastenerName;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getMainSize() {
        return mainSize;
    }

    public void setMainSize(String mainSize) {
        this.mainSize = mainSize;
    }

    public double getaMinSize() {
        return aMinSize;
    }

    public void setaMinSize(double aMinSize) {
        this.aMinSize = aMinSize;
    }

    public double getaSize() {
        return aSize;
    }

    public void setaSize(double aSize) {
        this.aSize = aSize;
    }

    public double getaMaxSize() {
        return aMaxSize;
    }

    public void setaMaxSize(double aMaxSize) {
        this.aMaxSize = aMaxSize;
    }

    public double getbSize() {
        return bSize;
    }

    public void setbSize(double bSize) {
        this.bSize = bSize;
    }

    public double getcSize() {
        return cSize;
    }

    public void setcSize(double cSize) {
        this.cSize = cSize;
    }

    public double getdSize() {
        return dSize;
    }

    public void setdSize(double dSize) {
        this.dSize = dSize;
    }
}
