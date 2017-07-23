package com.liyang.domain.person;

import com.liyang.domain.base.AbstractAuditorLog;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "person_log")
public class PersonLog extends AbstractAuditorLog<Person,PersonAct> {

}
