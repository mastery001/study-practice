package guava;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by zouziwen on 2017/4/5.
 */
public class JoinerAndSplitter {

    @Test
    public void joinByDelimiter() {
        List<String> strList = new ArrayList<String>(){};
        strList.add("1");
        strList.add("2");
        String delimiter = ".";
        String sb = joinByDelimiterForStringBuilder(strList , delimiter);
        String joiner = joinByDelimiterForJoiner(strList , delimiter);
        assertEquals(sb,joiner);
        List<String> expectedList = Splitter.on(".").splitToList(joiner);
        assertThat(strList , is(expectedList));
    }

    private String joinByDelimiterForJoiner(List<String> strList, String delimiter) {
        return Joiner.on(delimiter).skipNulls().join(strList);
    }

    private String joinByDelimiterForStringBuilder(List<String> strList, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for(String str : strList) {
            sb.append(str).append(delimiter);
        }
        sb.setLength(sb.length() - delimiter.length());
        return sb.toString();
    }

    @Test
    public void testMapJoiner() {
        //Using LinkedHashMap so that the original order is preserved
        String expectedString = "Washington D.C=Redskins#New York City=Giants#Philadelphia=Eagles#Dallas=Cowboys";
        Map<String,String> testMap = Maps.newLinkedHashMap();
        testMap.put("Washington D.C","Redskins");
        testMap.put("New York City","Giants");
        testMap.put("Philadelphia","Eagles");
        testMap.put("Dallas","Cowboys");
        String returnedString = Joiner.on("#").
                withKeyValueSeparator("=").join(testMap);
        assertThat(returnedString,is(expectedString));

        // use Splitter to split expectedString
        Splitter.MapSplitter mapSplitter =  Splitter.on("#").withKeyValueSeparator("=");
        Map<String , String> expectedMap = mapSplitter.split(expectedString);
        assertThat(testMap , is(expectedMap));
    }

}
