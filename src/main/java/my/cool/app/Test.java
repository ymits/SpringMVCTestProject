package my.cool.app;

import java.util.Date;

public class Test {
	private int valueInt = 1;
	private boolean valueBoolean = true;
	private Date valueDate = new Date();
	private TestEnum valueEnum = TestEnum.VALUE2;
	public int getValueInt() {
		return valueInt;
	}
	public void setValueInt(int valueInt) {
		this.valueInt = valueInt;
	}
	public boolean isValueBoolean() {
		return valueBoolean;
	}
	public void setValueBoolean(boolean valueBoolean) {
		this.valueBoolean = valueBoolean;
	}
	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	public TestEnum getValueEnum() {
		return valueEnum;
	}
	public void setValueEnum(TestEnum valueEnum) {
		this.valueEnum = valueEnum;
	}
	@Override
	public String toString() {
		return "Test [valueInt = "+valueInt+", valueBoolean = " + valueBoolean + ", valueDate = " + valueDate +", valueEnum = " + valueEnum + "]";
	}
	
}
