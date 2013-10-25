package cn.had.mobilesafe.domian;

import android.content.Context;

public class LoseProtectFunction {
private int funcName,funcIcon;
private Class<? extends Context> clz;
public int getFuncName() {
	return funcName;
}
public void setFuncName(int funcName) {
	this.funcName = funcName;
}
public int getFuncIcon() {
	return funcIcon;
}
public void setFuncIcon(int funcIcon) {
	this.funcIcon = funcIcon;
}
public Class<? extends Context> getClz() {
	return clz;
}
public void setClz(Class<? extends Context> clz) {
	this.clz = clz;
}
}
