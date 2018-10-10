package hu.oe.nik.szfmv.common;

import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.model.Classes.Statikus;
import hu.oe.nik.szfmv.model.XMLRead;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class XMLReadTest
{
    List<WorldObject> wordElements;

    @Test
    public void initializeTest()
    {
        wordElements = XMLRead.Read();
        assertNotNull(wordElements);
    }

    @Test
    public void countainsFirstObjectTest()
    {
        wordElements = XMLRead.Read();
        String type = "road_2lane_straight";
        assertEquals(type, (wordElements.get(0)).getImageFileName());
    }

    @Test
    public void countainsLastObjectTest()
    {
        wordElements = XMLRead.Read();
        String type = "roadsign_speed_60";
        assertEquals(type, (wordElements.get(45)).getImageFileName());
    }

}
