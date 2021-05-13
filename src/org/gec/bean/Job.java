package org.gec.bean;

public class Job {
    private Integer id;
    private String name;
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Job() {
        super();
    }

    public Job(Integer id, String name, String remark) {
        super();
        this.id = id;
        this.name = name;
        this.remark = remark;
    }

    public Job(String name, String remark) {
        super();
        this.name = name;
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Job [id=" + id + ", name=" + name + ", remark=" + remark + "]";
    }


}
