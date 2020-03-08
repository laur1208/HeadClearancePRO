package ps.room.headclearancefree;

import android.graphics.Bitmap;

public class FastenerDescription {
    private String fastenerType;
    private String fastenerTypeId;
    private String id;
    private String fastenerName;
    private String standard;
    private String standard_detailed;
    private Bitmap image;
    private int isFavorite;

    FastenerDescription(String fastenerType, String fastenerTypeId, String id, String fastenerName, String standard, String standard_detailed, Bitmap image, int isFavorite){
        this.setFastenerType(fastenerType);
        this.setFastenerTypeId(fastenerTypeId);
        this.setId(id);
        this.setFastenerName(fastenerName);
        this.setStandard(standard);
        this.setStandard_detailed(standard_detailed);
        this.setImage(image);
        this.setIsFavorite(isFavorite);
    }

    public String getFastenerType() {
        return fastenerType;
    }

    public void setFastenerType(String fastenerType) {
        this.fastenerType = fastenerType;
    }

    public String getFastenerTypeId() {
        return fastenerTypeId;
    }

    public void setFastenerTypeId(String fastenerTypeId) {
        this.fastenerTypeId = fastenerTypeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFastenerName() {
        return fastenerName;
    }

    public void setFastenerName(String fastenerName) {
        this.fastenerName = fastenerName;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getStandard_detailed() {
        return standard_detailed;
    }

    public void setStandard_detailed(String standard_detailed) {
        this.standard_detailed = standard_detailed;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }
}
