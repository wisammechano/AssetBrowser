package com.egps.ict.assetbrowser;

/**
 * Created by ICT on 2/19/2017.
 */

public class Asset {
    private String id, shortDesc, longDesc, location, parentId, type, groupType, sequenceId, area;

    public Asset() {
    }

    public Asset(String id, String parentId) {
        this.id = id;
        this.parentId = parentId;
    }

    public Asset(String id, String shortDesc, String longDesc, String location, String parentId, String type, String groupType, String sequenceId, String area) {
        this.id = id;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.location = location;
        this.parentId = parentId;
        this.type = type;
        this.groupType = groupType;
        this.sequenceId = sequenceId;
        this.area = area;
    }

    public Asset(String id, String parentId, String shortDesc, String longDesc) {
        this.id = id;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
