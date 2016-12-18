package com.app.test.entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import com.sun.javafx.beans.IDProperty;

import javax.persistence.*;

/**
 * Created by swathy.iyer on 18/12/16.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
@Table(name="Users")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String designation;

    @Column(nullable = false)
    private int empId;

    @Column(nullable = false)
    private String dept;

    public User(String name,String designation,int empId,String dept)
    {
        super();
        this.setName(name);
        this.setDesignation(designation);
        this.setEmpId(empId);
        this.setDept(dept);

    }

}
