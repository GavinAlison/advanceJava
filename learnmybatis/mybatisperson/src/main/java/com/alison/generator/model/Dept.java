package com.alison.generator.model;

public class Dept {
    private Integer id;

    private String deptno;

    private String deptname;

    public Dept(Integer id, String deptno, String deptname) {
        this.id = id;
        this.deptno = deptno;
        this.deptname = deptname;
    }

    public Dept() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno == null ? null : deptno.trim();
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname == null ? null : deptname.trim();
    }
}