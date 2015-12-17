package io.picsou.date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DateTest {

	@Test
	public void test(){
		DateTime d = DateTime.now();
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/YYYY");
		String s=dtf.print(d);
		System.out.println(s);
	}
}
