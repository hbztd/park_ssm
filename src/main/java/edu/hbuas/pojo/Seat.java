package edu.hbuas.pojo;

import java.io.Serializable;

public class Seat implements Serializable {
    private String seatId;

    private String seatNum;

    private String seatSection;

    private Integer seatState;

    private String seatTag;

    private Integer seatType;

    private static final long serialVersionUID = 1L;

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public String getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(String seatNum) {
        this.seatNum = seatNum;
    }

    public String getSeatSection() {
        return seatSection;
    }

    public void setSeatSection(String seatSection) {
        this.seatSection = seatSection;
    }

    public Integer getSeatState() {
        return seatState;
    }

    public void setSeatState(Integer seatState) {
        this.seatState = seatState;
    }

    public String getSeatTag() {
        return seatTag;
    }

    public void setSeatTag(String seatTag) {
        this.seatTag = seatTag;
    }

    public Integer getSeatType() {
        return seatType;
    }

    public void setSeatType(Integer seatType) {
        this.seatType = seatType;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Seat other = (Seat) that;
        return (this.getSeatId() == null ? other.getSeatId() == null : this.getSeatId().equals(other.getSeatId()))
            && (this.getSeatNum() == null ? other.getSeatNum() == null : this.getSeatNum().equals(other.getSeatNum()))
            && (this.getSeatSection() == null ? other.getSeatSection() == null : this.getSeatSection().equals(other.getSeatSection()))
            && (this.getSeatState() == null ? other.getSeatState() == null : this.getSeatState().equals(other.getSeatState()))
            && (this.getSeatTag() == null ? other.getSeatTag() == null : this.getSeatTag().equals(other.getSeatTag()))
            && (this.getSeatType() == null ? other.getSeatType() == null : this.getSeatType().equals(other.getSeatType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSeatId() == null) ? 0 : getSeatId().hashCode());
        result = prime * result + ((getSeatNum() == null) ? 0 : getSeatNum().hashCode());
        result = prime * result + ((getSeatSection() == null) ? 0 : getSeatSection().hashCode());
        result = prime * result + ((getSeatState() == null) ? 0 : getSeatState().hashCode());
        result = prime * result + ((getSeatTag() == null) ? 0 : getSeatTag().hashCode());
        result = prime * result + ((getSeatType() == null) ? 0 : getSeatType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", seatId=").append(seatId);
        sb.append(", seatNum=").append(seatNum);
        sb.append(", seatSection=").append(seatSection);
        sb.append(", seatState=").append(seatState);
        sb.append(", seatTag=").append(seatTag);
        sb.append(", seatType=").append(seatType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}