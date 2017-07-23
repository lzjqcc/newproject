package com.liyang.domain.asset;

import com.liyang.domain.base.AbstractAuditorLog;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "asset_log")
public class AssetLog extends AbstractAuditorLog<Asset,AssetAct> {

}
