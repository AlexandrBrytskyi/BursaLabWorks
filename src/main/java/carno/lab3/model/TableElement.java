package carno.lab3.model;


public class TableElement {

    private String canBe;
    private String desc;

    public TableElement(String canBe, String desc) {
        this.canBe = canBe;
        this.desc = desc;
    }


    public String isCanBe() {
        return canBe;
    }

    public void setCanBe(String canBe) {
        this.canBe = canBe;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "TableElement{" +
                ", canBe=" + canBe +
                ", desc='" + desc + '\'' +
                '}';
    }
}
