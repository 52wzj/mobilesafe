package cn.had.mobilesafe.domian;

import java.io.Serializable;

public class UpdateInfo implements Serializable{
private String version;
private String updateDescription;
private String apkUrl;
public String getVersion() {
	return version;
}
public void setVersion(String version) {
	this.version = version;
}
public String getUpdateDescription() {
	return updateDescription;
}
public void setUpdateDescription(String updateDescription) {
	this.updateDescription = updateDescription;
}
public String getApkUrl() {
	return apkUrl;
}
public void setApkUrl(String apkUrl) {
	this.apkUrl = apkUrl;
}
}
