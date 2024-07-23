package model;

public class Pet {

    Integer petId;
    String petType;
    String petBreed;
    String petName;
    Integer ownerId;



    public Integer getPetId() {
        return petId;
    }
    public void setPetId(Integer petId) {
        this.petId = petId;
    }
    public String getPetType() {
        return petType;
    }
    public void setPetType(String petType) {
        this.petType = petType;
    }
    public String getPetBreed() {
        return petBreed;
    }
    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }
    public String getPetName() {
        return petName;
    }
    public void setPetName(String petName) {
        this.petName = petName;
    }
    public Integer getOwnerId() {
        return ownerId;
    }
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }
    @Override
    public String toString() {
        return "Pet [petId=" + petId + ", petType=" + petType + ", petBreed=" + petBreed + ", petName=" + petName
                + ", ownerId=" + ownerId + "]\n";
    }

    
    
}
