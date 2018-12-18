package edu.hbuas.vo;

public class FindView {
    private String type;
    private String value;
    private String sortName;
    private String sortWay;
    private Integer start;
    private Integer size;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortWay() {
        return sortWay;
    }

    public void setSortWay(String sortWay) {
        this.sortWay = sortWay;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "FindView{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", sortName='" + sortName + '\'' +
                ", sortWay='" + sortWay + '\'' +
                ", start=" + start +
                ", size=" + size +
                '}';
    }
}
